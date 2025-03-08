package yeri_nihongo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import yeri_nihongo.config.jwt.JwtFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    String[] publicEndpoints= {
            "/api/v1/auth/**"
    };

    String[] publicGetEndpoints = {
            "/api/v1/courses",
            "/api/v1/courses/**",
            "/api/v1/reviews",
            "/api/v1/reviews/**",
            "/api/v1/main/youtube"
    };

    String[] forInstructorEndpoints = {
            "/api/v1/admin/**",
            "/api/v1/main/youtube"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors((SecurityConfig::corsAllow))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, publicGetEndpoints).permitAll()
                        .requestMatchers(forInstructorEndpoints).hasAuthority("ROLE_INSTRUCTOR")
                        .requestMatchers(publicEndpoints).permitAll() // 여러 경로를 한 줄로 그룹화
                        .anyRequest().authenticated() // 나머지 요청 인증 필요
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private static void corsAllow(CorsConfigurer<HttpSecurity> corsCustomizer) {
        corsCustomizer.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowedOrigins(Arrays.asList(
                    "http://localhost:3000", "http://localhost:5173", "https://yeri-nihongo.netlify.app", "https://www.yeri-jp.com"
            ));
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(3600L);

            return configuration;
        });
    }
}
