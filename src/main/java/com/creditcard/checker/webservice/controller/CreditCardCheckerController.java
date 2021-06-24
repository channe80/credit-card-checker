package com.creditcard.checker.webservice.controller;

import com.creditcard.checker.webservice.model.CardType;
import com.creditcard.checker.webservice.model.ValidationResult;
import com.creditcard.checker.webservice.service.CreditCardValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreditCardCheckerController {

    @Autowired
    private CreditCardValidationService creditCardValidationService;

    @PostMapping(path="/creditcards")
    public ValidationResult validateCreditCard(@RequestBody String cardNumber) {

        ValidationResult result = new ValidationResult();
        result.setCardNumber(cardNumber);

        CardType cardType = creditCardValidationService.getCardType(cardNumber);
        result.setCardType(cardType);

        if (CardType.Unknown.equals(cardType)) {
            result.setValid(Boolean.FALSE);
        } else {
            boolean validCardNumber = creditCardValidationService.isValidByLuhnAlgo();
            result.setValid(validCardNumber);
        }

        return result;
    }
}
