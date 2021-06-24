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
    void getCardType_validAmexFormat() {
        CreditCardValidationService cardValidationService = new CreditCardValidationServiceImpl();

        //valid amex format
        String amexCardNumber34 = "348282246310005";
        Assertions.assertEquals(CardType.AMEX, cardValidationService.getCardType(amexCardNumber34));

        String amexCardNumber37 = "378282246310005";
        Assertions.assertEquals(CardType.AMEX, cardValidationService.getCardType(amexCardNumber37));

        //non amex format - does not start with 34 / 37
        String doesNotStartwith34 = "448282246310005";
        Assertions.assertNotEquals(CardType.AMEX, cardValidationService.getCardType(doesNotStartwith34));

        String doesNotStartwith34b = "358282246310005";
        Assertions.assertNotEquals(CardType.AMEX, cardValidationService.getCardType(doesNotStartwith34b));

        //non amex format - length < 16
        String lengthLessThan13 = "3411111111111";
        Assertions.assertNotEquals(CardType.AMEX, cardValidationService.getCardType(lengthLessThan13));

        //non amex format - length > 16
        String lengthMoreThan16 = "3411111111111122222";
        Assertions.assertNotEquals(CardType.AMEX, cardValidationService.getCardType(lengthMoreThan16));

        //contains non-numeric
        String hasNonNumeric = "34abc1111111111";
        Assertions.assertEquals(CardType.Unknown, cardValidationService.getCardType(hasNonNumeric));
    }

    @Test
    void getCardType_validDiscoverFormat() {
        CreditCardValidationService cardValidationService = new CreditCardValidationServiceImpl();

        //valid discover format
        String amexCardNumber = "6011822463100050";
        Assertions.assertEquals(CardType.Discover, cardValidationService.getCardType(amexCardNumber));

        //non discover format - does not start with 6011
        String doesNotStartwith6011 = "7011822463100050";
        Assertions.assertNotEquals(CardType.Discover, cardValidationService.getCardType(doesNotStartwith6011));

        //non discover format - length < 16
        String lengthLessThan13 = "6011111111111";
        Assertions.assertNotEquals(CardType.Discover, cardValidationService.getCardType(lengthLessThan13));

        //non visa format - length > 16
        String lengthMoreThan16 = "6011111111111122222";
        Assertions.assertNotEquals(CardType.Discover, cardValidationService.getCardType(lengthMoreThan16));

        //contains non-numeric
        String hasNonNumeric = "6011abc1111111111";
        Assertions.assertEquals(CardType.Unknown, cardValidationService.getCardType(hasNonNumeric));
    }

    @Test
    void getCardType_validMasterCardFormat() {
        CreditCardValidationService cardValidationService = new CreditCardValidationServiceImpl();

        //valid mastercard format
        String masterCardNumber51 = "5105105105105100";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.getCardType(masterCardNumber51));

        String masterCardNumber52 = "5205105105105100";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.getCardType(masterCardNumber52));

        String masterCardNumber53 = "5305105105105100";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.getCardType(masterCardNumber53));

        String masterCardNumber54 = "5405105105105100";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.getCardType(masterCardNumber54));

        String masterCardNumber55 = "5505105105105100";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.getCardType(masterCardNumber55));

        String masterCardNumber51WithSpace = "5105 1051 0510 5106";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.getCardType(masterCardNumber51WithSpace));

        //non mastercard format - does not start with 51-55
        String doesNotStartwith51_55 = "6505105105105100";
        Assertions.assertNotEquals(CardType.MasterCard, cardValidationService.getCardType(doesNotStartwith51_55));

        String doesNotStartwith51_55b = "5605105105105100";
        Assertions.assertNotEquals(CardType.MasterCard, cardValidationService.getCardType(doesNotStartwith51_55b));

        //non mastercard format - length < 16
        String lengthLessThan13 = "55051051051051";
        Assertions.assertNotEquals(CardType.MasterCard, cardValidationService.getCardType(lengthLessThan13));

        //non mastercard format - length > 16
        String lengthMoreThan16 = "550510510510510022";
        Assertions.assertNotEquals(CardType.AMEX, cardValidationService.getCardType(lengthMoreThan16));

        //contains non-numeric
        String hasNonNumeric = "5505abc105105100";
        Assertions.assertEquals(CardType.Unknown, cardValidationService.getCardType(hasNonNumeric));
    }

    @Test
    void getCardType_validVisaFormat() {
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
        //non visa format - length > 16
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