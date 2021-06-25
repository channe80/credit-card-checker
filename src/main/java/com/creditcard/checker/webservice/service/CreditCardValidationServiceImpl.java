package com.creditcard.checker.webservice.service;

import com.creditcard.checker.webservice.model.CardType;
import org.springframework.stereotype.Service;

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

    /**
     * Determine credit card type based on the card number
     * @param cardNumber Series of digits without space
     * @return CardType
     */
    @Override
    public CardType determineCardType(String cardNumber) {
        if (null == cardNumber || cardNumber.isEmpty() || cardNumber.isBlank()) {
            return CardType.Unknown;
        }

        if (isAmexCardFormat(cardNumber)) {
            return CardType.AMEX;
        }
        if (isDiscoverCardFormat(cardNumber)) {
            return CardType.Discover;
        }
        if (isMasterCardFormat(cardNumber)) {
            return CardType.MasterCard;
        }
        if (isVisaCardFormat(cardNumber)) {
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

    /**
     * Validate card number using Luhn Algorithm
     * @param cardNumber Series of digits without space
     * @return boolean
     */
    @Override
    public boolean validateByLuhnAlgorithm(String cardNumber) {
        int sum = 0;
        boolean doubleDigit = false;
        for (int i = cardNumber.length()-1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (doubleDigit) {
                sum = doubleAndToSum(sum, digit);
                doubleDigit = false;
            } else {
                //add to sum
                sum += digit;
                doubleDigit = true;
            }
        }
        int mod = sum % 10;

        return mod == 0;
    }

    private int doubleAndToSum(int currentSum, int digit) {
        digit = digit * 2;
        if (digit < 10) {
            currentSum += digit;
        } else {
            //if double digit - separate and add
            String digitStr = String.valueOf(digit);
            for (int j = 0; j < digitStr.length(); j++ ) {
                currentSum += Character.getNumericValue(digitStr.charAt(j));
            }
        }
        return currentSum;
    }
}
