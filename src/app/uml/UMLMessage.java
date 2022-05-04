package app.uml;


// todo komentare
// todo popis souboru

/**
 * Class that represent sequence diagram method
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


    public UMLClass getFromClass(){
        return this.fromClass;
    }
    public UMLClass getToClass(){
        return this.toClass;
    }
    public String getToActor(){
        return this.toActor;
    }
    public String getFromActor(){
        return this.fromActor;
    }
    public String getType() {
        return this.type;
    }
    public UMLMethod getMethod() {
        return this.method;
    }
    public void setMethod(UMLMethod method) {
        this.method = method;
    }
    public void setType(String type) {
        this.type = type;
    }
}
