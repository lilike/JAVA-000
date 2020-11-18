package com.lilike;

import com.lilike.domain.Lilike;
import com.lilike.properteis.HelloworldProperties;
import com.lilike.service.HelloworldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author llk
 * @Date 2020/11/18 19:32
 * @Version 1.0
 */
@Import(Lilike.class)
@Configuration
@ConditionalOnClass({HelloworldService.class})
@EnableConfigurationProperties(HelloworldProperties.class)
public class HelloworldConfiguration {

    @Autowired
    private HelloworldProperties helloworldProperties;


    @Bean
    @ConditionalOnMissingBean(HelloworldService.class)
    public HelloworldService helloworldService() {
        HelloworldService helloworldService = new HelloworldService();
        helloworldService.setWords(helloworldProperties.getWords());
        return helloworldService;
    }



}
