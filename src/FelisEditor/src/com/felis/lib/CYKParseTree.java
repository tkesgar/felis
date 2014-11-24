package com.felis.lib;

public class CYKParseTree implements Comparable {
	
	private String node;
	private PairStringInt terminal;
	private CYKParseTree left;
	private CYKParseTree right;

	public CYKParseTree(String n, PairStringInt t) {
		node = n;
		terminal = t;
		left = null;
		right = null;
	}

	public CYKParseTree(String n, CYKParseTree l, CYKParseTree r) {
		node = n;
		terminal = null;
		left = l;
		right = r;
	}

	@Override
	public String toString() {
		if (terminal == null) {
			return node.toString();
		} else {
			return terminal.toString();
		}
	}

	public String getNode() {
		return node;
	}

	public CYKParseTree getLeft() {
		return left;
	}

	public CYKParseTree getRight() {
		return right;
	}

	@Override
	public int compareTo(Object o) {
		CYKParseTree tree = (CYKParseTree) o;
		return node.compareTo(tree.node);
	}

}
