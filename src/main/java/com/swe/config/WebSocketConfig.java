package com.swe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독 경로 prefix (서버 -> 클라이언트 푸시)
        registry.enableSimpleBroker("/topic");
        // 발행 경로 prefix (클라이언트 -> 서버 요청)
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹 프론트엔드에서 최초 연결할 핸드셰이크 엔드포인트
        registry.addEndpoint("/ws-sensor")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}