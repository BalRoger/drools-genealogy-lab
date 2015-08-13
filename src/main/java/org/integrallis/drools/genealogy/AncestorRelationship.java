package org.integrallis.drools.genealogy;

import java.util.ArrayList;
import java.util.List;

public class AncestorRelationship {

    private List<Person> members = new ArrayList<Person>();
    private Person ancestor;
    private Person descendant;

    public AncestorRelationship(Person ancestor, Person descendant) {
	    	members.add(ancestor);
	    	members.add(descendant);
	    	this.ancestor = ancestor;
	    	this.descendant = descendant;
    }

    public List<Person> getMembers() {
    		return members;
    }

	public void printIt() {
		System.out.println(ancestor.getName() + " is an ancestor of " + descendant.getName());
	}

	public Person getAncestor() {
		return ancestor;
	}

	public void setAncestor(Person ancestor) {
		this.ancestor = ancestor;
	}

	public Person getDescendant() {
		return descendant;
	}

	public void setDescendant(Person descendant) {
		this.descendant = descendant;
	}
}