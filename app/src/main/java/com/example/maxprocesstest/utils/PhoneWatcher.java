package com.example.maxprocesstest.utils;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public abstract class PhoneWatcher {


    public static TextWatcher watcher(MutableLiveData<List<String>> mItemList, int position) {

        return new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }
                final String str = MaskEditUtil.unmask(charSequence.toString());
                ArrayList arrayList = (ArrayList) mItemList.getValue();
                int nextPosition = position + 1;
                if (str.length() >= 10) {
                    isUpdating = true;
                    arrayList.set(position, str);
                    arrayList.size();
                    mItemList.setValue(arrayList);
                } else if (str.length() < 1) {
                    if (arrayList.size() >= nextPosition + 1) {
                        arrayList.remove(nextPosition);
                        mItemList.setValue(arrayList);
                        isUpdating = true;
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    ;
}
