package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssxt.common.enums.ActionMsgParam;
import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.json.JsonHelper;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.BasOwnerUnits;
import com.ssxt.web.service.OwnerUnitsService;

/**
 * 
 * <b>类名称：</b>OwnerUnitsController<br/>
 * <b>类描述：</b>归属单位管理OwnerUnitsController类<br/>
 * 
 * <b>创建人：</b>曾泽斌<br/>
 * 
 * <b>修改人：</b>曾泽斌<br/>
 * 
 * <b>修改时间：</b>2015-12-20 22:07:04<br/>
 * <b>修改备注：</b><br/>
 * 
 * @version 1.0.0<br/>
 * 
 */

@Controller
@RequestMapping(value = "/api/ownerUnits")
public class OwnerUnitsController extends SpringBaseController<BasOwnerUnits, String> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OwnerUnitsController.class);
	private static final String tableNameCn = "归属单位信息";
	private static final String sql = "";

	@Resource
	private OwnerUnitsService ownerUnitsService;

	/**
	 * @since 1.0.0
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * 功能新增
	 * 
	 * @param name
	 * @param pass
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@Comment("新增")
	public void add(@ModelAttribute("bean") BasOwnerUnits bean, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		AccData ad = CtxHelper.createFailAccData();

		Assert.notNull(bean, "参数不能为空");

		BasOwnerUnits isDevice = ownerUnitsService.get(bean.getOwnerNo());
		if (isDevice == null) {
			bean.setLockFlag(0);
//			bean.setCreateId(MDCUtil.getUserId());
			bean.setCreateName(CtxHelper.getCurUserName(request, ""));
			bean.setCreateTime(new java.util.Date());
			ownerUnitsService.saveDomain(tableNameCn + "新增", bean);
			ad.setMsg(ActionMsgParam.ACM_SAV_SUC);
			ad.setSuccess(true);
		} else {
			ad.setMsg("该单位已经存在");
			ad.setSuccess(false);
		}

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	/**
	 * 功能码定义表删除
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 *             void
	 * @exception @since
	 *                1.0.0
	 */
	@Comment("删除")
	@RequestMapping(value = "/delete/{keyNo}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String keyNo, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		AccData ad = CtxHelper.createFailAccData();
		try {
			Assert.notNull(keyNo, "编号不能为空");
			// System.out.println(5/0);

			BasOwnerUnits old = ownerUnitsService.get(keyNo);

			int oldFlag = DataUtil.notNullInt(old.getLockFlag(), -1);
			if (oldFlag == 1) {
				DataUtil.showMsgException("该项目已锁定,不能删除");
			}
			// BasSoftInstallInfo
			SqlBuffer sbf_relative = SqlBuffer.begin() // 创建查询条件缓冲区
					.add("ownerId", keyNo, "=", "and"); // =查询;
			PageControl pg = null;

			ownerUnitsService.delete(old);

			ad.setMsg(ActionMsgParam.ACM_DEL_SUC);
			ad.setSuccess(true);
			// log.debug("{}删除设备号:{}",tableNameCn,keyNo);
		} catch (RuntimeException e) {
			log.info(tableNameCn + "删除有误！", e);
			ad.setData(e.toString());
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setSuccess(false);

		} catch (Exception e) {
			log.error(tableNameCn + "删除失败！", e);
			ad.setData(e.toString());
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setSuccess(false);

		}
		CtxHelper.writeToJson(response, ad);
	}

	/**
	 * 功能码定义表删除
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @throws IOException
	 *             void
	 * @exception @since
	 *                1.0.0
	 */
	@Comment("锁定")
	@RequestMapping(value = "/lock/{keyNo}", method = RequestMethod.POST)
	public void lock(@PathVariable String keyNo, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		AccData ad = CtxHelper.createFailAccData();
		try {
			Assert.notNull(keyNo, "编号不能为空");
			// System.out.println(5/0);
			BasOwnerUnits old = ownerUnitsService.get(keyNo);

			int oldFlag = DataUtil.notNullInt(old.getLockFlag(), -1);
			if (oldFlag > 0) {
				DataUtil.showMsgException("该项目已经锁定");
			}
//			if (CtxHelper.getCurUserId(request) != old.getCreateId()) {
//				DataUtil.showMsgException("您不是该项目管理员，无法进行此操作");
//			}
			old.setLockFlag(1);
			ad.setMsg(ActionMsgParam.ACM_OP_SUC);
			ad.setSuccess(true);
			ownerUnitsService.updateDomain(tableNameCn + "进行锁定", old, old);
			// log.debug("{}删除设备号:{}",tableNameCn,keyNo);
		} catch (RuntimeException e) {
			log.info(tableNameCn + "锁定有误！", e);
			ad.setData(e.toString());
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setSuccess(false);

		} catch (Exception e) {
			log.error(tableNameCn + "锁定失败！", e);
			ad.setData(e.toString());
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setSuccess(false);

		}
		CtxHelper.writeToJson(response, ad);
	}

	/**
	 * 功能码定义表修改
	 * 
	 * @param id
	 *            void
	 * @exception @since
	 *                1.0.0
	 */
	@RequestMapping(value = "/update/{keyNo}", method = RequestMethod.PUT)
	@Comment("修改")
	public void modify(@PathVariable String keyNo, @ModelAttribute("bean") BasOwnerUnits bean,
			HttpServletRequest request, HttpServletResponse response) throws IOException {

		AccData ad = CtxHelper.createFailAccData();

		try {
			String info = "修改数据存在:{}";
			log.debug(info, bean);

			Assert.notNull(keyNo, "编号不能为空");
			Assert.notNull(bean, "参数不能为空");

			BasOwnerUnits old = ownerUnitsService.get(keyNo);
			if (old == null) {
				showMsgException("找不到数据");

			}

			int oldFlag = DataUtil.notNullInt(old.getLockFlag(), -1);
			if (oldFlag == 1) {
				DataUtil.showMsgException("该项目已锁定,不能修改");
			}

			/*
			 * BeanUtils.copyProperties(old, bean); deviceService.update(bean);
			 */
			ownerUnitsService.updateDomain(tableNameCn + "修改", old, bean);
			ad.setMsg(ActionMsgParam.ACM_MOD_SUC);
			ad.setSuccess(true);

		} catch (RuntimeException e) {
			e.printStackTrace();
			log.info(tableNameCn + "修改有误！", e);
			ad.setData(e.toString());
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(tableNameCn + "修改失败！", e);
			ad.setData(e.toString());
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setSuccess(false);
		}
		System.out.println("OwnerUnitsController.modify() " + keyNo + "修改结束");
		CtxHelper.writeToJson(response, ad);

	}

	@Comment("加载数据")
	@RequestMapping(value = "load/{id}", method = RequestMethod.GET)
	public void info(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.load(id, request, response);
	}

	// 查询
	@Comment("查询")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(

	@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		AccData ad = CtxHelper.createFailAccData();
		try {
			String info = "查找数据{}:{}-[{}]";
			log.debug(info, DataUtil.notNullString(searchtext), start, pageSize);

			pageSize = DataUtil.notNullInt(pageSize, ConstParam.DEFAULT_PAGE_SIZE);
			start = DataUtil.notNullInt(start, 0);
			pageSize = pageSize > 4 ? pageSize : ConstParam.DEFAULT_PAGE_SIZE;
			PageControl pageControl = PageControl.getPageEnableInstance(start, pageSize);

			String order = "";
			SqlBuffer sbf = SqlBuffer.begin();

			if (!DataUtil.isNull(sort) && !DataUtil.eq("1", sort)) {
				order = "order by " + sort + " ";
				if (!DataUtil.isNull(dir) && "desc".equalsIgnoreCase(dir))
					order = order + " " + dir;
				pageControl.setOrder(order);
			} else
				pageControl.setOrder("order by id asc");

			/*
			 * sbf.addLike("name", searchtext); String
			 * _p_id=request.getParameter("id");
			 * 
			 * if(!isNull(_p_id)){ sbf.add("id",
			 * DataUtil.parseInteger(_p_id),"=","and"); }
			 */

			// sbf.addLike("areaName", request.getParameter("areaName"));

			//
			// //维护分类
			// sbf.add("maint_type",request.getParameter("smaintType"),"=","and");
			//
			// //区域
			// sbf.add("region_id",request.getParameter("sregionId"),"=","and");
			//

			// sbf.add("owner_no",request.getParameter("ownerNo"),"=","and");

			sbf.addLike("ownerNo", request.getParameter("ownerNo"));
			sbf.addLike("ownerName", request.getParameter("ownerName"));

			log.warn("Con:{},pageControl:{}", sbf.end(), JsonHelper.toJSONString(pageControl));

			List<?> dataList = ownerUnitsService.findByCondition(pageControl, sbf.end());

			// List<?> dataList = deviceService.findByCondition(pageControl,
			// sbf.end());

			ad.setData(dataList);
			ad.setTotalCount(pageControl.getRowCount());
			ad.setMsg(ActionMsgParam.ACM_LIS_SUC);
			ad.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ad.setData(e.toString());
			ad.setMsg(DataUtil.getRootCauseMsg(e));
			ad.setSuccess(false);
		}
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	// 归属单位列表
	@RequestMapping(value = "qlist", method = RequestMethod.GET)
	public void qlist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AccData ad = CtxHelper.createFailAccData();
		ad.setMsg("归属单位列表");
		List<BasOwnerUnits> list = ownerUnitsService.findByCondition(PageControl.getQueryOnlyInstance() // 获得基础分页条件
				.setOrder("order by id desc") // 设置排序参数
				, SqlBuffer.begin().end());

		ad.setSuccess(true);
		ad.setData(list);
		CtxHelper.writeToJson(response, ad);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getCommentByHbm()
	 */
	@Override
	public boolean getCommentByHbm() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getEntityName()
	 */
	@Override
	public String getEntityName() {
		return tableNameCn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getService()
	 */
	@Override
	public GenericServiceImpl<BasOwnerUnits, String> getService() {
		// TODO Auto-generated method stub
		return ownerUnitsService;
	}

}
