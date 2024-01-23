package com.nice.petudio.common.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private final static String TITLE = "Petudio API Server";
    private final static String DESCRIPTION = "Petudio API Docs";
    private final static String VERSION = "0.0.1";

    @Value("${deploy.dev.url}")
    String deployServerUrl;

    @Bean
    public OpenAPI openAPI() {
        Server serverLocal = createServer("http://localhost:8080", "for local usages");
        Server serverDev = createServer(deployServerUrl, "for dev usages");

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Token");

        Info info = new Info()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION);

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer Token", securityScheme))
                .security(Arrays.asList(securityRequirement))
                .servers(List.of(serverLocal, serverDev))
                .info(info);
    }

    private Server createServer(String url, String description) {
        Server server = new Server();
        server.setUrl(url);
        server.setDescription(description);

        return server;
    }
}
