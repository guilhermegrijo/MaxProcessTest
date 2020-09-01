package com.example.maxprocesstest.validator.cpf;

import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.validator.Validator;

import java.util.Optional;
import java.util.regex.Pattern;

public class CpfUfRuleValidator implements Validator {

    public void validate(Contact c) {

        Optional.ofNullable(c)
                .filter(contact -> {
                    if (contact.getUf().equals("SP")) {
                        return Pattern.matches("(\\d{3}.?\\d{3}.?\\d{3}-?\\d{2})", contact.getCpf());
                    }
                    return true;
                })
                .orElseThrow(() -> new IllegalArgumentException("Cpf é obrigatório para contatos de SP"));


    }


}
