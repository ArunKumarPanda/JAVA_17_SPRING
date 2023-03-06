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

$ docker exec -t -i local-mysql bash

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

Program Arguments ==> application.properties ==> application.yml

====
Solution 4:
@ConditionalOnProperty(name = "dao", havingValue = "db")
@Repository("db")
public class BookDaoDbImpl implements BookDao {

@ConditionalOnProperty(name = "dao", havingValue = "mongo")
@Repository("mongo")
public class BookDaoMongoImpl implements BookDao {

application.properties
dao=mongo

=======

@ConditionalOnMissingBean("db") If a type of bean is not availble then only create this bean
@Repository("mongo")
public class BookDaoMongoImpl implements BookDao {

=======================================

Bean Factory ==> @Bean on factoryMethod() == > method which returns a object
* 3rd party classes which doesn't have spring annoations like @Component, @Service, ....
* Programatically I want to create instance by passing init values [ Spring uses default constructor]

DataSource --> pool of db connections is managed by Spring Container

@Configuration
public class AppConfig {
	@Bean("datasource")
	public DataSource getDataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("swaldman");                                  
		cpds.setPassword("test-password");                                  
			
		// the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		return cpds;
	}
}

@Repository
public class BookDaoImpl implments BookDao {
	@Autowired
	DataSource ds; 
}

=========
Default scope of bean is "singleton"

@Repository("mongo")
@Scope("request")
public class BookDaoMongoImpl implements BookDao {
	
}

@Scope("session")
Per user, multiple requests
Login bean is created; use it for multiple requests from same client
logout bean is destroyed

@Scope("proptotype")



@Repository("mongo")
@Scope("prototype")
public class BookDaoMongoImpl implements BookDao {
	
}

@Service
public class AService {
	@Autowired
	BookDao bookDao;
}


@Service
public class BService {
	@Autowired
	BookDao bookDao;
}

AService gets one instance of BookDaoMongoImpl
BService gets another instance of BookDaoMongoImpl

============================

Spring & ORM

ORM --> Object Relational Mapping

Class <-----> Relational Database Table
instance variables <----> columns of table

 ORM generates SQL based on mapping and contains APIs to do CRUD operations

 Application ==> JPA specification ==> ORM ==> JDBC ==> RDBMS

 ORM --> Hibernate / OpenJPA/ KODO / TopLink /..

 * Entity (objects mapped to RDBMS table)
 * PersistenceContext
 * DataSource
 * EntityManagerFactory
 * EntityManager

@Configuration
public class AppConfig {
	@Bean("datasource")
	public DataSource getDataSource() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
		cpds.setUser("swaldman");                                  
		cpds.setPassword("test-password");                                  
			
		// the settings below are optional -- c3p0 can work with defaults
		cpds.setMinPoolSize(5);                                     
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		return cpds;
	}

	@Bean
	public EntityManagerFactory getEmf(DataSource ds) {
		LocalContainerEntityManagerFactory emf = new ...
		emf.setJPAVendor(new HibernateJPAVendor());
		emf.setPackagesToScan("com.adobe.prj.entity");
		emf.setDataSource(ds);
		...
		return emf;
	}
}

@Respository
public class BookDaoJpaImpl implements BooDao {
	@PersistanceContext
	EntityManager em;

	addBook(Book b) {
		em.persist(b);
	}

	Book getbook(int id) {
		em.find(Book.class, id);
	}
}

======
Spring Data JPA simplfied using ORM

dependencies:
Spring Boot Devtools --> re-run spring container for every changes
Lombok --> Simplifies creating classes with constructor/ getter, setter / hashCode, equals
MySQL --> RDBMS driver
Spring data JPA ==> JPA layer for ORM

https://mvnrepository.com/
search lombok
 lombok 1.18.26 => jar download

 java -jar lombok-1.18.26.jar


create database DB_SPRING;

spring.jpa.hibernate.ddl-auto=update
options can be "create-drop", "update", "validate", "none"

1) create-drop
create tables based on mappings on application start and delete tables on exit
2) update
create table if not exists; if exists uses it; if alter required do it
3) validate
table already exists in DB; map to it --> if doesn't match throw errors
4) none
I write my own scripts 

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

ORM operatios --> i need to log SQLs generated

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
ORM --> to generate SQL for MySQL8 version

public interface ProductDao extends JpaRepository<Product, Integer> {

}

Spring Data JPA ==> creates Implementation class for the interface



docker exec -it local-mysql bash
bash-4.4# mysql -u root -p
Enter password: 
mysql> use DB_SPRING;
Database changed
mysql> show tables;
Empty set (0.00 sec)

