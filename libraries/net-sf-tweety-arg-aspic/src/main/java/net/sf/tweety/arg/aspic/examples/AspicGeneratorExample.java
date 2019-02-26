/*
 *  This file is part of "TweetyProject", a collection of Java libraries for
 *  logical aspects of artificial intelligence and knowledge representation.
 *
 *  TweetyProject is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License version 3 as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *  Copyright 2018 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package net.sf.tweety.arg.aspic.examples;

import net.sf.tweety.arg.aspic.reasoner.DirectionalAspicReasoner;
import net.sf.tweety.arg.aspic.reasoner.ModuleBasedAspicReasoner;
import net.sf.tweety.arg.aspic.reasoner.SimpleAspicReasoner;
import net.sf.tweety.arg.aspic.reasoner.RandomAspicReasoner;
import net.sf.tweety.arg.aspic.syntax.AspicArgumentationTheory;
import net.sf.tweety.arg.aspic.util.RandomAspicArgumentationTheoryGenerator;
import net.sf.tweety.arg.dung.reasoner.AbstractExtensionReasoner;
import net.sf.tweety.arg.dung.semantics.Semantics;
import net.sf.tweety.commons.InferenceMode;
import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.PlFormula;

/**
 * Exemplary code illustrating the use of the ASPIC theory generator.
 * Furthermore this code show a small performance comparison between
 * the naive ASPIC reasoner, the module based reasoner, and the random reasoner.
 * 
 * @author Matthias Thimm
 *
 */
public class AspicGeneratorExample {
	public static void main(String[] args) {		 
		int repetitions = 50;
		int numberAtoms = 35;
		int numberFormulas = 100;
		int maxLiteralsInPremises = 2;
		double percentageStrictRules = 0.2;
		
		SimpleAspicReasoner<PlFormula> naiveReasoner = new SimpleAspicReasoner<PlFormula>(AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GR));
		ModuleBasedAspicReasoner<PlFormula> moduleBasedReasoner = new ModuleBasedAspicReasoner<PlFormula>(AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GR));
		DirectionalAspicReasoner<PlFormula> dirReasoner = new DirectionalAspicReasoner<PlFormula>(AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GR));
		RandomAspicReasoner<PlFormula> randomReasoner = new RandomAspicReasoner<PlFormula>(AbstractExtensionReasoner.getSimpleReasonerForSemantics(Semantics.GR),600,100);
		
		long totalNaive = 0;
		long totalModulebased = 0;
		long totalDirectional = 0;
		long totalRandom = 0;
		long correctRandom = 0;
		long correctDirectional = 0;
		for(int i = 0; i < repetitions; i++) {
			AspicArgumentationTheory<PlFormula> theory = RandomAspicArgumentationTheoryGenerator.next(numberAtoms, numberFormulas, maxLiteralsInPremises, percentageStrictRules);
			System.out.println(i + "\t" + theory);
			PlFormula query = new Proposition("A1");
			// Naive
			long millis = System.currentTimeMillis();
			boolean answer = naiveReasoner.query(theory,query,InferenceMode.CREDULOUS);
			totalNaive += System.currentTimeMillis()-millis;
			// Module
			millis = System.currentTimeMillis();
			moduleBasedReasoner.query(theory,query,InferenceMode.CREDULOUS);
			totalModulebased += System.currentTimeMillis()-millis;
			// Directional
			millis = System.currentTimeMillis();
			if(dirReasoner.query(theory,query,InferenceMode.CREDULOUS) == answer)
				correctDirectional++;
			totalDirectional += System.currentTimeMillis()-millis;
			// Random
			millis = System.currentTimeMillis();
			if(randomReasoner.query(theory,query,InferenceMode.CREDULOUS) == answer)
				correctRandom++;
			totalRandom += System.currentTimeMillis()-millis;
		}	
		System.out.println();
		System.out.println("Runtime naive reasoner: " + totalNaive + "ms");
		System.out.println("Runtime module-based reasoner: " +  totalModulebased+ "ms");
		System.out.println("Runtime directional reasoner: " +  totalDirectional+ "ms");
		System.out.println("Runtime random reasoner: " +  totalRandom + "ms");
		System.out.println("Accuracy directional reasoner: " +(new Double(correctDirectional)/(repetitions)));
		System.out.println("Accuracy random reasoner: " +(new Double(correctRandom)/(repetitions)));
	}	
	
}
