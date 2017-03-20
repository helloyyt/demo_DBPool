package com.my.pool;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;
import java.util.Vector;

public final class Pools {
	private Vector<PoolConnection> pools = new Vector<PoolConnection>();
	private int poolMaxConnection=20;
	private int poolInitConnection=1;
	public static final String url = "jdbc:mysql://192.168.1.108/test";  
	public static final String name = "com.mysql.jdbc.Driver";  
	public static final String user = "root";  
	public static final String password = "111111";  

	//ֻ�뱻ͬһpackage��������ĸ���
	protected Pools(){};
	
	private void init() throws Exception{
		if(poolInitConnection<0)
			throw new Exception("poolInitConnection can not less than 0");
		if(poolMaxConnection<0)
			throw new Exception("poolMaxConnection can not less than 0");
		if(poolMaxConnection<poolInitConnection)
			throw new Exception("poolMaxConnection can not less than poolInitConnection");
		
		if(poolInitConnection>0 && pools.isEmpty()){	
			for(int i=0;i<poolInitConnection;i++)
			  pools.addElement(createPoolConnection());			
		}
	}

	private PoolConnection createPoolConnection()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName(name).newInstance();
		Connection conn = DriverManager.getConnection(url, user, password);//��ȡ���� 
		PoolConnection pc = new PoolConnection(conn,false,UUID.randomUUID().toString());
		return pc;
	}

	public PoolConnection getConnection() throws Exception {
		if( pools.isEmpty()){
			synchronized(this){
				if( pools.isEmpty()){
					init();
				}
			}
		}
		return getRealConnetion();
	}
/**
 *  ��������ϼ���synchronized�����ڲ��������Ļ�
 *  ����߳� ��pools.get(i) �õ��п�����ͬһ��.����һ���߳�pc.setBusy(true);���ں��棬
 *  �ڴ�֮ǰ��ֵ����false
 * @return
 * @throws InstantiationException
 * @throws IllegalAccessException
 * @throws ClassNotFoundException
 * @throws SQLException
 */
	private  PoolConnection getRealConnetion() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		PoolConnection pc = null;
		synchronized(this){
			while(pc==null){
				for (int i = 0; i < pools.size(); i++) {
					if(!pools.get(i).isBusy()){
						pc = pools.get(i);
						Connection conn = pc.getConn();
						try {
							if(!conn.isValid(300)){
								conn = DriverManager.getConnection(url, user, password);
								pc.setConn(conn);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				if(pc==null && pools.size()<poolMaxConnection){
					System.out.println("create new connection...");
					pc = createPoolConnection();
					pools.addElement(pc);
				}
				if(pc!=null){
				 pc.setBusy(true);
				 break;
				}
		   }
			//����ѭ������
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return pc;
	}

}
