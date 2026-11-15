package pe.ventage.valuo.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.AfterFilterFunctions.removeResponseHeader;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.removeRequestHeader;
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

    @Value("${valuo.services.rentabilidad.url:http://localhost:8084}")
    private String rentabilidadUrl;

    @Value("${valuo.services.reportes.url:http://localhost:8085}")
    private String reportesUrl;

    @Value("${valuo.services.monitoring.url:http://localhost:8086}")
    private String monitoringUrl;

    @Bean
    public RouterFunction<ServerResponse> customGatewayRoutes() {
        return route("msusuarios")
                .route(path("/api/v1/auth/**")
                        .or(path("/api/v1/usuarios/**"))
                        .or(path("/api/v1/distritos/**")), http())
                .before(uri(usuariosUrl))
                .before(removeRequestHeader("Origin"))
                .after(removeResponseHeader("Access-Control-Allow-Origin"))
                .after(removeResponseHeader("Access-Control-Allow-Credentials"))
                .build()
                .and(route("msinmuebles")
                        .route(path("/api/v1/inmuebles/**")
                                .or(path("/uploads/**")), http())
                        .before(uri(inmueblesUrl))
                        .before(removeRequestHeader("Origin"))
                        .after(removeResponseHeader("Access-Control-Allow-Origin"))
                        .after(removeResponseHeader("Access-Control-Allow-Credentials"))
                        .build())
                .and(route("msprediccion")
                        .route(path("/api/v1/estimaciones/**")
                                .or(path("/api/v1/modelos/**")), http())
                        .before(uri(prediccionUrl))
                        .before(removeRequestHeader("Origin"))
                        .after(removeResponseHeader("Access-Control-Allow-Origin"))
                        .after(removeResponseHeader("Access-Control-Allow-Credentials"))
                        .build())
                .and(route("msrentabilidad")
                        .route(path("/api/v1/rentabilidad/**"), http())
                        .before(uri(rentabilidadUrl))
                        .before(removeRequestHeader("Origin"))
                        .after(removeResponseHeader("Access-Control-Allow-Origin"))
                        .after(removeResponseHeader("Access-Control-Allow-Credentials"))
                        .build())
                .and(route("msreportes")
                        .route(path("/api/v1/reportes/**"), http())
                        .before(uri(reportesUrl))
                        .before(removeRequestHeader("Origin"))
                        .after(removeResponseHeader("Access-Control-Allow-Origin"))
                        .after(removeResponseHeader("Access-Control-Allow-Credentials"))
                        .build())
                .and(route("valuo-monitoring-service")
                        .route(path("/api/v1/dashboard/**")
                                .or(path("/api/v1/governance/**"))
                                .or(path("/api/v1/audit/**")), http())
                        .before(uri(monitoringUrl))
                        .before(removeRequestHeader("Origin"))
                        .after(removeResponseHeader("Access-Control-Allow-Origin"))
                        .after(removeResponseHeader("Access-Control-Allow-Credentials"))
                        .build());
    }
}
