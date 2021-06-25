package com.creditcard.checker.webservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreditCardValidationResult {

    String cardNumber;
    CardType cardType;
    Boolean valid;

    @JsonIgnore
    String cardNumberTrimmed;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getCardNumberTrimmed() {
        return cardNumberTrimmed;
    }

    public void setCardNumberTrimmed(String cardNumberTrimmed) {
        this.cardNumberTrimmed = cardNumberTrimmed;
    }
}
