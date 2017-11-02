package com.morgane.usecases;

/**
 * Interface of the Book object.
 */
public interface Book {

    String getIsbn();

    String getTitle();

    int getPrice();

    String getCover();

    String getSynopsis();
}
