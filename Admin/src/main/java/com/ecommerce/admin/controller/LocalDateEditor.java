package com.ecommerce.admin.controller;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateEditor extends PropertyEditorSupport {
    private final DateTimeFormatter formatter;

    public LocalDateEditor(String dateFormat) {
        formatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDate.parse(text, formatter));
    }
}
