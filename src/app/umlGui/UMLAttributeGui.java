/*
 * File:         UMLAttributeGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the UML attribute.
 */
package app.umlGui;

import javafx.scene.control.Label;

import java.util.Objects;

/**
 * Class represents the UML attribute with name and type.
 * It is inherited from the Element class. The type of the
 * attribute is represented by UMLClassifier type. It can
 * be used as UML class attribute or UML method argument.
 */
public class UMLAttributeGui extends Label {
	// type
	private UMLClassifierGui nodeType;
	// name
	private String name;
	// access
	private String access;

	/**
	 * UMLAttributeGui constructor.
	 *
	 * @param name name of the UML attribute
	 * @param type type of the UML attribute
	 * @param access access type
	 */
	public UMLAttributeGui(String name, UMLClassifierGui type, String access) {
		this.name = name;
		this.nodeType = type;
		this.access = convertAccess(access);
		this.setText(this.toStringAttrType());
	}

	/**
	 * Method converts the access string to the character.
	 * public    ->  +
	 * private   ->  -
	 * protected ->  #
	 *
	 * @param access access type
	 * @return string +, -, # or "" if invalid
	 */
	private String convertAccess(String access) {
		if (Objects.equals(access, "public"))
			return "+";
		if (Objects.equals(access, "private"))
			return "-";
		if (Objects.equals(access, "protected"))
			return "#";
		return "";
	}

	/**
	 * Method returns string that contains the information
	 * about the UML attribute.
	 * accessType attributeName : attributeType
	 * eg. +name:string
	 *
	 * @return string
	 */
	public String toStringAttrType() {
		return this.access + this.name + ":" + this.nodeType.getType();
	}

	/**
	 * Method returns string that contains the type of UML attribute.
	 *
	 * @return string
	 */
	public String toStringType() {
		return this.nodeType.getType();
	}
}