mysql> show tables;
+---------------------+
| Tables_in_DB_SPRING |
+---------------------+
| products            |
+---------------------+
1 row in set (0.00 sec)

mysql> select * from products;
+----+----------------+-------+------+


public class BookDaoJdbcImpl implements BookDao {
	public void addBook(Book b) {
		Connetion con = DriverManager.getConnection(URL);
		String SQL  = "insert into products values (?,?,?,?)"
		PreparedStatement ps = con.prepareStatement(SQL);
		ps.set

		ps.exceuteUpdate();
		con.close();
	}
}

addBook(Book b) {
		em.persist(b);
}

from Book								select * from books;
from Book where name ='JPA'				select * from books where title = 'JPA'
select name, price from Book 			select title,amount from books



==============

Day 4

Recap: SpringBoot @SpringApplication, @Bean, @Primary, @Profile..

Spring Data Jpa: simplifies using ORM
interface JpaRepository<Entity,PK> ==> Spring Data JPA creates Repository classes with all the provided methods for CRUD operations.
We can write custom methods in interface; also use @Query ==> can take SQL or JP-QL

SQL uses table names and columns, not case-sensitive while using table and column names
JP-QL uses class names and fields, case-sensitive for class names and fields. JP-QL is polymorphic

Product, Tv extends Product, Mobile extends Product ==> we can use different strategies in database to store

a single table to store objects of Prodct, Tv and mobile using discriminator column

products
id   name     		price     connectivit screen_type screen_size 		type
1    iPhone   		980000    4G			null 		null 			mobile
2    SonyBravia    1280000    null			OLED 		60 inch 		tv

OR 

products table
id name price

mobile table
id connectivity

tv table
id screen_type  screen_size

"from Product" ==> Polymorphic ==> gets data from all the 3 tables

"from Object" ==> data from all the tables of the database will be fetched


JDBC:
executeQuery() for SELECT statements returns ResultSet
executeUpdate() for INSERT, DELETE and UPDATE Sql ==> retunrs int

By default for all pre-defined methods to perform INSERT or DELETE Transcation is enabled. custom methods we need to explicitly write Tx boundaries
```
@Transactional
public Product updateProduct(int id, double price) {
		productDao.updateProduct(id, price);
		return this.getProductById(id);
}
```
Declarative Tx using @Transactional 
begins Transaction when method is called.
If no exception in method it commits
if any exception in method and not handled it rollsback
--> Abstraction

Programatic Transaction:
JDBC:
public void addBook(Book b) {
		Connetion con = null;
		try{
			con = DriverManager.getConnection(URL);
			con.setAutoCommit(false);
			String SQL  = "insert into products values (?,?,?,?)"
			PreparedStatement ps = con.prepareStatement(SQL);
			ps.set..	
			ps.exceuteUpdate();
			con.commit();
		} catch(SQLException ex) {
			con.rollback();
		} finally {
			con.close();
		}
}

Hibernate not JPA:
public void addBook(Book b) {
		SessionFactory = ..
		Session ses = null;
		Transaction tx;
		try{
			ses =factory.getSession();
			tx = ses.beginTransaction();
			ses.save(b);
			tx.commit();
		} catch(SQLException ex) {
			tx.rollback();
		} finally {
			session.close();
		}
}

---
@Transactional is declarative and Distributed transaction
uses 2 phase commit protocol to handle multiple resource

atomic operations ==> combine in method and mark it as @Transcational

Banking example:
credit
debit
insert into transaction
send SMS

orders

oid  order_date  		customer_fk  		total
1    1-03-2023 1:45		a@adobe.com			540000.00
2    28-02-2023 3:12	a@adobe.com			7000.00
3 	 1-03				b@adobe.com 		900


items
item_id   product_fk    order_fk  quantity  amount
100       1              1        1         98000.00
101 	  2              1        2         1600.00

https://sqlzoo.net/wiki/SQL_Tutorial

@OneToOne
@ManyToOne
@OneToMany
@ManyToMany

@ManyToOne introduces foriegn key in owning table ==> orders
@OneToMany introduces foriegn key in child table ==> items


@ManyToOne
@JoinColumn(name="customer_fk")
private Customer customer;
	
@OneToMany
@JoinColumn(name="order_fk")
private List<Item> items = new ArrayList<>();


===

Without Cascade:
@OneToMany()
@JoinColumn(name="order_fk")
private List<Item> items = new ArrayList<>();

Order o = ,,
o has 4 items i1, i2, i3, i4

em.save(o);
em.save(i1);
em.save(i2);
em.save(i3);
em.save(i3);

