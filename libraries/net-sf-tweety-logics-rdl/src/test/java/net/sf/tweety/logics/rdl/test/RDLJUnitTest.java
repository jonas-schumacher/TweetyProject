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
package net.sf.tweety.logics.rdl.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import net.sf.tweety.logics.fol.reasoner.FolReasoner;
import net.sf.tweety.logics.fol.reasoner.NaiveFolReasoner;
import net.sf.tweety.logics.rdl.DefaultTheory;
import net.sf.tweety.logics.rdl.NaiveDefaultReasoner;
import net.sf.tweety.logics.rdl.parser.RdlParser;

/**
 * 
 * @author Nils Geilen
 *
 */

public class RDLJUnitTest {
	
	@Before
	public void init(){
		//FolTheoremProver.setDefaultProver(new Prover9("C:\\app\\prover9\\bin\\prover9.exe"));
		FolReasoner.setDefaultReasoner(new NaiveFolReasoner());
	/*	if(System.getProperty("os.name").matches("Win.*")){
			System.out.println("Initializing Eprover for Windows");
			FolTheoremProver.setDefaultProver(new EProver("C:/app/E/PROVER/eprover.exe", Shell.getCygwinShell("C:/cygwin64/bin/bash.exe")));
		} else {
			System.out.println("Initializing Eprover for Unix");
			FolTheoremProver.setDefaultProver(new EProver("/home/nils/app/E/PROVER/eprover", Shell.getNativeShell()));
		}*/
	}
	
	@Test
	public void test1() throws Exception{
		String bsp = "Animal = {tweety, penguin} \n"
				+ "type(Flies(Animal)) \n type(Bird(Animal)) \n "
				+ "Bird(tweety) \n Bird(penguin) \n !Flies(penguin) \n "
				+" Bird(X)::Flies(X)/Flies(X)";

		RdlParser parser = new RdlParser();
		DefaultTheory theory = parser.parseBeliefBase(bsp);
		NaiveDefaultReasoner ndr = new NaiveDefaultReasoner();
		assertTrue(ndr.query(theory,parser.parseFormula("Flies(tweety)")).getAnswerBoolean());
		assertFalse(ndr.query(theory,parser.parseFormula("Flies(penguin)")).getAnswerBoolean());
		assertFalse(ndr.query(theory,parser.parseFormula("!Flies(tweety)")).getAnswerBoolean());
		assertTrue(ndr.query(theory,parser.parseFormula("!Flies(penguin)")).getAnswerBoolean());
		assertTrue(ndr.getAllExtensions(theory).size()==1);
	}
	
	@Test
	public void test2() throws Exception{
		String bsp = "type(a) \n type(b) \n type(c) \n "
				+ "a \n "
				+" a::!b/!b \n ::c/b";

		RdlParser parser = new RdlParser();
		DefaultTheory theory = parser.parseBeliefBase(bsp);
		NaiveDefaultReasoner ndr = new NaiveDefaultReasoner();
		assertTrue(ndr.query(theory,parser.parseFormula("a")).getAnswerBoolean());
		assertTrue(ndr.query(theory,parser.parseFormula("b")).getAnswerBoolean());
		assertFalse(ndr.query(theory,parser.parseFormula("!b")).getAnswerBoolean());
		assertTrue(ndr.getAllExtensions(theory).size()==1);
	}
	
	@Test
	public void test3() throws Exception{
		String bsp = "type(a) \n type(b) \n type(c) \n "
				+ " \n "
				+" ::a/a \n ::b/!b";

		RdlParser parser = new RdlParser();
		DefaultTheory theory = parser.parseBeliefBase(bsp);
		NaiveDefaultReasoner ndr = new NaiveDefaultReasoner();
		assertTrue(ndr.getAllExtensions(theory).isEmpty());
	}
	
	@Test
	public void test4() throws Exception{
		String bsp = "type(a) "
				+ " \n "
				+" ::a/a \n ::!a/!a";

		RdlParser parser = new RdlParser();
		DefaultTheory theory = parser.parseBeliefBase(bsp);
		NaiveDefaultReasoner ndr = new NaiveDefaultReasoner();
		assertTrue(ndr.getAllExtensions(theory).size() == 2);
	}
	
	
	@Test
	public void test5() throws Exception{
		String bsp = "type(a) \n type(b) \n type(c) \n type(d) \n"
				+ "\n "
				+" ::!b;!d/a \n ::!b;!d/c \n ::!a;!c/d \n a::!c/b \n";

		RdlParser parser = new RdlParser();
		DefaultTheory theory = parser.parseBeliefBase(bsp);
		NaiveDefaultReasoner ndr = new NaiveDefaultReasoner();
		assertTrue(ndr.query(theory,parser.parseFormula("a")).getAnswerBoolean());
		assertTrue(ndr.query(theory,parser.parseFormula("c")).getAnswerBoolean());
		assertTrue(ndr.query(theory,parser.parseFormula("d")).getAnswerBoolean());
		assertFalse(ndr.query(theory,parser.parseFormula("b")).getAnswerBoolean());
		assertFalse(ndr.query(theory,parser.parseFormula("!b")).getAnswerBoolean());
		assertTrue(ndr.getAllExtensions(theory).size()==2);
	}

}