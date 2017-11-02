package com.morgane.repository.network;

import com.morgane.usecases.CommercialOffer;
import com.morgane.usecases.CommercialOffersRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CommercialOffersNetworkRepositoryTest {

    @Rule
    public MockWebServer server = new MockWebServer();

    private NetworkModule networkModule;

    private CommercialOffersNetworkRepository repository;

    @Before
    public void setup() {
        networkModule = new NetworkModule();
        final Retrofit retrofit = networkModule.getRetrofit(server.url("/"));
        repository = new CommercialOffersNetworkRepository(retrofit);
    }

    @Test(expected = CommercialOffersRepository.GenericException.class)
    public void loadCommercialOffers_WithGenericException() throws CommercialOffersRepository.GenericException {
        final MockResponse response = new MockResponse()
                .setResponseCode(500);
        server.enqueue(response);

        final CommercialOffer commercialOffers = repository.loadCommercialOffers(Arrays.asList("1234", "5678"));

        assertThat(commercialOffers).isNull();
    }

    @Test
    public void loadCommercialOffers() throws IOException, CommercialOffersRepository.GenericException {
        final MockResponse response = new MockResponse()
                .setResponseCode(200)
                .setBody(FileUtils.read("commercialOffers.json"));
        server.enqueue(response);

        final CommercialOffer commercialOffers = repository.loadCommercialOffers(Arrays.asList("1234", "5678"));

        assertThat(commercialOffers).isNotNull();

        assertThat(commercialOffers.getPercentage()).isEqualTo(4);
        assertThat(commercialOffers.getMinus()).isEqualTo(15);
        assertThat(commercialOffers.getSliceValue()).isEqualTo(100);
        assertThat(commercialOffers.getValue()).isEqualTo(12);
    }
}
