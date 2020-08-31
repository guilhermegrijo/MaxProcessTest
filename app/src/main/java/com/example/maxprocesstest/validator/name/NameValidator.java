package com.example.maxprocesstest.validator.name;

import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.validator.Validator;

import java.util.Optional;

public class NameValidator implements Validator {
    @Override
    public void validate(Contact c) {
        Optional.of(c)
                .filter(contact -> contact.getName().length() >= 3)
                .orElseThrow(() -> new IllegalArgumentException("Nome Inválido"));
    }
}
