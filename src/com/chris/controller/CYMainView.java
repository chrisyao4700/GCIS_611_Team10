package com.chris.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.chris.connection.CYDataHandler;
import com.chris.connection.CYDataReader;
import com.chris.implement.CYViewRefresher;
import com.chris.model.CYFamilyPlan;

import chris.com.uielements.CYButton;
import chris.com.uielements.CYScrollPanel;
import chris.com.uielements.CYTable;
import chris.com.uielements.CYView;

public class CYMainView extends CYView implements CYViewRefresher {
	private String[] listColumn;
	private String[][] listContent;

	private CYDataReader dataReader;

	private ArrayList<CYFamilyPlan> planList;
	private CYViewRefresher delegate;

	public CYMainView(String title, JFrame _lastFrame) {
		super(title, _lastFrame);
		this.frame.setSize(800, 600);

		dataReader = new CYDataHandler();

		frame.setLayout(null);

		this.refresh();

		delegate = this;
		planList = dataReader.getPlanList();
		listColumn = CYFamilyPlan.getStringColumnNames();
		for (int i = 0; i < planList.size(); i++) {
			String[] row = planList.get(i).getStringContent();
			for (int j = 0; j < 8; j++) {
				listContent[i][j] = row[j];
			}
		}

		CYScrollPanel tableScroll = new CYScrollPanel(800, 500, 0, 0,
				createTable());
		

		frame.getContentPane().add(tableScroll.getScrollPanel());

		/*
		 * ******** For Test ********
		 * 
		 * listContent = new String[2][8]; listColumn =
		 * CYFamilyPlan.getStringColumnNames();
		 * 
		 * 
		 * CYFamilyPlan yuan = new
		 * CYFamilyPlan("Yuan","8143843885","4GB","5","2"); CYFamilyPlan jielu =
		 * new CYFamilyPlan("Jielu","8145664700", "10GB", "2", "5");
		 * 
		 * for(int i=0;i<8;i++){ String[] contentStr = yuan.getStringContent();
		 * listContent[0][i] = contentStr[i]; } for(int i=0;i<8;i++){ String[]
		 * contentStr = jielu.getStringContent(); listContent[1][i] =
		 * contentStr[i]; }
		 */

	}

	@Override
	protected JPanel createPanel() {
		JPanel panel = super.createPanel();
		panel.setLocation(0, 500);
		CYButton addButton = new CYButton(100, 30, 20, 20, "Add");
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CYAddView addView = new CYAddView("Add A New Family Plan",
						frame, delegate);
				addView.init();

			}

		});
		CYButton editButton = new CYButton(100, 30, 680, 20, "Edit");
		editButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				CYEditView editView = new CYEditView("Edit Family Plan", frame, delegate);
				editView.init();
				
			}
			
		});
		//CYButton updateButton = new CYButton(100, 30, 400, 20, "Update");

		panel.add(addButton.getButton());
		panel.add(editButton.getButton());
		//panel.add(updateButton.getButton());

		return panel;
	}

	private JTable createTable() {

		CYTable table = new CYTable(750, 600, 0, 0, listColumn, listContent);

		return table.getTable();
	}

	@Override
	public void refresh() {
		planList = dataReader.getPlanList();
		listColumn = CYFamilyPlan.getStringColumnNames();
		listContent = new String[planList.size()][8];
		for (int i = 0; i < planList.size(); i++) {
			String[] row = planList.get(i).getStringContent();
			for (int j = 0; j < 8; j++) {
				listContent[i][j] = row[j];
			}
		}

		CYScrollPanel tableScroll = new CYScrollPanel(800, 500, 0, 0,
				createTable());
		frame.getContentPane().removeAll();
		frame.getContentPane().add(tableScroll.getScrollPanel());
		super.init();
		//System.out.println("I am working!");
	}

	@Override
	public ArrayList<CYFamilyPlan> getPlanList() {
		// TODO Auto-generated method stub
		return this.planList;
	}

}
