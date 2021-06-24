package com.creditcard.checker.webservice.service;

import com.creditcard.checker.webservice.model.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardValidationServiceImplTest {


    @Test
    void getCardType_emptyNullValues() {
        CreditCardValidationService cardValidationService = new CreditCardValidationServiceImpl();
        Assertions.assertEquals(CardType.Unknown, cardValidationService.getCardType(null));
        Assertions.assertEquals(CardType.Unknown, cardValidationService.getCardType(""));
        Assertions.assertEquals(CardType.Unknown, cardValidationService.getCardType("  "));
    }

    @Test
    void getCardType_validVisa() {
        CreditCardValidationService cardValidationService = new CreditCardValidationServiceImpl();

        //valid visa format
        String visaCardNumber13 = "4012888888881881";
        Assertions.assertEquals(CardType.Visa, cardValidationService.getCardType(visaCardNumber13));

        String visaCardNumber16 = "4111111111111";
        Assertions.assertEquals(CardType.Visa, cardValidationService.getCardType(visaCardNumber16));

        //non visa format - does not start with 4
        String doesNotStartwith4 = "5012888888881881";
        Assertions.assertNotEquals(CardType.Visa, cardValidationService.getCardType(doesNotStartwith4));
        //non visa format - length < 13
        String lengthLessThan13 = "411111111111";
        Assertions.assertNotEquals(CardType.Visa, cardValidationService.getCardType(lengthLessThan13));
        //non visa format - 13 > length < 16
        String lengthIs15 = "411111111111122";
        Assertions.assertNotEquals(CardType.Visa, cardValidationService.getCardType(lengthIs15));
        //non visa format - length 16
        String lengthMoreThan16 = "411111111111122222";
        Assertions.assertNotEquals(CardType.Visa, cardValidationService.getCardType(lengthMoreThan16));

        //contains non-numeric
        String hasNonNumeric = "4ab1111111111";
        Assertions.assertEquals(CardType.Unknown, cardValidationService.getCardType(hasNonNumeric));
    }

    @Test
    void isValidByLuhnAlgo() {
        //todo:
        Assertions.assertTrue(false);
    }
}