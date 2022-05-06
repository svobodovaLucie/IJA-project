/*
 * File:         UMLAttributeGui.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains implementation of the UML Message displayed in GUI.
 */


package app.umlGui;
import app.uml.UMLMessage;
import app.uml.UMLMethod;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Label;


/**
 * Represent one UMLMessage in sequence diagram. Hold all the GUI information about message.
 */
public class UMLMessageGui {

    // backend message
    UMLMessage message;

    // holds arrow
    private Group arrow;

    // arrow order
    private int order;

    // information abou consistency
    private boolean consitent;

    // First actor X pos
    public int baseXPos = 260;
    public int spaceBetweenActors = 150;
    public int baseYPos = 70;
    public int spaceBetweenMessages = 50;

    // index in actors list
    int indexActFrom;
    int indexActTo;

    /**
     * Constructor
     * @param message Backend message representation
     * @param order order of message.
     * @param seq sequence diagram of message
     */
    public UMLMessageGui(UMLMessage message, int order, UMLSeqDiaGui seq){
        this.message = message;
        this.order = order;

        int indexActFrom = seq.getActorGuiIndex(message.getFromActor(),
                                                message.getFromClass());
        int indexActTo   = seq.getActorGuiIndex(message.getToActor(),
                                                message.getToClass());
        if (indexActFrom < 0 || indexActTo < 0) {
            System.out.println("ERROR: indexFrom " + indexActFrom + "indexTo " + indexActTo);
        }

        this.indexActFrom = indexActFrom;
        this.indexActTo = indexActTo;

        String messageText;
        if (message.getMethod() != null){
            messageText = message.getMethod().getName();
        }
        else {
            messageText = "UNKNOWN";
        }

        if (message.getMethod() == null)
            this.consitent = true;
        else
            this.consitent = false;

        if (message.getType().equals("synch")){
            this.createSynchArrow(indexActFrom, indexActTo, messageText);
        }
        else if (message.getType().equals("asynch")){
            this.createAsynchArrow(indexActFrom, indexActTo, messageText);
        }
        else if (message.getType().equals("response")){
            createDottedArrow(indexActFrom, indexActTo, "response");
        }
        else if (message.getType().equals("create")){
            seq.paintActor(message.getToActor(), message.getToClass());
            indexActTo   = seq.getActorGuiIndex(message.getToActor(),
                    message.getToClass());

            createDottedArrow(indexActFrom, indexActTo, "<<create>>");
        }
        else if (message.getType().equals("free")){
            // i will free the ACTGUI
            if(indexActTo > 0){

                seq.getNthActorGui(indexActTo).setFreed(true);
            }
            createDottedArrow(indexActFrom, indexActTo, "<<delete>>");
        }
        else{
            this.arrow = null;
        }

    }

    /**
     * Create line
     * @param xFrom
     * @param xTo
     * @param yFrom
     * @param yTo
     * @return line
     */
    private Line createLine(int xFrom, int xTo, int yFrom, int yTo){
        Line line = new Line();
        line.setStartX(xFrom);
        line.setEndX(xTo);
        line.setStartY(yFrom);
        line.setEndY(yTo);
        return line;
    }

    /**
     * Create circle
     * @param x x center position
     * @param y y center positon
     * @param radius
     * @return circle
     */
    private Circle createCircle(int x, int y, int radius){
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        return circle;
    }

    /**
     * Create horizontal dotted line
     * @param posFrom
     * @param posTo
     * @param yPos both from to
     * @return doted line
     */
    private Group createDottedLine(int posFrom, int posTo, int yPos){
        Group group = new Group();
        if (posTo < posFrom){
            int a = posTo;
            posTo = posFrom;
            posFrom = a;
        }
        for (int i = posFrom; i < posTo;){
            Line line = this.createLine(i, i+5, yPos, yPos);
            i+=10;
            group.getChildren().add(line);
        }
        return group;
    }

    /**
     * Create dotted arrow from actor to actor
     * @param indexActorFrom
     * @param indexActorTo
     * @param text that ll be on arrow.
     */
    private void createDottedArrow(int indexActorFrom, int indexActorTo, String text){
        int posFrom = this.countPosFrom(indexActorFrom);
        int posTo   = this.countPosTo(posFrom, indexActorFrom, indexActorTo);
        int yPos    = this.countYPos();

        Group group = this.createDottedLine(posFrom, posTo, yPos);

        Line line2;
        Line line3;
        if (indexActorFrom < indexActorTo){
            line2 = this.createLine(posTo-20, posTo, yPos-10, yPos);
            line3 = this.createLine(posTo-20, posTo, yPos+10, yPos);
        }
        else {
            line2 = this.createLine(posTo+20, posTo, yPos-10, yPos);
            line3 = this.createLine(posTo+20, posTo, yPos+10, yPos);
        }

        Circle circle = createCircle(posFrom, yPos, 2);

        group.getChildren().add(line2);
        group.getChildren().add(line3);
        group.getChildren().add(circle);

        int textXpos = (posTo + posFrom) / 2 - 5;
        int textYpos = yPos - 20;

        if(indexActorFrom > indexActorTo){
            group.getChildren().add(createArrowText(textXpos-20, textYpos, text));
        }
        else {
            group.getChildren().add(createArrowText(textXpos, textYpos, text));
        }
        setArrow(group);
    }

