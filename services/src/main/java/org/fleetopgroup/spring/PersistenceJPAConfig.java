package org.fleetopgroup.spring;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:persistence.properties" })
@ComponentScan({ "org.fleetopgroup.persistence" })
@EnableJpaRepositories(basePackages = "org.fleetopgroup.persistence.dao")
public class PersistenceJPAConfig {

    @Autowired
    private Environment env;

    public PersistenceJPAConfig() {
        super();
    }

    //

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "org.fleetopgroup.persistence.model" });
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public DataSource dataSource() {
    	
    	 HikariDataSource dataSource	= new HikariDataSource();
    	 dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
         dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
         dataSource.setUsername(env.getProperty("jdbc.user"));
         dataSource.setPassword(env.getProperty("jdbc.pass"));
         dataSource.setMaximumPoolSize(env.getProperty("hikari.maximumPoolSize", Integer.class));
         dataSource.setIdleTimeout(env.getProperty("hikari.idleTimeout", Integer.class));
         dataSource.setMinimumIdle(env.getProperty("hikari.minimumIdle", Integer.class));
         dataSource.setConnectionTimeout(env.getProperty("hikari.connectionTimeout", Integer.class));
         dataSource.setPoolName(env.getProperty("hikari.poolName"));
         dataSource.setMaxLifetime(env.getProperty("hikari.maxLifetime", Integer.class));
         
         return dataSource;
    }

	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() 
	{
	    return new BCryptPasswordEncoder();
	}

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans", env.getProperty("hibernate.enable_lazy_load_no_trans"));
        return hibernateProperties;
	}



}