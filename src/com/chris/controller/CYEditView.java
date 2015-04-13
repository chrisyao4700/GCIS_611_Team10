package com.chris.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.chris.connection.CYDataHandler;
import com.chris.connection.CYDataWriter;
import com.chris.implement.CYViewRefresher;
import com.chris.model.CYFamilyPlan;

import chris.com.uielements.CYButton;
import chris.com.uielements.CYLabel;
import chris.com.uielements.CYTextField;
import chris.com.uielements.CYView;


public class CYEditView extends CYView{

	private CYViewRefresher gate;
	private ArrayList<CYFamilyPlan> planList;
	private CYFamilyPlan currentPlan;
	ArrayList<CYLabel> labels;
	ArrayList<CYTextField> fields;
	
	public CYEditView(String title, JFrame _lastFrame, CYViewRefresher _gate) {
		super(title, _lastFrame);
		this.gate = _gate;
		planList = this.gate.getPlanList();
		
	}
	
	@Override
	protected JPanel createPanel(){
		JPanel panel = super.createPanel();
		labels = this.createLabels();
		fields = this.createTextFields();
		
		CYLabel groupLabel = new CYLabel(200,30,50,30,"Group: ");
		panel.add(groupLabel.getLabel());
		
		JComboBox<String> box = this.createBox();
		box.setSize(200, 30);
		box.setLocation(300, 30);
		panel.add(box);
		
		for(CYLabel label : labels){
			panel.add(label.getLabel());
		}
		for(CYTextField field : fields){
			panel.add(field.getTextField());
		}
		
		CYButton doneButton = new CYButton(100, 30, 200, 500,"Done");
		doneButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean flag = true;
				ArrayList<String> values = new ArrayList<String>();
				for (CYTextField field : fields) {
					if (field.getText() != null) {
						values.add(field.getText());
					} else {
						flag = false;
						JOptionPane.showMessageDialog(frame,
								"Information is not completed!");
						break;
					}
				}
				if (flag == true) {
					currentPlan = new CYFamilyPlan(values.get(0),
							values.get(1), values.get(2), values.get(3),
							values.get(4));
					CYDataWriter writer = new CYDataHandler();
					writer.erase(currentPlan);
					writer.write(currentPlan);
					lastFrame.setVisible(true);
					gate.refresh();
					frame.dispose();
				}
				
			}
			
		});
		
		panel.add(doneButton.getButton());
		CYButton deleteButton = new CYButton(100, 30, 500, 500 ,"Delete" );
		deleteButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				boolean flag = true;
				ArrayList<String> values = new ArrayList<String>();
				for (CYTextField field : fields) {
					if (field.getText() != null) {
						values.add(field.getText());
					} else {
						flag = false;
						JOptionPane.showMessageDialog(frame,
								"Information is not completed!");
						break;
					}
				}
				if (flag == true) {
					currentPlan = new CYFamilyPlan(values.get(0),
							values.get(1), values.get(2), values.get(3),
							values.get(4));
					CYDataWriter writer = new CYDataHandler();
					writer.erase(currentPlan);
					
					lastFrame.setVisible(true);
					gate.refresh();
					frame.dispose();
				}
				
			}
			
		});
		panel.add(deleteButton.getButton());
		
		
		return panel;
	}

	private ArrayList<CYLabel> createLabels() {
		ArrayList<CYLabel> labelList = new ArrayList<CYLabel>();
		ArrayList<String> keys = CYFamilyPlan.getColumnNames();
		for (int i = 0; i < 5; i++) {
			int yPosition = 130 + (i * 80);
			String title = keys.get(i) + ":  ";
			CYLabel label = new CYLabel(200, 30, 50, yPosition, title);
			labelList.add(label);
		}
		return labelList;
	}
	
	private ArrayList<CYTextField> createTextFields() {
		ArrayList<CYTextField> fieldList = new ArrayList<CYTextField>();

		for (int i = 0; i < 5; i++) {
			int yPosition = 130 + (i * 80);
			CYTextField textField = new CYTextField(200, 30, 300, yPosition, 20);
			fieldList.add(textField);
		}
		return fieldList;
	}
	
	private JComboBox<String> createBox(){
		JComboBox<String> box = new JComboBox<String>(this.converPlanList());
		box.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				currentPlan = planList.get(box.getSelectedIndex());
				HashMap<String,String> values = currentPlan.getContentHashMap();
				ArrayList<String> keys = CYFamilyPlan.getColumnNames();
				for(int i =0; i<fields.size();i++){
					fields.get(i).setText(values.get(keys.get(i)));
				}
			}
			
		});
		return box;
	}
	private String[] converPlanList(){
		String[] groupTitles = new String[planList.size()];
		for(int i = 0 ; i< planList.size();i++){
			groupTitles[i] = planList.get(i).getGroupName();
		}
		return groupTitles;
	}
}
