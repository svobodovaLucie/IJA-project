/*
 * File:         Element.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the UML element.
 */
package app.uml;

/**
 * Class represents an UML element present in the diagram.
 */
public class Element {

	protected String name;

	/**
	 * Element contructor.
	 *
	 * @param name name of the UML constructor
	 */
	public Element(String name) {
		this.name = name;
	}

	/**
	 * Method returns the name of the element.
	 *
	 * @return name of the element as a string
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Method renames the element to newName.
	 *
	 * @param newName new name of the element
	 */
	public void rename(String newName) {
		this.name = newName;
	}
}
