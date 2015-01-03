package org.weka.web.application.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.weka.web.application.service.SMOService;
import org.weka.web.application.service.impl.SMOServiceImpl;

/**
 * Created by suad on 1/3/2015.
 */
@Import(value = {WebAppConfiguration.class,RepositoryConfig.class})
@Configuration
public class RootAppConfiguration {
    @Bean
    public SMOService smoService(){return new SMOServiceImpl();}
}
