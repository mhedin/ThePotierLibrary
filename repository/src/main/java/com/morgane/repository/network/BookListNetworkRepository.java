package com.morgane.repository.network;

import com.google.gson.Gson;
import com.morgane.repository.transform.BookTransformer;
import com.morgane.usecases.Book;
import com.morgane.usecases.BookListRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * This class connects to a service which download the list of books available.
 */
public class BookListNetworkRepository implements BookListRepository {

    interface BookListService {

        @GET("books")
        Call<String> getBookListAsString();
    }

    private final Retrofit retrofit;

    public BookListNetworkRepository(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    private String loadBookListAsString() {
        try {
            final Response<String> response = retrofit.create(BookListService.class)
                    .getBookListAsString()
                    .execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            System.err.print(e);
        }
        return null;
    }

    @Override
    public List<Book> loadBookList() throws GenericException {
        final List<Book> bookList = new ArrayList<>();
        final BookTransformer transformer = new BookTransformer();
        final String bookListAsString = loadBookListAsString();

        Gson gson = new Gson();
        JsonBook[] booksArray = gson.fromJson(bookListAsString, JsonBook[].class);

        if (booksArray != null && booksArray.length > 0) {
            for (JsonBook jsonBook : booksArray) {
                bookList.add(transformer.toBook(jsonBook));
            }

        } else {
            throw new GenericException();
        }

        return bookList;
    }
}
