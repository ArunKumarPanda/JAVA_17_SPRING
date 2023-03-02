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

=========================

JDK 17 --> new string literal

String data = """
	{
		"name": "Linda",
		"age": 24
	}
""";

data = "{\"name\":\"Linda\" , \n \"age\" : 24 }";

=============

Additions to Stream api
Java 9
1) takeWhile(Predicate)
Stream.of(1,2,3,4,5)
	.takeWhile(n -> n < 3)
	.collect(Collectors.toList()); // [1,2]

2) dropWhile(Predicate)
Stream.of(1,2,3,4,5)
	.dropWhile(n -> n < 3)
	.collect(Collectors.toList()); // [3,4,5]

Java 12:
3) Teeing Collector
It is a composite of two downstream collectors.
Every element is processed by both downstream collectors
The result is passed to merge function

double mean = Stream.of(1,2,3,4,5)
	.collect(Collectors.teeing(
		Collectors.summing(i -> i),
		Collectors.counting(),
		(sum, count) -> sum / count));
	
// 3.0

String functions ==> strip(), stripLeading(), stripTrailing(), repeat(), lines()


-----------

Garbage Collection:

Java 8 --> CMS Concurrent Mark Sweep GC

Java 8 - 17
GC Types:
1) Epsilon GC --> NO GC --> allocates objects ; doesn't delete objects
--> BenchMarking
--> Short running applications ==> Financial applications ==> 9 AM start --> 5 PM Shut Down

% java -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC MemoryPolluter.java
Terminating due to java.lang.OutOfMemoryError: Java heap space

2) G1GC
java -XX:+UseG1GC
4TB

3) ZGC
java -XX:+ZGC
Low latency scalable garbage collector
--> Pause time shall not exceed 10 ms [ Mark Start , Mark End, Relocate Start, RelocateEnd]
supports 16TB

4) Shenandoah [ shah nuhn doh uh] --> Preview in JDK 17

=========================

FlighRecorder ==> Free and available in java 9 version onwards
==> Profile of Memory, heap dumps, threads


=========================
Java 1.2 --> bean --> any reusable object is a  bean 

SOLID Design Principles:
D --> Dependecy Injection

Spring Framework
Light weight application framework for building enterprise applications.
It provides container -> Manage life cycle of beans, wires dependencies

Bean --> any object managed by Spring is called as bean

public interface EmployeeDao {
	void addEmployee(Employee e);
}

public class EmployeeDaoMongoImpl implements EmployeDao {
	public void addEmployee(Employee e) {
		db.collections.insert(json of e);
	}
}

public class EmployeeDaoRdbmsImpl implements EmployeDao {
	public void addEmployee(Employee e) {
		insert into employess ...
	}
}

public class SampleService {
	private EmployeeDao employeeDao;
	public static void setEmployeeDao(EmployeeDao dao) {
		this.employeeDao = dao;
	}

	public void doTask() {
		Employee e =...
		employeeDao.addEmployee(e);
	}
}



beans.xml
<beans>
	<bean id="mongo" class="pkg.EmployeeDaoMongoImpl"/>
	<bean id="db" class="pkg.EmployeeDaoRdbmsImpl"/>
	<bean id="service" class="pkg.SampleService">
		<property name="emloyeeDao" ref="db">
	</bean>
</beans>

<bean id="service" class="pkg.SampleService" auto-wiring="type | name"> 

Starting a Spring Container:

ApplicationContext ctx = new ClassPathXMLApplicationContext("beans.xml");

ApplicationContext is now a reference to Spring container

SampleService s  = ctx.getBean("service");

==========
Using Annotation as metadata -> no xml files 

public interface EmployeeDao {
	void addEmployee(Employee e);
}

@Repository
public class EmployeeDaoMongoImpl implements EmployeDao {
	public void addEmployee(Employee e) {
		db.collections.insert(json of e);
	}
}

@Service
public class SampleService {
	@Autowired
	private EmployeeDao employeeDao; ̑
	 public void doTask() {
		Employee e =...
		employeeDao.addEmployee(e);
	}
}

Spring Container creates beans which has one of these annoations:
1) @Component
2) @Repository
spring-framework/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml 
3) @Service
4) @Controller
5) @RestController
6) @Configuration
7) @ControllerAdvice
8) method marked with @Bean is a factory method --> any object returned form such a method is managed by
spring container


try {

} catch(SQLException ex) {
	if(ex.getErrorCode() == 1052) {
		throw new DuplicateKeyException(...);
	}
}

Spring uses ByteCode Insrumentation libraries:
CGlib, JavaAssist, ByteBuddy

ApplicationContext ctx = new AnnotationConfigApplicationContext();
ctx.scan("com.adobe.prj"); // package and sub-packages

----

Spring Boot 3
Spring Boot is a project that is built on the top of the Spring Framework 6. 
It provides an easier and faster way to set up, configure, and run both simple and entriprse application

Spring Boot is highly opiniated framework, Out of the box it configures few things:
1) if we choose JDBC --> configures DB connection pool using HikariCP
2) if we write web based application --> Configure Embedded tomcat server
3) if we write ORM code --> configures Hibernate as ORM provider

Spring Boot 3/ Spring Framework 6 --> uses JDK 17+


Eclipse ==> Eclipse Marketplace ==> Search [STS] ==> install Spring Tool suite 4.x

=========

Day 3

Day 1 & Day 2: java 8 -- 17 features

Spring Container --> Core module --> Container --> Manage life cycle of object and wiring

* Understand Spring Container in Spring Boot application
* ORM using Persisentence API with Hibernate as ORM provider
* Building RESTful Webservices

https://start.spring.io/

New Spring Starter Project

@SpringBootApplication
1) @ComponentScan
	scan for above mentioned anntations like @Component,... from "com.example.demo" and subpackages
	and create instance when spring containers

 com.adobe.service < -- not discoverable

2) @EnableAutoConfiguration
 --> create beans based on the context
 if we are using RDBMS --> create a pool of DB connection ==> HikariCP
 --> ORM ==> Hibernate
 -> for Web create embedded Tomcat container ==> TomcatWebContainer
 ..

 3) @Configuration

--
SpringApplication.run(SpringdemoApplication.class, args); --> creates Spring container

https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html

---
Problem:
Field bookDao in com.example.demo.service.BookService required a single bean, but 2 were found:
	- bookDaoDbImpl: 
	- bookDaoMongoImpl:

Solution 1:
one of the implementation should hava @Primary
@Primary
@Repository
public class BookDaoDbImpl implements BookDao {

Solution 2:
@Qualifier

AService needs BookDaoDbImpl
BService needs BookDaoMongoImpl

@Repository("mongo")
public class BookDaoMongoImpl implements BookDao {

@Repository("db")
public class BookDaoDbImpl implements BookDao {


@Service()
public class BookService {
	@Qualifier("mongo")
	@Autowired
	private BookDao bookDao;


Solution 3: @Profile

@Profile("prod")
@Repository("mongo")
public class BookDaoMongoImpl implements BookDao {

@Profile("dev")
@Repository("db")
public class BookDaoDbImpl implements BookDao {

Program arguments
Run As -> Run Configuration -> Arguments
--spring.profiles.active=prod

OR
src/main/resources
application.properties / application.yml
spring.profiles.active=prod

====
