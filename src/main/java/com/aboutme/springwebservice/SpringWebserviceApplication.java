package com.aboutme.springwebservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringWebserviceApplication {

    public static final String APPLICATION_LOCATIONS ="spring.config.import="
            + "optional:file:/home/ec2-user/app/abapplication.yml ,"
            + "optional:file:/home/ec2-user/app/config/real-application.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringWebserviceApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
