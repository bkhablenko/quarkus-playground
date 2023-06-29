package com.github.bkhablenko.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.qute.TemplateExtension;
import jakarta.enterprise.inject.spi.CDI;

@TemplateExtension(namespace = "json")
public class JsonTemplateExtensions {

    private static final ObjectMapper objectMapper;

    static {
        // @TemplateExtension methods must be static, so we cannot @Inject dependencies
        objectMapper = CDI.current().select(ObjectMapper.class).get();
    }

    public static String escapeString(String json) throws Exception {
        final var result = objectMapper.writeValueAsString(json);

        // Remove extra quotation marks
        return result.substring(1, result.length() - 1);
    }
}
