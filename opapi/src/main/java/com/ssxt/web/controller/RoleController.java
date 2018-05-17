package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.notNullString;

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
import com.ssxt.common.page.WebSearchBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.AccData;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.SymRolePermissionService;

@Controller
@RequestMapping(value = "/api/role/")
public class RoleController extends SpringBaseController<SymRole, Integer> {

	@Autowired
	private RoleService service;

	@Autowired
	private SymRolePermissionService symRolePermissionService;

	private static final String ENTITY_NAME = "角色管理";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getCommentByHbm()
	 */
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
		return ENTITY_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getService()
	 */
	@Override
	public GenericServiceImpl<SymRole, Integer> getService() {
		return service;
	}

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RoleController.class);

	@Comment("新增数据")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") SymRole bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		bean.setCreateTime(new Date());
		super.add(bean, request, response);
	}

	@Comment("角色权限添加")
	@RequestMapping(value = "addPermission", method = RequestMethod.POST)
	public void addPermission(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Integer roleId = Integer.parseInt(request.getParameter("roleId"));
		String[] menuIds = request.getParameter("menuIds").split(",");
		symRolePermissionService.addPermission(roleId, menuIds);
		super.toJson(null, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("获取角色菜单")
	@RequestMapping(value = "getPermission", method = RequestMethod.GET)
	public void getPermission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SymUser user = CtxHelper.getCurUser(request);

		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add("roleId", DataUtil.parseInteger(request.getParameter("roleId")));
		List<?> list = symRolePermissionService.findByCondition(PageControl.getQueryOnlyInstance(), sbf.end());
		AccData ad = CtxHelper.createFailAccData();
		ad.setData(list);
		ad.setTotalCount(list.size());
		ad.setMsg(ActionMsgParam.ACM_LIS_SUC);
		ad.setSuccess(true);
		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("获取当前用户角色菜单")
	@RequestMapping(value = "getUserPermission", method = RequestMethod.GET)
	public void getUserPermission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SymUser user = CtxHelper.getCurUser(request);

		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add("roleId", DataUtil.parseInteger(user.getRoleId().toString()));
		List<?> list = symRolePermissionService.findByCondition(PageControl.getQueryOnlyInstance(), sbf.end());
		super.toJson(list, list.size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);

	}

	@Comment("修改角色权限菜单")
	@RequestMapping(value = "updatePermission", method = RequestMethod.PUT)
	public void updatePermission(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.getPermission(request, response);
	}

	@Comment("修改角色")
	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable Integer id, @ModelAttribute("bean") SymRole bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SymRole old = service.get(id);
		service.updateDomain("修改", old, bean);
		super.toJson(null, 0, true, ActionMsgParam.ACM_MOD_SUC, request, response);

	}

	@Comment("刪除角色")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id, @ModelAttribute("bean") SymRole bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		service.deleteByKey(id);
		symRolePermissionService.deleteRole(id);
		super.toJson(null, 0, true, ActionMsgParam.ACM_DEL_SUC, request, response);
	}

	@Comment("加载数据")
	@RequestMapping(value = "load/{id}", method = RequestMethod.GET)
	public void load(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.load(id, request, response);
	}

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SqlBuffer sbf=SqlBuffer.begin();
		sbf.addLike("name",request.getParameter("name"));
		 PageControl p = super.selectHql(searchtext, sort, dir, start, pageSize, request, response, sbf);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_DEL_SUC, request, response);

	}
	
	@Comment("加载全部数据")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll(HttpServletRequest request, HttpServletResponse response)
			throws  Exception {
		// bean.setCreateTime(new Date());
		super.loadAll(request, response,null,null,null);
	}
}
