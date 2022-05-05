/*
 * File:         UMLMethodGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLMethodGui class that represents
 * the UML method displayed in the GUI.
 */
package app.umlGui;

import app.uml.UMLMethod;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Class represents a method displayed in the GUI.
 */
public class UMLMethodGui extends TextField {
	// method to be represented
	private UMLMethod umlMethod;

	// inherited from super (UMLAttributeGui):
	// type, name, access
	// TODO probably not inherited

	// type
	protected String type;
	// name
	protected String name;
	// access
	protected String access;
	// list of attributes
	private List<UMLAttributeGui> attributes;

	/**
	 * UMLMethodGui constructor.
	 *
	 * @param umlMethod UMLMethod to be represented in GUI
	 */
	public UMLMethodGui(UMLMethod umlMethod) {
		this.umlMethod = umlMethod;

		List <String> nameTypeAccess = umlMethod.getNameTypeAccess();
		this.name = nameTypeAccess.get(0);
		this.type = nameTypeAccess.get(1);
		this.access = convertAccess(nameTypeAccess.get(2));

		this.attributes = new ArrayList<>();
		this.setStyle("-fx-background-color: transparent;\n" +
					  "-fx-border-style: none none none none;\n" +
				      "-fx-background-insets: 0, 0 0 1 0 ;\n" +
					  "-fx-background-radius: 0;\n" +
					  "-fx-border-color: transparent;");

		// event listener
		this.textProperty().addListener(((observableValue, s, t1) ->
				this.umlMethod.setNameTypeAccess(this.getNameTypeAccess())
		));
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
		if (Objects.equals(access, "default"))
			return "~";
		return " ";
	}

	/**
	 * Method inserts new argument to the list of UMLMethodGui arguments.
	 * Argument is inserted to the end of the list. If the list
	 * already contains an argument with the name same, new argument
	 * is not added to the list.
	 *
	 * @param arg UML argument to be added to the list of UML arguments
	 * @return true if the argument was inserted,
	 *         false if an exception is catched
	 */
	public boolean addArgumentGui(UMLAttributeGui arg) {
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
	 * Method returns string that contains the information
	 * about the UML method.
	 * accessType methodName (argType1, argType2, ...) : returnType
	 * eg. +draw(int, string):void
	 *
	 * @return string
	 */
	public String toStringMethAttr() {
		// accessType name
		String tmp = this.access + this.name + "(";
		// attributes type
		for (UMLAttributeGui attr : this.attributes) {
			tmp = tmp + attr.toStringType() + ",";
		}
		if (this.attributes.size() > 0) {
			tmp = tmp.substring(0, tmp.length() - 1);
		}
		// returnType
		tmp = tmp + "):" + this.type;
		return tmp;
	}

	/**
	 * Method returns a list of UMLAttributeGui attributes.
	 *
	 * @return list of attributes
	 */
	public List<UMLAttributeGui> getMethodAttributes() {
		return this.attributes;
	}

	/**
	 * Method loads the text input and saves the types of method's attributes
	 * in a list of strings.
	 * Eg. [int, void, string]
	 *
	 * @return list of strings with the method's attributes
	 */
	public List<String> getMethodAttributesTypes() {
		List<String> attr = new ArrayList<>();
		String string = this.getText();
		string = string.split("\\(")[1];
		while (string.length() > 0) {
			if (Pattern.matches(".*,.*\\).*", string)) {
				// has more types
				attr.add(string.split(",")[0]);	// int
				string = string.split(",")[1];	// void):string
			} else if (Pattern.matches(".*\\).*", string)) {
				// check if there is last type
				if (string.charAt(0) == ')') {
					// ) - no more type
					attr.add("");
				} else {
					// type)
					attr.add(string.split("\\)")[0]);	// void
				}
				break;
			} else {
				// change color to red or sth
				System.out.println("Invalid type\n");
				break;
			}
		}
		return attr;
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
		if (Objects.equals(access, "~"))
			return "default";
		return "";
	}

	/**
	 * Method returns a list of strings that contains name,
	 * type and access of UML method.
	 *
	 * @return list of strings [name, type, access]
	 */
	public List<String> getNameTypeAccess() {
		List<String> nameTypeAccess = new ArrayList<>();
		// +name(type,type):type
		String toSplit = this.getText();
		try {
			nameTypeAccess.add(toSplit.split("\\(")[0].substring(1));
		} catch (Exception exception) {
			nameTypeAccess.add("");
		}
		try {
			nameTypeAccess.add(toSplit.split(":")[1]);
		} catch (Exception exception) {
			nameTypeAccess.add("");
		}
		try {
			nameTypeAccess.add(this.toStringAccess(Character.toString(toSplit.charAt(0))));
		} catch (Exception exception) {
			nameTypeAccess.add("");
		}
		// check if the text is in right format
		if (Pattern.matches("^[+\\-~#][^():]+\\([^():]*\\):[^():]+$", this.getText())) {
			this.setStyle("-fx-background-color: transparent;\n" +
					"-fx-border-style: none none none none;\n" +
					"-fx-background-insets: 0, 0 0 1 0 ;\n" +
					"-fx-background-radius: 0;\n" +
					"-fx-border-color: transparent;\n" +
					"-fx-text-fill: black;");
		} else {
			this.setStyle("-fx-background-color: transparent;\n" +
					"-fx-border-style: none none none none;\n" +
					"-fx-background-insets: 0, 0 0 1 0 ;\n" +
					"-fx-background-radius: 0;\n" +
					"-fx-border-color: transparent;\n" +
					"-fx-text-fill: red;");
		}
		return nameTypeAccess;
	}

	public UMLMethod getMethod() {
		return this.umlMethod;
	}
}

