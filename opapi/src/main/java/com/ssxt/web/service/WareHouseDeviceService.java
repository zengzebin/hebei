package com.ssxt.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.web.bean.BasDeviceOrder;
import com.ssxt.web.bean.BasWarehouseDevice;
import com.ssxt.web.bean.BasWarehouseSurplus;
import com.ssxt.web.bean.BasWarehouseSurplusId;
import com.ssxt.web.bean.ModelRepairDeviceUse;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasDeviceType;
import com.ssxt.web.bean.BasRepairDeviceUse;
import com.ssxt.web.bean.BasRepairSurplus;
import com.ssxt.web.bean.BasRepairSurplusId;
import com.ssxt.web.dao.WareHouseDeviceDao;
import com.ssxt.web.dao.WarehouseSurplusDao;
import com.ssxt.web.dao.DeviceInfoDao;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.controller.CommonController;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.OrderDao;
import com.ssxt.web.dao.RepairDeviceUseDao;
import com.ssxt.web.dao.RepairSurplusDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "wareHouseDeviceService")
public class WareHouseDeviceService extends GenericServiceImpl<BasWarehouseDevice, Integer> {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WareHouseDeviceService.class);

	protected WareHouseDeviceDao deviceDao;

	@Autowired
	private OrderDao applyOrderDao;

	@Autowired
	private WarehouseSurplusDao warehouseSurplusDao;

	// 运维人员使用
	@Autowired
	private RepairDeviceUseDao repaorDeviceUseDao;

	// 运维人员库存
	@Autowired
	private RepairSurplusDao repairSurplusDao;

	@Override
	public GenericDao<BasWarehouseDevice, Integer> getDao() {
		return deviceDao;
	}

	@Autowired
	public void setDao(WareHouseDeviceDao dao) {
		this.deviceDao = dao;
	}

	/**
	 * 入库
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(BasWarehouseDevice bean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		bean.setAddTime(new Date());
		bean.setAddUserId(MDCUtil.getUserId());
		this.save(bean);
		BasWarehouseSurplusId id = new BasWarehouseSurplusId();
		id.setModel(bean.getModel());
		id.setWareHouseId(bean.getWareHouseId());
		id.setState(bean.getState());
		id.setDeviceTypeId(bean.getDeviceTypeId());
		// 是否库存有这个记录
		BasWarehouseSurplus isExist = warehouseSurplusDao.get(id);
		if (isExist == null) {
			// 没有记录这个库存添加
			BasWarehouseSurplus saveBean = new BasWarehouseSurplus();
			saveBean.setId(id);
			saveBean.setNum(bean.getNum());
			saveBean.setModifyTime(new Date());
			saveBean.setModifyUserId(MDCUtil.getUserId());
			warehouseSurplusDao.save(saveBean);
		} else {
			// 更新库存
			BasWarehouseSurplus saveBean = new BasWarehouseSurplus();
			saveBean.setNum(isExist.getNum() + bean.getNum());
			StringBuffer sql = new StringBuffer(ReflectSql.updateSql(saveBean, null, false));
			sql.append(" and wareHouseId=" + id.getWareHouseId());
			sql.append(" and state=" + id.getState());
			sql.append(" and model='").append(id.getModel()).append("'");
			this.updateByNativeCondition(sql.toString(), null);
		}
	}

	/**
	 * 运维人员使用
	 * 
	 * @param bean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Transactional
	public void repairUse(ModelRepairDeviceUse DeviceUse, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		for (int i = 0; i < DeviceUse.getDeviceUse().size(); i++) {
			BasRepairDeviceUse use = DeviceUse.getDeviceUse().get(i);
			use.setUserId(MDCUtil.getUserId());
			use.setModifyTime(new Date());

			BasRepairSurplusId Id = new BasRepairSurplusId();
			Id.setDeviceTypeId(use.getDeviceTypeId());
			Id.setUserId(MDCUtil.getUserId());
			Id.setState(use.getState());
			Id.setModel(use.getModel());

			BasRepairSurplus isExist = repairSurplusDao.get(Id);
			if (isExist == null) {
				isExist.setId(Id);
				isExist.setModifyTime(new Date());
				isExist.setNum(use.getNum());
				// 不存在
				repairSurplusDao.save(isExist);
			} else {
				// 更新
				isExist.setModifyTime(new Date());
				isExist.setNum(isExist.getNum() - use.getNum());

				StringBuffer sql = new StringBuffer(ReflectSql.updateSql(isExist, null, false));
				sql.append(" and deviceTypeId=" + Id.getDeviceTypeId());
				sql.append(" and userId=").append(Id.getUserId());
				this.updateByNativeCondition(sql.toString(), null);

			}

		}
		repaorDeviceUseDao.saveOrUpdateAll(DeviceUse.getDeviceUse());

	}

}
