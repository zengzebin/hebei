package com.ssxt.common.enums;

public class RdBoxParam {

	public static class RdParam{

		public static final String FUNCTION_CODE_HEARTBEAT="51";
		public static final String FUNCTION_CODE_UP_PARAM="52";
		public static final String FUNCTION_CODE_SET_PARAM="53";
		public static final String FUNCTION_CODE_UP_ERROR="54";
		public static final String FUNCTION_CODE_UP_VERSION="55";
		public static final String FUNCTION_CODE_DOWN_VERSION="56";
		public static final String MSGTYPE_UP="55AA";
		public static final String MSGTYPE_DOWN="AA55";
		public static final String MSGTYPE_TEST="0000";
		public static final String MSGTYPE_UP_END="5A5A";
		public static final String MSGTYPE_DOWN_END="A5A5";
	}

	public static class SwParam{
		public static final String CodeModel_ASCII="ASCII";
		public static final String CodeModel_BCD="BCD";
		public static final String TransModel_M1="M1";
		public static final String TransModel_M2="M2";
		public static final String TransModel_M3="M3";
		public static final String TransModel_M4="M4";

		public static final String FUNCTION_CODE_HEARTBEAT="51";
		public static final String FUNCTION_CODE_UP_PARAM="52";
		public static final String FUNCTION_CODE_SET_PARAM="53";
		public static final String FUNCTION_CODE_UP_ERROR="54";
//		public static final byte MSGTYPE_UP=RdUtil.decodeHexFirst("00");
//		public static final byte MSGTYPE_DOWN=RdUtil.decodeHexFirst("08");
		public static final byte MSGTYPE_UP=0;
		public static final byte MSGTYPE_DOWN=8;
		public static final byte MSGTYPE_SOH_BCD_MIN=0x7E;
		public static final byte[] MSGTYPE_SOH_BCD=new byte[]{MSGTYPE_SOH_BCD_MIN,MSGTYPE_SOH_BCD_MIN};
		public static final byte MSGTYPE_SOH=0x01;
		public static final byte MSGTYPE_STX= 0x02;
		public static final byte MSGTYPE_SYN= 0x16;
		public static final byte MSGTYPE_ETX= 0x03;		//结束,后续无报文		
		public static final byte MSGTYPE_ETB= 0x17;		//结束,后续有报文
		public static final byte MSGTYPE_ENQ= 0x05;	
		public static final byte MSGTYPE_EOT= 0x04;				
		public static final byte MSGTYPE_ACK= 0x06;		
		public static final byte MSGTYPE_NAK= 0x15;		
		public static final byte MSGTYPE_ESC= 0x1B;		
//		public static final String DATE_FORMAT="yyMMddHHmmss";
//		public static final String MSGTYPE_TEST="0000";

		public static final String FOAMAT_DATE_SendTime="yyMMddHHmmss";
		public static final String FOAMAT_DATE_MonitorTime="yyMMddHHmm";
		
	}
}
