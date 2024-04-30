## Application for editing class and sequence UML diagrams

- Course:       IJA - Java Programming Language  
- Institution:  FIT BUT 2021/2022  
- Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz 
              Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
- Points: 80/80

Application loads a class diagram and sequence diagrams from JSON file and
displays them in GUI. The diagrams can be edited and saved back to JSON file.

### Build
Compiles the application and generates the documentation. Jar file will be 
generated inside the `dest/` directory.
`ant build`

### Generate documentation
`ant doc`

### Run the application
`ant run -Darg="diagram_absolute_filepath"`

### Get libraries
`cd lib && ./get-libs.sh`
