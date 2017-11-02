package com.morgane.repository.network;

import java.util.List;

/**
 * Json representation of the Book objects downloaded.
 */
public class JsonBook {

    private String isbn;

    private String title;

    private int price;

    private String cover;

    private List<String> synopsis;

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getCover() {
        return cover;
    }

    public List<String> getSynopsis() {
        return synopsis;
    }
}
