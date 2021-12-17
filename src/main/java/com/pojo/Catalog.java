package com.pojo;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Catalog {
    private List<Book> book = new ArrayList<>();
}

