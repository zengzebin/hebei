package com.ssxt.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.telnet.TelnetClient;

import com.ssxt.web.controller.TaskInfoController;

public class TelnetConnection {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TelnetConnection.class);

	private TelnetClient telnetClinet = null;
	private InputStream is = null;
	private OutputStream os = null;

	/**
	 * 链接服务是否畅通
	 * 
	 * @param ip
	 * @param port
	 */
	public boolean ping(String ip, int port) {
		boolean bool = false;
		try {
			telnetClinet = new TelnetClient();
			telnetClinet.connect(ip, port);
			telnetClinet.setConnectTimeout(5000);
			is = telnetClinet.getInputStream();
			os = telnetClinet.getOutputStream();
			bool = true;
		} catch (Exception e) {
			// e.printStackTrace();
			log.info("链接不到服务器");
		} finally {
			return bool;
		}
	}

	/**
	 * 连接远程计算机,连接完成后，获取读取流与发送流
	 * 
	 * @param ip
	 *            远程计算机IP地址
	 * @param port
	 *            远程计算机端口
	 */
	public void connection(String ip, int port) {
		try {
			telnetClinet = new TelnetClient();
			telnetClinet.connect(ip, port);
			is = telnetClinet.getInputStream();
			os = telnetClinet.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取远程计算机返回的信息
	 */
	public String readTelnetMsg() {
		try {
			int len = 0;
			byte[] b = new byte[1024];
			do {
				len = is.read(b);
				if (len >= 0)
					return new String(b, 0, len);
			} while (len >= 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 向远端计算机发送指令消息
	 * 
	 * @param msg
	 *            需要传送的指令
	 **/
	public void sendTelnetMsg(String msg) {
		byte[] b = msg.getBytes();
		try {
			os.write(b, 0, b.length);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查找远端计算机返回的指令中是否包含想要指令 一直查找，直到包含，返回true
	 */
	public boolean findStr(String str) {
		for (;;) {
			String msg = readTelnetMsg();
			if (msg.indexOf(str) != -1)
				return true;
		}
	}

	/**
	 * 关闭连接，关闭IO
	 */
	public void close() {
		try {
			is.close();
			os.close();
			telnetClinet.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开始测试,连接远程计算机后，发送一个shutdown关机指令
	 */
	public static void main(String[] args) throws Exception {
		TelnetConnection connection = new TelnetConnection();
		System.out.println(connection.ping("127.0.0.1", 24));
		// test.connection("183.0.10.1", 23);// 登录本机,23端口
		// if (test.findStr("login")) // 如果远程计算机返回login字符
		// test.sendTelnetMsg("zengzebin\n");//
		// 传输用户名,改写你的用户名,该用户名属于TelnetClients组
		// if (test.findStr("password")) // 如果远程计算机返回password
		// test.sendTelnetMsg("zengzebin123\n");// 传输用户密码,改写你的密码
		// if (test.findStr(">")) // 如果远程计算机返回>字符
		// test.sendTelnetMsg("ipconfig");// 发送关机指令
		// if (test.findStr(">")) // 如果远程计算机返回>字符,代表刚才指令发送成功
		// test.close();// 断开远程计算机连接
	}

}