package com.example.demo.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SpringFoxConfig {                                    
   

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/shoes/**"))
                .build()
                .apiInfo(metaInfo());
    }


	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo(
                "Shoe Shop API",
                "Shoe Shop API",
                "1.0",
                "Terms of Service",
                new Contact("SA", "https://github.com/lhabdou",
                        "soilihi.abdoulhalim@outlook.fr"),
                "Apache License Version 2.0",
                " http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList()
        );

        return apiInfo;
    }
	
}