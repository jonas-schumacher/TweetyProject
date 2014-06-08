package net.sf.tweety.logics.pl.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.tweety.ParserException;
import net.sf.tweety.logics.commons.analysis.BeliefSetInconsistencyMeasure;
import net.sf.tweety.logics.commons.analysis.streams.DefaultInconsistencyListener;
import net.sf.tweety.logics.commons.analysis.streams.DefaultStreamBasedInconsistencyMeasure;
import net.sf.tweety.logics.commons.analysis.streams.StreamBasedInconsistencyMeasure;
import net.sf.tweety.logics.pl.DefaultConsistencyTester;
import net.sf.tweety.logics.pl.LingelingEntailment;
import net.sf.tweety.logics.pl.PlBeliefSet;
import net.sf.tweety.logics.pl.analysis.ContensionInconsistencyMeasure;
import net.sf.tweety.logics.pl.analysis.ContensionInconsistencyMeasurementProcess;
import net.sf.tweety.logics.pl.parser.PlParser;
import net.sf.tweety.logics.pl.syntax.PropositionalFormula;
import net.sf.tweety.streams.DefaultFormulaStream;

public class ContensionTest {
	public static void main(String[] args) throws ParserException, IOException, InterruptedException{
		// Create some knowledge base
		PlBeliefSet kb = new PlBeliefSet();
		PlParser parser = new PlParser();
	
		kb.add((PropositionalFormula)parser.parseFormula("a"));
		kb.add((PropositionalFormula)parser.parseFormula("!a && b"));
		kb.add((PropositionalFormula)parser.parseFormula("!b"));
		kb.add((PropositionalFormula)parser.parseFormula("c || a"));
		kb.add((PropositionalFormula)parser.parseFormula("!c || a"));
		kb.add((PropositionalFormula)parser.parseFormula("!c || d"));
		kb.add((PropositionalFormula)parser.parseFormula("!d"));
		kb.add((PropositionalFormula)parser.parseFormula("d"));
		kb.add((PropositionalFormula)parser.parseFormula("c"));
		
		// test contension inconsistency measure		
		BeliefSetInconsistencyMeasure<PropositionalFormula> cont = new ContensionInconsistencyMeasure(new DefaultConsistencyTester(new LingelingEntailment("/Users/mthimm/Projects/misc_bins/lingeling")));
		System.out.println("Cont: " + cont.inconsistencyMeasure(kb));
		
		Thread.sleep(1000);
		
		// test stream-based variant
		Map<String,Object>config = new HashMap<String,Object>();
		config.put(ContensionInconsistencyMeasurementProcess.CONFIG_KEY_WITNESSPROVIDER, new DefaultConsistencyTester(new LingelingEntailment("/Users/mthimm/Projects/misc_bins/lingeling")));
		config.put(ContensionInconsistencyMeasurementProcess.CONFIG_KEY_NUMBEROFPOPULATIONS, 10);
		config.put(ContensionInconsistencyMeasurementProcess.CONFIG_KEY_SIGNATURE, kb.getSignature());
		StreamBasedInconsistencyMeasure<PropositionalFormula> cont2 = new DefaultStreamBasedInconsistencyMeasure<PropositionalFormula>(ContensionInconsistencyMeasurementProcess.class,config);
		cont2.addInconsistencyListener(new DefaultInconsistencyListener());
		cont2.getInconsistencyMeasureProcess(new DefaultFormulaStream<PropositionalFormula>(kb,true)).start();
	}
}