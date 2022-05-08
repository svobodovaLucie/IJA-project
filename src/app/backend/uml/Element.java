/*
 * File:         Element.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the UML element.
 */
package app.backend.uml;

/**
 * Class represents a UML element present in the diagram.
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
}
