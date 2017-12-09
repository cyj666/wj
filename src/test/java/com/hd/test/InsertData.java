package com.hd.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.Test;

public class InsertData {
	
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/data", "root", "root");
		PreparedStatement ps = null;
//		for (int i = 1; i < 31; i++) {
//			String sql = "INSERT INTO p_ot VALUES('PAS02507611','D101',0,36,135,5,'2017-08-"+i+"')";
//			ps = conn.prepareStatement(sql);
//			ps.execute();
//		}
		String sql = "INSERT INTO p_ot VALUES('HAS04412211','C102',41,145,248,18,'2017-08-18')";
		ps = conn.prepareStatement(sql);
		ps.execute();
		ps.close();
	}
	
	@Test
	public void test() {
		String batchNo = "'P','H','F'";
		System.out.println(batchNo);
		System.out.println(batchNo.substring(0, 1));
		
		String reg1 = "^[A-Z]{1}[0-9]{3}-[0-9]{1,3}(,[0-9]{1,3}){0,12}$";
		String reg3 = "^[A-Z]{1}[0-9]{3}-[0-9]{1,2}\\-[0-9]{1,2}$";
		
		System.out.println(reg1.matches("F123,H201"));
		System.out.println(reg3.matches("A12,G02"));
	}
}
