package org.geryon.maasecretary.parser;

import android.util.Log;

import java.util.ArrayList;

import org.geryon.maasecretary.model.AIsoModel;
import org.geryon.maasecretary.model.EIsoModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

//import android.util.Log;

public class IsoParser {
	static String TAG = "IsoParser";
	public static ArrayList<EIsoModel> parse_EISO (String text){

		text = text.substring(text.indexOf("<body"), text.indexOf("</body>"));
		Document doc = Jsoup.parse(text);
		Elements elems = doc.select("body div section article table");
		ArrayList<EIsoModel> eisos = new ArrayList<EIsoModel>();

		for (int i = 0; i <elems.size(); i++){
			Log.w("Table_", elems.get(i).text());
			if((elems.get(i).text().contains("Character")) && (!elems.get(i).text().contains("(Agent)"))){
				Elements isoelems = elems.get(i).select("tr");
				for (int j = 2; j<isoelems.size();j++){
					if (!isoelems.get(j).select("td").get(0).hasAttr("rowspan")){
						Log.w("ro number", String.valueOf(j));
						eisos.add(new EIsoModel(removeExcessName(isoelems.get(j).select("td").get(1).text()),
								isoelems.get(j).select("td").get(3).text(),
								isoelems.get(j).select("td").get(2).text(),
								isoelems.get(j).select("td").get(2).select("a").attr("title"),
								isoelems.get(j).select("td").get(5).text()));



					} else {
						int tempRowSpan = Integer.valueOf(isoelems.get(j).select("td").attr("rowspan"));
						String multiNames = isoelems.get(j).select("td").get(3).text();

						for (int k = 1; k < tempRowSpan; k++){
							multiNames +="; "+ isoelems.get(j+k).select("td").get(0).text();
						} 
						eisos.add(new EIsoModel(removeExcessName(isoelems.get(j).select("td").get(1).text()),
								multiNames,
								isoelems.get(j).select("td").get(2).text(),
								isoelems.get(j).select("td").get(2).select("a").attr("title"),
								isoelems.get(j).select("td").get(5).text()));


						j += Integer.valueOf(isoelems.get(j).select("td").attr("rowspan"))-1;

					}
				}
			}
		}
		return eisos;

	}

	public static ArrayList<AIsoModel> parse_AISO (String text){
		text = text.substring(text.indexOf("<body"), text.indexOf("</body>"));
		Document doc = Jsoup.parse(text);
		Elements elems = doc.select("body div section article table");
		ArrayList<AIsoModel> aisos = new ArrayList<AIsoModel>();

		for (int i = 0; i <elems.size(); i++){
			if(elems.get(i).text().contains("Character")){
				Elements isoelems = elems.get(i).select("tr");
				for (int j = 2; j<isoelems.size();j++){
					aisos.add(new AIsoModel(removeExcessName(isoelems.get(j).select("td").get(1).text()),
							isoelems.get(j).select("td").get(3).text(),
							isoelems.get(j).select("td").get(2).text(),
							isoelems.get(j).select("td").get(2).select("a").attr("title").replace("; ", "\n"),
							isoelems.get(j).select("td").get(5).text(),
							isoelems.get(j).select("td").get(4).text()));
				}
			}
		}
		return aisos;
	}

	private static String removeExcessName(String inString){
		if (inString.contains("Empowered Iso-8")){
			return inString.replace("Empowered Iso-8", "");
		} else if (inString.contains("Augmented Iso-8")){
			return (inString.replace("Augmented Iso-8", ""));
		}else if (inString.contains(" Iso-8")){
			return (inString.replace(" Iso-8", ""));
		} else {
			return inString;
		}
	}


}