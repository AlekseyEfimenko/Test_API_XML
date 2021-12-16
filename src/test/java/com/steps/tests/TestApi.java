package com.steps.tests;

import com.pojo.Catalog;
import com.steps.TestSteps;
import com.utils.ApiUtils;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class TestApi {
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final String CONTENT_TYPE = ContentType.XML.toString();
    private static final String ATTRIBUTE = "id";
    private final TestSteps step = new TestSteps();
    private final Catalog catalog = ApiUtils.getInstance().getCatalog(Catalog.class, "");

    @Test
    public void testGet() {
        step.getBooks();
        step.assertStatusCode(SUCCESS_STATUS_CODE);
        step.assertListFormat(CONTENT_TYPE);
        step.assertListIsSortedByAttribute(ATTRIBUTE);
        step.assertTitleAndDescriptionNotEquals(catalog);
    }
}

