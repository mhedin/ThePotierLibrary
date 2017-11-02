package com.morgane.presentation;

import com.morgane.usecases.CommercialOffer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(RobolectricTestRunner.class)
public class CommercialOffersPresenterTest {

    @Mock
    private CommercialOffersView view;

    private CommercialOffersPresenter presenter;

    @Captor
    private ArgumentCaptor<CommercialOffersViewModel> viewModelCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new CommercialOffersPresenter(view, RuntimeEnvironment.application.getResources());
    }

    @Test
    public void onPendingRequest() {
        presenter.onPendingRequest();

        then(view).should().displayLoading();
    }

    @Test
    public void onGenericException() {
        presenter.onGenericException();

        then(view).should().displayErrorMessage("Veuillez réessayer plus tard");
    }

    @Test
    public void onCommercialOffersReceived_WithBestPercentage() {
        presenter.onCommercialOffersReceived(getMockedCommercialOffer(10, 0, 100, 0), 50);

        then(view).should().displayCommercialOffer(viewModelCaptor.capture());
        final CommercialOffersViewModel viewModel = viewModelCaptor.getValue();

        assertThat(viewModel.getFinalPrice()).isEqualTo("45,00 €");
    }

    @Test
    public void onCommercialOffersReceived_WithBestMinus() {
        presenter.onCommercialOffersReceived(getMockedCommercialOffer(1, 20, 100, 0), 50);

        then(view).should().displayCommercialOffer(viewModelCaptor.capture());
        final CommercialOffersViewModel viewModel = viewModelCaptor.getValue();

        assertThat(viewModel.getFinalPrice()).isEqualTo("30,00 €");
    }

    @Test
    public void onCommercialOffersReceived_WithBestSlice() {
        presenter.onCommercialOffersReceived(getMockedCommercialOffer(1, 0, 20, 5), 50);

        then(view).should().displayCommercialOffer(viewModelCaptor.capture());
        final CommercialOffersViewModel viewModel = viewModelCaptor.getValue();

        assertThat(viewModel.getFinalPrice()).isEqualTo("40,00 €");
    }

    private CommercialOffer getMockedCommercialOffer(int percentage, int minus, int sliceValue, int value) {
        CommercialOffer commercialOffer = Mockito.mock(CommercialOffer.class);

        given(commercialOffer.getPercentage()).willReturn(percentage);
        given(commercialOffer.getMinus()).willReturn(minus);
        given(commercialOffer.getSliceValue()).willReturn(sliceValue);
        given(commercialOffer.getValue()).willReturn(value);

        return commercialOffer;
    }
}
