package com.creditcard.checker.webservice.service;

import com.creditcard.checker.webservice.model.CardType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CreditCardValidationServiceImpl implements CreditCardValidationService {

    //AMEX Card begins with 34 or 37, number length of 15
    private static Pattern AMEX_PATTERN = Pattern.compile("^3[47][0-9]{13}$");
    //Discover Card begins with 6011, number length of 16
    private static Pattern DISCOVER_PATTERN = Pattern.compile("^6011[0-9]{12}$");
    //Mastercard begins with 51 to 55, number length of 16
    private static Pattern MASTERCARD_PATTERN = Pattern.compile("^5[1-5][0-9]{14}$");
    //Visa begins with 4, number length of 13 or 16
    private static Pattern VISA_PATTERN = Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$");

    @Override
    public CardType getCardType(String cardNumber) {
        if (null == cardNumber || cardNumber.isEmpty() || cardNumber.isBlank()) {
            return CardType.Unknown;
        }

        String cardNumberTrimmed = StringUtils.trimAllWhitespace(cardNumber);

        if (isAmexCardFormat(cardNumberTrimmed)) {
            return CardType.AMEX;
        }
        if (isDiscoverCardFormat(cardNumberTrimmed)) {
            return CardType.Discover;
        }
        if (isMasterCardFormat(cardNumberTrimmed)) {
            return CardType.MasterCard;
        }
        if (isVisaCardFormat(cardNumberTrimmed)) {
            return CardType.Visa;
        }

        return CardType.Unknown;
    }

    private boolean isAmexCardFormat(String cardNumber) {
        Matcher m = AMEX_PATTERN.matcher(cardNumber);
        return m.matches();
    }

    private boolean isDiscoverCardFormat(String cardNumber) {
        Matcher m = DISCOVER_PATTERN.matcher(cardNumber);
        return m.matches();
    }

    private boolean isMasterCardFormat(String cardNumber) {
        Matcher m = MASTERCARD_PATTERN.matcher(cardNumber);
        return m.matches();
    }

    private boolean isVisaCardFormat(String cardNumber) {
        Matcher m = VISA_PATTERN.matcher(cardNumber);
        return m.matches();
    }

    @Override
    public boolean isValidByLuhnAlgo() {

        return false;
    }

}
