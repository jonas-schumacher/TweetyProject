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
 *  Copyright 2020 The TweetyProject Team <http://tweetyproject.org/contact/>
 */

package net.sf.tweety.math.opt.problem;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.sf.tweety.math.equation.Statement;

/**
 * This class implements a combinatorial optimization problem 
 * @author Sebastian Franke
 */

public abstract class CombinatoricsProblem extends HashSet<ElementOfCombinatoricsProb>{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Static constant for the type "minimization"
	 */
	public static final int MINIMIZE = 0;
	
	/**
	 * Static constant for the type "maximization"
	 */
	public static final int MAXIMIZE = 1;
	
	List<ElementOfCombinatoricsProb> elements;
	Collection<Statement> constraints = new ArrayList<Statement>();
		
	public CombinatoricsProblem(List<ElementOfCombinatoricsProb> elements){
		super(elements);

	}
	/**
	 * 
	 * @param c the List to be subtracted from "this" List
	 * @return the differnece of the lists
	 */
	public ArrayList<ElementOfCombinatoricsProb> createDifference(ArrayList<ElementOfCombinatoricsProb> c) {
		ArrayList<ElementOfCombinatoricsProb> newColl = new ArrayList<ElementOfCombinatoricsProb>();
		System.out.println("hi");
	    for(ElementOfCombinatoricsProb i : this) {
	    	if(!c.contains(i))
	    			newColl.add(i);
	    }
	   //System.out.println("addable: " +  newColl);
	    return newColl;
	}
	/**
	 * @param sol is the solution to be viewd
	 * @return if the solution sol is valid under the constraints of the problem
	 */
	public abstract double sumOfWeights(ArrayList<ElementOfCombinatoricsProb> sol);
	
	public ArrayList<ArrayList<ElementOfCombinatoricsProb>> formNeighborhood(ArrayList<ElementOfCombinatoricsProb> currSol, int minIterations, int maxIteration, double threshold)
	{
		int cnt = 0;
		int thresholdCnt = 0;
		boolean thresholdSwitch = false;
		ArrayList<ArrayList<ElementOfCombinatoricsProb>> result = new ArrayList<ArrayList<ElementOfCombinatoricsProb>>();
		while((cnt < minIterations || thresholdCnt < 10) && cnt < maxIteration)
		{

			ArrayList<ElementOfCombinatoricsProb> newSol = createRandomNewSolution(currSol);
			result.add(newSol);
			double eval = evaluate(newSol);
			if(thresholdSwitch == true)
				thresholdCnt++;
			else if(eval >= threshold)
				thresholdSwitch = true;
			cnt++;
		}

		return result;
	}
	public abstract ArrayList<ElementOfCombinatoricsProb> createRandomNewSolution(ArrayList<ElementOfCombinatoricsProb> currSol);
	public abstract double evaluate(ArrayList<ElementOfCombinatoricsProb> sol);
	public abstract boolean isValid(ArrayList<ElementOfCombinatoricsProb> sol);

}