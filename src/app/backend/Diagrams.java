package app.backend;

import app.uml.ClassDiagram;
import app.uml.SeqDiagram;

import java.util.ArrayList;
import java.util.List;

public class Diagrams {
    // one class diagram
    private ClassDiagram classDiagram;

    // sequence diagrams
    private List<SeqDiagram> seqDiagrams;

    // Diagrams constructor
    public Diagrams() {
        this.classDiagram = null;
        this.seqDiagrams = new ArrayList<>();
    }

    public void addClassDiagram(ClassDiagram classDiagram) {
        this.classDiagram = classDiagram;
    }

    public void addSeqDiagram(SeqDiagram seqDiagram) {
        this.seqDiagrams.add(seqDiagram);
    }

    // method returns class diagram
    public ClassDiagram getClassDiagram() {
        return this.classDiagram;
    }

    // method returns list of sequence diagrams
    public List<SeqDiagram> getSeqDiagrams() {
        return this.seqDiagrams;
    }


}
