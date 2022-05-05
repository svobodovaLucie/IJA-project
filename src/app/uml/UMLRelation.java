package app.uml;

public class UMLRelation {
    UMLClass from;
    UMLClass to;
    String type;

    public UMLRelation(ClassDiagram classDiagram, String classFrom, String classTo, String type) {
        assert classDiagram != null;
        // TODO handle null
        this.from = classDiagram.findClass(classFrom);
        this.to = classDiagram.findClass(classTo);
        assert this.from != null;
        assert this.to != null;
        this.type = type;
        System.out.println("Relation created");
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
