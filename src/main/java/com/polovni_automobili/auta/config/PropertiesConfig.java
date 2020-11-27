package com.polovni_automobili.auta.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class PropertiesConfig {

    private String jwtSecret;
    private Long jwtExpirationInMs;

}
