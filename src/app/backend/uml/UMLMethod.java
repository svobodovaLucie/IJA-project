/*
 * File:         UMLMethod.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLMethod class that represents
 * the UML method.
 */
package app.backend.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a method, that has its name, return type and
 * a list of arguments. It is inherited from UMLAttribute class,
 * from that it inherits the name and the list of arguments.
 * Argument is represented by UMLAttribute class. It can be used
 * as a part of UML classifier class or interface.
 */
public class UMLMethod extends UMLAttribute {
	private List<UMLAttribute> attributes;

	/**
	 * UMLMethod constructor.
	 *
	 * @param name name of the UML method
	 * @param type type of the UML method
	 * @param access type of access (private, public, protected)
	 */
	public UMLMethod(String name, UMLClassifier type, String access) {
		super(name, type, access);
		this.attributes = new ArrayList<>();
	}

	/**
	 * Method inserts new argument to the list of UMLMethod arguments.
	 * Argument is inserted to the end of the list. If the list
	 * already contains an argument with the name same, new argument
	 * is not added to the list.
	 *
	 * @param arg UML argument to be added to the list of UML arguments
	 * @return true if the argument was inserted,
	 *         false if an exception is caught
	 */
	public boolean addArgument(UMLAttribute arg) {
		if (this.attributes.contains(arg))
			return true;
		try {
			this.attributes.add(arg);
		} catch (UnsupportedOperationException uoe) {
			return false;
		}
		return true;
	}

	/**
	 * Method creates new attribute of UMLMethod with
	 * empty name, empty type and empty access.
	 */
	public void createArgument() {
		UMLClassifier c = new UMLClassifier("");
		UMLAttribute atr = new UMLAttribute("", c, "");
		this.attributes.add(atr);
	}

	/**
	 * Method removes an attribute of the UMLMethod.
	 */
	public void removeArgument() {
		this.attributes.remove(0);
	}

	/**
	 * Method returns name of the UMLMethod.
	 *
	 * @return name of the method
	 */
	public String getName(){
		return super.name;
	}

	/**
	 * Method returns a list of method's attributes.
	 *
	 * @return
	 */
	public List<UMLAttribute> getAttributes() {
		return this.attributes;
	}
}

