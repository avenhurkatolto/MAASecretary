package org.geryon.maasecretary.model;

public class OvenModel {
	
	int lvl;
	int time;
	
	public OvenModel(Integer inLvl, Integer inTime) {
		this.lvl = inLvl;
		this.time = inTime;
	}

	public int getLvl() {
		return lvl;
	}

	public int getTime() {
		return time;
	}

	
}
