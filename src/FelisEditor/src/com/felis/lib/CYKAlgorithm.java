package com.felis.lib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

class CYKAlgorithm {

	private static TreeMap<String, TreeSet<CYKParseTree>> dpMap;
	private static boolean errorAtLastRun;
	private static int errorSize;
	private static int errorLine;

	public static boolean isErrorAtLastRun() {
		return errorAtLastRun;
	}

	public static int getErrorLineAtLastRun() {
		return errorAtLastRun ? errorLine : -1;
	}

	private static TreeSet<CYKParseTree> findDP(
		ArrayList<PairStringInt> inputs,
		TreeSet<ProductionUnit> units,
		TreeSet<ProductionNonunit> nonunits
		)
	{
		TreeSet<CYKParseTree> results = new TreeSet<>();

		String key = "";
		for (PairStringInt si : inputs) {
			key += si.toString() + si.getIndex();
		}
		
		if (dpMap.containsKey(key)) {
			//System.out.println(key);
			results = dpMap.get(key);
		} else {

			if (inputs.size() == 1) {
				for (ProductionUnit p : units) {
					if (p.validTerminal(inputs.get(0))) {
						results.add(new CYKParseTree(p.getSymbol(), inputs.get(0)));
					}
				}
			} else {
				for (int i = 0; i < inputs.size() - 1; i++) {
					ArrayList<PairStringInt> leftInputs = new ArrayList<>();
					ArrayList<PairStringInt> rightInputs = new ArrayList<>();
					for (int j = 0; j <= i; j++) {
						leftInputs.add(inputs.get(j));
					}
					for (int j = i + 1; j < inputs.size(); j++) {
						rightInputs.add(inputs.get(j));
					}
					for (ProductionNonunit p : nonunits) {
						TreeSet<CYKParseTree> leftTrees = findDP(leftInputs, units, nonunits);
						TreeSet<CYKParseTree> rightTrees = findDP(rightInputs, units, nonunits);
						for (CYKParseTree leftTree : leftTrees) {
							for (Iterator<CYKParseTree> it = rightTrees.iterator(); it.hasNext();) {
								CYKParseTree rightTree = it.next();
								if (leftTree.getNode().toString().equals(p.getLeft()) && rightTree.getNode().toString().equals(p.getRight())) {
									results.add(new CYKParseTree(p.getSymbol(), leftTree, rightTree));
								}
							}
						}
					}
				}
			}
			dpMap.put(key, results);
			
		}
		if (results.isEmpty()) {
			if (errorSize > inputs.size()) {
				errorSize = inputs.size();
				errorLine = inputs.get(0).getIndex();
			}
		}
		return results;
	}

	public static boolean check(
		ArrayList<PairStringInt> inputs,
		String start,
		TreeSet<ProductionUnit> units,
		TreeSet<ProductionNonunit> nonunits
		)
	{
		dpMap = new TreeMap<>();
		errorSize = inputs.size();
		TreeSet<CYKParseTree> resultTrees = findDP(inputs, units, nonunits);
		for (CYKParseTree resultTree : resultTrees) {
			if (resultTree.getNode().toString().equals(start)) {
				return true;
			}
		}
		errorAtLastRun = true;
		return false;
	}

	public static CYKParseTree getParseTree(
		ArrayList<PairStringInt> inputs,
		String start,
		TreeSet<ProductionUnit> units,
		TreeSet<ProductionNonunit> nonunits
		)
	{
		dpMap = new TreeMap<>();
		errorSize = inputs.size();
		TreeSet<CYKParseTree> resultTrees = findDP(inputs, units, nonunits);
		for (CYKParseTree resultTree : resultTrees) {
			if (resultTree.getNode().toString().equals(start)) {
				return resultTree;
			}
		}
		errorAtLastRun = true;
		return null;
	}
	
}