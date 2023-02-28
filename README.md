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


