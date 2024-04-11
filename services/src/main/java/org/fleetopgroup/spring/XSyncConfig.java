package org.fleetopgroup.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.antkorwin.xsync.XSync;

@Configuration
public class XSyncConfig {
	
	@Bean
    public XSync<Integer> intXSync(){
        return new XSync<>();
    }
    
    @Bean
    public XSync<String> xSync(){
        return new XSync<>();
    }
    
    @Bean
    public XSync<Long> longXSync(){
        return new XSync<>();
    }

}
