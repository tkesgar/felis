package com.felis.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InputProcessor {

	public static ArrayList<PairStringInt> splitInputText(String t) {
		ArrayList<PairStringInt> result = new ArrayList<>();

		Scanner input = new Scanner(t);

		int comment_depth = 0;
		int line = 1;
		while (input.hasNextLine()) {
			Scanner subinput = new Scanner(input.nextLine());
			while (subinput.hasNext()) {
				String s = subinput.next();
				switch (s) {
					case "{":
						comment_depth++;
						break;
					case "}":
						comment_depth--;
						break;
					default:
						if (comment_depth == 0) {
							result.add(new PairStringInt(s, line));
						}
						break;
				}
			}
			line++;
		}

		return result;
	}

	public static void loadGrammarFromFile(CNFGrammar g, File f) throws FileNotFoundException, IOException {
		FileInputStream ifstream = new FileInputStream(f);
		Scanner fin = new Scanner(ifstream);
		ArrayList<ProductionUnit> units = new ArrayList<>();
		ArrayList<ProductionNonunit> nonunits = new ArrayList<>();
		int line = 1;
		while (fin.hasNextLine()) {
			String s = fin.nextLine().trim();
			String[] splitted = s.split("[\\s]+");
			//for (String ss : splitted) {
			//	System.out.print("_" + ss + "_" + " ");
			//}
			//System.out.println(splitted.length);
			if ((splitted.length == 3) || (splitted.length == 4)) {
				if (splitted[1].equals("->")) {
					if (splitted.length == 3) {
						units.add(new ProductionUnit(splitted[0], splitted[2]));
					} else {
						nonunits.add(new ProductionNonunit(splitted[0], splitted[2], splitted[3]));
					}
				} else {
					throw new IOException("Error encountered in line " + line);
				}
			} else {
				throw new IOException("Error encountered in line " + line);
			}
			line++;
		}
		g.clear();
		for (ProductionUnit p : units) {
			g.addUnitProduction(p);
		}
		for (ProductionNonunit p : nonunits) {
			g.addNonunitProduction(p);
		}
	}

}
