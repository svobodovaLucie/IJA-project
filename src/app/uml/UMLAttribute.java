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

/**
 * Class represents the UML attribute with name and type.
 * It is inherited from the Element class. The type of the
 * attribute is represented by UMLClassifier type. It can
 * be used as UML class attribute or UML method argument.
 */
public class UMLAttribute extends Element {
	private UMLClassifier type;

	/**
	 * UMLAttribute constructor.
	 *
	 * @param name name of the UML attribute
	 * @param type type of the UML attribute
	 */
	public UMLAttribute(String name, UMLClassifier type) {
		super(name);
		this.type = type;
	}

	/**
	 * Method returns type of the UML attribute.
	 *
	 * @return type of the UML attribute as UMLClassifier
	 */
	public UMLClassifier getType() {
		return this.type;
	}

	/**
	 * Method returns an UMLAttribute as a string in the format:
	 * "name:type".
	 *
	 * @return "name:type" string that represens the UMLAttribute
	 */
	public String toString() {
		return this.getName() + ":" + this.type.toString();
	}
}

