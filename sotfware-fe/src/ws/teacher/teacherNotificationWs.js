// webSocket.js
import {ElMessage} from "element-plus";
import {wsBackendEndpoint} from "@/configuration/context.js";

const text1 = "您有新的订单，请及时处理！";
let ws = null;// 判断当前浏览器是否支持WebSocket
let isOpen = false;
const server = `ws://` + wsBackendEndpoint + `/teacher/notice/`;// WebSocket服务地址
const notif = new Audio('sound/notification.wav');
let cid = null;

// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
	ws.close();
	isOpen = false;
};
// 创建实例websocket
let createWebSocket = (server) => {
	if (isOpen) {
		console.log("ws已连接，无需继续创建")
		return
	}
	try {
		if('WebSocket' in window){
			ws = new WebSocket(server)
			isOpen = true
			console.log("ws已连接")
		} else  {
			ElMessage.error("当前浏览器不支持websocket协议,建议使用现代浏览器")
		}
		// 连接建立时触发
		initEventHandle()
	} catch (e) {
		console.log("ERR-----------捕获异常", e)
	}
};
// 初始化事件函数
let initEventHandle = () => {
	// 连接报错
	ws.onerror = function (evt, e) {

	};
	// 连接关闭
	ws.onclose = function (evt) {
		console.log("连接关闭---" + new Date().toLocaleTimeString());
		isOpen = false;
	};
	// 链接成功
	ws.onopen = function (evt) {
		// heartCheck.reset().start();// 心跳检测重置
		// count = 0;// 重置重连次数
	};
	// 接受数据
	ws.onmessage = function (evt) {// 如果获取到消息，心跳检测重置
		// heartCheck.reset().start();// 拿到任何消息都说明当前连接是正常的
		let eventData  = undefined;
		try {
			console.log("收到消息：" + evt.data)
			ElMessage.info(evt.data)
			notif.play();
			// eventData = JSON.parse(evt.data);
			// handMsg(eventData)
		}catch (e) {
			console.log("捕获异常: 当前返回的数据不能解析;");
			console.log("内容：" + evt.data)
		}
	};
};
// 心跳检测
// const heartCheck = {
// 	timeout: 25000,        // 设置心跳时间
// 	timeoutObj: null,
// 	serverTimeoutObj: null,
// 	reset: function () {
// 		clearTimeout(this.timeoutObj);
// 		clearTimeout(this.serverTimeoutObj);
// 		return this;
// 	},
// 	start: function () {
// 		const self = this;
// 		this.timeoutObj = setTimeout(function () {
// 			// 这里发送一个心跳，后端收到后，返回一个心跳消息，onmessage拿到返回的心跳就说明连接正常
// 			ws.send("ping--------------Ping");
// 			self.serverTimeoutObj = setTimeout(function () {
// 				// 如果超过一定时间还没重置，说明后端主动断开了
// 				// 如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
// 				ws.close();
// 			},self.timeout)
// 		},this.timeout)
// 	}
// };
// 处理消息
// let handMsg = (eventData) => {
// 	if (ws.readyState === WebSocket.OPEN) {
// 		if (eventData.code === "2000" && eventData.type === "order_notice") {
// 			Notification({message: text1,type:"warning"})
// 		}
// 	}
// };
export let startTeacherNotifWebSocket = (cid) => {
	createWebSocket(server + cid)
};

export let closeTeacherNotifWebSocket = (cid) => {
	ws.close()
	isOpen = false
}

