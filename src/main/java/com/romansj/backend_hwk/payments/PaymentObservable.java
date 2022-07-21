package com.romansj.backend_hwk.payments;

import com.romansj.backend_hwk.notifications.email.PaymentData;
import com.romansj.backend_hwk.utils.models.ObservableSubject;
import io.reactivex.rxjava3.core.Observable;


// a singleton which holds an observable, which can be subscribed to and notified of new values
public class PaymentObservable {

    // Wanted to use annotation @Component, which would create singleton, but it could only be autowired in first caller class.
    // However, manual singleton getInstance call works in first class and returns the instance in second caller class
    private ObservableSubject<PaymentData> observable = new ObservableSubject<>();
    private static PaymentObservable INSTANCE;

    public static PaymentObservable getInstance() {
        if (INSTANCE == null) INSTANCE = new PaymentObservable();
        return INSTANCE;
    }

    private PaymentObservable() {
    }


    public Observable<PaymentData> getObservable() {
        return observable.getObservable();
    }


    public void notify(PaymentData paymentData) {
        observable.setValue(paymentData);
    }


}
