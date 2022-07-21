package com.romansj.backend_hwk.payments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerMvcTest {
    public static final String API = "/api/v1/";


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        this.mockMvc
                .perform(get(API))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World!")));
    }

    @Test
    public void paymentsShouldReturnNoContent() throws Exception {
        this.mockMvc
                .perform(get(API + "payment"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    public void canAddPayment() throws Exception {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        var payment = Payments.aPayment();
        var jsonSerialized = objectMapper.writeValueAsString(payment);


        this.mockMvc
                .perform(post(API + "payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonSerialized) // not using hardcoded textblock, but generating JSON on the fly
                )
                .andExpect(status().isCreated());
    }


    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
    }
}