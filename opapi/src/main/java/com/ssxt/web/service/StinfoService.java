package com.ssxt.web.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.bean.FileInfo;
import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.CtxHelper;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.FileUploadUtils;
import com.ssxt.common.util.FileUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.common.util.PoiTool;
import com.ssxt.common.util.PropertiesUtil;
import com.ssxt.common.util.SpringUtil;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.web.bean.BasClassRoom;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasParam;
import com.ssxt.web.bean.BasStinfoB;
import com.ssxt.web.bean.BasStinfoImage;
import com.ssxt.web.bean.BasStinfoRoute;
import com.ssxt.web.bean.BasStinfoRouteImage;
import com.ssxt.web.dao.StinfoDao;
import com.ssxt.web.dao.StinfoImageDao;
import com.ssxt.web.dao.StinfoRouteDao;
import com.ssxt.web.dao.StinfoRouteImageDao;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SymUser;
import com.ssxt.web.bean.SysAddvcdB;

import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "stinfoService")
public class StinfoService extends GenericServiceImpl<BasStinfoB, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(StinfoService.class);
	protected StinfoDao stinfoDao;

	@Override
	public GenericDao<BasStinfoB, Integer> getDao() {
		return stinfoDao;
	}

	@Autowired
	public void setDao(StinfoDao dao) {
		this.stinfoDao = dao;
	}

	@Autowired
	private StinfoImageDao stinfoImageDao;

	@Autowired
	private StcdSttpService stcdSttpService;

	@Autowired
	private StinfoRouteDao stinfoRouteDao;
	@Autowired
	private StinfoRouteImageDao stinfoRouteImageDao;
	@Autowired
	UserService userService;

	@Autowired
	AddvcdService addvcdService;

	@Transactional
	public void add(BasStinfoB info) {
		info.setCreateTime(new Date());
		stinfoDao.save(info);
		// stcdSttpService.add(info.getStcd(), info.getSttp());
	}

	@Transactional
	public void update(int id, BasStinfoB bean) {
		BasStinfoB old = stinfoDao.get(id);

		super.updateDomain("更新设备测站", old, bean);

	}

	/**
	 * 测站上传图片
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	public void uploadImage(String stcd, HttpServletRequest request, HttpServletResponse response) {

		StringBuffer saveFile = new StringBuffer(CtxHelper.getPath(request));
		StringBuffer saveUrl = new StringBuffer(CtxHelper.getUrl(request));

		String projectName = PropertiesUtil.getProperty("file.saveFile");

		saveFile.append(projectName).append(File.separator).append("station").append(File.separator).append(stcd)
				.append(File.separator);
		saveUrl.delete(0, saveUrl.length() - 1);
		saveUrl.append(projectName).append("/").append("station").append("/").append(stcd).append("/");

		log.info(saveFile.toString());
		log.info(saveUrl.toString());
		File fileDir = new File(saveFile.toString());
		if (!fileDir.isDirectory()) {
			// 如果目录不存在，则创建目录
			fileDir.mkdirs();
		}
		List<FileInfo> list = FileUploadUtils.uoloadFile(request, saveFile.toString());
		List<BasStinfoImage> saveImages = new ArrayList<BasStinfoImage>();
		for (int i = 0; i < list.size(); i++) {
			BasStinfoImage bean = new BasStinfoImage();
			FileInfo fileInfo = list.get(i);
			bean.setFile(fileInfo.getSaveFile());
			bean.setFileName(fileInfo.getName());
			bean.setUrl(saveUrl + fileInfo.getName());
			bean.setSize(fileInfo.getSize());
			bean.setType(fileInfo.getType());
			bean.setCreateTime(new Date());
			bean.setCreateUserId(MDCUtil.getUserId());
			bean.setStcd(stcd);
			saveImages.add(bean);
		}
		stinfoImageDao.saveOrUpdateAll(saveImages);

	}

	/**
	 * 设置测站字典缓存
	 * 
	 * @return
	 */
	public Map<String, BasStinfoB> selectStcd() {
		// 测站缓存
		CachePool.getInstance().removeCacheItem("stion");
		StinfoService stinfoService = (StinfoService) SpringUtil.getContext().getBean("stinfoService");
		List<BasStinfoB> basStinfoBs = stinfoService.loadAll();
		Map<String, BasStinfoB> stion = new HashMap<String, BasStinfoB>();

		for (int i = 0; i < basStinfoBs.size(); i++) {
			BasStinfoB bean = basStinfoBs.get(i);
			stion.put(bean.getStcd(), bean);
		}
		CachePool.getInstance().putCacheItem("stion", stion);
		return stion;

	}

	/**
	 * 上传测站路线图片
	 * 
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int routeUpload(BasStinfoRoute bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		bean.setCreateTime(new Date());
		bean.setUserName(MDCUtil.getUserName());
		bean.setUserId(MDCUtil.getUserId());
		stinfoRouteDao.save(bean);
		StringBuffer saveFile = new StringBuffer(CtxHelper.getPath(request));
		StringBuffer saveUrl = new StringBuffer(CtxHelper.getUrl(request));

		String projectName = PropertiesUtil.getProperty("file.saveFile");

		saveFile.append(projectName).append(File.separator).append("stationRoute").append(File.separator)
				.append(bean.getStcd()).append(File.separator);
		saveUrl.delete(0, saveUrl.length() - 1);
		saveUrl.append(projectName).append("/").append("stationRoute").append("/").append(bean.getStcd()).append("/");

		log.info(saveFile.toString());
		log.info(saveUrl.toString());

		File fileDir = new File(saveFile.toString());
		if (!fileDir.isDirectory()) {
			// 如果目录不存在，则创建目录
			fileDir.mkdirs();
		}
		List<FileInfo> list = FileUploadUtils.uoloadFile(request, saveFile.toString());
		List<BasStinfoRouteImage> saveImages = new ArrayList<BasStinfoRouteImage>();
		for (int i = 0; i < list.size(); i++) {
			BasStinfoRouteImage image = new BasStinfoRouteImage();

			FileInfo fileInfo = list.get(i);
			image.setPath(fileInfo.getSaveFile());
			image.setFileName(fileInfo.getName());
			image.setUrl(saveUrl + fileInfo.getName());
			image.setSize(fileInfo.getSize());
			image.setType(fileInfo.getType());
			image.setProcId(bean.getId());
			saveImages.add(image);
		}

		stinfoRouteImageDao.saveAll(saveImages);
		return saveImages.size();
	}

	/**
	 * 导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook export(HttpServletRequest request, HttpServletResponse response) throws Exception {

		SqlBuffer where = SqlBuffer.begin();

		StringBuffer sql = new StringBuffer(
				"select stinfo.stcd,stinfo.stnm,stinfo.stlc,stinfo.longitude,stinfo.latitude,d.argName");

		sql.append(
				", CASE  stinfo.serviceType   WHEN 1 THEN '地下水'   WHEN 2 THEN '山洪'  WHEN 3 THEN '中小河流站'  WHEN 4 THEN '雨量基本站' ELSE '其他' END serviceType");

		sql.append(",b.name,b.phone,c.addvnm");
		sql.append(" from " + ReflectSql.getTblaeName(new BasStinfoB())).append(" stinfo");
		sql.append(" LEFT JOIN sym_user b  ON (stinfo.repairId = b.id)");
		sql.append(" LEFT JOIN sys_addvcd_b  c  ON (stinfo.addvcd=c.addvcd)");
		sql.append(" LEFT JOIN  bas_param  d  ON (stinfo.sttp=d.argValue)");

		where.addEndWith("stinfo.addvcd", AddvcdService.likeStart(request.getParameter("addvcd")));
		where.addEndWith("stinfo.addvcd", AddvcdService.likeStart(MDCUtil.getAddvcd()));

		where.add("stinfo.sttp", request.getParameter("sttp"));
		where.add("stinfo.serviceType", request.getParameter("serviceType"));
		where.add("stinfo.valid", request.getParameter("valid"));
		where.add("stinfo.stcd", request.getParameter("stcd"));
		where.addLike("stinfo.stnm", request.getParameter("stnm"));

		PageControl p = PageControl.getQueryOnlyInstance();
		this.findByNativeCondition(p, where.end(), sql.toString(), List.class);

		HSSFWorkbook wb = new HSSFWorkbook();// 创建Excel工作簿对象
		HSSFSheet sheet = wb.createSheet("new sheet");// 创建Excel工作表对象

		HSSFCellStyle cellStyle = wb.createCellStyle();// 创建单元格样式
		// 设置边框样式
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置边框颜色
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		String[] tile = { "测站编码", "测站名称", "测站位置", "经度", "纬度", "测站类型", "业务类型", "维修人员", "维修人员电话", "区域" };
		ExcelTile(tile, sheet, cellStyle);

		for (int i = 0; i < p.getList().size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			List o = (List) p.getList().get(i);

			for (int j = 0; j < o.size(); j++) {
				HSSFCell cell = row.createCell(j); // 创建列
				cell.setCellStyle(cellStyle);// 设置列样式
				cell.setCellValue(o.get(j) == null ? "" : o.get(j).toString());// 设置值
			}
		}

		return wb;
	}

	/**
	 * excle 头
	 * 
	 * @param tile
	 * @param sheet
	 * @param cellStyle
	 * @return
	 */
	public HSSFRow ExcelTile(String[] tile, HSSFSheet sheet, HSSFCellStyle cellStyle) {
		HSSFRow row = sheet.createRow(0);

		for (int i = 0; i < tile.length; i++) {
			HSSFCell cell = row.createCell(i); // 创建列
			cell.setCellStyle(cellStyle);// 设置列样式
			cell.setCellValue(tile[i]);// 设置值
		}
		return row;
	}

	/**
	 * 导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		StringBuffer saveFile = new StringBuffer(CtxHelper.getPath(request));
		int count = 0;
		// 用户
		List<SymUser> users = userService.loadAll();
		Map<String, Integer> userMap = new HashMap<String, Integer>();
		for (int i = 0; i < users.size(); i++) {
			userMap.put(users.get(i).getName(), users.get(i).getId());
		}

		// 测站类型
		SqlBuffer where = SqlBuffer.begin();
		where.add("paramType", "com-ssxt-op-stcdType");
		List<BasParam> params = userService.findByNativeCondition(PageControl.getPageDisableInstance(), where.end(),
				"select * from bas_param", BasParam.class);
		Map<String, String> sttpMap = new HashMap<String, String>();
		for (int i = 0; i < params.size(); i++) {
			sttpMap.put(params.get(i).getArgName(), params.get(i).getArgValue());
		}

		// 区域
		List<SysAddvcdB> addvcds = addvcdService.loadAll();
		Map<String, String> addvcdMap = new HashMap<String, String>();
		for (int i = 0; i < addvcds.size(); i++) {
			addvcdMap.put(addvcds.get(i).getAddvnm(), addvcds.get(i).getAddvcd());
		}
		String projectName = PropertiesUtil.getProperty("file.saveFile");

		saveFile.append(projectName).append(File.separator).append("excel").append(File.separator);

		log.info(saveFile.toString());
		File fileDir = new File(saveFile.toString());
		if (!fileDir.isDirectory()) {
			// 如果目录不存在，则创建目录
			fileDir.mkdirs();
		}
		List<FileInfo> list = FileUploadUtils.uoloadFile(request, saveFile.toString());

		if (list.size() > 0) {

			Sheet sheet = PoiTool.loadExcel(list.get(0).getSaveFile());
			int rowNum = sheet.getLastRowNum() + 1;
			int i = 0;
			try {
				for (i = 1; i < rowNum; i++) {

					Row row = sheet.getRow(i);

					if (row.getRowNum() > 0) {
						BasStinfoB bean = new BasStinfoB();
						bean.setStcd(PoiTool.getCellValue(row.getCell(0)));
						bean.setStnm(PoiTool.getCellValue(row.getCell(1)));
						bean.setStlc(PoiTool.getCellValue(row.getCell(2)));
						bean.setLongitude(DataUtil.parseDouble(PoiTool.getCellValue(row.getCell(3)), 0));
						bean.setLatitude(DataUtil.parseDouble(PoiTool.getCellValue(row.getCell(4)), 0));
						bean.setSttp(sttpMap.get(PoiTool.getCellValue(row.getCell(5))));
						String serviceType = PoiTool.setServiceType((PoiTool.getCellValue(row.getCell(6))));
						bean.setServiceType(serviceType);
						bean.setRepairId(userMap.get(PoiTool.getCellValue(row.getCell(7))));
						bean.setAddvcd(addvcdMap.get(PoiTool.getCellValue(row.getCell(9))));
						bean.setValid(1);
						this.save(bean);
						count++;

					}

				}
			} catch (Exception e) {
				FileUtil.deleteFile(list.get(0).getSaveFile());
				DataUtil.showMsgException("第" + (i+1) + "行导入有错误,请检测是否已经存在或者必填项为空");
			}
			FileUtil.deleteFile(list.get(0).getSaveFile());
		}

		return "成功导入" + count + "条";
	}

}
