package it.sisal.kairos.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;

public class StructUtils {

    private StructUtils() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static class StructConversionException extends RuntimeException {
        public StructConversionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static <T> T convertStructToObject(Struct struct, Class<T> clazz) {
        String jsonString;
        try {
            jsonString = JsonFormat.printer().print(struct);
        } catch (InvalidProtocolBufferException e) {
            throw new StructConversionException("Error converting Struct to JSON string", e);
        }

        try {
            return new ObjectMapper().readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            throw new StructConversionException("Error converting JSON string to object", e);
        }
    }

    public static Struct convertObjectToStruct(Object pojo) {
        String jsonString;
        try {
            jsonString = new ObjectMapper().writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            throw new StructConversionException("Error converting object to JSON string", e);
        }

        Struct.Builder structBuilder = Struct.newBuilder();
        try {
            JsonFormat.parser().ignoringUnknownFields().merge(jsonString, structBuilder);
        } catch (InvalidProtocolBufferException e) {
            throw new StructConversionException("Error converting JSON string to Struct", e);
        }
        return structBuilder.build();
    }
}
