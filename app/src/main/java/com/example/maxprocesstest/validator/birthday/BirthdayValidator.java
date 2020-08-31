package com.example.maxprocesstest.validator.birthday;

import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.validator.Validator;

import java.util.Optional;

public class BirthdayValidator implements Validator {
    @Override
    public void validate(Contact c) {
        Optional.of(c)
                .filter(contact -> contact.getBirthday() != null)
                .orElseThrow(() -> new IllegalArgumentException("Data Inválida"));
    }
}
