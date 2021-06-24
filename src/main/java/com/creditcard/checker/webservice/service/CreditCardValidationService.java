package com.creditcard.checker.webservice.service;

import com.creditcard.checker.webservice.model.CardType;

public interface CreditCardValidationService {

    CardType getCardType(String cardNumber);

    boolean isValidByLuhnAlgo();
}

