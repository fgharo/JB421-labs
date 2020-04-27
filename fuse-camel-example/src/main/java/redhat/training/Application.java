/*
 * @ComponentScan is bundled with @SpringBootApplication. When no base package is given it uses the package the annotation of the class is in. If this 
 * is not in any package it will use default package which will scan everything. This can cause problems for Spring boot as it will pick up other classes 
 * in default that will mess with how it wires things together?
 * @author fharo
 *
 */
package redhat.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
