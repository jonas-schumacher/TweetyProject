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
 *  Copyright 2016 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package net.sf.tweety.logics.pl.examples;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import net.sf.tweety.commons.ParserException;
import net.sf.tweety.logics.commons.analysis.DHitInconsistencyMeasure;
import net.sf.tweety.logics.commons.analysis.DMaxInconsistencyMeasure;
import net.sf.tweety.logics.commons.analysis.DSumInconsistencyMeasure;
import net.sf.tweety.logics.pl.analysis.DalalDistance;
import net.sf.tweety.logics.pl.parser.PlParser;
import net.sf.tweety.logics.pl.semantics.PossibleWorld;
import net.sf.tweety.logics.pl.semantics.PossibleWorldIterator;
import net.sf.tweety.logics.pl.syntax.PlBeliefSet;
import net.sf.tweety.logics.pl.syntax.PropositionalFormula;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

public class DSumMeasureExample {

	public static void main(String[] args) throws ParserException, IOException{
		Collection<PropositionalFormula> c = new HashSet<PropositionalFormula>();
		PlParser parser = new PlParser();
		c.add((PropositionalFormula) parser.parseFormula("a && b && c"));
		c.add((PropositionalFormula) parser.parseFormula("!a && !b && !c"));
				
		PropositionalSignature sig = new PropositionalSignature();
		for(PropositionalFormula f: c) sig.addAll(f.getSignature());
		
		DSumInconsistencyMeasure<PossibleWorld,PlBeliefSet,PropositionalFormula> inc1 = new DSumInconsistencyMeasure<PossibleWorld,PlBeliefSet,PropositionalFormula>(new DalalDistance(),new PossibleWorldIterator(sig));
		DMaxInconsistencyMeasure<PossibleWorld,PlBeliefSet,PropositionalFormula> inc2 = new DMaxInconsistencyMeasure<PossibleWorld,PlBeliefSet,PropositionalFormula>(new DalalDistance(),new PossibleWorldIterator(sig));
		DHitInconsistencyMeasure<PossibleWorld,PlBeliefSet,PropositionalFormula> inc3 = new DHitInconsistencyMeasure<PossibleWorld,PlBeliefSet,PropositionalFormula>(new DalalDistance(),new PossibleWorldIterator(sig));
		
		System.out.println(inc1.inconsistencyMeasure(c));
		System.out.println(inc2.inconsistencyMeasure(c));
		System.out.println(inc3.inconsistencyMeasure(c));
		
	}
}
