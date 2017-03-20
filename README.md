# demo_DBPool
动手写的一个数据库连接池的小程序，关键是synchronized的使用



说明如下：
com.my.pool.Pools 构造方法设置成protected,Pools实例只能通过PoolsManager获取的。
com.my.pool.PoolConnection 改造的Connection。
com.my.pool.PoolsManager 单例模式得到Pools实例。

com.my.test.Test 测试类
