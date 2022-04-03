package app.uml;

/**
 * Třída reprezentuje klasifikátor v diagramu. Odvozené třídy
 * reprezentují konkrétní podoby klasifikátoru (třída, rozhraní,
 * atribut, apod.)
 *
 * @author Lucie Svobodová
 */
public class UMLClassifier extends Element {
	private boolean isUserDefined;

	// Tovární metoda pro vytvoření instance třídy Classifier
	// pro zadané jméno. Instance reprezentuje klasifikátor,
	// který není v diagramu modelován.
	public static UMLClassifier forName(String name) {
		return new UMLClassifier(name, false);
	}

	// Zjišťuje, zda objekt reprezentuje klasifikátor,
	// který je modelován uživatelem v diagramu nebo ne.
	public boolean isUserDefined() {
		return this.isUserDefined;
	}

	// Vrací řetězec reprezentující klasifikátor v podobě
	// "nazev(userDefined)", kde userDefined je true nebo false.
	public String toString() {
		return getName() + "(" + this.isUserDefined + ")";
		//"nazev(true/false)";
	}

	// Konstruktor - vytvoří instanci třídy Classifier.
	public UMLClassifier(String name) {
		super(name);
		this.isUserDefined = true;
	}

	// Konstruktor - vytvoří instanci třídy Classifier.
	// Instance je uživatelsky definována (je součástí diagramu).
	public UMLClassifier(String name, boolean isUserDefined) {
		super(name);
		this.isUserDefined = isUserDefined;
	}
}

