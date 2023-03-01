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

Day 2

Recap:
Java 8 - 17 features
java 9:
Named modules ==> module-info.java
Automatic Modules ==> "jars" without module-info.java added to module-path ==> jar file name becomes the module name
Un-named modules ==> "jars" added to class-path ==> no module-info.java ==> similar to Java 8 or prev

exports, requires, provides, with, uses

Java 10 -> "var" keyword Local variable type inference


Java 9 --> Class Data Sharing CDS

JRE ==> ClassLoaders

loadClass(), verifyClass(), defineClass() --> internal data structrue ==> JVM MetaData

CDS Archive ==> built-in classes ==> file

JVM 1

JVM 2

Java 12 ==> Application Class Data Sharing

java -jar AppCDS.jar
0.898 seconds

java -XX:ArchiveClassesAtExit=appCDS.jsa -jar AppCDS.jar
0.885 seconds

java -XX:SharedArchiveFile=appCDS.jsa -jar AppCDS.jar
0.692 seconds

http://localhost:9999/api/greeting

java -XX:SharedArchiveFile=appCDS.jsa -XX:DumpLoadedClassList=hello.lst  -jar AppCDS.jar


======
java 11 --> run Java commaond on Source code, no need to compile

java 12 [preview] --> Pattern Matching

java --enable-preview Example.java

jshell --> java 9

Prior to Java 12:
```
Object obj = "Hello World!!!";
if(obj instanceof String) {
	String s = (String) obj;
	System.out.println(s.toUpperCase());
}

```		
With Java 12: Pattern Matching for instanceof
```
Object obj = "Hello World!!!";
 if(obj instanceof String s)  {
	System.out.println(s.toUpperCase());
 }
```

Java 12 --> Switch Expression
```
package examples;

public class Test {

	public static void main(String[] args) {
		System.out.println(getValueArrow("b"));
	}
	
	public static int getValue(String mode) {
		int result = -1;
		switch(mode) {
			case "a": result = 1; break;
			case "b": result = 2; break;
			default: result = 3;
		}
		
		return result;
	}
	
	// java 12 Switch expression
	public static int getValueArrow(String mode) {
		int result = switch(mode) {
			case "a", "b" -> 1;
			case "c"-> 2;
			default -> 3;
		};
		
		return result;
	}
	// java 13 switch expression ==> yield to return a value
		public static int getValueYield(String mode) {
			int result = switch(mode) {
				case "a", "b" -> {
					System.out.println("Case a and b");
					yield 1;
				}
				case "c"-> 2;
				default -> 3;
			};
			
			return result;
		}

}
```

java 14 --> record

Records were introduced to create immutable objects ==> reduce boilerplate code in data model POJOS [DTO]

public record Person(String, name, int age) {}

--> we get constructor, getters, equals and hashCode, toString

Similar to What Lombok gives you --> 
@Value
public class Person {
	private String name;
	private int age;
}

Can't be used for entities to Map to Database table
Hibernate ==> Mutate the code ==> setting PK ==> Proxy has to be added

@Entity
public record Person(...){ } // fails

----

public record Person(String name, int age) {
	public Person {
		if(age < 0) {
			throw new IllegalArgumentException("invalid age");
		}
	}
	@Override
	public String name() { // name() and not getName()
		return name.toUpperCase();
	}
}

====
JDK 19 --> value
public value record Person(String, name, int age) {}

it treats this as primitive and not reference type

=======

jdk 17 => sealed

more fine grained inheritance control

Product --> only Tv, Mobile can be extended; Don't want any other classes to extend


sealed interface AsyncReturn<V> permits Success, Failure, Timeout, Interrupted{}

record Success<V>(V result) implements AsyncReturn<V> {}
record Failure<V>(V result) implements AsyncReturn<V> {}
record Timeout<V>(V result) implements AsyncReturn<V> {}
record Interrupted<V>(V result) implements AsyncReturn<V> {}

public interface Future<V> {
	AsyncReturn<V> get();
}

main
Future<Integer> future = new Future<>() {
	@Override
	pulic AsyncReturn<Integer> get() {
		return new Success<Integer>(100);
	}
}
AsyncReturn<V> r = future.get();
switch(r) {
	case Success(var result) -> System.out.println(result);
	case Failure(var result) -> System.out.println(result);
	case Timeout(var result) -> System.out.println(result);
	case Interrupted(var result) -> System.out.println(result);
}

------

sealed abstract class Product permits TV, Mobile {
	//
}

final class Tv extends Product {

}

sealed class Mobile extends Product permits SmartPhone {

}



final: Cannot be extended further

sealed: Can only be extended by its permitted subclasses

non-sealed: Can be extended by unknown subclasses; a sealed class cannot prevent its permitted subclasses from doing this

==
pattern matching switch , record, sealed

Resume @ 4:20





