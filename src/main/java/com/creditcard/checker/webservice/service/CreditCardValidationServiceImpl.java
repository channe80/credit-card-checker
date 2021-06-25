package com.creditcard.checker.webservice.service;

import com.creditcard.checker.webservice.model.CardType;
import com.creditcard.checker.webservice.model.CardTypePattern;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CreditCardValidationServiceImpl implements CreditCardValidationService {

    private static Map<CardType, Pattern> CARD_TYPE_PATTERNS = new HashMap<>();

    @PostConstruct
    public void onBoot() {
        try {
            File regexPatterns = new ClassPathResource("static/creditCardRegex.json").getFile();
            ObjectMapper mapper = new ObjectMapper();
            List<CardTypePattern> cardTypePatterns = mapper.readValue(regexPatterns, new TypeReference<>() {});
            for (CardTypePattern cardTypePattern : cardTypePatterns) {
                CARD_TYPE_PATTERNS.put(cardTypePattern.getCardType(), Pattern.compile(cardTypePattern.getRegex()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        for (CardType cardType : CARD_TYPE_PATTERNS.keySet()) {
            Matcher m = CARD_TYPE_PATTERNS.get(cardType).matcher(cardNumber);
            if (m.matches()) {
                return cardType;
            }
        }

        return CardType.Unknown;
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