    /**
     * Create synchronous arrow
     * @param indexActorFrom
     * @param indexActorTo
     * @param text on arrow
     */
    private void createSynchArrow(int indexActorFrom, int indexActorTo, String text){
        int posFrom = this.countPosFrom(indexActorFrom);
        int posTo   = this.countPosTo(posFrom, indexActorFrom, indexActorTo);
        int yPos    = this.countYPos();

        Group group = new Group();
        Line line  = createLine(posFrom, posTo, yPos, yPos);

        Circle circle = createCircle(posFrom, yPos, 2);

        Polygon triangle = new Polygon();
        if (indexActorFrom < indexActorTo){
            triangle.getPoints().setAll(
                    (posTo-20.0), (yPos-10.0),
                    (posTo+0.0), (yPos+0.0),
                    (posTo-20.0), (yPos+10.0)
            );
        }
        else {
            triangle.getPoints().setAll(
                    (posTo+20.0), (yPos-10.0),
                    (posTo+0.0), (yPos+0.0),
                    (posTo+20.0), (yPos+10.0)
            );
        }

        group.getChildren().add(line);
        group.getChildren().add(circle);
        group.getChildren().add(triangle);

        int textXpos = (posTo + posFrom) / 2 - 5;
        int textYpos = yPos - 20;

        group.getChildren().add(createArrowText(textXpos, textYpos, text));
        setArrow(group);

    }

    /**
     * Create asynchronous arrow
     * @param indexActorFrom
     * @param indexActorTo
     * @param text on arrow
     */
    private void createAsynchArrow(int indexActorFrom, int indexActorTo, String text){

        int posFrom = this.countPosFrom(indexActorFrom);
        int posTo   = this.countPosTo(posFrom, indexActorFrom, indexActorTo);
        int yPos    = this.countYPos();

        Group group = new Group();
        Line line = createLine(posFrom, posTo, yPos, yPos);

        Circle circle = createCircle(posFrom, yPos, 2);

        Line line2;
        Line line3;
        if (indexActorFrom < indexActorTo){
            line2 = this.createLine(posTo-20, posTo, yPos-10, yPos);
            line3 = this.createLine(posTo-20, posTo, yPos+10, yPos);
        }
        else {
            line2 = this.createLine(posTo+20, posTo, yPos-10, yPos);
            line3 = this.createLine(posTo+20, posTo, yPos+10, yPos);
        }

        group.getChildren().add(line);
        group.getChildren().add(line2);
        group.getChildren().add(line3);
        group.getChildren().add(circle);

        int textXpos = (posTo + posFrom) / 2 - 5;
        int textYpos = yPos - 20;

        group.getChildren().add(createArrowText(textXpos, textYpos, text));
        setArrow(group);

    }

    /**
     * Create text on top of arrow
     * @param x
     * @param y
     * @param text
     * @return Label
     */
    private Label createArrowText(int x, int y, String text){
        Label label = new Label(text);
        label.prefHeight(40);
        label.prefWidth(40);
        if (this.getType().equals("create") ||
                this.getType().equals("free") ||
                this.getType().equals("response")){

            label.setStyle("-fx-font-size: 14;");

        }
        else {
            if (this.getMethod() == null){
                System.out.println("....");
                System.out.println(text);
                System.out.println(this.getMethod());
                System.out.println("....");
                label.setStyle("-fx-border-color: red; -fx-font-size:15;");
            }
            else{
                label.setStyle("-fx-font-size: 14;");
            }
        }

        // set position
        label.setLayoutX(x);
        label.setLayoutY(y);

        return label;
    }

    /**
     * Store arrow
     * @param arrow
     */
    private void setArrow(Group arrow){
        this.arrow = arrow;
    }

    /**
     * @return message order
     */
    public int getOrder(){
        return this.order;
    }

    /**
     * @return message arrow
     */
    public Group getArrow(){
        return this.arrow;
    }

    /**
     * Count x position from.
     * @param indexActorFrom
     * @return position where to start drawing.
     */
    private int countPosFrom(int indexActorFrom){
        return this.baseXPos + ((indexActorFrom) * this.spaceBetweenActors);
    }

    /**
     * Count x postiton where to end.
     * @param posFrom
     * @param indexActorFrom
     * @param indexActorTo
     * @return position where to end drawing.
     */
    private int countPosTo(int posFrom, int indexActorFrom, int indexActorTo){
        int res;
        int a;
        if (indexActorFrom < indexActorTo){
            a = indexActorTo - indexActorFrom;
            res = posFrom + (this.spaceBetweenActors*a);
        }
        else {
            a = indexActorFrom - indexActorTo;
            res = posFrom - (this.spaceBetweenActors*a);
        }
        return res;
    }

    /**
     * @return UMLMessage
     */
    public UMLMessage getMessage(){
        return this.message;
    }

    /**
     * @return y position where to draw
     */
    private int countYPos(){
        return baseYPos + ((this.getOrder()+1) * this.spaceBetweenMessages);
    }

    /**
     * @return UMLMethod of message
     */
    private UMLMethod getMethod(){
        return this.message.getMethod();
    }

    /**
     * @return message type
     */
    private String getType(){
        return this.message.getType();
    }

    /**
     * @return index actor to
     */
    public int getIndexActFrom() {
        return indexActFrom;
    }

    /**
     * @return index actor to
     */
    public int getIndexActTo(){
        return indexActTo;
    }
}