to delete:
em.delete(o);
em.delete(i1);
em.delete(i2);
em.delete(i3);
em.delete(i4);

With Cascade:
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name="order_fk")
private List<Item> items = new ArrayList<>();


Order o = ,,
o has 4 items i1, i2, i3, i4

em.save(o); ==> takes care of saving all items
em.delete(o); ==> takes care of deleting all items of the order

--> no Need for ItemDao

====

fetch = FetchType.EAGER
orderDao.findById(1);
gets order and all items items into memory

fetch = FetchType.LAZY
orderDao.findById(1); ==> select * from orders where oid = 1;
Doesn't fetch items;
itemDao.getItemsForOrder(1);

=======

Order JSON:
{
	"customer": {"email":"a@adobe.com"},
	"items": [
		{"product": {id:1}, quantity:2},
		{"product": {id:2}, quantity:1}
	]
}

Weekend Task:

employees
email   		name
a@adobe.com		Anitha

companies
id  name  
1	Acme

projects
pid name 		company_fk start_date  		end_date  
1   SomeProj	1			10-03-2019		null		
2	secondProj  2			9-2-2015		10-5-2022


class EmployeeProject{
	Project project
	Employee employee;
}

project_employees
id project_fk employee_fk 		start_date 		end_date 	role
1  1			a@adobe.com		10-03-2019		30-9-2020	junior_developer
2  2            a@adobe.com
3  1  			b@adobe.com

Project --> company is @ManyToOne
EmployeeProject ---> Project is @ManyToOne
EmployeeProject --> Employee is @ManyToOne

No direct relationship between Employee and Project

1) insert employees
2) insert companies
3) insert project --> has relatioship with compnany
4) Assign Employees to Project [ Employee and Project records should exist]

===========
Building RESTful Web services:
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
--> Spring boot by adding above dependency gives the below config:
1) Embedded TomcatServer --> by default runs on port 8080
to change
application.properties
server.port=9999

2) HttpMessageHandler --> jackson library

Java <--> JSON
a) jackson
b) jettison
c) GSON
d) Moxy

3) DispatcherServlet --> FrontController --> intercept all HTTP requests from client
and delegate to different @Controller or @RestController by using HandlerMapping


@RestController
@RequestMapping("api/products")
public class ProductController {
	@GetMapping()
	m1(){}

	@PostMapping()
	m2() {}
}

@RestController
@RequestMapping("api/customer")
public class CustomerController {
	@GetMapping()
	x(){}

	@PostMapping()
	y() {}
}

HttpMessageHandler:
HttpHeaders:
Accept: application/json
Accept: text/xml

to write response to client

Content-type:applcation/json
content-type:text/xml

JSON data coming as payload from client is converted to Java using HttpMessageHandler --> Jackson

ContentNegotiationHandler

RESTful Web services:
REST --> Representational State Transfer --> distibuted hypermedia systems --> 2000 Roy Fielding

HTTP 1.1 ==> GET POST PUT DELETE PATCH

Resource ==> any information on server

URI to identify the resource
http://localhost:8080/api/products
http://localhost:8080/api/orders

* Collection 
A collection resource is a server-managed resource

* store
store is client-managed resource
Spotify
http://localhost:8080/users/banu@gmail.com/playlists

* Controller
like executable functions
http://localhost:8080/users/banu@gmail.com/playlists/play

use HTTP methods to perform CRUD operations:
GET for READ
POST for CREATE a sub-resource
DELETE for delete a sub-resource
PUT/PATCH for update a sub-resource


@Controller ==> Server Side Rendering; return ModelView

@RestController ==> RESTful services ==> server representation of resource [json/xml] to client ==> CSR; return JSON/XML/CSV/RSS/Atom

Multi-part Request handling

POSTMAN

POST http://localhost:8080/api/products
Headers:
Accept: application/json
Content-type: application/json

Body Raw --> JSON
{
    "name": "Wacom",
    "price": 8900.00,
    "quantity": 100
}

---

PUT http://localhost:8080/api/products/4

Headers:
Accept: application/json
Content-type: application/json 	


Body Raw --> JSON
{
    "price": 38900.00
}

Task:
CustomerController
OrderController ==> PostMapping and GetMapping

for POST:
{
		"customer": {"email":"a@adobe.com"},
		"items": [
			{"product": {id:1}, quantity:2},
			{"product": {id:2}, quantity:1}
			]
		}
}

---

AOP, ExceptionHandling, Validate input fields, Testing Controller

http://localhost:8080/products

@Controller
public class ProductController {
	@RequestMapping(value="/products", method=HttpMethod.GET)
	public ModelAndView listProduct() {
		ModelAndView mav = new ModelAndView();
		mav.addModel("products", service.getProducts());
		mav.setViewName("print.jsp")
	}
}

