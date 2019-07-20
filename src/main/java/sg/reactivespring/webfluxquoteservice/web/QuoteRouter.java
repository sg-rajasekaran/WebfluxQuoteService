package sg.reactivespring.webfluxquoteservice.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.awt.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * created by NR on 1 Jul 2019
 */
@Configuration
public class QuoteRouter {
    @Bean
    public RouterFunction<ServerResponse> route(QuoteHandler handler) {
        return RouterFunctions
                .route(GET("/quotes")
                        .and(accept(MediaType.APPLICATION_JSON)),handler::fetchQuotes)
                        .andRoute(GET("/quotes").and(accept(MediaType.APPLICATION_STREAM_JSON)),
                                handler::streamQuotes);
                }
}
