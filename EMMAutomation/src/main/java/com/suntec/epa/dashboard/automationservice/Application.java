/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.suntec.epa.dashboard.automationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.integration.config.EnableIntegration;

/**
 * The Application class is the entry point into the spring boot application.
 * 
 * The application's packages will be scanned for Configuration, Component, and Service annotated
 * classes to instantiate as beans.  In those classes all methods with the Bean annotation
 * will be invoked and the return value will become a bean, named after the method.
 * 
 * This being a CXF application, any beans with JAX-RS annotations will be mapped as REST services.
 * 
 * Don't forget to update your application.yml with static application configuration.
 * Any configuration that may need tuning should be passed in using environment variables
 * through the application manifest, or tuned via "cf set-env".
 * 
 * Spring Cloud Connectors checks the environment to determine where it's running and how it should
 * retrieve connector configuration.  It can detect if it's running in an actual cloud environment.
 * However, if you are doing local testing, spring-cloud-bootstrap.properties must be present
 * in the classpath (src/test/resources, for example), and it must contain a property 
 * "spring.cloud.propertiesFile" that points to another properties file which will contain your
 * connector configuration.
 * 
 * @see https://cxf.apache.org/docs/springboot.html
 * @see http://cloud.spring.io/spring-cloud-connectors/
 */
@SpringBootApplication
@EnableIntegration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
