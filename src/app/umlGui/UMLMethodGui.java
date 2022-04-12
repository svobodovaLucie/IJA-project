/*
 * File:         UMLMethodGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation od UMLMethod class that represents
 * the UML method.
 */
package app.umlGui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class represents a method, that has its name, return type and
 * a list of arguments. It is inherited from UMLAttribute class,
 * from that it inherits the name and the list of arguments.
 * Argument is represented by UMLAttribute class. It can be used
 * ad a part of UML classifier class or interface.
 */
public class UMLMethodGui extends TextField {
	// type
	private UMLClassifierGui nodeType;
	// name
	private String name;
	// access
	private String access;
	// list of attributes
	private List<UMLAttributeGui> attributes;

	/**
	 * UMLMethodGui constructor.
	 *
	 * @param name name of the UML method
	 * @param type type of the UML method
	 * @param access access type of the UML method
	 */
	public UMLMethodGui(String name, UMLClassifierGui type, String access) {
		this.name = name;
		this.nodeType = type;
		this.attributes = new ArrayList<>();
		this.access = convertAccess(access);
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
			//this.getChildren().add(this.attributes.get(attributes.size() - 1));
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
		tmp = tmp + "):" + this.nodeType.getType() ;
		return tmp;
	}
}

