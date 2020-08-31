package com.example.maxprocesstest.ui.contactDetail;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.maxprocesstest.R;
import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.Response;
import com.example.maxprocesstest.utils.MaskEditUtil;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.base.Optional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class ContactDetailFragment extends Fragment {

    private ContactDetailViewModel mViewModel;

    private View mView;
    private Calendar mMyCalendar = Calendar.getInstance();

    ItemAdapter mAdapter;

    DatePickerDialog.OnDateSetListener mDate;

    @Inject
    ContactDetailViewModelFactory factory;

    @BindView(R.id.birthday_editTxt)
    EditText etBirthday;

    @BindView(R.id.name_editTxt)
    TextInputLayout etName;

    @BindView(R.id.cpf_editTxt)
    TextInputLayout etCpf;

    @BindView(R.id.uf_editTxt)
    TextInputLayout etUf;

    @BindView(R.id.contact_detail_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.filled_exposed_dropdown)
    AutoCompleteTextView spnUf;

    @BindView(R.id.contact_list_recycler_view)
    RecyclerView recyclerView;

    public static ContactDetailFragment newInstance() {
        return new ContactDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.contact_detail_fragment, container, false);
        ButterKnife.bind(this, mView);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);
        final ActionBar ab = activity.getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        setupView();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(ContactDetailViewModel.class);
        mViewModel.response().observe(getViewLifecycleOwner(),this::processResponse);

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;

            case R.id.action_save:
                saveContact();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void processResponse(Response response) {
        switch (response.status) {
            case COMPLETED:

                break;

            case ERROR:
                Log.e("Save contact error", "processResponse: ", response.error);
                break;
        }

    }

    private void setupView(){
        etCpf.getEditText().addTextChangedListener(MaskEditUtil.mask(etCpf.getEditText(),MaskEditUtil.FORMAT_CPF));

        mDate = (view, year, monthOfYear, dayOfMonth) -> {
            mMyCalendar.set(Calendar.YEAR, year);
            mMyCalendar.set(Calendar.MONTH, monthOfYear);
            mMyCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.brazilian_states, android.R.layout.simple_dropdown_item_1line);
        spnUf.setAdapter(adapter);

        mAdapter = new ItemAdapter();
        mAdapter.setHasStableIds(true);
        recyclerView.setItemAnimator(null);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemList(new ArrayList<>(Arrays.asList("")));


    }

    @OnClick(R.id.birthday_editTxt)
    public void clickBirthdayEditTxt(){
        new DatePickerDialog(getContext(), mDate, mMyCalendar
                .get(Calendar.YEAR), mMyCalendar.get(Calendar.MONTH),
                mMyCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void updateLabel() {


        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

        etBirthday.setText(sdf.format(mMyCalendar.getTime()));
    }
    private void saveContact(){
        Contact contact = new Contact();
        contact.setName(etName.getEditText().getText().toString());
        contact.setUf(etUf.getEditText().getText().toString());
        contact.setCpf(etUf.getEditText().getText().toString());



        mViewModel.createNewContact(contact,mAdapter.getItemList());
    }

}