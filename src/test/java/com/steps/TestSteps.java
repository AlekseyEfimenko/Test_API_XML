package com.steps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;
import com.pojo.Catalog;
import com.utils.APIUtils;
import io.restassured.http.ContentType;
import org.apache.log4j.Logger;
import java.util.stream.IntStream;

public class TestSteps {
    private static final Logger LOGGER = Logger.getLogger(TestSteps.class);
    private double maxPrice;
    private double minPrice;
    private Catalog catalog;

    public void getBooks() {
        LOGGER.info("Sending \"Get\" request to receive the list of books");
        APIUtils.getInstance().getRequest("");

    }

    public void assertStatusCode(int statusCode) {
        assertEquals(APIUtils.getInstance().getStatusCode(), statusCode);
    }

    public void assertListFormat(ContentType format) {
        LOGGER.info("Check if list is formatted as XML");
        APIUtils.getInstance().checkContentType(format);
    }

    public void assertListIsSortedByAttribute(String attr) {
        LOGGER.info("Check if list is sorted by " + attr);
        boolean sorted = true;
        String temp = "";
        for (String str : APIUtils.getInstance().getArrayOfAttributes(attr)) {
            if (str.compareTo(temp) < 0) {
                sorted = false;
            }
            temp = str;
        }
        assertTrue(sorted);
    }

    public void assertTitleAndDescriptionNotEquals() {
        setMinAndMaxPrice();
        LOGGER.info("Checking if \"title\" and \"description\" of the books with max and min price are different");
        catalog.getBook().forEach(maxBook -> {
            if (maxBook.getPrice() == maxPrice) {
                String maxTitle = maxBook.getTitle();
                String maxDescription = maxBook.getDescription();
                catalog.getBook().forEach(minBook -> {
                    if (minBook.getPrice() == minPrice) {
                        assertNotEquals(maxTitle.compareTo(minBook.getTitle()), 0);
                        assertNotEquals(maxDescription.compareTo(minBook.getDescription()), 0);
                    }
                });
            }
        });
    }

    private void setMinAndMaxPrice() {
        LOGGER.info("Looking for max and min price");
        catalog = APIUtils.getInstance().getCatalog();
        maxPrice = catalog.getBook().get(0).getPrice();
        minPrice = catalog.getBook().get(0).getPrice();
        IntStream intStream = IntStream.range(0, catalog.getBook().size());
        intStream.forEach(i -> {
            double temp = catalog.getBook().get(i).getPrice();
            if (temp > maxPrice) {
                maxPrice = temp;
            }
            if (temp < minPrice) {
                minPrice = temp;
            }
        });
    }
}
