package app.uml;

/**
 * Třída reprezentuje atribut, který má své jméno a typ.
 * Je odvozena (rozšiřuje) od třídy Element. Typ atributu
 * je reprezentován třidou UMLClassifier. Lze použít jako
 * atribut UML třídy nebo argument operace.
 *
 * @author Lucie Svobodová
 */
public class UMLAttribute extends Element {
	private UMLClassifier type;

	// Poskytuje informaci o typu atributu.
	public UMLClassifier getType() {
		return this.type;
	}

	// Vrací řetězec reprezentující stav atributu v podobě "nazev:typ".
	public String toString() {
		return this.getName() + ":" + this.type.toString();
	}

	// Konstruktor - vytvoří instanci atributu.
	public UMLAttribute(String name, UMLClassifier type) {
		super(name);
		this.type = type;
	}
}

