package pe.ventage.valuo.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class GatewayRoutesConfig {

    @Value("${valuo.services.usuarios.url:http://localhost:8081}")
    private String usuariosUrl;

    @Value("${valuo.services.inmuebles.url:http://localhost:8082}")
    private String inmueblesUrl;

    @Value("${valuo.services.prediccion.url:http://localhost:8083}")
    private String prediccionUrl;

    @Bean
    public RouterFunction<ServerResponse> customGatewayRoutes() {
        return route("msusuarios")
                .route(path("/api/v1/auth/**")
                        .or(path("/api/v1/usuarios/**"))
                        .or(path("/api/v1/distritos/**")), http())
                .before(uri(usuariosUrl))
                .build()
                .and(route("msinmuebles")
                        .route(path("/api/v1/inmuebles/**")
                                .or(path("/uploads/**")), http())
                        .before(uri(inmueblesUrl))
                        .build())
                .and(route("msprediccion")
                        .route(path("/api/v1/estimaciones/**")
                                .or(path("/api/v1/modelos/**")), http())
                        .before(uri(prediccionUrl))
                        .build());
    }
}
