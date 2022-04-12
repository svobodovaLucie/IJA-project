## Application for editing class and sequence UML diagrams

Course:       IJA - Java Programming Language  
Institution:  FIT BUT 2021/2022  
Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz 
              Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz

Application loads a class diagram from JSON file and displays it in the GUI.
It can be edited and saved back to JSON format. Default path where the diagram
will be saved is `dest/savedDiagram.json`.

### Build
`ant build`

### Generate documentation
`ant doc`

### Run the application
`ant run -Darg="diagram_absolute_filepath"`

### Get libraries
`cd lib && ./get-libs.sh`
