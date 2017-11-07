package com.rao.translate.model;

import lombok.Data;

@Data
public class TranslateRequest {
    private String query;
    private String source;
    private String target;
}
