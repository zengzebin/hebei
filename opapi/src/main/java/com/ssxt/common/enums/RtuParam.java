package com.ssxt.common.enums;

public class RtuParam {

    /// <summary>
    /// 总体分类
    /// </summary>
	public static class InsideMainType
    {
        /// <summary>
        /// 未知设备
        /// </summary>
        //未知设备
       public static final byte None = 0x10;

        /// <summary>
        /// RTU设备
        /// </summary>
        //RTU设备
        public static final byte RTUDevice = 0x11;

        /// <summary>
        /// GPS设备
        /// </summary>
        //GPS设备
        public static final byte GPSDevice = 0x12;

        /// <summary>
        /// 前置机服务器
        /// </summary>
        //前置机服务器
        public static final byte GatherServer = 0x13;

        /// <summary>
        /// 采集总控软件
        /// </summary>
        //采集总控软件
        public static final byte ControlServer = 0x14;


        /// <summary>
        /// 订阅软件
        /// </summary>
        //订阅软件
        public static final byte OrderServices = 0x15;


        /// <summary>
        /// GSM
        /// </summary>
        //GSM
        public static final byte GSMDevice = 0x16;

        /// <summary>
        /// GSMRTU
        /// </summary>
        //GSMRTU
        public static final byte GSMRTUDevice = 0x17;


        /// <summary>
        /// GSMGPS
        /// </summary>
        //GSMGPS
        public static final byte GSMGPSDevice = 0x18;

        /// <summary>
        /// 水资源RTU设备
        /// </summary>
        //水资源RTU设备
        public static final byte SZYRTUDevice = 0x19;

        /// <summary>
        /// 四川水文RTU设备
        /// </summary>
        //水资源RTU设备
        public static final byte SCSWRTUDevice = 0x20;

        /// 墒情web下发指令
        /// </summary>
        //墒情web下发指令
        public static final byte SoilWebDevice = 0x21;

        //四信广播机
        public static final byte SXGBDevice = 0x22;
    }

    /// <summary>
    /// 命令分类
    /// </summary>
    public static class InsideCmdType
    {
        /// <summary>
        /// 未知命令
        /// </summary>
        //未知命令
        public static final byte None = 0x10;

        /// <summary>
        /// 自报数据命令
        /// </summary>
        //自报数据命令
        public static final byte SelfReported = 0x11;

        /// <summary>
        /// 查询命令
        /// </summary>
        //查询命令
        public static final byte Query = 0x12;

        /// <summary>
        /// 查询应答命令
        /// </summary>
        //查询应答命令
        public static final byte QueryACK = 0x13;

        /// <summary>
        /// 控制命令
        /// </summary>
        //控制命令
        public static final byte Control = 0x14;

        /// <summary>
        /// 控制应答命令
        /// </summary>
        //控制应答命令
        public static final byte ControlACK = 0x15;

        /// <summary>
        /// 鉴权命令
        /// </summary>
        //鉴权命令
        public static final byte Authentication = 0x16;

        /// <summary>
        /// 鉴权应答命令
        /// </summary>
        //鉴权应答命令
        public static final byte AuthenticateACK = 0x17;
    }

    /// <summary>
    /// 数据分类
    /// </summary>
    public static class InsideDataType
    {
    	public static byte[] NoNeedReplyArr=new byte[]{RtuParam.InsideDataType.HeartBeat,RtuParam.InsideDataType.None};
        /// <summary>
        /// 未知数据
        /// </summary>
        //未知数据
        public static final byte None = 0x10;

        /// <summary>
        /// 控制类通用应答数据
        /// </summary>
        //控制类通用应答数据
        public static final byte ControlAck = 0x11;

        /// <summary>
        /// 实时数据
        /// </summary>
        //实时数据
        public static final byte RealTime = 0x12;

        /// <summary>
        /// 时段数据
        /// </summary>
        //时段数据
        public static final byte Time = 0x13;

        /// <summary>
        ///定位数据 
        /// </summary>
        //定位数据
        public static final byte Locate = 0x14;

        /// <summary>
        /// 报警数据
        /// </summary>
        //报警数据
        public static final byte Alarm = 0x15;



