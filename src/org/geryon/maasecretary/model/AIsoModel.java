package org.geryon.maasecretary.model;

public class AIsoModel extends EIsoModel{
	
	private String action = "";
	
	public AIsoModel(String inName, String inHero, String inEffect,
			String inDesc, String inLoc, String inAction) {
		super(inName, inHero, inEffect, inDesc, inLoc);
		this.action = inAction;
		this.obtained = 0;
	}
	
	public AIsoModel(String inName, String inHero, String inEffect,
			String inDesc, String inLoc, int inObtained, String inAction) {
		super(inName, inHero, inEffect, inDesc, inLoc, inObtained);
		this.action = inAction;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
/*
	@Override
	public String getFormattedText(){

		return "ISO name: " + this.getIsoname() +"\n"
				+"Hero: " + this.getHero() +"\n"
				+"Effect: " + this.getEffect() +"\n"
				+"Description: " + this.getDescription() +"\n"
				+"Action(s): "+ this.getAction() +"\n"
				+"Location(s): " + this.getLocation() +"\n\n";
	}
	*/
}