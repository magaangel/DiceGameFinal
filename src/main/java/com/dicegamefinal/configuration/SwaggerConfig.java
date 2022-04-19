package com.dicegamefinal.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public Docket documentation(){
        return new Docket(DocumentationType.SWAGGER_2)//--> tipo de documentacion
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))//--> API selector buildder
                .paths(PathSelectors.any())//--> la clase RestController y cada uno de los paths
                // se toman en cuenta para la creacion de la documentacion
                .build();//--> retorna el objeto Docket
    }
}
