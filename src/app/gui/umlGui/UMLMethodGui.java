/*
 * File:         UMLMethodGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of UMLMethodGui class that represents
 * the UML method displayed in the GUI.
 */
package app.gui.umlGui;

import app.backend.uml.UMLAttribute;
import app.backend.uml.UMLMethod;
import javafx.scene.control.TextField;
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
		checkInputFormat();

		// event listener
		this.textProperty().addListener(((observableValue, s, t1) ->
				this.umlMethod.setNameTypeAccess(this.getNameTypeAccess())
		));
	}

	/**
	 * Method returns the UML method that is represented by this UMLMethodGui.
	 *
	 * @return backend UML method
	 */
	public UMLMethod getMethod() {
		return this.umlMethod;
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
		if (Objects.equals(access, "package"))
			return "~";
		return " ";
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
			return "package";
		return "";
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
			if (Pattern.matches(".*,.+\\).*", string)) {
				// has more types
				attr.add(string.split(",")[0]);	// int
				string = string.split(",", 2)[1];	// void):string
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
				checkInputFormat();
				break;
			}
		}
		return attr;
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
		if (checkInputFormat()) {
			checkArgumentsTypes();
		}
		return nameTypeAccess;
	}

	/**
	 * Method checks the input and returns true if okay and false if not okay (red).
 	 */
	private boolean checkInputFormat() {
		String style = "-fx-background-color: transparent;\n" +
				"-fx-border-style: none none none none;\n" +
				"-fx-background-insets: 0, 0 0 1 0 ;\n" +
				"-fx-background-radius: 0;\n" +
				"-fx-border-color: transparent;\n";
		if (Pattern.matches("^[+\\-~#][^():]+\\([^():]*\\):[^():]+$", this.getText())) {
			// format is okay
			this.setStyle(style + "-fx-text-fill: black;");
			return true;
		} else {
			// format is not okay -> red color
			this.setStyle(style + "-fx-text-fill: red;");
			return false;
		}
	}

	/**
	 * Method sets the message color to green (-> overriden).
	 */
	public void setOverridden() {
		String style = "-fx-background-color: transparent;\n" +
				"-fx-border-style: none none none none;\n" +
				"-fx-background-insets: 0, 0 0 1 0 ;\n" +
				"-fx-background-radius: 0;\n" +
				"-fx-border-color: transparent; -fx-text-fill: green;\n";
		this.setStyle(style);
	}

	/**
	 * Method checks the attributes types.
	 */
	private void checkArgumentsTypes() {
		// get UMLMethod attributes and attributes from the text input
		List<String> attributesGui = getMethodAttributesTypes();
		List<UMLAttribute> attributes = this.getMethod().getAttributes();
		// check number of attributes
		int num = attributesGui.size() - attributes.size();
		for (int i = 0; i < Math.abs(num); i++) {
			if (num > 0) {	// add attribute
				this.getMethod().createArgument();
			} else {		// remove attribute
				this.getMethod().removeArgument();
			}
		}
		// check attributes types
		int n = 0;
		for (UMLAttribute a : attributes) {
			a.setType(attributesGui.get(n++));
		}
	}
}

