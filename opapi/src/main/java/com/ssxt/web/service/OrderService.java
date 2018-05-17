package com.ssxt.web.service;

import java.nio.channels.ShutdownChannelGroupException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssxt.common.dao.GenericDao;
import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlBuffer;
import com.ssxt.common.service.GenericServiceImpl;
import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.MDCUtil;
import com.ssxt.reflect.ReflectSql;
import com.ssxt.web.bean.BasDeviceOrder;
import com.ssxt.web.bean.BasDeviceOrderDetails;
import com.ssxt.web.bean.BasRepairSurplus;
import com.ssxt.web.bean.BasRepairSurplusId;
import com.ssxt.web.bean.BasWarehouseDevice;
import com.ssxt.web.bean.BasWarehouseOut;
import com.ssxt.web.bean.BasWarehouseSurplus;
import com.ssxt.web.bean.BasWarehouseSurplusId;
import com.ssxt.web.bean.ModelDevcieDetails;
import com.ssxt.web.bean.ModelDevcieOutDetails;
import com.ssxt.web.bean.SymAddvcdUser;
import com.ssxt.web.bean.SymMenu;
import com.ssxt.web.bean.SysAddvcdB;
import com.ssxt.web.dao.AddvcdDao;
import com.ssxt.web.dao.DeviceOrderDetailsDao;
import com.ssxt.web.dao.OrderDao;
import com.ssxt.web.dao.RepairSurplusDao;
import com.ssxt.web.dao.WareHouseDeviceOutDao;
import com.ssxt.web.dao.WarehouseSurplusDao;
import com.ssxt.web.dao.MenuDao;

@Service(value = "applyOrderService")
public class OrderService extends GenericServiceImpl<BasDeviceOrder, Integer> {

	protected OrderDao dao;
	@Autowired
	private WareHouseDeviceService houseDevice;

	// 申请清单
	@Autowired
	DeviceOrderDetailsDao detailsDao;

	// 出库清单
	@Autowired
	WareHouseDeviceOutDao wareHouseDeviceOutDao;

	// 库存
	@Autowired
	private WarehouseSurplusDao warehouseSurplusDao;

	// 运维人员仓库
	@Autowired
	private RepairSurplusDao repairSurplusDao;

	@Override
	public GenericDao<BasDeviceOrder, Integer> getDao() {
		return dao;
	}

	@Autowired
	public void setDao(OrderDao dao) {
		this.dao = dao;
	}

	/**
	 * 申请设备
	 * 
	 * @param order
	 * @param details
	 * @param request
	 * @param response
	 */
	@Transactional
	public void applyDevice(BasDeviceOrder order, ModelDevcieDetails detailsList, HttpServletRequest request,
			HttpServletResponse response) {
		order.setModifyTime(new Date());
		dao.save(order);
		order.setApplyTime(new Date());
		for (int i = 0; i < detailsList.getDetailsList().size(); i++) {
			detailsList.getDetailsList().get(i).setOrderId(order.getOrderId());
		}
		detailsDao.saveOrUpdateAll(detailsList.getDetailsList());
	}

	/**
	 * 修改申请设备
	 * 
	 * @param order
	 * @param details
	 * @param request
	 * @param response
	 */
	public void updateApplyDevice(BasDeviceOrder order, ModelDevcieDetails details, HttpServletRequest request,
			HttpServletResponse response) {
		order.setModifyTime(new Date());
		this.updateDomain("修改", this.get(order.getOrderId()), order);
		this.deleteSql("delete from bas_device_order_details wehre orderId=" + order.getOrderId(), null);
		for (int i = 0; i < details.getDetailsList().size(); i++) {
			details.getDetailsList().get(i).setOrderId(order.getOrderId());
		}
		detailsDao.saveOrUpdateAll(details.getDetailsList());
	}

	/**
	 * 同意设备
	 * 
	 * @param orderId
	 * @param deviceInfo
	 * @throws Exception
	 */
	@Transactional
	public void examine(BasDeviceOrder order, ModelDevcieOutDetails deviceOutDetails, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		order.setReviewUserId(MDCUtil.getUserId());
		order.setModifyTime(new Date());
		BasDeviceOrder oldBean = dao.get(order.getOrderId());
		this.updateDomain("审批申请订单", oldBean, order);
		if (order.getExamine() == 20) {

			for (int i = 0; i < deviceOutDetails.getDeviceOutDetails().size(); i++) {
				BasWarehouseOut out = deviceOutDetails.getDeviceOutDetails().get(i);
				out.setOrderId(order.getOrderId());
				out.setCreateTime(new Date());
			}
			wareHouseDeviceOutDao.saveOrUpdateAll(deviceOutDetails.getDeviceOutDetails());

			for (int i = 0; i < deviceOutDetails.getDeviceOutDetails().size(); i++) {
				BasWarehouseOut out = deviceOutDetails.getDeviceOutDetails().get(i);

				BasWarehouseSurplusId id = new BasWarehouseSurplusId();
				id.setModel(out.getModel());
				id.setWareHouseId(out.getWareHouseId());
				id.setState(out.getState());
				id.setDeviceTypeId(out.getDeviceTypeId());
				// 找出原本库存
				BasWarehouseSurplus isExist = warehouseSurplusDao.get(id);

				// ##########库存更新#########
				int surplus = isExist.getNum() - out.getNum();
				if (surplus < 0) {
					DataUtil.showMsgException("第" + i + "行出库数量超过库存");
					return;
				}

				BasWarehouseSurplus surplusBean = new BasWarehouseSurplus();
				surplusBean.setNum(surplus);

				StringBuffer sql = new StringBuffer(ReflectSql.updateSql(surplusBean, null, false));
				sql.append(" and wareHouseId=" + id.getWareHouseId());
				sql.append(" and state=" + id.getState());
				sql.append(" and deviceTypeId=" + id.getDeviceTypeId());
				sql.append(" and model='").append(id.getModel()).append("'");
				this.updateByNativeCondition(sql.toString(), null);

				// ##########运维人员库存#########
				BasRepairSurplusId RepairId = new BasRepairSurplusId();
				RepairId.setUserId(oldBean.getApplyUserId());
				RepairId.setDeviceTypeId(out.getDeviceTypeId());
				RepairId.setState(out.getState());
				RepairId.setModel(out.getModel());
				BasRepairSurplus isRepair = repairSurplusDao.get(RepairId);

				BasRepairSurplus saveRepair = new BasRepairSurplus();
				saveRepair.setModifyTime(new Date());
				if (isRepair == null) {
					// 不存在这个设备
					saveRepair.setId(RepairId);
					saveRepair.setNum(out.getNum());
					repairSurplusDao.save(saveRepair);
				} else {
					// 已经存在的设备

					saveRepair.setId(RepairId);
					saveRepair.setNum(isRepair.getNum() + out.getNum());
					StringBuffer sql2 = new StringBuffer(ReflectSql.updateSql(saveRepair, null, false));
					sql2.append(" and deviceTypeId=" + out.getDeviceTypeId());
					sql2.append(" and userId=" + saveRepair.getId().getUserId());
					sql2.append(" and state=" + saveRepair.getId().getState());
					sql2.append(" and model=" + saveRepair.getId().getModel());

					this.updateByNativeCondition(sql2.toString(), null);

				}
			}

		}

	}

	/**
	 * 
	 * @param orderId
	 */
	public void DisagreeOrder(int orderId) {

	}

	public static void main(String[] args) {

	}

}
