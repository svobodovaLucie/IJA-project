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
import java.util.regex.Pattern;

/**
 * Class represents the UML attribute with name and type.
 */
public class UMLAttributeGui extends TextField {

	// BE attribute that is represented in GUI
	private UMLAttribute umlAttribute;

	// type
	protected String type;
	// name
	protected String name;
	// access
	protected String access;

	/**
	 * UMLAttributeGui constructor.
	 *
	 * @param umlAttribute UMLAttribute to be represented in GUI
	 */
	public UMLAttributeGui(UMLAttribute umlAttribute) {
		this.umlAttribute = umlAttribute;
		List <String> nameTypeAccess = umlAttribute.getNameTypeAccess();
		this.name = nameTypeAccess.get(0);
		this.type = nameTypeAccess.get(1);
		this.access = convertAccess(nameTypeAccess.get(2));
		this.setText(this.toStringAttrType());
		this.setStyle("-fx-background-color: transparent;\n" +
				"-fx-border-style: none none none none;\n" +
				"-fx-background-insets: 0, 0 0 1 0 ;\n" +
				"-fx-background-radius: 0;\n" +
				"-fx-border-color: transparent;");

		// event listener
		this.textProperty().addListener(((observableValue, s, t1) ->
				this.umlAttribute.setNameTypeAccess(this.getNameTypeAccess())
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
		if (Objects.equals(access, "package"))
			return "~";
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
		return this.access + this.name + ":" + this.type;
	}

	/**
	 * Method returns string that contains the type of UML attribute.
	 *
	 * @return string
	 */
	public String toStringType() {
		return this.type;
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
	 * Method returns a list of strings that contains name,
	 * type and access of UML attribute.
	 *
	 * @return list of strings [name, type, access]
	 */
	public List<String> getNameTypeAccess() {
		List<String> nameTypeAccess = new ArrayList<>();
		String toSplit = this.getText();
		try {
			nameTypeAccess.add(toSplit.split(":")[0].substring(1));
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
		checkInputFormat();
		return nameTypeAccess;
	}

	public UMLAttribute getAttribute() {
		return this.umlAttribute;
	}

	private void checkInputFormat() {
		String style = "-fx-background-color: transparent;\n" +
				"-fx-border-style: none none none none;\n" +
				"-fx-background-insets: 0, 0 0 1 0 ;\n" +
				"-fx-background-radius: 0;\n" +
				"-fx-border-color: transparent;\n";
		if (Pattern.matches("^[+\\-~#][^():]*:[^():]+$", this.getText())) {
			// format is okay
			this.setStyle(style + "-fx-text-fill: black;");
		} else {
			// format is not okay -> red color
			this.setStyle(style + "-fx-text-fill: red;");
		}
	}
}

