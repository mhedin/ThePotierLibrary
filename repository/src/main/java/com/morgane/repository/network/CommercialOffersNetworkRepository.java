package com.morgane.repository.network;

import com.google.gson.Gson;
import com.morgane.repository.transform.CommercialOffersTransformer;
import com.morgane.usecases.CommercialOffer;
import com.morgane.usecases.CommercialOffersRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * This class connects to a service which download the list of commercial offers available.
 */
public class CommercialOffersNetworkRepository implements CommercialOffersRepository {

    interface CommercialOffersService {

        @GET("books/{isbnList}/commercialOffers")
        Call<String> getCommercialOffersAsString(@Path("isbnList") String isbnList);

    }

    private final Retrofit retrofit;

    public CommercialOffersNetworkRepository(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    private String loadCommercialOffersAsString(String isbnList) {
        try {
            final Response<String> response = retrofit.create(CommercialOffersService.class)
                    .getCommercialOffersAsString(isbnList)
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
    public CommercialOffer loadCommercialOffers(List<String> isbnList) throws GenericException {
        final CommercialOffersTransformer transformer = new CommercialOffersTransformer();
        // Format the list of isbn to a string with isbn separated by comma
        final StringBuilder isbnListAsString = new StringBuilder();
        isbnListAsString.append(isbnList.get(0));
        for (int i = 1; i < isbnList.size(); i++) {
            isbnListAsString.append(",")
                    .append(isbnList.get(i));
        }

        final String commercialOffersAsString = loadCommercialOffersAsString(isbnListAsString.toString());

        if (commercialOffersAsString == null || commercialOffersAsString.isEmpty()) {
            throw new GenericException();

        } else {
            Gson gson = new Gson();
            JsonOffersList jsonOffersList = gson.fromJson(commercialOffersAsString, JsonOffersList.class);

            return transformer.toCommercialOffers(jsonOffersList);
        }
    }
}
