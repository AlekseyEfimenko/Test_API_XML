package com.utils;

import com.pojo.Catalog;
import io.restassured.http.ContentType;
import io.restassured.internal.path.xml.NodeChildrenImpl;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import java.util.stream.IntStream;

public class APIUtils {
    private static final String BASE_PATH = Config.getInstance().getStartURL();
    private static final Logger LOGGER = Logger.getLogger(APIUtils.class);
    private static APIUtils instance;
    private Response response;
    private NodeChildrenImpl books;

    private APIUtils() {}

    public static APIUtils getInstance() {
        if (instance == null) {
            instance = new APIUtils();
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

    public void checkContentType(ContentType format) {
        response.then().assertThat().contentType(format);
    }

    public Catalog getCatalog() {
        return response.xmlPath().getObject("$", Catalog.class);
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
