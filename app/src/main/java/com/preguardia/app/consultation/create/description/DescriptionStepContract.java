package com.preguardia.app.consultation.create.description;

/**
 * @author amouly on 4/6/16.
 */
public interface DescriptionStepContract {

    interface View {
        String getData();
    }

    interface Presenter {
        void attachView(DescriptionStepContract.View view);
    }
}
