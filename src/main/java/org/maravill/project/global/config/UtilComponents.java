package org.maravill.project.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UtilComponents {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
