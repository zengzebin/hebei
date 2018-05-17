package com.ssxt.web.controller;

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
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.MenuService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/menu/")
public class MenuController extends SpringBaseController<SymMenu, Integer> {
	@Autowired
	private UserService service;
	@Autowired
	private RoleService roleservice;

	@Autowired
	MenuService menuService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getCommentByHbm()
	 */

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
 
		SqlBuffer sbf=SqlBuffer.begin();
		sbf.add("parentId", DataUtil.parseInteger(request.getParameter("parentId")));
		sbf.addLike("menuText",request.getParameter("menuText"));
	 
	   PageControl p=super.selectHql(searchtext, sort, dir, start, pageSize, request, response, sbf);
	   super.toJson(p.getList(), p.getRowCount(), true,ActionMsgParam.ACM_LIS_SUC, request, response);
	   
	}
	
	
	
	@Comment("新增数据")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") SymMenu   bean, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// bean.setCreateTime(new Date());
	  menuService.save(bean);
	  super.toJson(null, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("修改数据数据")
	@RequestMapping(value = "update/{menuId}", method = RequestMethod.PUT)
	public void update(@ModelAttribute("bean") SymMenu bean, @PathVariable Integer menuId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// bean.setCreateTime(new Date());

		SymMenu old = menuService.get(menuId);
		menuService.updateDomain("修改菜單", old, bean);
	    super.toJson(null, 1, true, ActionMsgParam.ACM_MOD_SUC, request, response);

	}
	
	
	@Comment("刪除菜單")
	@RequestMapping(value = "delete/{menuId}", method = RequestMethod.DELETE)
	public void update( @PathVariable Integer menuId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// bean.setCreateTime(new Date());
        menuService.deleteByKey(menuId);
	    super.toJson(null, 1, true, ActionMsgParam.ACM_DEL_SUC, request, response);
    }
	
	
	@Comment("獲取全部菜單")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll( HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// bean.setCreateTime(new Date());
		super.loadAll(request, response,null,null,null);
//	    super.toJson(null, 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);
    }
	
	
	@Comment("獲取菜单")
	@RequestMapping(value = "load/{menuId}", method = RequestMethod.GET)
	public void load(@PathVariable Integer menuId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// bean.setCreateTime(new Date());
        super.load(menuId, request, response);
/*        super.toJson(null, 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);
*/    }
 
	 

	@Override
	public boolean getCommentByHbm() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getEntityName()
	 */
	@Override
	public String getEntityName() {
		return "用户管理";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getService()
	 */
	@Override
	public GenericServiceImpl<SymMenu, Integer> getService() {
		return menuService;
	}

}
