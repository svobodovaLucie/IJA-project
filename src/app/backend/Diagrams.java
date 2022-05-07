/*
 * File:         CommandBuilder.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * Backend file, that holds data from classDiagram and all the sequence diagrams.
 */
package app.backend;

import app.backend.uml.ClassDiagram;
import app.backend.uml.SeqDiagram;

import java.util.ArrayList;
import java.util.List;

/**
 * Backend class, that holds data from classDiagram and all the sequence diagrams.
 */
public class Diagrams {

    // one class diagram
    private ClassDiagram classDiagram;

    // sequence diagrams
    private List<SeqDiagram> seqDiagrams;


    /**
     * Diagram constructor.
     */
    public Diagrams() {
        this.classDiagram = null;
        this.seqDiagrams = new ArrayList<>();
    }

    /**
     * Add ClassDiagram to Backend.
     * @param classDiagram classDiagram that 'll be stored in backend.
     */
    public void addClassDiagram(ClassDiagram classDiagram) {
        this.classDiagram = classDiagram;
    }

    /**
     * Add one sequence diagram to Backend.
     * @param seqDiagram sequence diagram that 'll be stored in backend.
     */
    public void addSeqDiagram(SeqDiagram seqDiagram) {
        this.seqDiagrams.add(seqDiagram);
    }


    /**
     * @return class diagram stored in Backend.
     */
    public ClassDiagram getClassDiagram() {
        return this.classDiagram;
    }

    /**
     * @return list of sequence diagrams.
     */
    public List<SeqDiagram> getSeqDiagrams() {
        return this.seqDiagrams;
    }
}
