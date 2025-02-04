package it.sisal.kairos.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;

class StructUtilsTest {

    @Test
    void testUtilityClassConstructor() throws Exception {
        Constructor<StructUtils> constructor = StructUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
        assertEquals("Utility class should not be instantiated", exception.getCause().getMessage());
    }

    @Test
    void testConvertObjectToStruct() {
        TestObject testObject = new TestObject("testValue", 123);
        Struct struct = StructUtils.convertObjectToStruct(testObject);

        assertNotNull(struct);
        assertTrue(struct.getFieldsMap().containsKey("name"));
        assertTrue(struct.getFieldsMap().containsKey("value"));
        assertEquals("testValue", struct.getFieldsMap().get("name").getStringValue());
        assertEquals(123, (int) struct.getFieldsMap().get("value").getNumberValue());
    }

    @Test
    void testConvertObjectToStructThrowsException() {
        Object invalidObject = new Object() {
            // Questo oggetto anonimo non può essere serializzato correttamente
        };

        Exception exception = assertThrows(
            StructUtils.StructConversionException.class,
            () -> StructUtils.convertObjectToStruct(invalidObject)
        );

        assertTrue(exception.getMessage().contains("Error converting object to JSON string"));
    }

    @Test
    void testConvertStructToObject() {
        String json = "{\"name\":\"testValue\",\"value\":123}";
        Struct.Builder builder = Struct.newBuilder();
        try {
            JsonFormat.parser().merge(json, builder);
        } catch (InvalidProtocolBufferException e) {
            fail("Failed to create Struct for testing");
        }

        Struct struct = builder.build();
        TestObject result = StructUtils.convertStructToObject(struct, TestObject.class);

        assertNotNull(result);
        assertEquals("testValue", result.getName());
        assertEquals(123, result.getValue());
    }

    static class TestObject {
        private String name;
        private int value;

        public TestObject() {
        }

        public TestObject(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }
}
