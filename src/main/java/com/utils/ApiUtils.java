package com.utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.internal.path.xml.NodeChildrenImpl;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.util.stream.IntStream;

public class ApiUtils {
    private static final String BASE_PATH = Config.getInstance().getStartURL();
    private static ApiUtils instance;
    private Response response;
    private NodeChildrenImpl books;

    private ApiUtils() {}

    public static ApiUtils getInstance() {
        if (instance == null) {
            instance = new ApiUtils();
            RestAssured.filters(new MyRequestFilter());
        }
        return instance;
    }

    public void getRequest(String target) {
        response = RestAssured.when().get(BASE_PATH + target);
    }

    public int getStatusCode() {
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

    static class MyRequestFilter implements Filter {

        private static final Logger LOGGER = Logger.getLogger(MyRequestFilter.class.getName());

        @Override
        public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
            Response response = ctx.next(requestSpec, responseSpec);
            if (requestSpec.getMethod().equals("GET")) {
                LOGGER.info("Getting request from " + requestSpec.getURI());
            } else if (requestSpec.getMethod().equals("POST")) {
                LOGGER.info("Post request to " + requestSpec.getURI());
            }
            LOGGER.info("Status code of request is: " + response.statusCode());
            if (response.statusCode() >= 400) {
                LOGGER.log(Level.ERROR, requestSpec.getURI() + " => " + response.getStatusLine());
            }
            return response;
        }
    }
}

