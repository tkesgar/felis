/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.felis.lib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeSet;

public class CNFGrammar extends Observable implements Serializable {

	private TreeSet<ProductionUnit> units;
	private TreeSet<ProductionNonunit> nonunits;
	private String start;

	public CNFGrammar() {
		units = new TreeSet<>();
		nonunits = new TreeSet<>();
	}
        
        public void addAlias(String alias, String origin) {
                ArrayList<ProductionUnit> newUnits = new ArrayList<>();
                ArrayList<ProductionNonunit> newNonunits = new ArrayList<>();
                
                for (ProductionUnit p : units) {
                    if (p.getSymbol().equals(origin)) {
                        if (p instanceof ProductionPattern) {
                            newUnits.add(new ProductionPattern(alias, p.getTerminal()));
                        } else {
                            newUnits.add(new ProductionUnit(alias, p.getTerminal()));
                        }
                    }
                }
                
                for (ProductionNonunit p : nonunits) {
                    if (p.getSymbol().equals(origin)) {
                        newNonunits.add(new ProductionNonunit(alias, p.getLeft(), p.getRight()));
                    }
                }
                
                for (ProductionUnit p : newUnits) {
                    addUnitProduction(p);
                }
                for (ProductionNonunit p : newNonunits) {
                    addNonunitProduction(p);
                }
	}

	public void addUnitProduction(ProductionUnit p) {
		boolean found = false;
		for (ProductionUnit q : units) {
			if (q.getSymbol().equals(p.getSymbol()) && q.getTerminal().equals(p.getTerminal())) {
				found = true;
			}
			if (found) {
				break;
			}
		}
		if (!found) {
			units.add(p);
			setChanged();
			notifyObservers();
		}
	}

	public void addNonunitProduction(ProductionNonunit p) {
		boolean found = false;
		for (ProductionNonunit q : nonunits) {
			if (q.getSymbol().equals(p.getSymbol()) && q.getLeft().equals(p.getLeft()) && q.getRight().equals(p.getRight())) {
				found = true;
			}
			if (found) {
				break;
			}
		}
		if (!found) {
			nonunits.add(p);
			setChanged();
			notifyObservers();
		}
	}

	public void clear() {
		units.clear();
		nonunits.clear();
		start = null;
		setChanged();
		notifyObservers();
	}

	public boolean check(ArrayList<PairStringInt> inputs) {
		return CYKAlgorithm.check(inputs, start, getUnits(), getNonunits());
	}

	public CYKParseTree getParseTree(ArrayList<PairStringInt> inputs) {
		return CYKAlgorithm.getParseTree(inputs, start, getUnits(), getNonunits());
	}

	public TreeSet<ProductionUnit> getUnits() {
		return units;
	}

	public TreeSet<ProductionNonunit> getNonunits() {
		return nonunits;
	}

	public String getStart() {
		return start;
	}
	
	public void setStart(String s) {
		start = s;
		setChanged();
		notifyObservers();
	}

	public static boolean isErrorAtLastRun() {
		return CYKAlgorithm.isErrorAtLastRun();
	}

	public static int getErrorLineAtLastRun() {
		return CYKAlgorithm.getErrorLineAtLastRun();
	}

}
