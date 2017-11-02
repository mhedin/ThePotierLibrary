package com.morgane.presentation;

import java.io.Serializable;

/**
 * View Model of the books to be displayed.
 */
public class BookViewModel implements Serializable {

    private final String isbn;

    private final String title;

    private final String price;

    private String cover;

    private String synopsis;

    public BookViewModel(String isbn, String title, String price, String cover, String synopsis) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.cover = cover;
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPrice() {
        return price;
    }

    public String getCover() {
        return cover;
    }

    public String getSynopsis() {
        return synopsis;
    }
}
