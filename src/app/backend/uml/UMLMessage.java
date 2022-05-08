/*
 * File:         UMLMessage.java
 * Institution:  FIT BUT 2021/2022
 * Course:       IJA - Java Programming Language
 * Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
 *               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
 *
 * File contains backend representation of sequence diagram message.
 *
 */
package app.backend.uml;


/**
 * Class that represent sequence diagram message.
 */
public class UMLMessage {

    // store method from which is message created
    private UMLClass fromClass;
    private UMLClass toClass;

    private String fromActor;
    private String toActor;

    // synchrounous message     .....    synch
    // asynchrounous message    .....    asynch
    // message response         .....    response
    // object creation          .....    creat
    // object deletion          .....    free
    private String type;

    // method
    private UMLMethod method;

    /**
     * Constructor
     * @param type
     * @param method
     */
    public UMLMessage(UMLClass fromClass, UMLClass toClass,
                      String fromActor, String toActor,
                      String type, UMLMethod method){

        this.fromClass  = fromClass;
        this.toClass    = toClass;
        this.fromActor  = fromActor;
        this.toActor    = toActor;
        this.type       = type;
        this.method     = method;
    }

    /**
     *
     * Method return class that sends the message.
     *
     * @return From UMLClass
     */
    public UMLClass getFromClass(){
        return this.fromClass;
    }

    /**
     * Method returns class that receives the message.
     *
     * @return To UMLCLass
     */
    public UMLClass getToClass(){
        return this.toClass;
    }

    /**
     * Method returns name of the actor that receives the message.
     *
     * @return To actor name
     */
    public String getToActor(){
        return this.toActor;
    }

    /**
     * Method returns name of the actor that sends the message.
     *
     * @return Return From actor name
     */
    public String getFromActor(){
        return this.fromActor;
    }

    /**
     * Method returns type of the message.
     *
     * @return message type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Method returns UMLMethod.
     *
     * @return method
     */
    public UMLMethod getMethod() {
        return this.method;
    }

    /**
     * Method sets method that is used in the message.
     *
     * @param method used in message
     */
    public void setMethod(UMLMethod method) {
        this.method = method;
    }

    /**
     * Method sets type of the message.
     *
     * @param type of the message
     */
    public void setType(String type) {
        this.type = type;
    }
}
