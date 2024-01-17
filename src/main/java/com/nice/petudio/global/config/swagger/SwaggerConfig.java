package com.nice.petudio.global.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    private final static String TITLE = "Petudio API Server";
    private final static String DESCRIPTION = "Petudio API Docs";
    private final static String VERSION = "0.0.1";

    @Bean
    public OpenAPI openAPI() {
        Server serverLocal = createServer("http://localhost:8080", "for local usages");

        Info info = new Info()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION);

        return new OpenAPI()
                .servers(List.of(serverLocal))
                .info(info);
    }

    private Server createServer(String url, String description) {
        Server server = new Server();
        server.setUrl(url);
        server.setDescription(description);

        return server;
    }
}
