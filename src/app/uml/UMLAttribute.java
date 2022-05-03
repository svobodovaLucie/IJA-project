/*
 * File:         UMLAttribute.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the UML attribute.
 */
package app.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the UML attribute with name and type.
 * It is inherited from the Element class. The type of the
 * attribute is represented by UMLClassifier type. It can
 * be used as UML class attribute or UML method argument.
 */
public class UMLAttribute {
	//private UMLClassifier type;
	// type
	protected UMLClassifier type;
	// name
	protected String name;
	// access
	protected String access;

	/**
	 * UMLAttribute constructor.
	 *
	 * @param name name of the UML attribute
	 * @param type type of the UML attribute
	 * @param access type of access (private, public, protected)
	 */
	public UMLAttribute(String name, UMLClassifier type, String access) {
		this.name = name;
		this.type = type;
		this.access = access;
	}

	/**
	 * Method returns a list of strings that contains name,
	 * type and access of UML attribute.
	 *
	 * @return list of strings [name, type, access]
	 */
	// TODO zde se uz vse uklada ve spravnem formatu
	public List<String> getNameTypeAccess() {
		List<String> nameTypeAccess = new ArrayList<>();
		nameTypeAccess.add(this.name);
		nameTypeAccess.add(this.type.getName());
		nameTypeAccess.add(this.access);

		return nameTypeAccess;
	}

	/**
	 * Method returns type of the UML attribute.
	 *
	 * @return type of the UML attribute as UMLClassifier
	 */
	public String getType() {
		return this.type.toString();
	}

	/**
	 * Method returns an UMLAttribute as a string in the format:
	 * "name:type".
	 *
	 * @return "name:type" string that represens the UMLAttribute
	 */
	/*
	public String toString() {
		return this.getName() + ":" + this.type.toString();
	}
	*/
}

