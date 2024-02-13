package com.nice.petudio.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nice.petudio.common.exception.error.ErrorCode;
import com.nice.petudio.common.exception.model.InternalServerException;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T deserialize(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_EXCEPTION,
                    String.format("Json을 객체로 deserialize 하는 과정에서 에러가 발생하였습니다.\n[Class Type]" + type + "\n[JSON STRING]\n" + json));
        }
    }
}
