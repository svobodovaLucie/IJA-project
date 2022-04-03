package app.uml;

/**
 * Třída reprezentuje pojmenovaný element (thing),
 * který může být součástí jakékoliv části v diagramu.
 *
 * @author Lucie Svobodová
 */
public class Element {
	private String name;

	// Vrátí název elementu.
	public String getName() {
		return this.name;
	}

	// Přejmenuje element.
	public void rename(String newName) {
		this.name = newName;
	}

	// Konstruktor - vytvoří instanci se zadaným názvem.
	public Element(String name) {
		this.name = name;
	}
}

