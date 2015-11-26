package org.geryon.maasecretary.parser;

import java.util.ArrayList;

import org.geryon.maasecretary.model.OvenModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class OvenParser {
	public static ArrayList<OvenModel> OvenParse(String text){
		ArrayList<OvenModel> ovens = new ArrayList<OvenModel>();
		Document doc = Jsoup.parse(text);
		Elements ovenelems = doc.select("train");
		for (int i = 0; i<ovenelems.size();i++){
			
			ovens.add( new OvenModel ( 
					Integer.valueOf(ovenelems.get(i).attr("level")),
					Integer.valueOf(ovenelems.get(i).text())
					)
			);								
	
		}
		
		
		return ovens;


	}
}
