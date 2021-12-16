package com.steps.tests;

import com.steps.TestSteps;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class TestAPI {
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final ContentType CONTENT_TYPE = ContentType.XML;
    private static final String ATTRIBUTE = "id";
    TestSteps step = new TestSteps();

    @Test
    public void testGet() {
        step.getBooks();
        step.assertStatusCode(SUCCESS_STATUS_CODE);
        step.assertListFormat(CONTENT_TYPE);
        step.assertListIsSortedByAttribute(ATTRIBUTE);
        step.assertTitleAndDescriptionNotEquals();
    }
}
