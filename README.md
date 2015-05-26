# 智能家居开关控制
-----

注意 开关控制如不能及时响应,检查电源线

## 基本使用及原理介绍

开关sysfs控制

/sys/class/gpio/rf315-x

目前x支持0-3(4个通道)

	接法:
	数据口
	mtall 板子 j11 座 -> 315Mhz 无线模块 Dx(L c H)
		1     DTR_N     D1(c)
		2     DCO_N     D2(c)
		3     RIN       D3(c)
		5     DSR_N     D4(c)
		
	电源
	mtall 板子 j10 座 -> 315Mhz 无线模块 j2
		7     3.3v      +
		9     GND       -
	or
	mtall 板子 j14 座 -> 315Mhz 无线模块 j2
	
### 手工测试

开 无线通道0

	$> echo 1 > /sys/class/gpio/rf315-0

关 无线通道0

	$> echo 0 > /sys/class/gpio/rf315-0
	
#### 进入开发版的方法

##### root 密码

	username : root
	password : seecha888
	
##### wifi 密码

	AP SSID: SEWAGEMON-XXXXXX
	AP WAP2: 12345678
	
##### 接入程序

	串口调试程序 或 ssh root@192.168.1.1
	
### 系统测试工具

#### rf315 测试工具 rf315m

	$> rf315m [0~3]

## 系统服务 smarthome
-----

### 原理

mqtt协议

系统必须启用 mosqitto 服务 (默认已经启动)

	$> /etc/init.d/mosquitto enable
	$> /etc/init.d/mosquitto start

#### 系统服务的启动

启动、停止、重启、启用开机自启、禁止开机自启 应用

	$> /etc/init.d/smarthome [start|stop|restart|enable|disable] 

服务配置脚本 ‘/etc/config/smarthome’

	config smarthome general
    option hostip   '127.0.0.1'
    option port     '1883'
    option keeplive '30'
    option debug    ''
    
编辑配置脚本 debuidebug 选项并重启服务后,将会在控制台打印来往数据

	$> uci set smarthome.general.debug="-d"
	$> uci commit smarthome
	$> /etc/init.d/smarthome restart

调试可以手工启动(注意后面有个d)

	$> smarthomed [-H HOST -P PORT -K SECOND ] -d &

#### MQTT订阅参数

服务器IP

	127.0.0.1 #接入开发板ap host
	
端口

	1883

主题topic

	smarthome/rf315-x
	
测试方法 (示例: 通道1 按下)
	
	mosquitto_pub -t smarthome/rf315-x -m '{"api": "v1.0.0", "service": "smarthome-mqtt", "id": 1}'

#### 测试系统服务 trf315m

命令参数 channel是0~3通道 delay是延时(默认0.15s) pressed和released是模拟按键按下和抬起

	$> trf315m [{channel}] [{delay}] [pressed|released]

默认多命令持续测试

	$> trf315m
	
调试及打开Json输出

	$> export _DEBUG=1
	$> #单条命令 
	$> trf315m 1
	{
    "api": "v1.0.0",
    "service": "smarthome-mqtt",
    "id": 1
	}
	$> #通道3按下
	$> trf315m 3 - pressed
	{
	  "api": "v1.0.0",
	  "service": "smarthome-mqtt",
	  "id": 3,
	  "mode": "pressed"
	}
	$> #通道3释放
	$> trf315m 3 - released
	{
	  "api": "v1.0.0",
	  "service": "smarthome-mqtt",
	  "id": 3,
	  "mode": "released"
	}
	$> #通道2 按下 2 秒
	$> trf315m 2 2
	{
	  "api": "v1.0.0",
	  "service": "smarthome-mqtt",
	  "id": 2,
	  "delay": 2
	}
	$> #连续多条命令 wait是命令间延迟 
	$> trf315m
	{
	  "api": "v1.0.0",
	  "service": "smarthome-mqtt",
	  "commands": [
	    {
	      "id": 0,
	      "wait": 1
	    },
	    {
	      "id": 1,
	      "delay": 1,
	      "wait": 1
	    },
	    {
	      "id": 2,
	      "wait": 1
	    },
	    {
	      "id": 3,
	      "delay": 2,
	      "wait": 1
	    },
	    {
	      "id": 1,
	      "mode": "pressed",
	      "delay": 3,
	      "wait": 1
	    },
	    {
	      "id": 1,
	      "mode": "released"
	    }
	  ]
	}
	
注意delay/wait最大值为600s(10分钟)

delay是pressed按下与released释放之间的时间长度,wait是两次按键之间的时间长度

delay最小是70ms即0.07s,默认是0.15s,太短时间,不确保发送数据(315模块特性)

