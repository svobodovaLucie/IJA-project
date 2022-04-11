/*
 * File:         UMLClassifierGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLClassifier class that represents
 * the UML classifier.
 */
package app.umlGui;

/**
 * Class represents a diagram classifier. Classes that are inherited
 * from this class represents various classifiers - class, interface,
 * attribute etc.
 */
public class UMLClassifierGui {
	//private boolean isUserDefined;
	private String name;

	/**
	 * UMLClassifier constructor.
	 *
	 * @param name name of the classifier
	 */
	public UMLClassifierGui(String name) {
		this.name = name;
	}

	/**
	 * Method returns the classifier as a string:
	 * "name(isUserDefined)", isUserDefined is true/false.
	 *
	 * @return string "name(isUserDefined)"
	 */

	public String getType() {

		return this.name;// + "(" + this.isUserDefined + ")";
	}

}

