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
package app.backend.uml;

/**
 * Class represents a diagram classifier. Classes that are inherited
 * from this class represents various classifiers - class, interface,
 * attribute etc.
 */
public class UMLClassifier {
	// name
	protected String name;

	/**
	 * UMLClassifier constructor.
	 *
	 * @param name name of the classifier
	 */
	public UMLClassifier(String name) {
		this.name = name;
	}

	/**
	 * Method sets name of the UMLClassifier.
	 *
	 * @param name new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method returns name of the classifier.
	 *
	 * @return name of the classifier
	 */
	public String getName() {
		return this.name;
	}
}

