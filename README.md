# FucturaBank - OracleDB with JPA/Hibernate

[![LICENSE](https://img.shields.io/github/license/Ivi-SCD/project-fucturabank-oraclecrud)](https://github.com/Ivi-SCD/project-fucturabank-oraclecrud/blob/main/LICENSE)

## About the project: 
This is a project that i made during learning of OracleDB and JPA/Hibernate in Fuctura, 
it consists with a similar and simple bank system.
The project include 
(Custom queries, CRUD and Local Database).


## 💻 Technologies used: 
|Tool|Version|
---|---|
|JavaSE|17|
|SpringBoot|3.0.0-M5|
|SpringToolSuite (IDE)|v4.16.0|
|Maven|3.8.6|
|OracleDB 21c|21.3.0.0.0|

### Maven Dependencies
|Dependency|Version|
---|---|
|Ojdbc8|21.7.0.0|
|Hibernate-Core|6.1.5.Final|
|Hibernate-EntityManager|5.6.14.Final|

## Execute the project:
You need to have Java, Maven, OracleDB (With SQLDeveloper) installed and configured locally.

* Open a folder that you will put the project.
* Open your git bash and init your git.
* Execute the following command to clone repository: 
```bash
# Cloning repository
git clone https://github.com/Ivi-SCD/project-fucturabank-oraclecrud/
```

* Open the archive **META-INF/persistence.xml** and change the configurations:

```xml
<property name="javax.persistence.jdbc.url" value="jdbc:oracle:thin:@localhost:1521:xe" /> //Change if you use a different URL
<property name="javax.persistence.jdbc.user" value="ivi" /> //Change your user configurated in SQLDeveloper
<property name="javax.persistence.jdbc.password" value="12345" /> //Change your password configurated to your user in SQLDeveloper
```

* Open the `fucturabank-oraclecrud` in your IDE as a Maven project and execute it as a Java Application

To visualize database, open your SQLDeveloper and init the connection with user that you configurated.
To made the CRUD and Selects you can use the program itself.
