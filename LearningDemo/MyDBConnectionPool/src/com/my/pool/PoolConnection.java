package com.my.pool;

import java.sql.Connection;

public class PoolConnection {

	private boolean isBusy;
	private Connection conn;
	private String name;
	public PoolConnection(Connection conn,boolean isBusy) {
		super();
		this.isBusy = isBusy;
		this.conn = conn;
	}
	public PoolConnection( Connection conn,boolean isBusy, String name) {
		super();
		this.isBusy = isBusy;
		this.conn = conn;
		this.name = name;
	}
	public boolean isBusy() {
		return isBusy;
	}
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public void close() {
		setBusy(false);
	}
	public String getName() {
		return name;
	}
	
}
