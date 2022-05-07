package app.backend.uml;

public class UMLRelation {
    UMLClass from;
    UMLClass to;
    String type;

    public UMLRelation(ClassDiagram classDiagram, String classFrom, String classTo, String type) {
        assert classDiagram != null;
        // TODO handle null
        this.from = classDiagram.findClassInterface(classFrom);
        this.to = classDiagram.findClassInterface(classTo);
        assert this.from != null;
        assert this.to != null;
        this.type = type;
    }

    public UMLClass getClassFrom() {
        return this.from;
    }

    public UMLClass getClassTo() {
        return this.to;
    }

    public String getRelationType() {
        return this.type;
    }
}
