package com.example.maxprocesstest.validator.birthday;

import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.validator.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class BirthdayUfRule implements Validator {
    @Override
    public void validate(Contact c) {
        Optional.ofNullable(c)
                .filter(contact -> {
                    if (contact.getUf().equals("MG")) {
                        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                        int d1 = Integer.parseInt(formatter.format(contact.getBirthday()));
                        int d2 = Integer.parseInt(formatter.format(new Date()));
                        int age = (d2 - d1) / 10000;
                        return age >= 18;
                    }
                    return true;
                })
                .orElseThrow(() -> new IllegalArgumentException("Idade precisa ser maior que 17 anos para contatos de MG"));
    }
}
