# mysql-java-docker
Experiments with Docker. Creating a simple web service which connects to MySQL. The intent is to dockerize and compare the performance and other metrics agains that of Docker. 

1. Create a simple web application which connects to MySQL. Use Junit to integration test. 
2. Dockerize the application. Use docker profile to run on Docker. 
3. Use VM profile to run on local VM. 
4. Deploy on Heroku. Use heroku profile to deploy on Heroku. 

# Running on local VM
1. Create mysql DB and on that create marketlog database. 
2. Create a user and give privileges on marketlog databse. 
3. Update application-vm.properties
```
db.datasource.driver=com.mysql.jdbc.Driver
db.datasource.url=jdbc:mysql://localhost:3306/marketlog
db.datasource.username=muser
db.datasource.password=aks123
spring.datasource.platform=mysql

```
4. To integration test run `mvn verify -Pvm`
5. To run , run `mvn spring-boot:run -Pvm`

# Running on Docker
1. On Linux VM with Docker engine -
  1. Make sure docker machine configuration is commented in pom.xml.
  2. Stop mysql service to avoid port conflict , run `sudo service mysql stop` 
  3. To integration test run `mvn verify -Pdocker`
  4. To run , run `mvn docker:start -Pdocker`
  5. Run `docker ps` to verify to docker containers running. 
  6. To view logs run `docker logs <container-id> -f`
  7. To stop run , `mvn docker:stop -Pdocker`
2. On Windows -
  1. Create docker machine. Run `docker-machine create --driver virtualbox <machine-name>`
  2. Uncomment docker machine configuration. Add the name of machine created above.  
  3. Connect to docker machine. Run from powershell `docker-machine env <machine-name> | Invoke-Expression`
  4. Rest of the command to integration test, run ,view logs, stop etc remain same as above. 
  
# Running on Heroku
1. Login to Heroku. 
2. Create Heroku app. From project root run `heroku create` . View app name by running `heroku apps`
3. Add mysql addon. Run `heroku addons:create cleardb:ignite`
4. Verify heroku config by running `heroku config` . CLEARDB_DATABASE_URL should be displayed.
5. To integration test run `mvn verify -Pheroku -Dheroku.appName=<heroku-app-name>`
6. To deploy run `mvn heroku:deploy -Pheroku`
7. To test run `heroku open orders/all`

## Heroku Datasource
Refer `MarketLogApplication` class. For heroku profile the datasource is created by following method. 
```java
    @Bean
	@Profile("heroku")
	public DataSource dataSourceHeroku() throws URISyntaxException{
		BasicDataSource basicDataSource =new BasicDataSource();

		URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));

	    String username1 = dbUri.getUserInfo().split(":")[0];
	    String password1 = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();

		basicDataSource.setDriverClassName(driverClass);
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username1);
		basicDataSource.setPassword(password1);
		logger.info("Datasource - "+driverClass+":"+url+":"+username1+":"+password1);
		
		return basicDataSource;
	}
``` 

For other profiles by following, notice `@Profile("!heroku")` -
```java
    @Bean
	@Profile("!heroku")
	public DataSource dataSource(){
		BasicDataSource basicDataSource =new BasicDataSource();
		
		basicDataSource.setDriverClassName(driverClass);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(passwd);
		logger.info("Datasource - "+driverClass+":"+url+":"+username+":"+passwd);
		
		return basicDataSource;
	}
```

# Docker Learning Path
## Pluralsight has some great resources to learn Docker 

  Learning Path "Container Management using Docker" https://app.pluralsight.com/paths/skills/docker
  
  "Play by Play: Docker for Java Developers with Arun Gupta and Michael Hoffman" https://app.pluralsight.com/library/courses/play-by-play-docker-java-developers-arun-gupta-michael-hoffman/table-of-contents
  
  "Containers and Images: The Big Picture" https://app.pluralsight.com/library/courses/containers-images-big-picture/table-of-contents
  
## Docker MySQL 
  "Docker MySQL" https://hub.docker.com/_/mysql/
  
## Docker Maven Plugin
  https://dmp.fabric8.io/
  
## Docker Machine
  https://docs.docker.com/machine/get-started/
  
# Issues Faced
# Lessons Learned
