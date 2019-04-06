package com.wander.utils.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.appranix.cost.aggregator.web.resource.reports",
        "com.appranix.cost.aggregator.web.resource"
})
public class WebAppContext extends WebMvcConfigurerAdapter {
	
}
