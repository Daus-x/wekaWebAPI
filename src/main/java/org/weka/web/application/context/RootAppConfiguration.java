package org.weka.web.application.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by suad on 1/3/2015.
 */
@Import(value = {WebAppConfiguration.class})
@ComponentScan
@Configuration
public class RootAppConfiguration {

}
