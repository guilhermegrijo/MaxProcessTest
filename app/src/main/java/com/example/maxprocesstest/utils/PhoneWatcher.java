package com.example.maxprocesstest.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.List;

public abstract class PhoneWatcher {

    public static TextWatcher watcher(List<String> mItemList){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence,  int start, int before, int count) {
                if(count>)



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    };
}
