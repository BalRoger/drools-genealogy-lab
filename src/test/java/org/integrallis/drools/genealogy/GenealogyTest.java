package org.integrallis.drools.genealogy;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.integrallis.drools.junit.BaseDroolsTestCase;
import org.junit.Test;
import org.kie.api.runtime.rule.QueryResults;

public class GenealogyTest extends BaseDroolsTestCase {

	public GenealogyTest() {
		super("ksession-rules");
	}

	@Test
	public void ancestors() {
        Person phil = new Person("Phil");
        Person claire = new Person("Claire");
        Person jessica = new Person("Jessica");
        Person michael = new Person("Michael", claire, phil);
        Person steve = new Person("Steve", jessica, phil);
        Person chuck = new Person("Chuck", claire, phil);
        Person jane = new Person("Jane");
        Person kim = new Person("Kim", jane, michael);

        knowledgeSession.insert(phil);
        knowledgeSession.insert(claire);
        knowledgeSession.insert(jessica);
        knowledgeSession.insert(michael);
        knowledgeSession.insert(steve);
        knowledgeSession.insert(jane);
        knowledgeSession.insert(chuck);
        knowledgeSession.insert(kim);

        knowledgeSession.fireAllRules();

        QueryResults results = knowledgeSession.getQueryResults("getAncestors");

        assertEquals(10, results.size());

        QueryResults kimResults = knowledgeSession.getQueryResults("getAncestorsOf", kim);

        Set<Person> kimAncestors = new LinkedHashSet<>(Arrays.asList(jane, michael, claire, phil));
        assertEquals(kimAncestors.size(), kimResults.size());
        kimResults.forEach(row -> assertTrue(kimAncestors.contains(((AncestorRelationship)(row.get("rel"))).getAncestor())));

        QueryResults michaelResults = knowledgeSession.getQueryResults("getAncestorsOf", michael);

        Set<Person> michaelAncestors = new LinkedHashSet<>(Arrays.asList(claire, phil));
        assertEquals(michaelAncestors.size(), michaelResults.size());
        michaelResults.forEach(row -> assertTrue(michaelAncestors.contains(((AncestorRelationship)(row.get("rel"))).getAncestor())));

        QueryResults steveResults = knowledgeSession.getQueryResults("getAncestorsOf", steve);

        Set<Person> steveAncestors = new LinkedHashSet<>(Arrays.asList(jessica, phil));
        assertEquals(steveAncestors.size(), steveResults.size());
        steveResults.forEach(row -> assertTrue(steveAncestors.contains(((AncestorRelationship)(row.get("rel"))).getAncestor())));

        QueryResults chuckResults = knowledgeSession.getQueryResults("getAncestorsOf", chuck);

        Set<Person> chuckAncestors = new LinkedHashSet<>(Arrays.asList(claire, phil));
        assertEquals(chuckAncestors.size(), chuckResults.size());
        chuckResults.forEach(row -> assertTrue(chuckAncestors.contains(((AncestorRelationship)(row.get("rel"))).getAncestor())));

        QueryResults janeResults = knowledgeSession.getQueryResults("getAncestorsOf", jane);

        Set<Person> janeAncestors = new LinkedHashSet<>(Arrays.asList());
        assertEquals(janeAncestors.size(), janeResults.size());
        janeResults.forEach(row -> assertTrue(janeAncestors.contains(((AncestorRelationship)(row.get("rel"))).getAncestor())));

        QueryResults janeDResults = knowledgeSession.getQueryResults("getDescendantsOf", jane);

        Set<Person> janeDescendants = new LinkedHashSet<>(Arrays.asList(kim));
        assertEquals(janeDescendants.size(), janeDResults.size());
        janeDResults.forEach(row -> assertTrue(janeDescendants.contains(((AncestorRelationship)(row.get("rel"))).getDescendant())));

        QueryResults philDResults = knowledgeSession.getQueryResults("getDescendantsOf", phil);

        Set<Person> philDescendants = new LinkedHashSet<>(Arrays.asList(michael, steve, chuck, kim));
        assertEquals(philDescendants.size(), philDResults.size());
        philDResults.forEach(row -> assertTrue(philDescendants.contains(((AncestorRelationship)(row.get("rel"))).getDescendant())));

        results.forEach(row -> ((AncestorRelationship)(row.get("rel"))).printIt());
	}

//	public static final void main(String[] args) {
//	    KieSession knowledgeSession = null;
//	    try {
//	        // load up the knowledge base
//	        KieServices ks = KieServices.Factory.get();
//		    KieContainer kContainer = ks.getKieClasspathContainer();
//		    knowledgeSession = kContainer.newKieSession("ksession-rules");
//
//			// 4 - create and assert some facts
//            Person phil = new Person("Phil");
//            Person claire = new Person("Anne");
//            Person jessica = new Person("Jessica");
//            Person michael = new Person("Michael", claire, phil);
//            Person steve = new Person("Steve", jessica, phil);
//            Person chuck = new Person("Chuck", claire, phil);
//
//            knowledgeSession.insert(phil);
//            knowledgeSession.insert(claire);
//            knowledgeSession.insert(jessica);
//            knowledgeSession.insert(michael);
//            knowledgeSession.insert(steve);
//            knowledgeSession.insert(chuck);
//
//			// 5 - fire the rules
//			knowledgeSession.fireAllRules();
//		} catch (Throwable t) {
//			t.printStackTrace();
//		} finally {
//			knowledgeSession.dispose();
//		}
//	}
}
