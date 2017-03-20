package com.my.pool;

public class PoolsManager {

	static class Nested  {
		static Pools pools=new Pools();
	}
	
	private PoolsManager(){};
	
	
	public static Pools getPoolsInstance() {
		return Nested.pools;
	}

}
