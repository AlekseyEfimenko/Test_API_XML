package com.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    @Getter @Setter private List<Book> book = new ArrayList<>();
}

