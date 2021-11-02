### Importing the database:

I have included the .sql file to import directly into mySQL. First, create a schema called "music".
Next, simply import MusicSchema.sql into the music schema. That concludes the mySQL portion.

### Setting up and running the application:

The .class file is already built for you, so you will need to simply run the following command:

java -cp lib/mysql-connector-java-8.0.23.jar;classes SER322Final.Music "jdbc:mysql://localhost/music?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&&serverTimezone=America/New_York" root <INSERT PASSWORD HERE> com.mysql.cj.jdbc.Driver

You may need to tweak one or two things in the CLI based on your setup as well as operating
system (this was tested/developed on Windows).