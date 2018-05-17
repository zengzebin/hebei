package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.cfg.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.web.bean.BasClassRoom;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.SymRole;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.ClassRoomService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/classRoom/")

public class ClassRoomController extends SpringBaseController<BasClassRoom, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ClassRoomController.class);

	@Autowired
	ClassRoomService classRoomService;

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer sbf = SqlBuffer.begin();

		sbf.addLike("name", request.getParameter("name"));
		PageControl p = super.selectHql(searchtext, sort, dir, start, pageSize, request, response, sbf);
		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("新增数据")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") BasClassRoom bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		classRoomService.add(bean, request, response);

		toJson(bean, 1, true, ActionMsgParam.ACM_SAV_SUC, request, response);
	}

	@Comment("修改数据数据")
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public void update(@ModelAttribute("bean") BasClassRoom bean, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		BasClassRoom old = classRoomService.get(bean.getId());
		old.setModifyUserId(MDCUtil.getUserId());
		old.setModifyTime(new Date());
		classRoomService.updateDomain("更新", old, bean);

		AccData ad = CtxHelper.createFailAccData();

		ad.setMsg(ActionMsgParam.ACM_MOD_SUC);
		ad.setSuccess(true);

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("加载单个数据")
	@RequestMapping(value = "load/{id}", method = RequestMethod.GET)
	public void update(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// bean.setCreateTime(new Date());
		super.load(id, request, response);
	}

	@Comment("删除数据")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		classRoomService.delete(id);

		AccData ad = CtxHelper.createFailAccData();
		ad.setMsg(ActionMsgParam.ACM_DEL_SUC);
		ad.setSuccess(true);

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("上传图片")
	@RequestMapping(value = "uploadImage/{id}", method = RequestMethod.POST)
	public void uploadImage(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		classRoomService.uploadImage(id, request, response);
		toJson(null, 1, true, "上传成功", request, response);
	}

	@Comment("加载全部数据")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// bean.setCreateTime(new Date());
		super.loadAll(request, response, null, null, null);
	}

	@Comment("下载文件")
	@RequestMapping(value = "download/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(@PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String sql = "update BasClassRoom set clickNum=clickNum+1 where id=" + id;
			classRoomService.updateByCondition(sql, null);
			BasClassRoom old = classRoomService.get(id);
			File file = new File(old.getFile());
			HttpHeaders headers = new HttpHeaders();
			String name = old.getName() + old.getType();
			String fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.sendError(404);
			} catch (IOException e1) {

			}
			// toJson(null, 1, false, "文件找不到了", request, response);
			return null;
		}
	}

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
		return "用户管理";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ssxt.rdbox.common.web.SpringBaseController#getService()
	 */
	@Override
	public GenericServiceImpl<BasClassRoom, Integer> getService() {
		return classRoomService;
	}

}
