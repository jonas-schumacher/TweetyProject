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
package net.sf.tweety.commons.postulates;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import net.sf.tweety.commons.BeliefSet;
import net.sf.tweety.commons.BeliefSetIterator;
import net.sf.tweety.commons.Formula;

/**
 * Evaluates some approach (reasoner, measure, etc.) wrt. a series
 * of rationality postulates on a given series of knowledge bases.
 * 
 * @author Matthias Thimm
 *
 * @param <S> The type of formulas used in the evaluation.
 * @param <T> The type of belief bases used in the evaluation.
 */
public class PostulateEvaluator<S extends Formula,T extends BeliefSet<S>>{
	
	/**
	 * The belief base sampler used to test the rationality postulates
	 */
	private BeliefSetIterator<S,T> iterator;
	
	/**
	 * The approach being evaluated.
	 */
	private PostulateEvaluatable<S> ev;
	
	/**
	 * the list of postulates the approach is evaluated against
	 */
	private List<Postulate<S>> postulates = new LinkedList<Postulate<S>>();
	
	/**
	 * Creates a new evaluator for the given evaluatable and
	 * belief base generator.
	 * @param iterator some belief set iterator
	 * @param ev some evaluatable
	 * @param postulates a set of postulates
	 */
	public PostulateEvaluator(BeliefSetIterator<S,T> iterator, PostulateEvaluatable<S> ev, Collection<Postulate<S>> postulates) {
		this.iterator = iterator;
		this.ev = ev;
		this.postulates.addAll(postulates);
	}
	
	/**
	 * Creates a new evaluator for the given evaluatable and
	 * belief base generator.
	 * @param iterator some belief set iterator
	 * @param ev some evaluatable
	 */
	public PostulateEvaluator(BeliefSetIterator<S,T> iterator, PostulateEvaluatable<S> ev) {
		this.iterator = iterator;
		this.ev = ev;
	}
	
	/**
	 * Adds the given postulate
	 * @param p some postulate
	 */
	public void addPostulate(Postulate<S> p) {
		this.postulates.add(p);
	}
	
	/**
	 * Removes the given postulate
	 * @param p some postulate
	 * @return true if this contained the specified postulate.
	 */
	public boolean removePostulate(Postulate<S> p) {
		return this.postulates.remove(p);
	}
	
	/**
	 * Evaluates all postulates of this evaluator on the given 
	 * approach on <code>num</code> belief bases generated by
	 * the sampler of this evaluator.
	 * @param num the number of belief bases to be applied.
	 * @param stopWhenFailed if true the evaluation of one postulate
	 * 	will be stopped once a violation has been encountered.
	 * @return a report on the evaluation
	 */
	public PostulateEvaluationReport<S> evaluate(long num, boolean stopWhenFailed) {
		PostulateEvaluationReport<S> rep = new PostulateEvaluationReport<S>(this.ev,this.postulates);
		Collection<Postulate<S>> failedPostulates = new HashSet<Postulate<S>>();
		for(int i = 0; i < num; i++) {
			T instance = this.iterator.next();
			for(Postulate<S> postulate: this.postulates) {
				if(stopWhenFailed && failedPostulates.contains(postulate))
					continue;
				if(!postulate.isApplicable(instance)) 
					rep.addNotApplicableInstance(postulate, instance);
				else if(postulate.isSatisfied(instance, this.ev))
					rep.addPositiveInstance(postulate, instance);
				else {
					rep.addNegativeInstance(postulate, instance);
					failedPostulates.add(postulate);
				}
			}
		}
		return rep;
	}
	
	/**
	 * Evaluates all postulates of this evaluator on the given 
	 * approach on <code>num</code> belief bases generated by
	 * the sampler of this evaluator. The evaluation of any 
	 * one postulate will be stopped once a violation has been
	 * encountered.
	 * @param num the number of belief bases to be applied.
	 * @return a report on the evaluation
	 */
	public PostulateEvaluationReport<S> evaluate(long num) {
		return this.evaluate(num, true);
	}
}