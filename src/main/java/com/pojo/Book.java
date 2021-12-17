package com.pojo;

import lombok.Data;

@Data
public class Book {
    private String author;
    private String title;
    private String genre;
    private double price;
    private String publish_date;
    private String description;
}