this redirects to print.jsp

<c:forEach items={products} val="product">
	Name : {product.name} <br />
	Price : {produce.price}
</c:forEach>

==> HTML

===================

Day 5:

Recap: --> Spring Data JPA and RestController
@OneToMany
@ManyToOne
@JoinColumn
Cascade and Lazy Fetching

@RestController, @RequestMapping, @ResponseBody, @RequestBody, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @PathVariable, @RequestParam

=============

Bi-directional association:

class Customer {

	@OneToMany(mappedBy="customer")
	private List<Order> orders = new ArrayList<>();
}

class Order {

	@ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@Joincolumn(name="customer_fk")
	private Customer customer;
}

Customer c = customerDao.findById("a@adobe.com");

List<Order> orders = c.getOrders();

Other side:

Order o = orderDao.findById(1);
Customer c = o.getCustomer();


==============================================

Movie
	id
	name

Actor
	id
	name

MovieActor
actor_id 	movie_id	role
1			1			Antognist
1			2			Comedian

MovieActor --> ManyToOne Actor
MovieActor --> ManyTone Movie


----

One to One

Employee
	email
	name
	@OneToOne
	@JoinColumn("laptop_fk)
	Laptop laptop;

Laptop
	serialNo
	RAM
	SDD
	Screen

employees
email   		name  		laptop_fk
a@adobe.com		Anitha		KLR421
x@adobe.com  	Xi			YLK123

laptop
serail_no   ram 	sdd screen
YLK123
KLR421


====
EAGER:
  select
        o1_0.oid,
        o1_0.customer_fk,
        o1_0.order_date,
        o1_0.total 
    from
        orders o1_0
Hibernate: 
    select
        c1_0.email,
        c1_0.first_name 
    from
        customers c1_0 
    where
        c1_0.email=?
Hibernate: 
    select
        i1_0.order_fk,
        i1_0.item_id,
        i1_0.amount,
        p1_0.id,
        p1_0.name,
        p1_0.price,
        p1_0.qty,
        i1_0.quantity 
    from
        items i1_0 
    left join
        products p1_0 
            on p1_0.id=i1_0.product_fk 
    where
        i1_0.order_fk=?

	
LAZY:
get orders and not items:
Hibernate: 
    select
        o1_0.oid,
        o1_0.customer_fk,
        o1_0.order_date,
        o1_0.total 
    from
        orders o1_0
Hibernate: 
    select
        c1_0.email,
        c1_0.first_name 
    from
        customers c1_0 
    where
        c1_0.email=?

ManyToOne is EAGER fetch by default
OneToMany is LAZY fetch by default

====

ManyToMany is a very rare association

Project <--> Employee

one project can have multiple employees
employee can work in multipl projects

project											employee
pid name start_date end_date					email 	name hire_date  end_date

project_employee [ link table]
pid  email
1		a@adobe.com
2		b@adobe.com
3		a@adobe.com

Where can i add start_date and end_date of project
where can i add start_date and end_date of employee

? Need to add when employee was associated with project and role he played
project_employee --> EmployeeProject association entity class
pid  email				start_date   end_date   role
1		a@adobe.com		A				B		jr.developer
2		b@adobe.com
3		a@adobe.com		X				Y		TL

Project *<---->1 EmployeeProject 1<---->* Employee

ManyToMany

mysql> select * from users;
+----+--------+
| id | name   |
+----+--------+
|  7 | Rahul  |
|  8 | Swetha |
+----+--------+
2 rows in set (0.00 sec)

mysql> select * from roles;
+-------+------------------------------------------+
| role  | role_description                         |
+-------+------------------------------------------+
| ADMIN | Administrator can access all resources   |
| GUEST | Guest users can access only landing page |
+-------+------------------------------------------+
2 rows in set (0.00 sec)

mysql> select * from user_role;
+---------+-----------+
| user_id | role_name |
+---------+-----------+
|       7 | ADMIN     |
|       7 | GUEST     |
|       8 | GUEST     |
+---------+-----------+

================

Exception Handling and Validation

AOP Aspect Oriented Programming
--> Why?
eliminates Cross-cutting concerns which lead to code-tangling and code-scattering

Aspect --> cross-cutting concern
Example: Logging, Profile, Transaction, Exception Handling,...

public class MyService {
	doTask() {
		metrics.record();
		log.debug("started task!!");
		try {
		tx.begin();
			// actual code
		tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		}
		log.debug("completed!!")
		metrics.endRecord();
	}
}


public class AnotherService {
	doAnotherTask() {
		metrics.record();
		log.debug("started task!!");
		try {
		tx.begin();
			// actual code
		tx.commit();
		} catch(Exception ex) {
			tx.rollback();
		}

		metrics.endRecord();
	}
}

Aspect --> concern generally leading to code-tangling and code-scattering
JointPoint --> a place in code where aspect can be weaved {method and exception}
PointCut --> selected JoinPoint
Advice --> How Aspect is weaved to pointcut ==> Before, After, Around, AfterThrowing, AfterReturing
https://docs.spring.io/spring-framework/docs/2.0.x/reference/aop.html
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)
          throws-pattern?)


