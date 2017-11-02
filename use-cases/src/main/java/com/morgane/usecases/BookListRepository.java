package com.morgane.usecases;

import java.util.List;

/**
 * Interface of the repository which will manage the book list.
 */
public interface BookListRepository {

    List<Book> loadBookList() throws GenericException;

    class GenericException extends Exception {

    }
}
