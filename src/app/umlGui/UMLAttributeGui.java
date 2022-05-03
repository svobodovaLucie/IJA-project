/*
 * File:         UMLAttributeGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the UML attribute displayed in GUI.
 */
package app.umlGui;

import app.uml.UMLAttribute;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class represents the UML attribute with name and type.
 */
public class UMLAttributeGui extends TextField {

	// BE attribute that is represented in GUI
	private UMLAttribute umlAttribute;

	// type
	protected UMLClassifierGui nodeType;
	// name
	protected String name;
	// access
	protected String access;

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
		this.setStyle("-fx-background-color: transparent;\n" +
				"-fx-border-style: none none none none;\n" +
				"-fx-background-insets: 0, 0 0 1 0 ;\n" +
				"-fx-background-radius: 0;\n" +
				"-fx-border-color: transparent;");
	}

	/**
	 * Method converts the access string to the character.
	 * public    ->  +
	 * private   ->  -
	 * protected ->  #
	 *
	 * @param access access type
	 * @return string +, -, # or " " if invalid
	 */
	private String convertAccess(String access) {
		if (Objects.equals(access, "public"))
			return "+";
		if (Objects.equals(access, "private"))
			return "-";
		if (Objects.equals(access, "protected"))
			return "#";
		return " ";
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

	/**
	 * Method returns string with the full name of access
	 * from a string containing the shortened version.
	 * + -> public
	 * - -> private
	 * # -> protected
	 *
	 * @param access short version of access (+, -, #)
	 * @return long version of access (public, private, protected)
	 */
	public String toStringAccess(String access) {
		if (Objects.equals(access, "+"))
			return "public";
		if (Objects.equals(access, "-"))
			return "private";
		if (Objects.equals(access, "#"))
			return "protected";
		return "";
	}

	/**
	 * Method returns a list of strings that contains name,
	 * type and access of UML attribute.
	 *
	 * @return list of strings [name, type, access]
	 */
	public List<String> getNameTypeAccess() {
		List<String> nameTypeAccess = new ArrayList<>();
		String toSplit = this.getText();
		nameTypeAccess.add(toSplit.split(":")[0].substring(1));
		nameTypeAccess.add(toSplit.split(":")[1]);
		nameTypeAccess.add(this.toStringAccess(Character.toString(toSplit.charAt(0))));
		return nameTypeAccess;
	}
}

