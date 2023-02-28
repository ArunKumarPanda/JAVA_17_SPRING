# JAVA_17_SPRING

Banuprakash C

Full Stack Architect, Co-founder Lucida Technologies Pvt Ltd., Corporate Trainer,

Email: banuprakashc@yahoo.co.in

https://www.linkedin.com/in/banu-prakash-50416019/

https://github.com/BanuPrakash/JAVA_17_SPRING

===================================

Softwares Required:
1)  openJDK 17
https://jdk.java.net/java-se-ri/17

2) Eclipse for JEE  
	https://www.eclipse.org/downloads/packages/release/2022-09/r/eclipse-ide-enterprise-java-and-web-developers

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

```
a) docker pull mysql

b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -t -i <container_name> /bin/bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

```

Java 8 - 17 new features

java 17 LTS

Java 9 --> Java Platform Module System : "Modules"

OSGi --> Open service Gateway Initiative ==> container for loading modules. Different classloader --> dynamically add / remove modules

WordApp ==> SpellCheck module

MANIFEST.MF
Export-Packages: com.adobe.service
IMPORT-Packages: jakarata.persistence

mylib.jar ==> many packages ==> transitive depenedencies {GSON.jar, ...}

adding mylib.jar to classpath exposes all public classes from different packages and transitive dependenies

Bad tooling

JPMS --> more modularized applications, less footprint, self-contained application

Without JPMS:

Dockerfile
FROM:openjdk8:alphine
COPY target/app.jar app.jar
CMD java -jar app.jar

==> "rt.jar" and "jce.jar"
java.xml
java.sql
java.util
....

With JPMS:

Dockerfile
COPY target/app.jar app.jar
CMD java -jar app.jar

app.jar contains required "java executable" and only necessary module
java.base
java.util

Module Types:
1) System Modules --> JDK modules
java --list-modules

java --describe-module java.sql
java.sql@17.0.5
exports java.sql
exports javax.sql
requires java.base mandated
requires java.transaction.xa transitive
requires java.logging transitive
requires java.xml transitive
uses java.sql.Driver


2) Named Modules / Application Module: we build --> needs module-info.java
exports, requires, uses, ...

3) Automatic Modules: we add jars [built without module-info.java ] into "module-path" not "classpath"
name of the "jar becomes automatic module
Example: jpa; spring --> becomes automatic modules spring.core.jar ==> spring.core module

4) Unnamed module --> adding JAR to classpath --> just like how we use <= java8 version
ignores module-info.java ==> everything public is visible to users of the module


Scenario 1:

sample is un-named module ==> no module.info.java
mylib.jar is added as classpath to "sample" and not module-path
now sample project can access all public members of mylib --> no restrictions ==> [ignores module-info.java of mylib]


A Single Java Project can contain multiple JPMS modules
--> IDE support is not there
see: multimodule.zip README.md for executing

--------------

Automatic modules

common.zip a module without module-info.java < -- built using without JPMS

check automaticmodules.zip

javac --module-source-path src -p mods -m main.app  -d out

jar files present in "mods" folder becomes an automatic-module ==> module-name will be the name of jar


MyModule { can apply restrictions } ==> common.jar as module path

-------

Multi Maven Project with JPMS and JPMS --> provides, uses, ...


==========================

Step 1:
New Maven Project
groupid com.adobe
artificatId: serviceexample
version: 1.0.0
package:pom

add below config to pom.xml
<build>
	  <pluginManagement>
		  <plugins>
			  <plugin>
				  <groupId>org.apache.maven.plugins</groupId>
				  <artifactId>maven-compiler-plugin</artifactId>
				  <version>3.11.0</version>
				  <configuration>
					  <source>17</source>
					  <target>17</target>
				  </configuration>
			  </plugin>
		  </plugins>
	  </pluginManagement>
  </build>

Step 2:
create a new Maven Module Project
create simple
api

ServiceLoader of java 8:
META-INF/services/com.example.LogService
com.adobe.service.LogStdOutImpl
com.adobe.service.LogFileImpl

JPMS:
java -p api/target/api-1.0.0.jar:client/target/client-1.0.0.jar:impl/target/impl-1.0.0.jar -m client/client.Main
STDOUT: Hello World!!

 jlink -p api/target/api-1.0.0.jar:client/target/client-1.0.0.jar:impl/target/impl-1.0.0.jar --add-modules client,api,impl --output myimage --launcher MYAPP=client/client.Main 

$sh MYAPP

===============

Java 10 => Local variable type interface

keyword "var"

variables declared using "var" keyword are statically typed

var text = "Hello Java 10"; 
same as
String text = "Hello Java 10";

Map<String,List<Integer>> data = new HashMap<String,List<Integer>>();

var data = new HashMap<String,List<Integer>>();

var is not allowed when the compiler is incapable of infering the correct type
Below code is not allowed:
var a; // not allowed

var nothing = null; // not allowed

var lambda = () -> System.out.println("Hello!!!"); // not allowed

Java 11 -> allowed "var" in lambda parameters

Predicate<Integer> p1 = (@NotNull Integer data) -> true;
Predicate<Integer> p2 = data -> true; // implied type -> type inference
Predicate<Integer> p3 = (@NotNull var data) -> true; // using type inference

=================



