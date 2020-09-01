package com.example.maxprocesstest.ui.updateContact;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.maxprocesstest.R;

public class UpdateContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_activity);
        Intent intent = getIntent();
        if (savedInstanceState == null) {

            Bundle bundle = new Bundle();

            if (intent.hasExtra("ContactId")) {
                bundle.putLong("ContactId", intent.getExtras().getLong("ContactId"));
            }

            Log.d("Contato Detalhe", String.valueOf((bundle != null)));


            UpdateContactFragment fragment = UpdateContactFragment.newInstance();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contact_detail_container, fragment)
                    .commitNow();
        }
    }
}