# demo_DBPool
动手写的一个数据库连接池的小程序，关键是synchronized的使用

实现功能点有可以设置
初始化连接数
最大连接数

如果连接不够，则创建连接直到最大连接数位置

说明如下：
com.my.pool.Pools 构造方法设置成protected,Pools实例只能通过PoolsManager获取的。
com.my.pool.PoolConnection 改造的Connection。
com.my.pool.PoolsManager 单例模式得到Pools实例。

com.my.test.Test 测试类
