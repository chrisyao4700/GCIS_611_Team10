package com.chris.connection;

import com.chris.model.CYFamilyPlan;

public interface CYDataWriter {
	

	public void write(CYFamilyPlan plan);
	public void erase(CYFamilyPlan plan);
}
