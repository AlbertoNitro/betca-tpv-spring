package es.upm.miw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        /*
         * List<Parameter> parameters = new ArrayList<Parameter>(); ParameterBuilder parameterBuilder = new ParameterBuilder();
         * parameters.add(parameterBuilder.name("Authorization").modelRef(new
         * ModelRef("string")).parameterType("header").required(false).build());
         */

        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .apiInfo(apiInfo()); // .pathMapping("").globalOperationParameters(parameters);
    }

    // http://localhost:8080/api/v0/swagger-ui.html
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Máster en Ingeniería Web. Universidad Politécnica de Madrid")
                .description("BETCA. Back-end con Tecnologías de Código Abierto (SPRING). https://github.com/miw-upm/BETCA-spring").build();
    }

}
