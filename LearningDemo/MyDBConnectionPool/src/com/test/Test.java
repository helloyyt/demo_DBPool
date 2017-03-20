package com.test;

import java.sql.ResultSet;
import java.sql.Statement;

import com.my.pool.PoolConnection;
import com.my.pool.Pools;
import com.my.pool.PoolsManager;

public class Test {

	
	public static void main(String[] args) {
		for(int i=0;i<100;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//Pools pools2 = new Pools();
					Pools pools = PoolsManager.getPoolsInstance();
					try {
						PoolConnection pc = pools.getConnection();
						Statement st = pc.getConn().createStatement();
						ResultSet rs = st.executeQuery("select * from Items");
						Thread.sleep(1000);
						System.out.println(Thread.currentThread().getName()+" get connection "+pc.getName());
						while(rs.next()){
							System.out.println(rs.getString("id")+","+rs.getString("name")+","+rs.getString("price"));
						}
						pc.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}).start();
		}

	}

}
