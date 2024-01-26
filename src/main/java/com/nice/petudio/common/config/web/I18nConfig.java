package com.nice.petudio.common.config.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class I18nConfig {

    private static final String ENCODING_TYPE = "UTF-8";
    private static final int MESSAGES_CACHE_SECONDS = 3600; // 한 번 읽어온 properties 파일을 1시간 동안 캐싱

    @Value("${spring.messages.basename}")
    private String messagesBasename = null;

    @Bean
    public ReloadableResourceBundleMessageSource i18nMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(messagesBasename.split(","));
        messageSource.setDefaultEncoding(ENCODING_TYPE);
        messageSource.setCacheSeconds(MESSAGES_CACHE_SECONDS);

        return messageSource;
    }

}
