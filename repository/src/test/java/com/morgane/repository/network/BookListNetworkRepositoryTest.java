package com.morgane.repository.network;

import com.morgane.usecases.Book;
import com.morgane.usecases.BookListRepository;
import com.morgane.usecases.CommercialOffer;
import com.morgane.usecases.CommercialOffersRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class BookListNetworkRepositoryTest {

    @Rule
    public MockWebServer server = new MockWebServer();

    private NetworkModule networkModule;

    private BookListNetworkRepository repository;

    @Before
    public void setup() {
        networkModule = new NetworkModule();
        final Retrofit retrofit = networkModule.getRetrofit(server.url("/"));
        repository = new BookListNetworkRepository(retrofit);
    }

    @Test(expected = BookListNetworkRepository.GenericException.class)
    public void loadBookList_WithGenericException() throws BookListNetworkRepository.GenericException {
        final MockResponse response = new MockResponse()
                .setResponseCode(500);
        server.enqueue(response);

        final List<Book> bookList = repository.loadBookList();

        assertThat(bookList).isNull();
    }

    @Test
    public void loadBookList() throws IOException, BookListRepository.GenericException {
        final MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setBody(FileUtils.read("bookList.json"));
        server.enqueue(response);

        final List<Book> bookList = repository.loadBookList();

        assertThat(bookList).isNotNull();

        final Book book = bookList.get(0);
        assertThat(book.getIsbn()).isEqualTo("c8fabf68-8374-48fe-a7ea-a00ccd07afff");
        assertThat(book.getTitle()).isEqualTo("Henri Potier à l'école des sorciers");
        assertThat(book.getPrice()).isEqualTo(35);
        assertThat(book.getCover()).isEqualTo("http://henri-potier.xebia.fr/hp0.jpg");
    }
}
