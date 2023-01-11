package com.programmingtechie.productservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProject {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
