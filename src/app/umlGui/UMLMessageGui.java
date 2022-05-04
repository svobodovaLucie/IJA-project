package app.umlGui;

import app.uml.UMLMessage;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class UMLMessageGui {


    // holds arrow
    private Group arrow;

    // arrow order
    private int order;

    private boolean consitent;

    // First actor X pos
    public int baseXPos = 260;
    public int spaceBetweenActors = 150;
    public int baseYPos = 100;
    public int spaceBetweenMessages = 50;


    public UMLMessageGui(UMLMessage message, int order, UMLSeqDiaGui seq){
        this.order = order;


        int indexActFrom = seq.getActorGuiIndex(message.getFromActor(),
                                                message.getFromClass());
        int indexActTo   = seq.getActorGuiIndex(message.getToActor(),
                                                message.getFromClass());

        if (message.getMethod() == null)
            this.consitent = true;
        else
            this.consitent = false;

        // synch message
        // TODO TADY POKRACOVAT V PRACI
        if (message.getType().equals("synch")){
            System.out.println("kkkkkkkkkkkk");
            createSynchArrow(indexActFrom, indexActTo);
        }
        else if (message.getType().equals("asynch")){
            System.out.println("asynch");
        }
        else if (message.getType().equals("response")){
            System.out.println("response");
        }
        else if (message.getType().equals("creat")){
            System.out.println("creat");
        }
        else if (message.getType().equals("free")){
            System.out.println("free");
        }
        else{
            this.arrow = null;
        }


    }


    private int countPosFrom(int indexActorFrom, int indexActorTo){
        return this.baseXPos + ((indexActorFrom) * this.spaceBetweenActors);
    }

    private int countPosTo(int posFrom, int indexActorFrom, int indexActorTo){
        int res;
        System.out.println("indexes " + "from " + indexActorFrom + " to " + indexActorTo);
        if (indexActorFrom < indexActorTo){
            res = posFrom + this.spaceBetweenActors;
        }
        else {
            res = posFrom - this.spaceBetweenActors;
        }
        return res;
    }

    private int countYPos(){
        return baseYPos + ((this.getOrder()+1) * this.spaceBetweenMessages);
    }

    private void createSynchArrow(int indexActorFrom, int indexActorTo){
        // TODO TADY POKRACUJ
        if(indexActorTo == -1)
            indexActorTo = 1;
        if(indexActorFrom == -1)
            indexActorFrom = 1;
        int posFrom = this.countPosFrom(indexActorFrom, indexActorTo);
        int posTo   = this.countPosTo(posFrom, indexActorFrom, indexActorTo);
        int yPos    = this.countYPos();

        Group group = new Group();
        Line line  = new Line();
        line.setStartX(posFrom);
        line.setStartY(yPos);
        line.setEndX(posTo);
        line.setEndY(yPos);

        Circle circle = new Circle();
        circle.setCenterX(posFrom);
        circle.setCenterY(yPos);
        circle.setRadius(5);

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
        setArrow(group);

    }

    private void createAsynchArrow(){

        /*
        Group group = new Group();
        Line line  = new Line();
        line.setStartX(200);
        line.setStartY(200);
        line.setEndX(400);
        line.setEndY(200);

        Circle circle = new Circle();
        circle.setCenterX(200);
        circle.setCenterY(200);
        circle.setRadius(5);

        Line line2 = new Line();
        line2.setStartY(190);
        line2.setEndY(200);
        line2.setStartX(380);
        line2.setEndX(400);

        Line line3 = new Line();
        line3.setStartY(210);
        line3.setEndY(200);
        line3.setStartX(380);
        line3.setEndX(400);

        group.getChildren().add(line);
        group.getChildren().add(line2);
        group.getChildren().add(line3);
        group.getChildren().add(circle);
        this.getChildren().add(group);
        */

    }

    private void createDottedArrow(){
        //todo
        /*
        Group group = new Group();
        for (int i = 200; i < 400;){
            Line line = new Line();
            line.setStartX(i);
            line.setEndX(i+5);
            line.setStartY(200);
            line.setEndY(200);
            i+=10;
            group.getChildren().add(line);
        }

        Circle circle = new Circle();
        circle.setCenterX(200);
        circle.setCenterY(200);
        circle.setRadius(5);

        Line line2 = new Line();
        line2.setStartY(190);
        line2.setEndY(200);
        line2.setStartX(380);
        line2.setEndX(400);

        Line line3 = new Line();
        line3.setStartY(210);
        line3.setEndY(200);
        line3.setStartX(380);
        line3.setEndX(400);

        group.getChildren().add(line2);
        group.getChildren().add(line3);
        group.getChildren().add(circle);
        this.getChildren().add(group);
         */
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




}
