/*
 * File:         UMLAttribute.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the UML attribute.
 */
package app.backend.uml;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents the UML attribute with name and type.
 * It is inherited from the Element class. The type of the
 * attribute is represented by UMLClassifier type. It can
 * be used as UML class attribute or UML method argument.
 */
public class UMLAttribute {
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
		return this.type.getName();
	}

	/**
	 * Method sets name, type and access.
	 *
	 * @param nameTypeAccess list of strings that represent Name, Type, Access
	 */
	public void setNameTypeAccess(List <String> nameTypeAccess) {
		this.name = nameTypeAccess.get(0);
		this.type.setName(nameTypeAccess.get(1));
		this.access = nameTypeAccess.get(2);
	}

	/**
	 * Method sets type of the attribute.
	 *
	 * @param type string that represent attribute type
	 */
	public void setType(String type) {
		this.type.setName(type);
	}
}

