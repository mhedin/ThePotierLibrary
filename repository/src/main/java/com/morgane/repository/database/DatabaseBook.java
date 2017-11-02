package com.morgane.repository.database;

import com.morgane.usecases.Book;

/**
 * Implementation of Book, which can be used to store it in database.
 */
public class DatabaseBook implements Book {

    private String isbn;

    private String title;

    private int price;

    private String cover;

    private String synopsis;

    public DatabaseBook() {

    }

    public DatabaseBook(String isbn, String title, int price, String cover, String synopsis) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.cover = cover;
        this.synopsis = synopsis;
    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getCover() {
        return cover;
    }

    @Override
    public String getSynopsis() {
        return synopsis;
    }
}
