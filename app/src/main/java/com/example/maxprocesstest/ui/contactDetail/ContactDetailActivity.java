package com.example.maxprocesstest.ui.contactDetail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.maxprocesstest.R;

public class ContactDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_activity);
        Intent intent = getIntent();
        if (savedInstanceState == null) {

            Bundle bundle = new Bundle();

            if(intent.hasExtra("contactId")) {
                bundle.putParcelable("ContactId", intent.getExtras().getParcelable("ContactId"));
            }

            Log.d("Contato Detalhe", String.valueOf((bundle != null)));


            ContactDetailFragment fragment = ContactDetailFragment.newInstance();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contact_detail_container, fragment)
                    .commitNow();
        }
    }
}