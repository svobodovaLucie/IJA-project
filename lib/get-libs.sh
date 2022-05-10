# File:         get-libs.sh
# Institution:  FIT BUT 2021/2022
# Course:       IJA - Java Programming Language
# Authors:      Lucie Svobodová, xsvobo1x@stud.fit.vutbr.cz
#               Jakub Kuzník, xkuzni04@stud.fit.vutbr.cz
#
# Script downloads necessary libraries.
 
# Download JavaFX
wget https://download2.gluonhq.com/openjfx/18/openjfx-18_linux-x64_bin-sdk.zip
unzip openjfx-18_linux-x64_bin-sdk.zip

# Download JSON simple
wget http://www.java2s.com/Code/JarDownload/json-simple/json-simple-1.1.1.jar.zip
unzip json-simple-1.1.1.jar.zip
mv javafx-sdk-18/lib/* .
rm -r javafx-sdk-18/

