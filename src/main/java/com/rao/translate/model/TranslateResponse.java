package com.rao.translate.model;

public class TranslateResponse {
    private String translatedString;

    public String getTranslatedString() {
        return translatedString;
    }

    public TranslateResponse(final String translatedString) {
        this.translatedString = translatedString;
    }
}
