package com.ssxt.common.util;
/*package com.ssxt.rdbox.common.util;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssxt.rdbox.net.config.BaseConfig;
import com.ssxt.rdbox.net.config.BaseHandleConfig;
import com.ssxt.rdbox.net.handler.BaseServerHandler;

import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

*//**
 * <b>类名称：</b>SocketUtil<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年7月27日 下午4:13:44<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 *//*
public class SocketUtil {
	private static Logger log = LoggerFactory.getLogger(SocketUtil.class);

	
	*//**
	 * 根据clientAddr发送数据，不管UDP还是TCP<br/>
	 * udp则bmsg不能为空<br/>
	 * @param config		配置文件
	 * @param clientAddr 客户端地址，不能为空
	 * @param bmsg 信息字节数组(udp发送需要，一般为Unpooled.wrappedBuffer(byte[]))
	 * @param msg 信息内容(TCP,必须根据Decode后的实际类型,一般为BaseServerHandler的泛型类型)
	 *//*
	public static <T> void sendMsgByUser(BaseConfig config, String clientAddr, ByteBufConverter<?> b,T msg){
		BaseHandleConfig handleConfig=config.getHandleConfig();
		String channelId=handleConfig.getUserIdMap().get(clientAddr);
		InetSocketAddress socketAddress =(handleConfig.getUdpMap().get(channelId));
		if(socketAddress!=null){
			Channel channel=handleConfig.getSessionChannelMap().get(channelId);
			log.warn("{}下发UDP消息,userId:{},channelId:{},address:{}" ,DataUtil.getCallerName(),clientAddr,channelId,socketAddress );
			channel.writeAndFlush(new DatagramPacket(b.toByteBuf(), socketAddress));
		}
		else{
			log.debug("{}下发TCP消息,userId:{},channelId:{}" , DataUtil.getCallerName(),clientAddr,channelId);
			BaseServerHandler.messageSendByUser(handleConfig, clientAddr, msg);
//			BaseServerHandler.messageSendByUser(swConfig.getHandleConfig(), clientAddr, (down.getTotal()));
		}
	}

	*//**
	 * 根据clientAddr发送数据，不管UDP还是TCP<br/>
	 * (注意事项):<br/>
	 * udp则socketAddress,channel,bmsg不能为空<br/>
	 * @param config		配置文件
	 * @param clientAddr 客户端地址
	 * @param b		信息ByteBuf(udp发送需要，一般为Unpooled.wrappedBuffer(byte[]))
	 * @param msg  信息内容(TCP需要,必须根据Decode后的实际类型,一般为BaseServerHandler的泛型类型)
	 *//*
	public static <T> void sendMsg(BaseConfig config, Channel channel, InetSocketAddress socketAddress, String clientAddr, ByteBufConverter<?> b,T msg){
		String channelId=RdUtil.getChannelId(channel);
		if(socketAddress!=null){
			log.warn("{}发送UDP请求:{},channelId:{}" ,DataUtil.getCallerName(),socketAddress,channelId );
			channel.writeAndFlush(new DatagramPacket(b.toByteBuf(), socketAddress));
		}
		else if(clientAddr!=null){
			log.warn("{}发送TCP消息给用户,userId:{},channelId:{}" ,DataUtil.getCallerName(),clientAddr,channelId );
			BaseServerHandler.messageSendByUser(config.getHandleConfig(),clientAddr, msg);
		}
		else {
			log.debug("{}发送TCP消息给channel,channelId:{}" , DataUtil.getCallerName(),channelId);
			BaseServerHandler.messageSend(config.getHandleConfig(),RdUtil.getChannelId(channel), msg);
		}
	}


}

*/