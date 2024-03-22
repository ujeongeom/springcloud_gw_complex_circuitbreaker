package com.kt.edu.thirdproject.common.route;

import com.kt.edu.thirdproject.common.filter.LoggingGatewayFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@Configuration
public class EduRouteLocator {

    //private final AuthorizationHeaderFilter authorizationHeaderFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,LoggingGatewayFilterFactory loggingGatewayFilterFactory) {
        return builder.routes()
                .route("sample-api", r -> r.path("/api/v1/**")
                        //.filters(f -> f.filter(authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config()))
                         //       .removeRequestHeader("Content-Type")
                         //       .addRequestHeader("Content-Type", "application/json")
                        //)
                        .filters(f -> f.filter(loggingGatewayFilterFactory.apply(new LoggingGatewayFilterFactory.Config("message", true, true)))
                        .addRequestHeader("Hello", "World"))
                        .uri("http://localhost:8081/api/v1"))
                .build();
    }

/*
    @Bean
    public RedisRateLimiter redisRateLimiter()
    {
        return new RedisRateLimiter(1, 1, 1);
    }*/

    // @Bean
    // public GlobalFilter customGlobalFilter() {
    // 	return new CustomGlobalFilter();
    // }

    /*@Bean
    public GlobalFilter requestTransformationGlobalFilter() {
        return new RequestTransformGlobalFilter();
    }

    @Bean
    public GlobalFilter responseTransformationGlobalFilter() {
        return new ResponseTransformGlobalFilter();
    }*/

/*
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, LoggingGatewayFilterFactory loggingGatewayFilterFactory) {

        log.info("test");
        return builder.routes()
                .route("path_route1", r -> r.path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://localhost:8081"))

                .route("path_route2", r -> r.path("/post")
                        .filters(f -> f.filter(loggingGatewayFilterFactory.apply(new LoggingGatewayFilterFactory.Config("message", true, true))))
                        .uri("http://localhost:8081"))

                .route("path_route_1", r -> r.path("/backend1/**")
                        .filters(f -> f.rewritePath("/backend1/(?<segment>.*)","/${segment}"))
                        .uri("http://localhost:8081"))

                .route("path_route_2", r -> r.path("/backend2/**")
                        .filters(f -> f.rewritePath("/backend2/(?<segment>.*)","/${segment}"))
                        .uri("http://localhost:8082"))

                .route("host_route", r -> r.host("*.myhost.org")
                        .uri("http://localhost:8082"))

                .route("rewrite_route", r -> r.host("*.rewrite.org")
                        .filters(f -> f.rewritePath("/foo/(?<segment>.*)","/${segment}"))
                        .uri("http://localhost:8081"))

                .route("circuitbreaker_route", r -> r.host("*.circuitbreaker.org")
                        .filters(f -> f.circuitBreaker(c -> c.setName("testcirguitbreaker")))
                        .uri("http://localhost:8081"))

                .route("circuitbreaker_fallback_route", r -> r.host("*.circuitbreakerfallback.org")
                        .filters(f -> f.circuitBreaker(c -> c.setName("testcirguitbreaker").setFallbackUri("forward:/circuitbreakerfallback")))
                        .uri("http://localhost:8081"))

                .route("limit_route", r -> r.host("*.limited.org").and().path("/auth/**")
                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
                        .uri("http://localhost:8081"))

                .route("websocket_route", r -> r.path("/echo")
                        .uri("ws://localhost:9000"))
                .build();
    }

    @Bean
    RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 2);
        // Token Bucket 알고리즘 시반
        // replenishRate: 초당처리량
        // burstCapacity : 최대 버킷크기
        // 일시적으로 최대 2 request/second를 처리할 수 있으며, 평균적으로는 1 request/second를 처리하며, 이를 초과하는 요청에 대해서는 HTTP 429 - Too Many Requets를 리턴
    }*/
   /*
    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http.httpBasic().and().csrf().disable().authorizeExchange().pathMatchers("/auth/**").authenticated()
                .anyExchange().permitAll().and().build();
    }

    @Bean
    public MapReactiveUserDetailsService reactiveUserDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }*/

}