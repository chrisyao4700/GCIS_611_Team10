package com.chris.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class CYAddView extends CYView {

	private ArrayList<CYLabel> labels;
	private ArrayList<CYTextField> fields;
	private CYFamilyPlan currentPlan;

	private CYViewRefresher gate;
	
	public CYAddView(String title, JFrame _lastFrame, CYViewRefresher _gate) {
		super(title, _lastFrame);
		gate = _gate;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected JPanel createPanel() {
		JPanel panel = super.createPanel();
		labels = createLabels();
		fields = createTextFields();

		// Set all Labels.
		for (CYLabel label : labels) {
			panel.add(label.getLabel());
		}
		for (CYTextField field : fields) {
			panel.add(field.getTextField());
		}

		CYButton done = new CYButton(150, 30, 325, 510, "Done");
		done.addActionListener(createDoneListener());
		panel.add(done.getButton());

		return panel;
	}

	private ActionListener createDoneListener() {
		ActionListener pushDone = new ActionListener() {

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
					writer.write(currentPlan);
					lastFrame.setVisible(true);
					gate.refresh();
					frame.dispose();
				}

			}

		};
		return pushDone;
	}

	private ArrayList<CYTextField> createTextFields() {
		ArrayList<CYTextField> fieldList = new ArrayList<CYTextField>();

		for (int i = 0; i < 5; i++) {
			int yPosition = 30 + (i * 80);
			CYTextField textField = new CYTextField(200, 30, 300, yPosition, 20);
			fieldList.add(textField);
		}
		return fieldList;
	}

	private ArrayList<CYLabel> createLabels() {
		ArrayList<CYLabel> labelList = new ArrayList<CYLabel>();
		ArrayList<String> keys = CYFamilyPlan.getColumnNames();
		for (int i = 0; i < 5; i++) {
			int yPosition = 30 + (i * 80);
			String title = keys.get(i) + ":  ";
			CYLabel label = new CYLabel(200, 30, 50, yPosition, title);
			labelList.add(label);
		}
		return labelList;
	}

}
