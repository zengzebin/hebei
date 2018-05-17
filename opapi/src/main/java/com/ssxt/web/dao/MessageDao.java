package com.ssxt.web.dao;

import com.ssxt.common.dao.GenericDaoImpl;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.web.bean.BasDeviceInfo;
import com.ssxt.web.bean.BasMessageRemind;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository(value = "messageDao")
public class MessageDao extends GenericDaoImpl<BasMessageRemind, Integer> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MessageDao.class);

}
