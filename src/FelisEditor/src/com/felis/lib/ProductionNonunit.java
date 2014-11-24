package com.felis.lib;

import java.io.Serializable;

public class ProductionNonunit implements Serializable, Comparable {
	private String symbol;
	private String left;
	private String right;

	public ProductionNonunit(String s, String l, String r) {
		symbol = s;
		left = l;
		right = r;
	}

	public String getSymbol() { return symbol; }
	public String getLeft() { return left; }
	public String getRight() { return right; }

	@Override
	public int compareTo(Object o) {
		ProductionNonunit p = (ProductionNonunit) o;
		int symbolCompare = symbol.compareTo(p.getSymbol());
		int leftCompare = left.compareTo(p.getLeft());
		int rightCompare = right.compareTo(p.getRight());
		return (symbolCompare != 0) ? symbolCompare : ((leftCompare != 0) ? leftCompare : rightCompare);
	}
}
