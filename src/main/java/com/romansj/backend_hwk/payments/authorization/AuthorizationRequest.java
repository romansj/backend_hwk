package com.romansj.backend_hwk.payments.authorization;

import com.romansj.backend_hwk.payments.entity.Payment;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "authorization_request")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "aRequest", toBuilder = true, setterPrefix = "with")
public class AuthorizationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;

    @NonNull
    private String token;

    @NonNull
    private LocalDateTime createdAt;

    @NonNull
    private LocalDateTime expiresAt;

    @Setter
    private LocalDateTime usedAt;


}
