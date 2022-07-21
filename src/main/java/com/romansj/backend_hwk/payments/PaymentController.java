package com.romansj.backend_hwk.payments;

import com.romansj.backend_hwk.payments.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Allows access from port 8081 from frontend
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/v1")
public class PaymentController {


    @Autowired
    private PaymentService service;


    @GetMapping("/")
    private ResponseEntity<String> getDefault() {
        return new ResponseEntity<>("Hello, World!", HttpStatus.OK);
    }


    /**
     * @return Returns a list of all payments if data exists, otherwise returns {@link HttpStatus.NO_CONTENT}
     */
    @GetMapping("/payment")
    private ResponseEntity<List<Payment>> getAllPayments() {
        var payments = service.findAll();
        if (payments.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    /**
     * @param id Sought-after payment's Id
     * @return Returns payment data if found, otherwise returns {@link HttpStatus.NOT_FOUND}
     */
    @GetMapping("/payment/{id}")
    private ResponseEntity<Payment> getPaymentById(@PathVariable("id") long id) {
        var paymentOptional = service.findPaymentById(id);

        if (paymentOptional.isPresent()) return new ResponseEntity<>(paymentOptional.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * @param providedPayment JSON serialized Payment data that needs to be stored on backend
     * @return See {@link PaymentService#addPayment(Payment)}
     * <br><br>
     * Can throw {@link com.romansj.backend_hwk.configuration.exceptions.MyConstraintException} if incorrect input data is sent, with {@link HttpStatus.BAD_REQUEST}
     */
    @PostMapping("/payment")
    private ResponseEntity<Payment> createPayment(@RequestBody Payment providedPayment) {
        var savedPayment = service.addPayment(providedPayment);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }


    /**
     * @param token The token string to find payment. See more in the service layer {@link PaymentService}
     * @return Returns updated Payment with made modifications or {@link HttpStatus.NOT_FOUND} if service did not return data for given parameter
     */
    @PostMapping("/payment-approve")
    private ResponseEntity<Payment> updatePayment(@RequestParam(required = true) String token) {
        var updatedPaymentOptional = service.updatePaymentByToken(token);

        if (updatedPaymentOptional.isPresent()) return new ResponseEntity<>(updatedPaymentOptional.get(), HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }


}
