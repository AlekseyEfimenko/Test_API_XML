package com.utils;

import org.apache.log4j.Logger;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

public class MyAssert {
    private static final Logger LOGGER = Logger.getLogger(MyAssert.class);

    private MyAssert() {}

    public static void myAssertTrue(boolean condition) {
        try {
            assertTrue(condition);
        } catch (AssertionError ex) {
            LOGGER.error("Expected condition is true, but found condition is false: " + ex.getMessage());
            throw new AssertionError();
        }
    }

    public static void myAssertFalse(boolean condition) {
        try {
            assertFalse(condition);
        } catch (AssertionError ex) {
            LOGGER.error("Expected condition is false, but found condition is true");
            throw new AssertionError();
        }
    }

    public static <T> void myAssertEquals(T actual, T expected) {
        try {
            assertEquals(actual, expected);
        } catch (AssertionError ex) {
            LOGGER.error("This two objects are not equals: " + ex.getMessage());
            throw new AssertionError();
        }
    }

    public static <T> void myAssertNotEquals(T actual, T expected) {
        try {
            assertNotEquals(actual, expected);
        } catch (AssertionError ex) {
            LOGGER.error("This two objects are equals: " + ex.getMessage());
            throw new AssertionError();
        }
    }

    public static void myAssertNotNull(Object obj) {
        try {
            assertNotNull(obj);
        } catch (AssertionError ex) {
            LOGGER.error(("This object is null: " + ex.getMessage()));
            throw new AssertionError();
        }
    }
}

