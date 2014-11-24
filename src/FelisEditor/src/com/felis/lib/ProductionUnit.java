package com.felis.lib;

import java.io.Serializable;

public class ProductionUnit implements Serializable, Comparable {

	private String symbol;
	private String terminal;

	public ProductionUnit(String s, String t) {
		symbol = s;
		terminal = t;
	}

	public String getSymbol() { return symbol; }
	public String getTerminal() { return terminal; }

	public boolean validTerminal(PairStringInt t) {
		return terminal.equals(t.toString());
	}

	@Override
	public int compareTo(Object o) {
		ProductionUnit p = (ProductionUnit) o;
		int symbolCompare = symbol.compareTo(p.getSymbol());
		int terminalCompare = terminal.compareTo(p.getTerminal());
		return (symbolCompare != 0) ? symbolCompare : terminalCompare;
	}
	
}
