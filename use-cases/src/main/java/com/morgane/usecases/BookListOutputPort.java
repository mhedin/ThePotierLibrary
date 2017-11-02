package com.morgane.usecases;

import java.util.List;

/**
 * Interface of the output port which will manage the book list.
 */
public interface BookListOutputPort {

    void onGenericException();

    void onPendingRequest();

    void onBookListReceived(List<Book> bookList);
}
