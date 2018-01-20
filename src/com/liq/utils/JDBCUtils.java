package com.liq.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {

	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	public static ComboPooledDataSource getDataSource() {
		return dataSource;
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
