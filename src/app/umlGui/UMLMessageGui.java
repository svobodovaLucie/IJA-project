package app.umlGui;

import app.uml.UMLMessage;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ContentDisplay;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

import java.awt.*;

public class UMLMessageGui {

    // holds arrow
    private Group arrow;

    // arrow order
    private int order;

    private boolean consitent;

    // First actor X pos
    public int baseXPos = 260;
    public int spaceBetweenActors = 150;
    public int baseYPos = 70;
    public int spaceBetweenMessages = 50;


    public UMLMessageGui(UMLMessage message, int order, UMLSeqDiaGui seq){
        this.order = order;


        int indexActFrom = seq.getActorGuiIndex(message.getFromActor(),
                                                message.getFromClass());
        int indexActTo   = seq.getActorGuiIndex(message.getToActor(),
                                                message.getToClass());
        if (indexActFrom < 0 || indexActTo < 0) {
            System.out.println("ERROR: indexFrom " + indexActFrom + "indexTo " + indexActTo);
            //System.out.println("       " + seq.getNthActorGui(indexActFrom).getActorName() );
            //System.out.println("       " + seq.getNthActorGui(indexActTo).getActorName() );
        }


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

        // synch message
        // TODO TADY POKRACOVAT V PRACI
        if (message.getType().equals("synch")){
            this.createSynchArrow(indexActFrom, indexActTo, messageText);
        }
        else if (message.getType().equals("asynch")){
            this.createAsynchArrow(indexActFrom, indexActTo, messageText);
        }
        else if (message.getType().equals("response")){
            createDottedArrow(indexActFrom, indexActTo, "response");
        }
        else if (message.getType().equals("creat")){
            seq.paintActor(message.getToActor(), message.getToClass());
            indexActTo   = seq.getActorGuiIndex(message.getToActor(),
                    message.getToClass());

            createDottedArrow(indexActFrom, indexActTo, "<<creat>>");
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


    private Line createLine(int xFrom, int xTo, int yFrom, int yTo){
        Line line = new Line();
        line.setStartX(xFrom);
        line.setEndX(xTo);
        line.setStartY(yFrom);
        line.setEndY(yTo);
        return line;
    }

    private Circle createCircle(int x, int y, int radius){
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        return circle;
    }

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


    private void createDottedArrow(int indexActorFrom, int indexActorTo, String text){
        int posFrom = this.countPosFrom(indexActorFrom, indexActorTo);
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

    private void createSynchArrow(int indexActorFrom, int indexActorTo, String text){
        int posFrom = this.countPosFrom(indexActorFrom, indexActorTo);
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

    //private void createSynchArrow(int indexActorFrom, int indexActorTo, String text){
    private void createAsynchArrow(int indexActorFrom, int indexActorTo, String text){

        int posFrom = this.countPosFrom(indexActorFrom, indexActorTo);
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

    private Label createArrowText(int x, int y, String text){
        Label label = new Label(text);
        label.prefHeight(40);
        label.prefWidth(40);
        label.setStyle("-fx-font-size: 14;");

        // set position
        label.setLayoutX(x);
        label.setLayoutY(y);

        return label;
    }

    private void setArrow(Group arrow){
        this.arrow = arrow;
    }


    public int getOrder(){
        return this.order;
    }

    public Group getArrow(){
        return this.arrow;
    }

    private int countPosFrom(int indexActorFrom, int indexActorTo){
        return this.baseXPos + ((indexActorFrom) * this.spaceBetweenActors);
    }

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

    private int countYPos(){
        return baseYPos + ((this.getOrder()+1) * this.spaceBetweenMessages);
    }





}
