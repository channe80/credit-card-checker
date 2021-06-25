package com.creditcard.checker.webservice.service;

import com.creditcard.checker.webservice.model.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class CreditCardValidationServiceImplTest {

    CreditCardValidationService cardValidationService = new CreditCardValidationServiceImpl();

    @BeforeEach
    void setup() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        //call post-constructor
        Method postConstruct =  CreditCardValidationServiceImpl.class.getDeclaredMethod("onBoot");
        postConstruct.setAccessible(true);
        postConstruct.invoke(cardValidationService);
    }

    @Test
    void getCardType_emptyNullUnknowValues() {
        Assertions.assertEquals(CardType.Unknown, cardValidationService.determineCardType(null));
        Assertions.assertEquals(CardType.Unknown, cardValidationService.determineCardType(""));
        Assertions.assertEquals(CardType.Unknown, cardValidationService.determineCardType("  "));
        Assertions.assertEquals(CardType.Unknown, cardValidationService.determineCardType("9111111111111111"));
    }

    @Test
    void getCardType_validAmexFormat() {
        //valid amex format
        String amexCardNumber34 = "348282246310005";
        Assertions.assertEquals(CardType.AMEX, cardValidationService.determineCardType(amexCardNumber34));

        String amexCardNumber37 = "378282246310005";
        Assertions.assertEquals(CardType.AMEX, cardValidationService.determineCardType(amexCardNumber37));

        //non amex format - does not start with 34 / 37
        String doesNotStartwith34 = "448282246310005";
        Assertions.assertNotEquals(CardType.AMEX, cardValidationService.determineCardType(doesNotStartwith34));

        String doesNotStartwith34b = "358282246310005";
        Assertions.assertNotEquals(CardType.AMEX, cardValidationService.determineCardType(doesNotStartwith34b));

        //non amex format - length < 16
        String lengthLessThan13 = "3411111111111";
        Assertions.assertNotEquals(CardType.AMEX, cardValidationService.determineCardType(lengthLessThan13));

        //non amex format - length > 16
        String lengthMoreThan16 = "3411111111111122222";
        Assertions.assertNotEquals(CardType.AMEX, cardValidationService.determineCardType(lengthMoreThan16));

        //contains non-numeric
        String hasNonNumeric = "34abc1111111111";
        Assertions.assertEquals(CardType.Unknown, cardValidationService.determineCardType(hasNonNumeric));
    }

    @Test
    void getCardType_validDiscoverFormat() {
        //valid discover format
        String amexCardNumber = "6011822463100050";
        Assertions.assertEquals(CardType.Discover, cardValidationService.determineCardType(amexCardNumber));

        //non discover format - does not start with 6011
        String doesNotStartwith6011 = "7011822463100050";
        Assertions.assertNotEquals(CardType.Discover, cardValidationService.determineCardType(doesNotStartwith6011));

        //non discover format - length < 16
        String lengthLessThan13 = "6011111111111";
        Assertions.assertNotEquals(CardType.Discover, cardValidationService.determineCardType(lengthLessThan13));

        //non visa format - length > 16
        String lengthMoreThan16 = "6011111111111122222";
        Assertions.assertNotEquals(CardType.Discover, cardValidationService.determineCardType(lengthMoreThan16));

        //contains non-numeric
        String hasNonNumeric = "6011abc1111111111";
        Assertions.assertEquals(CardType.Unknown, cardValidationService.determineCardType(hasNonNumeric));
    }

    @Test
    void getCardType_validMasterCardFormat() {
        //valid mastercard format
        String masterCardNumber51 = "5105105105105100";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.determineCardType(masterCardNumber51));

        String masterCardNumber52 = "5205105105105100";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.determineCardType(masterCardNumber52));

        String masterCardNumber53 = "5305105105105100";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.determineCardType(masterCardNumber53));

        String masterCardNumber54 = "5405105105105100";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.determineCardType(masterCardNumber54));

        String masterCardNumber55 = "5505105105105100";
        Assertions.assertEquals(CardType.MasterCard, cardValidationService.determineCardType(masterCardNumber55));

        //non mastercard format - does not start with 51-55
        String doesNotStartwith51_55 = "6505105105105100";
        Assertions.assertNotEquals(CardType.MasterCard, cardValidationService.determineCardType(doesNotStartwith51_55));

        String doesNotStartwith51_55b = "5605105105105100";
        Assertions.assertNotEquals(CardType.MasterCard, cardValidationService.determineCardType(doesNotStartwith51_55b));

        //non mastercard format - length < 16
        String lengthLessThan13 = "55051051051051";
        Assertions.assertNotEquals(CardType.MasterCard, cardValidationService.determineCardType(lengthLessThan13));

        //non mastercard format - length > 16
        String lengthMoreThan16 = "550510510510510022";
        Assertions.assertNotEquals(CardType.AMEX, cardValidationService.determineCardType(lengthMoreThan16));

        //contains non-numeric
        String hasNonNumeric = "5505abc105105100";
        Assertions.assertEquals(CardType.Unknown, cardValidationService.determineCardType(hasNonNumeric));
    }

    @Test
    void getCardType_validVisaFormat() {
        //valid visa format
        String visaCardNumber13 = "4012888888881881";
        Assertions.assertEquals(CardType.Visa, cardValidationService.determineCardType(visaCardNumber13));

        String visaCardNumber16 = "4111111111111";
        Assertions.assertEquals(CardType.Visa, cardValidationService.determineCardType(visaCardNumber16));

        //non visa format - does not start with 4
        String doesNotStartwith4 = "5012888888881881";
        Assertions.assertNotEquals(CardType.Visa, cardValidationService.determineCardType(doesNotStartwith4));
        //non visa format - length < 13
        String lengthLessThan13 = "411111111111";
        Assertions.assertNotEquals(CardType.Visa, cardValidationService.determineCardType(lengthLessThan13));
        //non visa format - 13 > length < 16
        String lengthIs15 = "411111111111122";
        Assertions.assertNotEquals(CardType.Visa, cardValidationService.determineCardType(lengthIs15));
        //non visa format - length > 16
        String lengthMoreThan16 = "411111111111122222";
        Assertions.assertNotEquals(CardType.Visa, cardValidationService.determineCardType(lengthMoreThan16));

        //contains non-numeric
        String hasNonNumeric = "4ab1111111111";
        Assertions.assertEquals(CardType.Unknown, cardValidationService.determineCardType(hasNonNumeric));
    }

    @Test
    void validateByLuhnAlgorithm() {
        //valid numbers
        String validNumber = "5105105105105100";
        Assertions.assertTrue(cardValidationService.validateByLuhnAlgorithm(validNumber));

        validNumber = "4012888888881881";
        Assertions.assertTrue(cardValidationService.validateByLuhnAlgorithm(validNumber));

        validNumber = "378282246310005";
        Assertions.assertTrue(cardValidationService.validateByLuhnAlgorithm(validNumber));

        validNumber = "6011111111111117";
        Assertions.assertTrue(cardValidationService.validateByLuhnAlgorithm(validNumber));

        //invalid numbers
        String invalidNumber = "4111111111111";
        Assertions.assertFalse(cardValidationService.validateByLuhnAlgorithm(invalidNumber));

        invalidNumber = "5105105105105106";
        Assertions.assertFalse(cardValidationService.validateByLuhnAlgorithm(invalidNumber));

        invalidNumber = "9111111111111111";
        Assertions.assertFalse(cardValidationService.validateByLuhnAlgorithm(invalidNumber));
    }
}