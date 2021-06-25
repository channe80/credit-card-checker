package com.creditcard.checker.webservice.controller;

import com.creditcard.checker.webservice.model.CardType;
import com.creditcard.checker.webservice.model.CreditCardValidationRequest;
import com.creditcard.checker.webservice.model.CreditCardValidationResult;
import com.creditcard.checker.webservice.service.CreditCardValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("creditcards")
public class CreditCardCheckerController {

    @Autowired
    private CreditCardValidationService creditCardValidationService;

    @PostMapping(path="/validate")
    public CreditCardValidationResult validateCreditCard(@RequestBody CreditCardValidationRequest creditCard) {

        CreditCardValidationResult result = new CreditCardValidationResult();
        result.setCardNumber(creditCard.getCardNumber());

        String trimmedCardNumber = StringUtils.trimAllWhitespace(creditCard.getCardNumber());

        CardType cardType = creditCardValidationService.determineCardType(trimmedCardNumber);
        result.setCardType(cardType);

        if (CardType.Unknown.equals(cardType)) {
            result.setValid(Boolean.FALSE);
        } else {
            boolean valid = creditCardValidationService.validateByLuhnAlgorithm(trimmedCardNumber);
            result.setValid(valid);
        }

        return result;
    }
}
