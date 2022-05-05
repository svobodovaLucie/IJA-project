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
package app.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a method, that has its name, return type and
 * a list of arguments. It is inherited from UMLAttribute class,
 * from that it inherits the name and the list of arguments.
 * Argument is represented by UMLAttribute class. It can be used
 * ad a part of UML classifier class or interface.
 */
public class UMLMethod extends UMLAttribute {
	private List<UMLAttribute> attributes;

	//private UMLClass owner;

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
		//this.owner = classDiagram;
	}

	/**
	 * Factory method that creates an instance of UMLMethod.
	 *
	 * @param name name of the UML method
	 * @param type type of the UML method
	 * @param args list of UML arguments
	 * @return UMLMethod
	 */
	/*
	public static UMLMethod create(String name, UMLClassifier type,
								   UMLAttribute... args) {
		UMLMethod inst = new UMLMethod(name, type);
		inst.attributes.addAll(Arrays.asList(args));
		return inst;
	}

	 */

	/**
	 * Method inserts new argument to the list of UMLMethod arguments.
	 * Argument is inserted to the end of the list. If the list
	 * already contains an argument with the name same, new argument
	 * is not added to the list.
	 *
	 * @param arg UML argument to be added to the list of UML arguments
	 * @return true if the argument was inserted,
	 *         false if an exception is catched
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
	 * Method returns a unmodifiable list of the UML method's arguments.
	 *
	 * @return list of the method's argument
	 */
	/*
	public List <UMLAttribute> getArguments() {
		return this.attributes;
	}

	 */

	/**
	 * Method returns a list of strings that contains name,
	 * type and access of UML method.
	 *
	 * @return list of strings [name, type, access]
	 */
	/*
	// TODO zde se uz vse uklada ve spravnem formatu
	public List<String> getNameTypeAccess() {
		List<String> nameTypeAccess = new ArrayList<>();
		nameTypeAccess.add(this.name);
		nameTypeAccess.add(this.type.getName());
		nameTypeAccess.add(this.access);

		return nameTypeAccess;
	}

	 */

	// TODO
	public List<UMLAttribute> getAttributes() {
		return this.attributes;
	}
}

