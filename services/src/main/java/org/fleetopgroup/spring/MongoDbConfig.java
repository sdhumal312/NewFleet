package org.fleetopgroup.spring;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
@ComponentScan(basePackages = "org.fleetopgroup")
@PropertySource({ "classpath:mongodb.properties" })
@EnableMongoRepositories(basePackages = "org.fleetopgroup.persistence.dao.mongo")
public class MongoDbConfig extends AbstractMongoConfiguration{

	@Autowired
    private Environment env;

	
	 @Override
	  public String getDatabaseName() {
		 
	    return env.getProperty("mongo.db");
	  }

	  @Override
	  @Bean
	  public MongoClient mongoClient() {
	    return new MongoClient(Collections.singletonList(new ServerAddress(env.getProperty("mongo.host"), env.getProperty("mongo.port", Integer.class))),
	    		Collections.singletonList(MongoCredential.createCredential(env.getProperty("mongo.user"), getDatabaseName(), env.getProperty("mongo.pass").toCharArray())));
	  }
	
}
