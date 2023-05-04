package com.coolbeans.babbysfirstrancher.globals;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BabbysFirstRancherConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DBConfiguration environmentConfig(){
        return DBConfiguration.getInstance();
    }
}