=======
Bean Validation
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
</dependency>

Specification are provided by javax.validation.constraints

Implementation --> Since Hibernate ORM is included, we get Hibernate Implmentation of javax.validation.constraints

```
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message="Name is required")
	private String name;
	
	@Min(value = 10, message="Price ${validatedValue} should be more than {value}")
	private double price;
	
	
	@Min(value = 0, message="Quantity ${validatedValue} should be more than {value}")
	@Column(name="qty")
	private int quantity;
}

		
@Validated
public class ProductController {
	public ResponseEntity<Product> addProduct(@RequestBody @Valid Product p) {

```
POST http://localhost:8080/api/products
{
    "name": "",
    "price": -100,
    "quantity": 0
}
MethodArgumentNotValidException:  
[Field error in object 'product' on field 'price': rejected value [-100.0]; codes [Min.product.price,Min.price,Min.double,Min];  [product.price,price]; arguments []; 
default message [price],10]; 
default message [Price -100.0 should be more than 10]] 

[Field error in object 'product' on field 'name': rejected value []; codes [NotBlank.product.name,NotBlank.name,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [product.name,name]; arguments []; 
default message [name]]; 
default message [Name is required]] ]

{
    "errors": [
        "Name is required",
        "Quantity -990 should be more than 0"
    ],
    "timestamp": "2023-03-06T11:09:09.024+00:00"
}

==============
no need to explictly --> included by default

<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
</dependency>

Unit Testing RestControllers

* JUnit or TestNG ==> Spring Boot by default configures JUnit-Jupiter version
* Mockito ==> By default spring boot confures Mockito for Mocking dependecy {EasyMock, JMock,..}

Testing in isolation
Dependency has to be mocked
RestController ===> Service ==> Dao ==> database

to test Dao we need to mock database
to test service we need to mock DAO layer
to test controller we need to mock service

* json-path library to handle json data
https://jsonpath.com/

* Hamcrest ==> assertion library
https://hamcrest.org/JavaHamcrest/tutorial

Write tests in
src/test/java

1) 
@WebMvcTest(ProductController.class)

 Annotation that can be used for a Spring MVC test that focuses <strong>only</strong> on Spring MVC components.
* Spring Container contains only MVC components
* Creates DispatcherTestServlet as FronController
* creates MockMvc for HTTP requests [ GET, POST, PUT, DELETE]
* Loads only ProductController.class not other RestController and Controller

this won't create beans of service / repos/ other components..

POST http://localhost:8080/api/orders

```
{
		"customer": {"email":"b@adobe.com"},
		"items": [
			{"product": {"id":1}, "quantity":1},
			{"product": {"id":2}, "quantity":5}
			]
		}
	}

```

API Documentation:
* RAML --> YAML format --> Programmatic config
/books:
  /{bookTitle}
    get:
      queryParameters:
        author:
          displayName: Author
          type: string
          description: An author's full name
          example: Mary Roach
          required: false
        publicationYear:
          displayName: Pub Year
          type: number
          description: The year released for the first time in the US
          example: 1984
          required: false
        rating:
          displayName: Rating
          type: number
          description: Average rating (1-5) submitted by users
          example: 3.14
          required: false
        isbn:
          displayName: ISBN
          type: string
          minLength: 10
          example: 0321736079
    put:
      queryParameters:
        access_token:
          displayName: Access Token
          type: string
          description: Token giving you permission to make call
          required: true

* openAPI --> Auto configuration
<dependency>
	<groupId>org.springdoc</groupId>
	<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
	<version>2.0.2</version>
</dependency>
	
application.properties
springdoc.paths-to-match=/api/**, /admin/**
#springdoc.packages-to-scan=com.adobe.prj.api

http://localhost:8080/v3/api-docs

REACT or Angular or Vue ==> create a UI for the DOCumentation

http://localhost:8080/swagger-ui/index.html


==========

RestTemplate, Webclient, Cache, @Schedule, json-patch, @HttpExchange, ...











