package com.chris.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import chris.com.jdbchandler.CYGoogleCloudConnector;
import com.chris.model.CYFamilyPlan;

public class CYDataHandler implements CYDataReader, CYDataWriter {

	private CYFamilyPlan currentPlan;
	private ArrayList<CYFamilyPlan> planList;
	private Connection dbConn;
	private ArrayList<String> keys;

	public CYDataHandler() {
		CYGoogleCloudConnector connector = new CYGoogleCloudConnector(
				"69.195.124.95", "chrisyao_CYDatabase", "chrisyao_4700",
				"yy900908");
		dbConn = connector.getDBConnection("chrisyao_CYDatabase");
		keys = CYFamilyPlan.getColumnNames();

		this.configPlanList();

	}

	private void configPlanList() {

		ResultSet rs;

		PreparedStatement ps;
		// String[] colNames = null;
		planList = new ArrayList<CYFamilyPlan>();
		try {
			ps = this.dbConn.prepareStatement("SELECT * FROM `plan_community`");
			rs = ps.executeQuery();

			while (rs.next()) {
				HashMap<String, String> row = new HashMap<String, String>();
				for (int i = 0; i < 8; i++) {
					row.put(keys.get(i), rs.getString(keys.get(i)));

				}
				// System.out.println(row);
				CYFamilyPlan plan = new CYFamilyPlan(row);
				planList.add(plan);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void writePlanToDatabase() {
		String query = "INSERT INTO  `chrisyao_CYDatabase`.`plan_community` (`";
		for (String str : keys) {
			query = query + str + "`, `";
		}
		query = query.substring(0, query.length() - 3) + ") VALUES ('";
		for (String str : currentPlan.getContentArray()) {
			query = query + str + "', '";
		}
		query = query.substring(0, query.length() - 3) + ");";
		try {
			PreparedStatement ps = this.dbConn.prepareStatement(query);
			ps.execute();
		} catch (SQLException e) {
			System.out.println("Cannot insert");
			e.printStackTrace();
		}
		// System.out.println(query);
	}

	private void delatePlanFromDatabase() {
		String query = "DELETE FROM `chrisyao_CYDatabase`.`plan_community` WHERE `plan_community`.`Group_Name` = '";
		query = query + currentPlan.getGroupName() + "';";
		PreparedStatement ps;
		try {
			ps = this.dbConn.prepareStatement(query);
			ps.execute();
		} catch (SQLException e) {
			System.out.println("Cannot erase");
			e.printStackTrace();
		}
		
	}

	@Override
	public void write(CYFamilyPlan plan) {
		this.currentPlan = plan;
		this.writePlanToDatabase();
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<CYFamilyPlan> getPlanList() {
		this.configPlanList();
		// TODO Auto-generated method stub
		return planList;
	}

	@Override
	public void erase(CYFamilyPlan plan) {
		this.currentPlan = plan;
		this.delatePlanFromDatabase();
		
	}

	

}
