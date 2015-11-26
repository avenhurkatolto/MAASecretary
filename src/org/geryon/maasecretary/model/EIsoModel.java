package org.geryon.maasecretary.model;

public class EIsoModel {
	String isoname;
	String hero;
	String effect;
	String description;
	String location;
	int obtained;

	public EIsoModel(String inName,  String inHero, String inEffect, String inDesc, String inLoc){
		this.isoname = inName;
		this.hero = inHero;
		this.effect = inEffect;
		this.description = inDesc;
		this.location = inLoc;
		this.obtained = 0;
	}

	public EIsoModel(String inName,  String inHero, String inEffect, String inDesc, String inLoc, int inObtained){
		this.isoname = inName;
		this.hero = inHero;
		this.effect = inEffect;
		this.description = inDesc;
		this.location = inLoc;
		this.obtained = inObtained;
	}


	public int getObtained() {
		return obtained;
	}

	public void setObtained(int inObtained) {
		this.obtained = inObtained;
	}

	public String getIsoname() {
		return isoname;
	}

	public void setIsoname(String isoname) {
		this.isoname = isoname;
	}

	public String getHero() {
		return hero;
	}
	public String getEffect() {
		return effect;
	}
	public String getDescription() {
	/*	if (this.description.contains("-")){
			return this.description.substring(this.description.indexOf("-")+2, this.description.length());
		}else {*/
			return description;	
		//}
	}

	public String getLocation() {
		if (this.location.contains("Store ")){
			return this.location.replace("Store ", "Store:").replace("(", "").replace(")", "") + " gold";	
		} else {
			return location;
		}
	}
	public void setHero(String hero) {
		this.hero = hero;
	}
	public void setEffect(String skill) {
		this.effect = skill;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFormattedText(){
		return "ISO name: " + this.getIsoname() +"\n"
				+"Hero: " + this.getHero() +"\n"
				+"Effect: " + this.getEffect() +"\n"
				+"Description: " + this.getDescription() +"\n"
				+"Location(s): " + this.getLocation() +"\n\n";
	}
	public boolean getObtainedBoolean(){
		if (this.obtained == 0){
			return false;
		} else {
			return true;
		}
	}
	public void toggleObtained(){
		if (this.obtained == 0){
			this.obtained = 1;
		} else {
			this.obtained = 0;
		}
	}

	public void setObtainedBoolean(boolean inObtained){
		if (inObtained){
			this.obtained = 1; 
		}else {
			this.obtained = 0;
		}
	}
}