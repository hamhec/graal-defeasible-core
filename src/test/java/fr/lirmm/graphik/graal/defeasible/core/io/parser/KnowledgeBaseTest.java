package fr.lirmm.graphik.graal.defeasible.core.io.parser;

import org.junit.Assert;
import org.junit.Test;

import fr.lirmm.graphik.graal.api.core.AtomSetException;
import fr.lirmm.graphik.graal.defeasible.core.DefeasibleKnowledgeBase;
import fr.lirmm.graphik.util.stream.IteratorException;

public class KnowledgeBaseTest {
	
	@Test
	public void ShouldAddAtom() throws AtomSetException, IteratorException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase();
		kb.add("p(a).");
		Assert.assertTrue(kb.getFacts().iterator().next().toString().equals("p(a)"));
	}
	
	@Test
	public void ShouldAddRulePreference() throws AtomSetException, IteratorException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase();
		kb.add("r1 >> r2 .");
		Assert.assertFalse(kb.getRulePreferences().isEmpty());
	}
	
	@Test
	public void ShouldAddAlternativePreference() throws AtomSetException, IteratorException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase();
		kb.add("r1 > r2 .");
		Assert.assertFalse(kb.getAlternativePreferences().isEmpty());
	}
	
	@Test
	public void ShouldAddStrictRule() throws AtomSetException, IteratorException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase();
		kb.add("p(X) :- q(Y).");
		Assert.assertFalse(kb.getStrictRules().isEmpty());
	}
	
	@Test
	public void ShouldAddDefeasibleRule() throws AtomSetException, IteratorException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase();
		kb.add("p(X) := q(Y).");
		Assert.assertFalse(kb.getDefeasibleRules().isEmpty());
	}
	
	@Test
	public void ShouldAddDefeaterRule() throws AtomSetException, IteratorException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase();
		kb.add("p(X) :~ q(Y).");
		Assert.assertFalse(kb.getDefeaterRules().isEmpty());
	}
	
	@Test
	public void ShouldAddPreferenceRule() throws AtomSetException, IteratorException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase();
		kb.add("X > Y :- q(Y,X).");
		Assert.assertFalse(kb.getPreferenceRules().isEmpty());
	}
	
	@Test
	public void ShouldAddNegativeConstraint() throws AtomSetException, IteratorException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase();
		kb.add("! :- p(X), q(Y).");
		Assert.assertFalse(kb.getNegativeConstraints().isEmpty());
	}
	
	@Test
	public void ShouldGenerateTransitivePreferences() throws AtomSetException, IteratorException {
		DefeasibleKnowledgeBase kb = new DefeasibleKnowledgeBase();
		kb.add("a > b .");
		kb.add("b > c .");
		kb.generateTransitiveAlternatives();
		Assert.assertEquals(3, kb.getAlternativePreferences().size());
	}
	
}
