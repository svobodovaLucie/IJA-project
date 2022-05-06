package app.uml;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class UMLInterface extends UMLClassifier {
    private List<UMLAttribute> attributes;
    private List<UMLMethod> methods;

    public UMLInterface(String name) {
        super(name);
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
    }
}
