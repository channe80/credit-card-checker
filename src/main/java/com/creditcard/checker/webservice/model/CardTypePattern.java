package com.creditcard.checker.webservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CardTypePattern {
    private CardType cardType;
    private String regex;
    @JsonIgnore
    private String description;

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
