package com.creditcard.checker.webservice.service;

import com.creditcard.checker.webservice.model.CardType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CreditCardValidationServiceImpl  implements CreditCardValidationService {

    //Visa begins with 4, number length of 13 or 16
    private static Pattern visa = Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$");

    @Override
    public CardType getCardType(String cardNumber) {
        if (null == cardNumber || cardNumber.isEmpty() || cardNumber.isBlank()) {
            return CardType.Unknown;
        }
        //remove white spaces
        String cardNumberTrimmed = StringUtils.trimAllWhitespace(cardNumber);

        if (isVisaCardFormat(cardNumberTrimmed)) {
            return CardType.Visa;
        }
        return CardType.Unknown;
    }

    private boolean isVisaCardFormat(String cardNumber) {
        Matcher m = visa.matcher(cardNumber);
        return m.matches();
    }

    @Override
    public boolean isValidByLuhnAlgo() {

        return false;
    }

}
