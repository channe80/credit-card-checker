package com.creditcard.checker.webservice.controller;

import com.creditcard.checker.webservice.model.CardType;
import com.creditcard.checker.webservice.model.ValidationResult;
import com.creditcard.checker.webservice.service.CreditCardValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditCardCheckerController {

    @Autowired
    private CreditCardValidationService creditCardValidationService;

    @GetMapping(path="/creditcards/{number}")
    public ValidationResult validateCreditCard(@PathVariable String number) {

        ValidationResult result = new ValidationResult();
        result.setCardNumber(number);

        CardType cardType = creditCardValidationService.getCardType(number);
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
