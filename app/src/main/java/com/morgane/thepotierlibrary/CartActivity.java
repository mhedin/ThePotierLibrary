package com.morgane.thepotierlibrary;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ViewFlipper;

import com.morgane.presentation.BookTransformer;
import com.morgane.presentation.BookViewModel;
import com.morgane.presentation.CommercialOffersPresenter;
import com.morgane.presentation.CommercialOffersView;
import com.morgane.presentation.CommercialOffersViewModel;
import com.morgane.repository.network.CommercialOffersNetworkRepository;
import com.morgane.repository.network.NetworkModule;
import com.morgane.thepotierlibrary.databinding.ActivityCartBinding;
import com.morgane.usecases.Book;
import com.morgane.usecases.CommercialOffersInteractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;

/**
 * This activity displays the content of the cart.
 */
public class CartActivity extends AppCompatActivity implements CommercialOffersView {

    /**
     * ID of the extra used to transmit the list of books added to the cart.
     */
    public static final String EXTRA_BOOKS_ADDED_TO_CART = "extraBooksAddedToCart";

    /**
     * URL used to download the commercial offers associated with the cart.
     */
    private static final String COMMERCIAL_OFFERS_URL = "http://henri-potier.xebia.fr/";

    /**
     * Position of the view when the commercial offers are loading.
     */
    private static final int VIEW_CHILD_LOADING = 0;

    /**
     * Position of the view when the commercial offers are displayed.
     */
    private static final int VIEW_CHILD_CART = 1;

    /**
     * Position of the view when there were an issue while downloading the commercial offers.
     */
    private static final int VIEW_CHILD_ERROR = 2;

    private ActivityCartBinding binding;

    private ViewFlipper viewFlipper;

    private List<BookViewModel> booksAddedToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);

        viewFlipper = findViewById(R.id.viewFlipper);

        // Display a custom message if there is nothing in the cart
        if (getIntent().getExtras() == null || !getIntent().getExtras().containsKey(EXTRA_BOOKS_ADDED_TO_CART)) {
            displayErrorMessage(getString(R.string.cart_empty));

        } else {

            Object[] bookViewModelsArray = (Object[]) getIntent().getSerializableExtra(EXTRA_BOOKS_ADDED_TO_CART);
            booksAddedToCart = Arrays.asList(Arrays.copyOf(bookViewModelsArray, bookViewModelsArray.length, BookViewModel[].class));

            // Display a custom message if there is nothing in the cart
            if (booksAddedToCart == null || booksAddedToCart.size() == 0) {
                displayErrorMessage(getString(R.string.cart_empty));

            } else {

                final NetworkModule networkModule = new NetworkModule();
                final HttpUrl httpUrl = HttpUrl.parse(COMMERCIAL_OFFERS_URL);
                final Retrofit retrofit = networkModule.getRetrofit(httpUrl);
                final CommercialOffersNetworkRepository repository = new CommercialOffersNetworkRepository(retrofit);
                final CommercialOffersPresenter presenter = new CommercialOffersPresenter(this, getResources());
                final CommercialOffersInteractor interactor = new CommercialOffersInteractor(presenter, repository);
                final BookTransformer transformer = new BookTransformer();
                new Thread() {
                    @Override
                    public void run() {
                        final List<Book> bookList = new ArrayList<>();
                        for (BookViewModel bookViewModel : booksAddedToCart) {
                            bookList.add(transformer.toBook(bookViewModel));
                        }
                        interactor.loadCommercialOffers(bookList);
                    }
                }.start();
            }
        }
    }

    @Override
    public void displayLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewFlipper.setDisplayedChild(VIEW_CHILD_LOADING);
            }
        });
    }

    @Override
    public void displayErrorMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.setErrorMessage(message);
                viewFlipper.setDisplayedChild(VIEW_CHILD_ERROR);
            }
        });
    }

    @Override
    public void displayCommercialOffer(final CommercialOffersViewModel viewModel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.setCommercialOffers(viewModel);

                final CartAdapter adapter = new CartAdapter(booksAddedToCart);
                final RecyclerView recyclerView = findViewById(R.id.cart_recyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(CartActivity.this,
                        DividerItemDecoration.VERTICAL));
                recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));

                viewFlipper.setDisplayedChild(VIEW_CHILD_CART);
            }
        });
    }
}
