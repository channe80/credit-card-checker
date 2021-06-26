package com.creditcard.checker.webservice.controller;

import com.creditcard.checker.webservice.model.CardType;
import com.creditcard.checker.webservice.model.CreditCardValidationRequest;
import com.creditcard.checker.webservice.model.CreditCardValidationResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditCardCheckerControllerTest extends AbstractTest{

    String validateURI = "/creditcards/validate";

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }
    @Test
    void validateCreditCard_validCard() throws Exception {
        //valid credit card
        CreditCardValidationRequest requestData = new CreditCardValidationRequest();
        requestData.setCardNumber("378282246310005");
        String jsonInput = super.mapToJson(requestData);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateURI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        CreditCardValidationResult result = super.mapFromJson(mvcResult.getResponse().getContentAsString(), CreditCardValidationResult.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(requestData.getCardNumber(), result.getCardNumber());
        Assertions.assertEquals(CardType.AMEX, result.getCardType());
        Assertions.assertTrue(result.getValid());

        CreditCardValidationRequest requestData2 = new CreditCardValidationRequest();
        requestData2.setCardNumber("4111111111111111");
        jsonInput = super.mapToJson(requestData2);
        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.post(validateURI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput)).andReturn();

        int status2 = mvcResult2.getResponse().getStatus();
        assertEquals(200, status2);
        CreditCardValidationResult result2 = super.mapFromJson(mvcResult2.getResponse().getContentAsString(), CreditCardValidationResult.class);
        Assertions.assertNotNull(result2);
        Assertions.assertEquals(requestData2.getCardNumber(), result2.getCardNumber());
        Assertions.assertEquals(CardType.Visa, result2.getCardType());
        Assertions.assertTrue(result2.getValid());
    }

    @Test
    void validateCreditCard_validCardWithSpaces() throws Exception {
        //valid credit card with spaces
        CreditCardValidationRequest requestData = new CreditCardValidationRequest();
        requestData.setCardNumber("37 8282 24631 0005");
        String jsonInput = super.mapToJson(requestData);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateURI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        CreditCardValidationResult result = super.mapFromJson(mvcResult.getResponse().getContentAsString(), CreditCardValidationResult.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(requestData.getCardNumber(), result.getCardNumber());
        Assertions.assertEquals(CardType.AMEX, result.getCardType());
        Assertions.assertTrue(result.getValid());
    }

    @Test
    void validateCreditCard_unknownCard() throws Exception {
        //valid credit card with spaces
        CreditCardValidationRequest requestData = new CreditCardValidationRequest();
        requestData.setCardNumber("212121212");
        String jsonInput = super.mapToJson(requestData);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateURI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        CreditCardValidationResult result = super.mapFromJson(mvcResult.getResponse().getContentAsString(), CreditCardValidationResult.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(requestData.getCardNumber(), result.getCardNumber());
        Assertions.assertEquals(CardType.Unknown, result.getCardType());
        Assertions.assertFalse(result.getValid());
    }

    @Test
    void validateCreditCard_nullCardNumber() throws Exception {
        //card number is not set at all
        CreditCardValidationRequest requestData = new CreditCardValidationRequest();
        String jsonInput = super.mapToJson(requestData);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateURI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        CreditCardValidationResult result = super.mapFromJson(mvcResult.getResponse().getContentAsString(), CreditCardValidationResult.class);
        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getCardNumber());
        Assertions.assertEquals(CardType.Unknown, result.getCardType());
        Assertions.assertFalse(result.getValid());

        //card number is set to null
        requestData = new CreditCardValidationRequest();
        requestData.setCardNumber(null);
        jsonInput = super.mapToJson(requestData);
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateURI)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonInput)).andReturn();

        status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        result = super.mapFromJson(mvcResult.getResponse().getContentAsString(), CreditCardValidationResult.class);
        Assertions.assertNotNull(result);
        Assertions.assertNull(result.getCardNumber());
        Assertions.assertEquals(CardType.Unknown, result.getCardType());
        Assertions.assertFalse(result.getValid());
    }

    @Test
    void validateCreditCard_requestHeaderIncorrect() throws Exception {
        CreditCardValidationRequest requestData = new CreditCardValidationRequest();
        String jsonInput = super.mapToJson(requestData);

        //content type not set
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(validateURI).content(jsonInput)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), status);

        //content not set
        mvc.perform(MockMvcRequestBuilders.post(validateURI).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), status);

    }
}