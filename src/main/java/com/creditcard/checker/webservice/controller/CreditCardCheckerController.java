package com.creditcard.checker.webservice.controller;

import com.creditcard.checker.webservice.model.CardType;
import com.creditcard.checker.webservice.model.CreditCardValidationRequest;
import com.creditcard.checker.webservice.model.CreditCardValidationResponse;
import com.creditcard.checker.webservice.service.CreditCardValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("creditcards")
public class CreditCardCheckerController {

    @Autowired
    private CreditCardValidationService creditCardValidationService;

    @PostMapping(path="/validate")
    public CreditCardValidationResponse validateCreditCard(@RequestBody CreditCardValidationRequest cardNumber) {

        CreditCardValidationResponse result = new CreditCardValidationResponse();
        result.setCardNumber(cardNumber.getCardNumber());

        CardType cardType = creditCardValidationService.getCardType(cardNumber.getCardNumber());
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
