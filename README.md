# Xml Editor

[![Build Status](https://travis-ci.org/onurkaraduman/com.fixed.svg?branch=master)](https://travis-ci.org/onurkaraduman/com.fixed)

The project is started to provide easy operation on xml files with visualization.


 ![GUI Overview](doc/images/fixed-screenshot.png)

### Technology Stack
* JavaFX

### Libraries
* [Dom4J](https://dom4j.github.io/) is used for xml parsing

### Requirements
* Install JavaFXSceneBuilder2.0

### Building
Before build, update the com.fixed-ui/pom.xml file with your javaFX library.
````
    <dependency>
            <groupId>com.oracle</groupId>
            <artifactId>javafx</artifactId>
            <version>2.2</version>
            <systemPath>/opt/JavaFXSceneBuilder2.0/runtime/jre/lib/ext/jfxrt.jar</systemPath> <!-- This line should be changed -->
            <scope>system</scope>
    </dependency>
````