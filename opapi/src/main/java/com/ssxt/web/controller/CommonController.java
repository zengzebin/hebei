package com.ssxt.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.alibaba.fastjson.JSONArray;
import com.ssxt.common.enums.ActionMsgParam;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.OpParam;
import com.ssxt.common.util.StatusCode;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.BasParam;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.ParamService;

/**
 * 
 * <b>类名称：</b>CodeFunctionController<br/>
 * <b>类描述：</b>设备管理管理Controller类<br/>
 * 
 * <b>创建人：</b>杨培新<br/>
 * 
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2015-12-20 22:07:04<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 * 
 */
@Controller
@RequestMapping(value = "")
public class CommonController extends SpringBaseController<BasParam, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CommonController.class);
	private static final String ENTITY_NAME = "功能";

	@Autowired
	ParamService service;

	/**
	 * 查询所有下拉(下拉菜单)
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/OptionSelect/{entity}", method = RequestMethod.GET)
	public void listoptions(@PathVariable(value = "entity") String entity,
			@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		AccData ad = CtxHelper.createFailAccData();
		try {
			log.info("----------OptionSel:{}", entity);
			SqlBuffer sbf = SqlBuffer.begin();
			PageControl pageControl = PageControl.getQueryOnlyInstance();
			String order = "";
			if (!DataUtil.isNull(sort)) {
				order = "order by " + sort + " ";
				if (!DataUtil.isNull(dir) && "desc".equalsIgnoreCase(dir))
					order = order + " " + dir;
				pageControl.setOrder(order);
			} // else pageControl.setOrder("order by id desc");
				// 查询条件组装
			sbf.addLike("name", searchtext);

			String keyName = "", keyAsName = "trueValue", showName = "", showAsName = "showValue", table = "";
			boolean dFlag = false;
			List list = new ArrayList();
			Map<String, String> dMap = new HashMap<String, String>();

			if (DataUtil.isNull(entity)) {
				DataUtil.showMsgException("entity不能为空");
			}
			if (entity.equals("serviceType")) {
				SqlBuffer buf = SqlBuffer.begin();
				String sql = "select codeHex,typeName  from  bas_station_type";
				List list1 = service.findByNativeCondition(PageControl.getQueryOnlyInstance(), buf.end(), sql,
						Map.class);
				list.addAll(list1);
			}
			if (entity.equals("statusCode")) {
				// SymRole role = CtxHelper.getSessionUserModule(request);
				List seelct = StatusCode.getSelct();
				// if (role.getIsOperation() == 1) {
				// seelct.remove(0);
				// seelct.remove(1);
				// }

				list.addAll(seelct);
			}
			if (entity.equals("codeFunction")) {
				list = service.findByNativeCondition(pageControl, sbf.end(),
						"select code,name from bas_cfg_code_function", Map.class);
			}
			if (entity.equals("warehouse")) {
				list = service.findByNativeCondition(pageControl, sbf.end(), "select  * from bas_warehouse", Map.class);
			}
			if (entity.equals("deviceType")) {
				list = service.findByNativeCondition(pageControl, sbf.end(), "select  * from bas_device_type",
						Map.class);
			}

			// if (entity.equals("priority")) {
			// list.addAll(DataUtil.convert2KVOptionList(OpParam.priorityMap));
			// }

			if (list.size() > 1) {

				ad.setData(list);
				ad.setTotalCount(pageControl.getRowCount());
				ad.setMsg(ActionMsgParam.ACM_LIS_SUC);
				ad.setSuccess(true);
			} else {
				if (!DataUtil.isNull(entity)) {

					table = "bas_param";
					keyName = "argValue";
					showName = "argName";
					String type = entity;
					if (DataUtil.isNull(type))
						type = "unknown";
					sbf.add("paramType", type);
					pageControl.setOrder("order by sort asc");
				}

				String preWhereSql = String.format("select %s as %s ,%s as %s  from %s", keyName, keyAsName, showName,
						showAsName, table);

				List<Map> dataList = service.findByNativeCondition(pageControl, sbf.end(), preWhereSql,
						java.util.Map.class);
				if (dFlag) {
					dataList.add(dMap);
				}
				log.debug("OptSel: {}", sbf.end());
				// List<SelectData> dataList = service.findByNativeCondition(
				// pageControl, conList, preWhereSql, SelectData.class);
				ad.setData(dataList);
				ad.setTotalCount(pageControl.getRowCount());
				ad.setMsg(ActionMsgParam.ACM_LIS_SUC);
				ad.setSuccess(true);

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setData(e.toString());
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad, request.getParameter("callback"));
	}

	@Override
	public boolean getCommentByHbm() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericServiceImpl<BasParam, Integer> getService() {
		// TODO Auto-generated method stub
		return service;
	}

}
