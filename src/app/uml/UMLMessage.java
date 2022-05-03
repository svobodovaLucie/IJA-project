package app.uml;

/**
 * Class that represent sequence diagram method
 */
public class UMLMessage {

    // store method from which is message created
    private UMLClass from;
    private UMLClass to;

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
     * @param from
     * @param to
     * @param type
     * @param method
     */
    public UMLMessage(UMLClass from, UMLClass to,
                      String type, UMLMethod method){
        this.from = from;
        this.to = to;
        this.type = type;
        this.method = method;
    }


    public UMLClass getFrom() {
        return this.from;
    }
    public UMLClass getTo() {
        return this.to;
    }
    public String getType() {
        return this.type;
    }
    public UMLMethod getMethod() {
        return this.method;
    }
    public void setFrom(UMLClass from) {
        this.from = from;
    }
    public void setTo(UMLClass to) {
        this.to = to;
    }
    public void setMethod(UMLMethod method) {
        this.method = method;
    }
    public void setType(String type) {
        this.type = type;
    }
}
