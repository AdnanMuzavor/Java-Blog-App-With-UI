# Java-Blog-App-With-UI
Java based GUI + CURD application with MySQL as database and AWT  used for creating GUI along with Swings for supporting table..


## Key Features

It is a blogging/content based website where user can:
- signup, login and contact
- see/read other's blogs 
- add comment on others blogs after being authenticated.

On login user gets access to following features:
- Writing and posting the blog
- Editing/Deleting only his own blog
- Adding and deleting comments he added.
- If admin, he/she can get all application information including number of users,blog by each user etc

# The file roles

Connector.java
- It connects with MYSQL database using MYSQL JDBC driver

HomeFrame.java
- It deals with all the UI componenets including creating frames and handling the events.

DBMSProject.java
- It connects above two files. It gets the Connection object from Connector.java and gives it to HomeFrame.java so that various queries can be executed.
