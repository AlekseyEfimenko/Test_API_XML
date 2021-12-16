package com.pojo;

import lombok.Getter;
import lombok.Setter;

public class Book {
    @Getter @Setter private String author;
    @Getter @Setter private String title;
    @Getter @Setter private String genre;
    @Getter @Setter private double price;
    @Getter @Setter private String publish_date;
    @Getter @Setter private String description;
}

