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
package net.sf.tweety.lp.asp.syntax;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;
import net.sf.tweety.logics.fol.syntax.FolSignature;

/**
 * This class represents a comparative atom, meaning an expression of the form t x u
 * where t,u are terms and x is in {<, <=, ==, !=, >, >=}. Comparatives are
 * called "Built-in atoms" in the ASP-Core-2 standard.
 * 
 * @author Anna Gessler
 */
public class ComparativeAtom extends ASPBodyElement {

	/** 
	 * The comparative operator of the atom.
	 */
	private ASPOperator.BinaryOperator op;
	
	/** 
	 * The left term of the comparative atom.
	 */
	private Term<?> left;
	
	/** 
	 * The right term of the comparative atom.
	 */
	private Term<?> right;

	public ComparativeAtom(ASPOperator.BinaryOperator op, Term<?> left, Term<?> right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	/**
	 * Copy-Constructor
	 * 
	 * @param comparativeAtom
	 */
	public ComparativeAtom(ComparativeAtom other) {
		this(other.op, other.left, other.right);
	}

	@Override
	public String toString() {
		return left.toString() + op.toString() + right.toString();
	}

	@Override
	public SortedSet<ASPLiteral> getLiterals() {
		return new TreeSet<ASPLiteral>();
	}

	@Override
	public Set<Predicate> getPredicates() {
		return new HashSet<Predicate>();
	}

	@Override
	public Set<ASPAtom> getAtoms() {
		return new HashSet<ASPAtom>();
	}

	@Override
	public ComparativeAtom substitute(Term<?> t, Term<?> v) {
		ComparativeAtom cat = new ComparativeAtom(this);
		if (this.left.equals(t))
			cat.left = v;
		if (this.right.equals(t))
			cat.right = v;
		return cat;
	}

	@Override
	public FolSignature getSignature() {
		FolSignature sig = new FolSignature();
		sig.add(left);
		sig.add(right);
		return sig;
	}

	@Override
	public ASPBodyElement clone() {
		return new ComparativeAtom(this);
	}

	@Override
	public Class<? extends Predicate> getPredicateCls() {
		return Predicate.class;
	}

	@Override
	public boolean isLiteral() {
		return false;
	}

	@Override
	public Set<Term<?>> getTerms() {
		Set<Term<?>> reval = new HashSet<Term<?>>();
		reval.add(left);
		reval.add(right);
		return reval;
	}

	/**	
	 * Returns the comparative operator of the atom.
	 * @return
	 */
	public ASPOperator.BinaryOperator getOperator() {
		return op;
	}

	/**
	 * Returns the left (first) term of the comparative atom.
	 * @return
	 */
	public Term<?> getLeft() {
		return left;
	}

	/**
	 * Returns the right (second) term of the comparative atom.
	 * @return
	 */
	public Term<?> getRight() {
		return right;
	}

	@Override
	public <C extends Term<?>> Set<C> getTerms(Class<C> cls) {
		Set<C> reval = new HashSet<C>();
		reval.addAll(left.getTerms(cls));
		reval.addAll(right.getTerms(cls));
		return reval;
	}

}
