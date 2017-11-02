package com.morgane.presentation;

import android.content.res.Resources;

import com.morgane.usecases.CommercialOffer;
import com.morgane.usecases.CommercialOffersOutputPort;

import java.util.Locale;

/**
 * This class transform the commercial offers received to a presentable offer.
 */
public class CommercialOffersPresenter implements CommercialOffersOutputPort {

    private final CommercialOffersView view;

    private final Resources resources;

    public CommercialOffersPresenter(CommercialOffersView view, Resources resources) {
        this.view = view;
        this.resources = resources;
    }

    @Override
    public void onPendingRequest() {
        view.displayLoading();
    }

    @Override
    public void onGenericException() {
        view.displayErrorMessage(resources.getString(R.string.message_generic_error));
    }

    @Override
    public void onCommercialOffersReceived(CommercialOffer commercialOffer, int totalPriceBeforeOffer) {
        final float totalPriceWithPercentage = totalPriceBeforeOffer * (100 - commercialOffer.getPercentage()) / 100;
        final float totalPriceWithMinus = totalPriceBeforeOffer - commercialOffer.getMinus();
        final int numberOfSlice = commercialOffer.getSliceValue() > 0 ? totalPriceBeforeOffer / commercialOffer.getSliceValue() : 0;
        final float totalPriceWithSlice = numberOfSlice > 0 ?
                totalPriceBeforeOffer - (commercialOffer.getValue() * numberOfSlice) : totalPriceBeforeOffer;

        float finalPrice = Math.min(totalPriceWithPercentage, Math.min(totalPriceWithMinus, totalPriceWithSlice));
        float saving = totalPriceBeforeOffer - finalPrice;

        String finalPriceWithUnit = String.format(Locale.FRANCE, "%.2f €", finalPrice);
        String savingWithUnit = String.format(Locale.FRANCE, "%.2f €", saving);

        CommercialOffersViewModel viewModel = new CommercialOffersViewModel(finalPriceWithUnit, savingWithUnit);

        view.displayCommercialOffer(viewModel);
    }
}
