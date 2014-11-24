package com.felis.lib;

public class FelisLanguage {
	public static final String SYMBOL_WORD = "[WORD]";
	public static final String SYMBOL_NUMBER = "[NUMBER]";
	public static final String SYMBOL_VARIABLE = "[VARIABLE]";
	public static final String SYMBOL_ANY = "[ANY]";

	public static void fillGrammarWithFelisLanguage(CNFGrammar grammar) {
		grammar.clear();
		addDefaultConstants(grammar);

		grammar.addUnitProduction( new ProductionUnit("OP-MATH", "+"));
		grammar.addUnitProduction( new ProductionUnit("OP-MATH", "-"));
		grammar.addUnitProduction( new ProductionUnit("OP-MATH", "*"));
		grammar.addUnitProduction( new ProductionUnit("LBRACE", "("));
		grammar.addUnitProduction( new ProductionUnit("RBRACE", ")"));
		grammar.addNonunitProduction( new ProductionNonunit("EXP", "EXP", "EXP-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP", SYMBOL_NUMBER, "EXP-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP", SYMBOL_VARIABLE, "EXP-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-TAIL", "OP-MATH", "EXP") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-TAIL", "OP-MATH", SYMBOL_NUMBER) );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-TAIL", "OP-MATH", SYMBOL_VARIABLE) );
		grammar.addNonunitProduction( new ProductionNonunit("EXP", "LBRACE", "EXP-BRACED-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-BRACED-TAIL", "EXP", "RBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-BRACED-TAIL", SYMBOL_NUMBER, "RBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-BRACED-TAIL", SYMBOL_VARIABLE, "RBRACE") );

