package com.aksh.marketlog;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class MarketLogApplication {
	
	private static final Logger logger = Logger.getLogger(MarketLogApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MarketLogApplication.class, args);
	}

	@Value("${init-db:false}")
	private String initDatabase;
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}
	@Value("${db.datasource.driver:com.mysql.jdbc.Drive}")
	private String driverClass;
	@Value("${db.datasource.url:junk}")
	private String url;
	@Value("${db.datasource.username:junk}")
	private String username;
	@Value("${db.datasource.password:junk}")
	private String passwd;

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
	
	
	
	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource){
		DataSourceInitializer dataSourceInitializer=new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		ResourceDatabasePopulator databasePopulator=new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource("data.sql"));
		dataSourceInitializer.setDatabasePopulator(databasePopulator);
		dataSourceInitializer.setEnabled(Boolean.parseBoolean(initDatabase));
		return dataSourceInitializer;
	}
	
	

}
