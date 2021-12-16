package com.pojo;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private List<Book> books = new ArrayList<>();

    public List<Book> getBook() {
        return books;
    }

    public void setBook(List<Book> books) {
        this.books = books;
    }
}
