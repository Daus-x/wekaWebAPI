package org.weka.web.application.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.weka.web.application.service.WekaClassifierService;
import org.weka.web.application.service.impl.RandomForestImpl;
import org.weka.web.application.service.impl.SMOServiceImpl;

/**
 * Created by suad on 1/3/2015.
 */
@Import(value = {WebAppConfiguration.class,RepositoryConfig.class})
@ComponentScan
@Configuration
public class RootAppConfiguration {
    @Bean
    public WekaClassifierService smoService(){return new RandomForestImpl();}
}
