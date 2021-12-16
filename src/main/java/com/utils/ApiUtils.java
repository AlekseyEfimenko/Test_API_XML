package com.utils;

import io.restassured.internal.path.xml.NodeChildrenImpl;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import java.util.stream.IntStream;

public class ApiUtils {
    private static final String BASE_PATH = Config.getInstance().getStartURL();
    private static final Logger LOGGER = Logger.getLogger(ApiUtils.class);
    private static ApiUtils instance;
    private Response response;
    private NodeChildrenImpl books;

    private ApiUtils() {}

    public static ApiUtils getInstance() {
        if (instance == null) {
            instance = new ApiUtils();
        }
        return instance;
    }

    public void getRequest(String target) {
        response = RestAssured.when().get(BASE_PATH + target);
    }

    public int getStatusCode() {
        LOGGER.info("Get Status code of request");
        return response.getStatusCode();
    }

    public String getContentType() {
        return response.contentType().split(";")[0];
    }

    public <T> T getCatalog(Class<T> cl, String target) {
        return RestAssured.when().get(BASE_PATH + target).xmlPath().getObject("$", cl);
    }

    public String[] getArrayOfAttributes(String attr) {
        setListOfBooks();
        String[] array = new String[books.size()];
        IntStream intStream = IntStream.range(0, books.size());
        intStream.forEach(i -> array[i] = books.get(i).getAttribute(attr));
        return array;
    }

    private void setListOfBooks() {
        books = response.then().extract().path(Config.getInstance().getProperties("books"));
    }
}

