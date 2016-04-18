package com.preguardia.app.consultation.create.description;

/**
 * @author amouly on 4/6/16.
 */
public class DescriptionStepPresenter implements DescriptionStepContract.Presenter {

    private DescriptionStepContract.View view;

    public DescriptionStepPresenter(DescriptionStepContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(DescriptionStepContract.View view) {
        this.view = view;
    }
}
