package edu.sjsu.assess;

import java.util.Arrays;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import edu.sjsu.assess.dao.CategoryDAOImpl;
import edu.sjsu.assess.service.CategoryServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import edu.sjsu.assess.controller.JobCodeControllerImpl;
import edu.sjsu.assess.dao.JobCodeDAOImpl;
import edu.sjsu.assess.dao.TrainingModuleDAOImpl;
import edu.sjsu.assess.service.JobCodeServiceImpl;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {


    //Local Database Credentials

	private static final String dbHost = "localhost";
	private static final String dbUserName = "postgres";
	private static final String dbPassword = "12345";
	private static final String dbName = "careerpath";
	private static final int dbPort = 5432;

    //AWS Database Credentials

//    private static final String dbHost = "postcareerpath.cle982qy65qb.us-west-1.rds.amazonaws.com";
//    private static final String dbUserName = "vineet";
//    private static final String dbPassword = "times123";
//    private static final String dbName = "careerpath";
//    private static final int dbPort = 5432;



    @Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("256KB");
		factory.setMaxRequestSize("256KB");
		return factory.createMultipartConfig();
	}

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {

			// System.out.println(beanName);
			if (beanName.equals("trainingModuleDAOImpl")) {
				System.out.println(beanName);
				TrainingModuleDAOImpl a = ctx.getBean(beanName,
						TrainingModuleDAOImpl.class);
				if (a != null) {
					if (a.dataSource != null) {
						System.out
								.println("TrainingModuleDAOImpl dataSource is not null");
					} else {
						System.out
								.println("TrainingModuleDAOImpl dataSource is  null");
					}
				} else {
					System.out.println(beanName + " is null");
				}
			} else if (beanName.equals("jobCodeControllerImpl")) {
				System.out.println(beanName);
				JobCodeControllerImpl a = ctx.getBean(beanName,
						JobCodeControllerImpl.class);
				if (a != null) {
					System.out.println("jobCodeControllerImpl is not null");
				} else {
					System.out.println("jobCodeControllerImpl is null");
				}
			} else if (beanName.equals("jobCodeServiceImpl")) {
				System.out.println(beanName);
				JobCodeServiceImpl a = ctx.getBean(beanName,
						JobCodeServiceImpl.class);
				if (a != null) {
					System.out.println("JobCodeServiceImpl is not null");
				} else {
					System.out.println("JobCodeServiceImpl is null");
				}
			} else if (beanName.equals("jobCodeDAOImpl")) {
				System.out.println(beanName);
				JobCodeDAOImpl a = ctx.getBean(beanName, JobCodeDAOImpl.class);
				if (a != null) {
					if (a.dataSource != null) {
						System.out
								.println("JobCodeDAOImpl dataSource is not null");
					} else {
						System.out
								.println("JobCodeDAOImpl dataSource is  null");
					}
				} else {
					System.out.println(beanName + " is null");
				}
			} else if (beanName.equals("categoryServiceImpl")) {
				System.out.println(beanName);
				CategoryServiceImpl a = ctx.getBean(beanName,
						CategoryServiceImpl.class);
				if (a != null) {
					System.out.println("categoryServiceImpl is not null");
				} else {
					System.out.println("categoryServiceImpl is null");
				}
			} else if (beanName.equals("categoryDAOImpl")) {
				System.out.println(beanName);
				CategoryDAOImpl a = ctx
						.getBean(beanName, CategoryDAOImpl.class);
				if (a != null) {
					if (a.dataSource != null) {
						System.out
								.println("CategoryDAOImpl dataSource is not null");
					} else {
						System.out
								.println("categoryDAOImpl dataSource is  null");
					}
				} else {
					System.out.println(beanName + " is null");
				}
			}

		}
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://" + dbHost + ":" + dbPort + "/"
				+ dbName + "?autoReconnect=true");
		dataSource.setUsername(dbUserName);
		dataSource.setPassword(dbPassword);

		return dataSource;
	}
}
