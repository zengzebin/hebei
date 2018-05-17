package com.ssxt.web.controller;

import static com.ssxt.common.util.DataUtil.notNullString;
import static com.ssxt.common.util.DataUtil.showMsgException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.cfg.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.ssxt.common.util.ExportBeanExcel;
import com.ssxt.common.util.FastJsonUtils;
import com.ssxt.common.util.FileUploadUtils;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.Student;
import com.ssxt.common.web.SpringBaseController;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.web.bean.BasClassRoom;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasStinfoImage;
import com.ssxt.web.bean.BasStinfoRoute;
import com.ssxt.web.service.AddvcdService;
import com.ssxt.web.service.DeviceInfoService;
import com.ssxt.web.service.StinfoService;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.dao.StinfoImageDao;
import com.ssxt.web.dao.StinfoRouteDao;
import com.ssxt.web.service.MenuService;
import com.ssxt.web.service.RoleService;
import com.ssxt.web.service.UserService;

@Controller
@RequestMapping(value = "/api/stinfo/")
public class StinfoController extends SpringBaseController<BasStinfoB, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StinfoController.class);

	@Autowired
	private StinfoService service;

	@Autowired
	private StinfoImageDao stinfoImageDao;

	@Autowired
	private StinfoRouteDao stinfoRouteDao;

	@Override
	public boolean getCommentByHbm() {
		return true;
	}

	@Comment("查询列表")
	@RequestMapping(value = "select", method = RequestMethod.GET)
	public void list(@RequestParam(value = "searchtext", required = false) String searchtext,
			@RequestParam(value = "order", required = false) String sort,
			@RequestParam(value = "orderdir", required = false) String dir,
			@RequestParam(value = "start", required = false) Integer start,
			@RequestParam(value = "length", required = false) Integer pageSize, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SqlBuffer where = SqlBuffer.begin();

		StringBuffer sql = new StringBuffer(ReflectSql.selectSql(new BasStinfoB(), "stinfo", "stcd,stlc,sttp,stnm"));
		sql.append(",stinfo.stcd,stlc,sttp,stnm,c.name,c.phone");
		sql.append(" from " + ReflectSql.getTblaeName(new BasStinfoB())).append(" stinfo");
		// sql.append(" LEFT JOIN faultSite b ON (stinfo.stcd = b.stcd)");
		sql.append(" LEFT JOIN sym_user c  ON (stinfo.repairId = c.id)");

		where.addEndWith("stinfo.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
		where.addEndWith("stinfo.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));

		where.add("stinfo.sttp", request.getParameter("sttp"));
		where.add("stinfo.serviceType", request.getParameter("serviceType"));
		where.add("stinfo.valid", request.getParameter("valid"));
		where.addEndWith("stinfo.stcd", request.getParameter("stcd"));
		where.addLike("stinfo.stnm", request.getParameter("stnm"));

		PageControl p = super.selectSql(searchtext, sort, dir, start, pageSize, request, response, Map.class, where,
				sql.toString());

		super.toJson(p.getList(), p.getRowCount(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("下载文件")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(HttpServletRequest request, HttpServletResponse response) {
		try {

			HSSFWorkbook wb = service.export(request, response);

			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			HttpHeaders headers = new HttpHeaders();
			String name = request.getParameter("fileName");
			name = name == null ? "测站信息.xls" : name + ".xls";
			String fileName = new String(name.getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
			headers.setContentDispositionFormData("attachment", fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(outByteStream.toByteArray(), headers, HttpStatus.OK);
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

	@Comment("上传文件")
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message = service.upload(request, response);
		toJson(null, 0, true, message, request, response);
	}

	@Comment("新增数据")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void add(@ModelAttribute("bean") BasStinfoB bean, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();
		SqlBuffer sbf = SqlBuffer.begin();
		sbf.add("stcd", bean.getStcd());
		if (this.exist(sbf).size() == 0) {
			service.add(bean);
			ad.setMsg("测站添加成功");
			ad.setSuccess(true);
		} else {
			showMsgException("测站已经存在");

		}

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("修改数据数据")
	@RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable Integer id, @ModelAttribute("bean") BasStinfoB bean, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// bean.setCreateTime(new Date());

		service.update(id, bean);
		AccData ad = CtxHelper.createFailAccData();

		ad.setMsg(ActionMsgParam.ACM_MOD_SUC);
		ad.setSuccess(true);

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("删除数据")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();

		service.deleteByKey(id);
		ad.setMsg(ActionMsgParam.ACM_DEL_SUC);
		ad.setSuccess(true);

		CtxHelper.setNoCache(response);
		CtxHelper.writeToJson(response, ad);
	}

	@Comment("加载数据数据")
	@RequestMapping(value = "load/{id}", method = RequestMethod.GET)
	public void load(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// bean.setCreateTime(new Date());
		AccData ad = CtxHelper.createFailAccData();

		super.load(id, request, response);

	}

	@Comment("加载数据数据")
	@RequestMapping(value = "loadAll", method = RequestMethod.GET)
	public void loadAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// bean.setCreateTime(new Date());
		SqlBuffer where = SqlBuffer.begin();

		StringBuffer sql = new StringBuffer(ReflectSql.selectSql(new BasStinfoB(), "stinfo", "stcd,stlc,sttp,stnm"));
		sql.append(",stinfo.stcd,stlc,sttp,stnm,b.vt");
		sql.append(" from " + ReflectSql.getTblaeName(new BasStinfoB())).append(" stinfo");
		sql.append(" LEFT JOIN faultSite b  ON (stinfo.stcd = b.stcd)");

		where.add("valid", request.getParameter("valid"));
		where.add("serviceType", request.getParameter("serviceType"));

		super.loadAll(request, response, where, sql.toString(), null);

	}

	@Comment("上传图片")
	@RequestMapping(value = "uploadImage/{stcd}", method = RequestMethod.POST)
	public void uploadImage(@PathVariable String stcd, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		service.uploadImage(stcd, request, response);
		toJson(null, 1, true, "上传成功", request, response);
	}

	@Comment("删除图片")
	@RequestMapping(value = "deleteImage/{id}", method = RequestMethod.DELETE)
	public void uploadImage(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BasStinfoImage bean = stinfoImageDao.get(id);
		FileUploadUtils.deleteImage(bean.getFile());
		stinfoImageDao.deleteByKey(id);
		toJson(null, 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("获取图片")
	@RequestMapping(value = "stcdImage/{stcd}", method = RequestMethod.GET)
	public void stcdImage(@PathVariable String stcd, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String sql = "select * from bas_stinfo_image";
		SqlBuffer where = SqlBuffer.begin();
		where.add("stcd", stcd);
		PageControl p = this.commonFind(request, response, null, where, sql, Map.class);
		toJson(p.getList(), 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("获取测站更换记录")
	@RequestMapping(value = "historyRecord/{stionId}", method = RequestMethod.GET)
	public void historyRecord(@PathVariable Integer stionId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StringBuffer sql = new StringBuffer("SELECT b.name,a.modifyTime,c.name typeName,a.model,a.num ");
		sql.append(" FROM bas_repair_device_use a");
		sql.append(" INNER JOIN sym_user b ON(a.userid=b.id)");
		sql.append(" INNER JOIN bas_device_type c ON(a.deviceTypeId=c.id)");

		SqlBuffer where = SqlBuffer.begin();
		where.add("stionId", stionId);
		PageControl p = this.commonFind(request, response, null, where, sql.toString(), Map.class);
		toJson(p.getList(), 1, true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("路线上传图片")
	@RequestMapping(value = "route/upload", method = RequestMethod.POST)
	public void routeUpload(@ModelAttribute("bean") BasStinfoRoute bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int num = service.routeUpload(bean, request, response);
		toJson(null, num, true, "上传成功", request, response);
	}

	@Comment("获取该测站图片")
	@RequestMapping(value = "route/imageLocation", method = RequestMethod.GET)
	public void imageLocation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sql = "SELECT * FROM bas_stinfo_route";
		PageControl p = PageControl.getQueryOnlyInstance();
		// p.setGroupBy("GROUP BY stcd,longitude,latitude,stcdKey");
		SqlBuffer where = SqlBuffer.begin();
		where.add("stcd", request.getParameter("stcd"));
		stinfoRouteDao.findByCondition(p, where.end());

		// this.commonFind(request, response, p, where, sql, Map.class);
		super.toJson(p.getList(), p.getList().size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
	}

	@Comment("获取该测站图片")
	@RequestMapping(value = "route/images", method = RequestMethod.GET)
	public void routeImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sql = "SELECT * FROM bas_stinfo_route";
		PageControl p = PageControl.getQueryOnlyInstance();
		SqlBuffer where = SqlBuffer.begin();
		where.add("stcdKey", request.getParameter("stcdKey"));
		this.commonFind(request, response, p, where, sql, Map.class);
		toJson(p.getList(), p.getList().size(), true, ActionMsgParam.ACM_LIS_SUC, request, response);
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
	public GenericServiceImpl<BasStinfoB, Integer> getService() {
		return service;
	}

}
