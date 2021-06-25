package com.creditcard.checker.webservice.service;

import com.creditcard.checker.webservice.model.CardType;

public interface CreditCardValidationService {

    CardType determineCardType(String cardNumber);

    boolean validateByLuhnAlgorithm(String cardNumber);
}

