package com.steps;

import static com.utils.MyAssert.myAssertEquals;
import static com.utils.MyAssert.myAssertNotEquals;
import static com.utils.MyAssert.myAssertTrue;
import com.pojo.Catalog;
import com.utils.ApiUtils;
import org.apache.log4j.Logger;
import java.util.stream.IntStream;

public class TestSteps {
    private static final Logger LOGGER = Logger.getLogger(TestSteps.class);
    private double maxPrice;
    private double minPrice;

    public void getBooks() {
        LOGGER.info("Sending \"Get\" request to receive the list of books");
        ApiUtils.getInstance().getRequest("");

    }

    public void assertStatusCode(int statusCode) {
        myAssertEquals(ApiUtils.getInstance().getStatusCode(), statusCode);
    }

    public void assertListFormat(String format) {
        LOGGER.info("Check if list is formatted as XML");
        myAssertEquals(ApiUtils.getInstance().getContentType(), format);
    }

    public void assertListIsSortedByAttribute(String attr) {
        LOGGER.info("Check if list is sorted by " + attr);
        boolean sorted = true;
        String temp = "";
        for (String str : ApiUtils.getInstance().getArrayOfAttributes(attr)) {
            if (str.compareTo(temp) < 0) {
                sorted = false;
            }
            temp = str;
        }
        myAssertTrue(sorted);
    }

    public void assertTitleAndDescriptionNotEquals(Catalog catalog) {
        setMinAndMaxPrice(catalog);
        LOGGER.info("Checking if \"title\" and \"description\" of the books with max and min price are different");
        catalog.getBook().forEach(maxBook -> {
            if (maxBook.getPrice() == maxPrice) {
                String maxTitle = maxBook.getTitle();
                String maxDescription = maxBook.getDescription();
                catalog.getBook().forEach(minBook -> {
                    if (minBook.getPrice() == minPrice) {
                        myAssertNotEquals(maxTitle.compareTo(minBook.getTitle()), 0);
                        myAssertNotEquals(maxDescription.compareTo(minBook.getDescription()), 0);
                    }
                });
            }
        });
    }

    private void setMinAndMaxPrice(Catalog catalog) {
        LOGGER.info("Looking for max and min price");
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

