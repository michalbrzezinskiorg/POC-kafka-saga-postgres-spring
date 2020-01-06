package com.decentralizer.spreadr.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class BeansConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
