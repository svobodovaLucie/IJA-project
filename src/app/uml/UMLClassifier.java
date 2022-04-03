/*
 * File:         UMLClassifier.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLClassifier class that represents
 * the UML classifier.
 */
package app.uml;

/**
 * Class represents a diagram classifier. Classes that are inherited
 * from this class represents various classifiers - class, interface,
 * attribute etc.
 */
public class UMLClassifier extends Element {
	private boolean isUserDefined;

	/**
	 * UMLClassifier constructor.
	 *
	 * @param name name of the classifier
	 */
	public UMLClassifier(String name) {
		super(name);
		this.isUserDefined = true;
	}

	/**
	 * UMLClassifier constructor.
	 *
	 * @param name name of the classifier
	 * @param isUserDefined true if the classifier is user defined
	 *                      (is implemented in the diagram), false if not
	 */
	public UMLClassifier(String name, boolean isUserDefined) {
		super(name);
		this.isUserDefined = isUserDefined;
	}

	/**
	 * Factory method that creates an instance of the UML classifier.
	 * It sets the isUserDefined boolean to false.
	 *
	 * @param name name of the classifier
	 * @return UMLClassifier
	 */
	public static UMLClassifier forName(String name) {
		return new UMLClassifier(name, false);
	}

	/**
	 * Method returns true if the classifier is user defined.
	 * "modelován uživatelem v diagramu".
	 *
	 * @return true if the classifier is user defined,
	 *         false if not
	 */
	public boolean isUserDefined() {
		return this.isUserDefined;
	}

	/**
	 * Method returns the classifier as a string:
	 * "name(isUserDefined)", isUserDefined is true/false.
	 *
	 * @return string "name(isUserDefined)"
	 */
	public String toString() {
		return getName() + "(" + this.isUserDefined + ")";
	}
}

