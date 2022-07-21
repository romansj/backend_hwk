package com.romansj.backend_hwk.payments.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Currency;

// todo need builders for local and for international transfers
//  local, intl-eu, intl
@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "aPayment", toBuilder = true, setterPrefix = "with")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    private PaymentType paymentType;

    @NonNull
    private String accountOrigin;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NonNull
    private ZonedDateTime paymentDate;

    @Builder.Default
    private ComissionCoverage comissionCoverage = ComissionCoverage.DIVIDED;

    @NonNull
    private String accountDestination;

    @NonNull
    private String nameDestination;

    private Country countryDestination;

    private String bankDestination;

    @NonNull
    private String swift;

    @NonNull
    private Double amount;

    @Builder.Default
    @NonNull
    private Currency currency = Currency.getInstance("EUR");

    @NonNull
    private String reason;

    // todo this ok? one field setter
    // @Setter
    @Builder.Default
    private Status status = Status.PENDING;


}
