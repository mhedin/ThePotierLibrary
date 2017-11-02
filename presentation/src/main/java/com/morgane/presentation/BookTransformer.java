package com.morgane.presentation;

import com.morgane.usecases.Book;

import java.util.Locale;

/**
 * This class is used to transform Books into View Model or View Model to Books.
 */
public class BookTransformer {

    public BookViewModel toBookViewModel(Book book) {
        String price = String.format(Locale.FRANCE, "%d €", book.getPrice());
        return new BookViewModel(book.getIsbn(), book.getTitle(), price, book.getCover(), book.getSynopsis());
    }

    public Book toBook(BookViewModel bookViewModel) {
        int price = Integer.parseInt(bookViewModel.getPrice().substring(0, bookViewModel.getPrice().indexOf(" €")));
        return new BookModel(bookViewModel.getIsbn(), bookViewModel.getTitle(), price, bookViewModel.getCover(), bookViewModel.getSynopsis());
    }
}