        /// <summary>
        /// 鉴权数据
        /// </summary>
        //鉴权数据
        public static final byte AuthCode = 0x17;

        /// <summary>
        /// RSA密钥
        /// </summary>
        //RSA密钥
        public static final byte RSAKey = 0x18;

        /// <summary>
        /// 人工置数
        /// </summary>
        //人工置数
        public static final byte CustomData = 0x19;

        /// <summary>
        /// 时段数据
        /// </summary>
        //时段数据
        public static final byte TimeInterval = 0x20;
        
        /**
         * 水文正式功能码
         */ 
        /// <summary>
        /// 心跳包
        /// </summary>
        //心跳包
//        public static final byte HeartBeat = 0x21;
        public static final byte HeartBeat = 0x2F;	//2fh

        /// <summary>
        /// 测试报
        /// </summary>
        //测试报
        public static final byte TestReport = 0x30;
//        public static final byte TestReport = 0x22;

        /// <summary>
        /// 遥测站均匀时段报31H
        /// </summary>
        //遥测站均匀时段报31H
        public static final byte RTUEven = 0x31;

//      #region RTU功能码枚举值
      /// <summary>
      /// 遥测站定时报32H
      /// </summary>
      //遥测站定时报32H
      public static final byte RTUTime = 0x32;

        /// <summary>
        /// 遥测站加报报33H
        /// </summary>
        //遥测站加报报33H
        public static final byte RTUAppend = 0x33;

        /// <summary>
        /// 遥测站小时报34H
        /// </summary>
        //遥测站小时报34H
        public static final byte RTUHour = 0x34;

        /// <summary>
        /// 人工置数自报35H
        /// </summary>
        //人工置数自报35H
        public static final byte RTUSelfCustomData = 0x35;

        /// <summary>
        /// 遥测站图片报或中心站查询遥测站图片采集信息
        /// </summary>
        //实时数据37H
        public static final byte RTUPiC= 0x36;

        /// <summary>
        /// 实时数据37H
        /// </summary>
        //实时数据37H
        public static final byte RTURealTime = 0x37;

        /// <summary>
        /// 时段数据38H
        /// </summary>
        //时段数据38H
        public static final byte RTUTimeInterval = 0x38;

        /// <summary>
        /// 人工置数39H
        /// </summary>
        //人工置数39H
        public static final byte RTUCustomData = 0x39;


        /// <summary>
        ///指定要素
        /// </summary>
        //指定要素
        public static final byte RTUKeyElement = 0x3A;

        /// <summary>
        /// 修改配置参数
        /// </summary>
        //修改配置参数
        public static final byte EditConfigure = 0x40;

        /// <summary>
        /// 配置参数
        /// </summary>
        //配置参数
        public static final byte Configure = 0x41;


        /// <summary>
        /// 修改RTU运行参数
        /// </summary>
        //修改RTU运行参数
        public static final byte EditRTURunConfig = 0x42;

        /// <summary>
        /// RTU运行参数
        /// </summary>
        //RTU运行参数
        public static final byte RTURunConfig = 0x43;

        /// <summary>
        /// 查询水泵电机实时工作数据 （未启用）
        /// </summary>
        //查询水泵电机实时工作数据 
        public static final byte RTUWpMotorRealStatus = 0x44;

//      /// <summary>
//      /// RTU状态46H
//      /// </summary>
//      //RTU状态46H
//      public static final byte RTUState = 0x46;



        ///////////////////////////////
        /// <summary>
        ///查询软件版本
        /// </summary>
        //查询软件版本
        public static final byte QueryVersion = 0x45;

        /// <summary>
        ///查询状态与报警
        /// </summary>
        //查询状态与报警
        public static final byte StatusAlarm = 0x46;
 
        /// <summary>
        ///初始化固态数据
        /// </summary>
        //初始化固态数据
        public static final byte InitData = 0x47;

        /// <summary>
        ///恢复出厂设置
        /// </summary>
        //恢复出厂设置
        public static final byte InitFac = 0x48;

        /// <summary>
        ///修改密码
        /// </summary>
        //修改密码
        public static final byte ModfiyPsw = 0x49;

