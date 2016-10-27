package net.sf.tweety.arg.aba.syntax;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.logics.commons.syntax.interfaces.Invertable;

/**
 * @author Nils Geilen
 *
 * @param <T>
 */
public class Deduction <T extends Invertable> extends Argument {
	
	ABARule<T> rule;
	Collection<Deduction<T>> subs = new HashSet<>();
		

	public Deduction(String name) {
		super(name);
	}
	
	
	
	public Deduction(String name, ABARule<T> rule, Collection<Deduction<T>> subs) {
		super(name);
		this.rule = rule;
		this.subs.addAll(subs);
	}



	public T getConclusion() {
		return rule.getConclusion();
	}
	

	/**
	 * @return the rule
	 */
	public ABARule<T> getRule() {
		return rule;
	}



	/**
	 * @param rule the rule to set
	 */
	public void setRule(ABARule<T> rule) {
		this.rule = rule;
	}



	public Collection<T> getAssumptions() {
		Collection<T> result = new LinkedList<>();
		if (rule.isAssumption())
			result.add(rule.getConclusion());
		else for (Deduction<T> sub : subs)
			result.addAll(sub.getAssumptions());
		return result;
	}
	
	public void addSubDeduction(Deduction<T> sub) {
		subs.add(sub);
	}
	

	public Collection<ABARule<T>> getRules() {
		Collection<ABARule<T>> result = new LinkedList<>();
		if (! rule.isAssumption())
			result.add(rule);
		for (Deduction<T> sub : subs)
			result.addAll(sub.getRules());
		return result;
	}

	/* (non-Javadoc)
	 * @see net.sf.tweety.arg.dung.syntax.Argument#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((rule == null) ? 0 : rule.hashCode());
		result = prime * result + ((subs == null) ? 0 : subs.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see net.sf.tweety.arg.dung.syntax.Argument#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deduction other = (Deduction) obj;
		if (rule == null) {
			if (other.rule != null)
				return false;
		} else if (!rule.equals(other.rule))
			return false;
		if (subs == null) {
			if (other.subs != null)
				return false;
		} else if (!subs.equals(other.subs))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see net.sf.tweety.arg.dung.syntax.Argument#toString()
	 */
	@Override
	public String toString() {
		return "{rule=" + rule + ", subs=" + subs + "}";
	}

	

}
