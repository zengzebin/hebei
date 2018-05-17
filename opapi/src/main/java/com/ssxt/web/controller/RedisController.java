package com.ssxt.web.controller;
/*package com.ssxt.web.sys.controller;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssxt.common.enums.ActionMsgParam;
import com.ssxt.common.enums.ConstParam;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.page.WebSearchBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
 
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.redis.RedisUtil;
import com.ssxt.web.sys.bean.SymRole;
import com.ssxt.web.sys.bean.SymUser;
import com.ssxt.web.sys.bean.SysAddvcdB;
import com.ssxt.web.sys.service.AddvcdService;
import com.ssxt.web.sys.service.RoleService;
import com.ssxt.web.sys.service.UserService;

@Controller
@RequestMapping(value = "/api/redis/")

public class RedisController extends SpringBaseController<SysAddvcdB, String> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RedisController.class);

	@Autowired
	AddvcdService addvcdService;
	@Autowired
	RedisUtil redisUtil;

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		AccData ad = CtxHelper.createFailAccData();

		String info = "查找数据{}:{}-[{}]";
		log.debug(info, notNullString(searchtext), start, pageSize);

		pageSize = DataUtil.notNullInt(pageSize, ConstParam.DEFAULT_PAGE_SIZE);
		start = DataUtil.notNullInt(start, 0);
		pageSize = pageSize > 4 ? pageSize : ConstParam.DEFAULT_PAGE_SIZE;
//		PageControl pageControl = PageControl.getPageEnableInstance(start, pageSize);
		PageControl pageControl=PageControl.getQueryOnlyInstance();
		String order = "";
		SqlBuffer sbf = SqlBuffer.begin();

		if (!DataUtil.isNull(sort) && !DataUtil.eq("1", sort)) {
			order = "order by ";
			
			 * if (!isNull(dir) && "desc".equalsIgnoreCase(dir)) order = order +
			 * " " + dir;
			 
			String zidu = "";
			String[] sortDate = sort.split(",");
			String[] dirDate = dir.split(",");
			for (int i = 0; i < sortDate.length; i++) {
				if (i == 0) {
					zidu = sortDate[i] + " " + dirDate[i];
				} else {
					zidu += "," + sortDate[i] + " " + dirDate[i];
				}
			}

			pageControl.setOrder(order + zidu);
		} else {
			pageControl.setOrder("ORDER BY id desc");
		}

		List<?> dataList = addvcdService.findByCondition(pageControl, sbf.end());

		ad.setData(dataList);
		ad.setTotalCount(pageControl.getRowCount());
		ad.setMsg(ActionMsgParam.ACM_LIS_SUC);
		ad.setSuccess(true);

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("获取全部区域")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Object mess=redisUtil.getCacheObject("test");
		if(mess==null)
		redisUtil.setCacheObject("test", "你好呀");
		addvcdService.loadAll();
	}

	
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getCommentByHbm()
	 
	@Override
	public boolean getCommentByHbm() {
		return true;
	}

	
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getEntityName()
	 
	@Override
	public String getEntityName() {
		return "用户管理";
	}

	
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getService()
	 
	@Override
	public GenericServiceImpl<SysAddvcdB, String> getService() {
		return addvcdService;
	}

}
*/