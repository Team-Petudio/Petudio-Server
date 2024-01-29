package com.nice.petudio.common.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageUtils {

    public static String getMessage(MessageSource messageSource, String sourceName) {
        return messageSource.getMessage(sourceName, null,
                LocaleContextHolder.getLocale());
    }
}
