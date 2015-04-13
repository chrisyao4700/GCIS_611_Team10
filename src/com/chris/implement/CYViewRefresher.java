package com.chris.implement;

import java.util.ArrayList;

import com.chris.model.CYFamilyPlan;

public interface CYViewRefresher {
	public void refresh();
	public ArrayList<CYFamilyPlan> getPlanList();

}
