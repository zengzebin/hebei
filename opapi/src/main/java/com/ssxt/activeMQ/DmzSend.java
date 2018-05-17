package com.ssxt.activeMQ;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmzSend {

	@Autowired
	private DmzProducerService producerService;

	@Resource(name = "dmz")
	private Destination dmz;

	public void sendDmzSql(String sql) {
		producerService.sendMessage(dmz, sql);
	}

}
