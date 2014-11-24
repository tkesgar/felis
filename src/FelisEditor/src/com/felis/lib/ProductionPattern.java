package com.felis.lib;

import java.util.regex.Pattern;

public class ProductionPattern extends ProductionUnit {

	Pattern pattern;

	public ProductionPattern(String s, String t, Pattern p) {
		super(s, t);
		pattern = p;
	}

	public ProductionPattern(String s, String p) {
		super(s, p);
		pattern = Pattern.compile(p);
	}

	@Override
	public boolean validTerminal(PairStringInt t) {
		return pattern.matcher(t.toString()).matches();
	}

}
