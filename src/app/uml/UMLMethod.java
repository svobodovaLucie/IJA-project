package app.uml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Třída reprezentuje operaci, která má své jméno, návratový typ
 * a seznam argumentů. Je odvozena (rozšiřuje) od třídy UMLAttribute,
 * od které přejímá název a návratový typ. Argument je reprezentován
 * třídou UMLAttribute. Lze použít jako součást UML klasifikátoru
 * třída nebo rozhraní.
 *
 * @author Lucie Svobodová
 */
public class UMLMethod extends UMLAttribute {
	private List<UMLAttribute> attributes;

	// Tovární metoda pro vytvoření instance operace.
	public static UMLMethod create(String name, UMLClassifier type,
								   UMLAttribute... args) {
		UMLMethod inst = new UMLMethod(name, type);
		inst.attributes.addAll(Arrays.asList(args));
		return inst;
	}

	// Přidá nový argument do seznamu argumentů. Argument
	// se vloží na konec seznamu. Pokud v seznamu již existuje
	// argument stejného názvu, operaci neprovede.
	public boolean addArgument(UMLAttribute arg) {
		if (this.attributes.contains(arg))
			return true;
		try {
			this.attributes.add(arg);
		} catch (UnsupportedOperationException uoe) {
			return false;
		}
		return true;
	}

	// Vrací nemodifikovatelný seznam argumentů. Lze využít pro zobrazení.
	public List <UMLAttribute> getArguments() {
		return this.attributes;
	}

	// Konstruktor pro vytvoření operace s daným názvem a návratovým typem.
	public UMLMethod(String name, UMLClassifier type) {
		super(name, type);
		this.attributes = new ArrayList<>();
	}
}