		grammar.addUnitProduction( new ProductionUnit("OP-COMP", "==="));
		grammar.addUnitProduction( new ProductionUnit("OP-COMP", "=/="));
		grammar.addUnitProduction( new ProductionUnit("OP-COMP", ">"));
		grammar.addUnitProduction( new ProductionUnit("OP-COMP", "<"));
		grammar.addUnitProduction( new ProductionUnit("OP-COMP", ">="));
		grammar.addUnitProduction( new ProductionUnit("OP-COMP", "<="));
		grammar.addNonunitProduction( new ProductionNonunit("COND", "EXP", "COND-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("COND", SYMBOL_NUMBER, "COND-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("COND", SYMBOL_VARIABLE, "COND-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("COND-TAIL", "OP-COMP", "EXP") );
		grammar.addNonunitProduction( new ProductionNonunit("COND-TAIL", "OP-COMP", SYMBOL_NUMBER) );
		grammar.addNonunitProduction( new ProductionNonunit("COND-TAIL", "OP-COMP", SYMBOL_VARIABLE) );
		grammar.addNonunitProduction( new ProductionNonunit("COND", "LBRACE", "COND-BRACED-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("COND-BRACED-TAIL", "COND", "RBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("COND-BRACED-TAIL", SYMBOL_NUMBER, "RBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("COND-BRACED-TAIL", SYMBOL_VARIABLE, "RBRACE") );
		grammar.addAlias("EXP", SYMBOL_NUMBER);
		grammar.addAlias("EXP", SYMBOL_VARIABLE);

		grammar.addUnitProduction( new ProductionUnit("L-IOBRACE", "("));
		grammar.addUnitProduction( new ProductionUnit("R-IOBRACE", ")"));
		grammar.addUnitProduction( new ProductionUnit("INPUT", "input"));
		grammar.addUnitProduction( new ProductionUnit("OUTPUT", "output"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "INPUT", "INPUT-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("INPUT-TAIL", "L-IOBRACE", "INPUT-CONTENT") );
		grammar.addNonunitProduction( new ProductionNonunit("INPUT-CONTENT", SYMBOL_VARIABLE, "R-IOBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "OUTPUT", "OUTPUT-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("OUTPUT-TAIL", "L-IOBRACE", "OUTPUT-CONTENT") );
		grammar.addNonunitProduction( new ProductionNonunit("OUTPUT-CONTENT", "EXP", "R-IOBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("OUTPUT-CONTENT", "COMP", "R-IOBRACE") );

		grammar.addUnitProduction( new ProductionUnit("BLOCK-BEGIN", "begin"));
		grammar.addUnitProduction( new ProductionUnit("BLOCK-END", "end"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "BLOCK-BEGIN", "BLOCK-END") );
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "BLOCK-BEGIN", "BLOCK-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("BLOCK-TAIL", "STATEMENT", "BLOCK-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("BLOCK-TAIL", "STATEMENT", "BLOCK-END") );

		grammar.addUnitProduction( new ProductionUnit("IF", "if"));
		grammar.addUnitProduction( new ProductionUnit("THEN", "then"));
		grammar.addUnitProduction( new ProductionUnit("ELSE", "else"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "IF", "IF-NEXT-1") );
		grammar.addNonunitProduction( new ProductionNonunit("IF-NEXT-1", "COND", "IF-NEXT-2") );
		grammar.addNonunitProduction( new ProductionNonunit("IF-NEXT-2", "THEN", "STATEMENT") );
		grammar.addNonunitProduction( new ProductionNonunit("IF-NEXT-2", "THEN", "IF-NEXT-3") );
		grammar.addNonunitProduction( new ProductionNonunit("IF-NEXT-3", "STATEMENT", "IF-NEXT-4") );
		grammar.addNonunitProduction( new ProductionNonunit("IF-NEXT-4", "ELSE", "STATEMENT") );

		grammar.addUnitProduction( new ProductionUnit("REPEAT", "repeat"));
		grammar.addUnitProduction( new ProductionUnit("UNTIL", "until"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "REPEAT", "REPEAT-NEXT-1") );
		grammar.addNonunitProduction( new ProductionNonunit("REPEAT-NEXT-1", "STATEMENT", "REPEAT-NEXT-1") );
		grammar.addNonunitProduction( new ProductionNonunit("REPEAT-NEXT-1", "STATEMENT", "REPEAT-NEXT-2") );
		grammar.addNonunitProduction( new ProductionNonunit("REPEAT-NEXT-2", "UNTIL", "COND") );

		grammar.addUnitProduction( new ProductionUnit("WHILE", "while"));
		grammar.addUnitProduction( new ProductionUnit("DO", "do"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "WHILE", "WHILE-NEXT-1") );
		grammar.addNonunitProduction( new ProductionNonunit("WHILE-NEXT-1", "COND", "WHILE-NEXT-2") );
		grammar.addNonunitProduction( new ProductionNonunit("WHILE-NEXT-2", "DO", "STATEMENT") );

		grammar.addUnitProduction( new ProductionUnit("OP-ASSIGN", ":="));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", SYMBOL_VARIABLE, "ASSIGN-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("ASSIGN-TAIL", "OP-ASSIGN", "EXP") );

		grammar.setStart("STATEMENT");
	}

	public static void fillGrammarWithFelisLanguageV2(CNFGrammar grammar) {
		grammar.clear();
		addDefaultConstants(grammar);

		grammar.addUnitProduction( new ProductionUnit("OP-MATH", "+"));
		grammar.addUnitProduction( new ProductionUnit("OP-MATH", "-"));
		grammar.addUnitProduction( new ProductionUnit("OP-MATH", "*"));
		grammar.addUnitProduction( new ProductionUnit("LBRACE", "("));
		grammar.addUnitProduction( new ProductionUnit("RBRACE", ")"));
		grammar.addNonunitProduction( new ProductionNonunit("EXP", "EXP", "EXP-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP", SYMBOL_NUMBER, "EXP-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP", SYMBOL_VARIABLE, "EXP-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-TAIL", "OP-MATH", "EXP") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-TAIL", "OP-MATH", SYMBOL_NUMBER) );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-TAIL", "OP-MATH", SYMBOL_VARIABLE) );
		grammar.addNonunitProduction( new ProductionNonunit("EXP", "LBRACE", "EXP-BRACED-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-BRACED-TAIL", "EXP", "RBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-BRACED-TAIL", SYMBOL_NUMBER, "RBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("EXP-BRACED-TAIL", SYMBOL_VARIABLE, "RBRACE") );

		grammar.addUnitProduction( new ProductionUnit("OP-COMP", "is-equwl-witt"));
		grammar.addUnitProduction( new ProductionUnit("OP-COMP", "is-not-equwl-witt"));
		grammar.addUnitProduction( new ProductionUnit("OP-COMP", "lager-dan"));
		grammar.addUnitProduction( new ProductionUnit("OP-COMP", "swaller-dan"));
		grammar.addUnitProduction( new ProductionUnit("OP-COMP", "lager-ow-equwl-dan"));
		grammar.addUnitProduction( new ProductionUnit("OP-COMP", "swaller-ow-equwl-dan"));
		grammar.addNonunitProduction( new ProductionNonunit("COND", "EXP", "COND-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("COND", SYMBOL_NUMBER, "COND-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("COND", SYMBOL_VARIABLE, "COND-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("COND-TAIL", "OP-COMP", "EXP") );
		grammar.addNonunitProduction( new ProductionNonunit("COND-TAIL", "OP-COMP", SYMBOL_NUMBER) );
		grammar.addNonunitProduction( new ProductionNonunit("COND-TAIL", "OP-COMP", SYMBOL_VARIABLE) );
		grammar.addNonunitProduction( new ProductionNonunit("COND", "LBRACE", "COND-BRACED-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("COND-BRACED-TAIL", "COND", "RBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("COND-BRACED-TAIL", SYMBOL_NUMBER, "RBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("COND-BRACED-TAIL", SYMBOL_VARIABLE, "RBRACE") );
		grammar.addAlias("EXP", SYMBOL_NUMBER);
		grammar.addAlias("EXP", SYMBOL_VARIABLE);

		grammar.addUnitProduction( new ProductionUnit("L-IOBRACE", "("));
		grammar.addUnitProduction( new ProductionUnit("R-IOBRACE", ")"));
		grammar.addUnitProduction( new ProductionUnit("INPUT", "inpwut"));
		grammar.addUnitProduction( new ProductionUnit("OUTPUT", "pwint"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "INPUT", "INPUT-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("INPUT-TAIL", "L-IOBRACE", "INPUT-CONTENT") );
		grammar.addNonunitProduction( new ProductionNonunit("INPUT-CONTENT", SYMBOL_VARIABLE, "R-IOBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "OUTPUT", "OUTPUT-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("OUTPUT-TAIL", "L-IOBRACE", "OUTPUT-CONTENT") );
		grammar.addNonunitProduction( new ProductionNonunit("OUTPUT-CONTENT", "EXP", "R-IOBRACE") );
		grammar.addNonunitProduction( new ProductionNonunit("OUTPUT-CONTENT", "COMP", "R-IOBRACE") );

		grammar.addUnitProduction( new ProductionUnit("BLOCK-BEGIN", "wigin"));
		grammar.addUnitProduction( new ProductionUnit("BLOCK-END", "end"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "BLOCK-BEGIN", "BLOCK-END") );
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "BLOCK-BEGIN", "BLOCK-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("BLOCK-TAIL", "STATEMENT", "BLOCK-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("BLOCK-TAIL", "STATEMENT", "BLOCK-END") );

		grammar.addUnitProduction( new ProductionUnit("IF", "if"));
		grammar.addUnitProduction( new ProductionUnit("THEN", "sow"));
		grammar.addUnitProduction( new ProductionUnit("ELSE", "if-not"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "IF", "IF-NEXT-1") );
		grammar.addNonunitProduction( new ProductionNonunit("IF-NEXT-1", "COND", "IF-NEXT-2") );
		grammar.addNonunitProduction( new ProductionNonunit("IF-NEXT-2", "THEN", "STATEMENT") );
		grammar.addNonunitProduction( new ProductionNonunit("IF-NEXT-2", "THEN", "IF-NEXT-3") );
		grammar.addNonunitProduction( new ProductionNonunit("IF-NEXT-3", "STATEMENT", "IF-NEXT-4") );
		grammar.addNonunitProduction( new ProductionNonunit("IF-NEXT-4", "ELSE", "STATEMENT") );

		grammar.addUnitProduction( new ProductionUnit("REPEAT", "nyan-loop"));
		grammar.addUnitProduction( new ProductionUnit("UNTIL", "stahp-if"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "REPEAT", "REPEAT-NEXT-1") );
		grammar.addNonunitProduction( new ProductionNonunit("REPEAT-NEXT-1", "STATEMENT", "REPEAT-NEXT-1") );
		grammar.addNonunitProduction( new ProductionNonunit("REPEAT-NEXT-1", "STATEMENT", "REPEAT-NEXT-2") );
		grammar.addNonunitProduction( new ProductionNonunit("REPEAT-NEXT-2", "UNTIL", "COND") );

		grammar.addUnitProduction( new ProductionUnit("WHILE", "whale"));
		grammar.addUnitProduction( new ProductionUnit("DO", "duh"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", "WHILE", "WHILE-NEXT-1") );
		grammar.addNonunitProduction( new ProductionNonunit("WHILE-NEXT-1", "COND", "WHILE-NEXT-2") );
		grammar.addNonunitProduction( new ProductionNonunit("WHILE-NEXT-2", "DO", "STATEMENT") );

		grammar.addUnitProduction( new ProductionUnit("OP-ASSIGN", ":3"));
		grammar.addNonunitProduction( new ProductionNonunit("STATEMENT", SYMBOL_VARIABLE, "ASSIGN-TAIL") );
		grammar.addNonunitProduction( new ProductionNonunit("ASSIGN-TAIL", "OP-ASSIGN", "EXP") );

		grammar.setStart("STATEMENT");
	}

	public static void addDefaultConstants(CNFGrammar grammar) {
		grammar.addUnitProduction(new ProductionPattern(SYMBOL_WORD, "\\w++"));
		grammar.addUnitProduction(new ProductionPattern(SYMBOL_NUMBER, "\\d++"));
		grammar.addUnitProduction(new ProductionPattern(SYMBOL_VARIABLE, "[\\w&&[^\\d]]++\\w*+"));
		grammar.addUnitProduction(new ProductionPattern(SYMBOL_ANY, ".*+"));
	}
	
}