        /// <summary>
        ///设置遥测站时钟
        /// </summary>
        //设置遥测站时钟
        public static final byte SetTime = 0x4A;
        /// <summary>
        ///设置遥测终端IC卡状态 （未启用）
        /// </summary>
        //设置遥测终端IC卡状态 
        public static final byte SetICStatus = 0x4B;
        /// <summary>
        ///控制水泵开关命令/水泵状态信息自报 （未启用）
        /// </summary>
        //控制水泵开关命令/水泵状态信息自报
        public static final byte PumpContrl = 0x4C;
        /// <summary>
        ///控制阀门开关命令/阀门状态信息自报 （未启用）
        /// </summary>
        //控制阀门开关命令/阀门状态信息自报
        public static final byte ValveControl= 0x4D;
        /// <summary>
        ///控制闸门开关命令/闸门状态信息自报 （未启用）
        /// </summary>
        //控制闸门开关命令/闸门状态信息自报
        public static final byte GateContrl  = 0x4E;
        /// <summary>
        ///水量定值控制命令 （未启用）
        /// </summary>
        //水量定值控制命令
        public static final byte WaterControl = 0x4F;
        /// <summary>
        ///查询事件记录
        /// </summary>
        //查询事件记录
        public static final byte AlertEvent = 0x50;
        /// <summary>
        ///中心站查询遥测站时钟 （未启用）
        /// </summary>
        //中心站查询遥测站时钟
        public static final byte ClientTime = 0x51;


       //////////////////////////////////////////////
//        #endregion

//        #region 水资源
        /// <summary>
        ///雨量参数
        /// </summary>
        //雨量参数
        public static final byte SZYRain = 0x59;

        /// <summary>
        ///水位参数
        /// </summary>
        //水位参数
        public static final byte SZYStage = 0x51;

        /// <summary>
        ///流量(水量)参数
        /// </summary>
        //流量(水量)参数
        public static final byte SZYFlow = 0x52;

        /// <summary>
        ///流速参数
        /// </summary>
        //流速参数
        public static final byte SZYSpeed = 0x53;

        /// <summary>
        ///闸位参数
        /// </summary>
        //闸位参数
        public static final byte SZYGateposition = 0x54;

        /// <summary>
        ///功率参数
        /// </summary>
        //功率参数
        public static final byte SZYPower = 0x55;

        /// <summary>
        ///气压参数
        /// </summary>
        //气压参数
        public static final byte SZYPressure = 0x56;

        /// <summary>
        ///风速参数
        /// </summary>
        //风速参数
        public static final byte SZYWind = 0x57;

        /// <summary>
        ///水温参数
        /// </summary>
        //水温参数
        public static final byte SZYWatertemperature = 0x58;

        /// <summary>
        ///水质参数
        /// </summary>
        //水质参数
        public static final byte SZYWaterquality = 0x60;

        /// <summary>
        ///土壤含水率参数
        /// </summary>
        //土壤含水率参数
        public static final byte SZYSoilmoisturecontent = 0x61;

        /// <summary>
        ///蒸发量参数
        /// </summary>
        //蒸发量参数
        public static final byte SZYEvapotranspiration = 0x62;

        /// <summary>
        ///报警或状态参数
        /// </summary>
        //报警或状态参数
        public static final byte SZYAlarmState = 0x63;

        /// <summary>
        ///统计雨量
        /// </summary>
        //统计雨量
        public static final byte SZYTotal = 0x64;

        /// <summary>
        ///水压参数
        /// </summary>
        //水压参数
        public static final byte SZYWatergage = 0x65;

        /// <summary>
        ///认可
        /// </summary>
        //认可
        public static final byte  byteSZYSure = 0x66;
//        #endregion
    }

    /// <summary>
    /// 数据编码
    /// </summary>
    public class InsideEncodeType
    {
        /// <summary>
        /// 未知数值或非法值
        /// </summary>
        //未知数据
        public static final byte None = -1;

        /// <summary>
        /// ASCII编码
        /// </summary>
        //ASCII编码
        public static final byte Ascii = 0;

        /// <summary>
        /// HEX/BCD编码
        /// </summary>
        //HEX/BCD编码
        public static final byte Bcd = 1;
    }
}
