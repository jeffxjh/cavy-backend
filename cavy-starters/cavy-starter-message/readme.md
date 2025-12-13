1.基于rabbitmq实现以下功能
2.定义一个接口BaseListen,有一个方法onMessage,业务上实现这个接口就可以接收指定消息
3.定义一个工具类MsgUtil,实现两个方法,sendMsg(String key,Object data,boolean isDurable)
4.第一个参数为发送到哪个Listen;data为业务数据;isDurable为是否持久化数据,意思是需要新建一张log表记录发送的msg数据,用于后续补偿数据,表关键信息是包含
是否已经消费,时间,用户,是否需要重发和其他.

### 如何使用
参考com.jh.configure.boot.OrderListen
1.定义一个类,实现BaseListen接口
2.添加注解@MsgListen("order.msg"),定义业务key
3.业务逻辑写在onMessage即可

todo 
1.消息对象封装
2.清理无效交换机和队列
3.支持多种类型交换机
4.事务支持
5.补充机制
