package com.example.maxprocesstest.ui.createContact;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
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
import android.widget.EditText;

import com.example.maxprocesstest.R;
import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.Response;
import com.example.maxprocesstest.validator.birthday.BirthdayUfRule;
import com.example.maxprocesstest.validator.birthday.BirthdayValidator;
import com.example.maxprocesstest.validator.cpf.CpfUfRuleValidator;
import com.example.maxprocesstest.validator.ValidatorComposite;
import com.example.maxprocesstest.validator.name.NameValidator;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class CreateContactFragment extends Fragment {

    private CreateContactViewModel mViewModel;

    private View mView;

    private Calendar mMyCalendar = Calendar.getInstance();

    private List<ValidatorComposite> validators;

    ItemAdapter mAdapter;

    DatePickerDialog.OnDateSetListener mDate;

    @Inject
    CreateContactViewModelFactory factory;

    @BindView(R.id.birthday_editTxt)
    EditText etBirthday;

    @BindView(R.id.birthday_Txt)
    TextInputLayout tlBirthday;

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



    public static CreateContactFragment newInstance() {
        return new CreateContactFragment();
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
        mViewModel = new ViewModelProvider(this, factory).get(CreateContactViewModel.class);
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
                getActivity().finish();
                break;
            case ERROR:
                Log.e("Save contact error", "processResponse: ", response.error);
                break;
        }

    }

    private void setupView(){
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

        setupViewValidation();


    }

    private void setupViewValidation(){

        validators = new ArrayList<>();

        ValidatorComposite cpfvalidation = new ValidatorComposite(Arrays.asList(new CpfUfRuleValidator()));
        ValidatorComposite nameValidation = new ValidatorComposite(Arrays.asList(new NameValidator()));
        ValidatorComposite birthdayValidation = new ValidatorComposite(Arrays.asList(new BirthdayValidator(),new BirthdayUfRule()));

        validators.add(nameValidation);
        validators.add(cpfvalidation);
        validators.add(birthdayValidation);
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
        contact.setCpf(etCpf.getEditText().getText().toString());
        contact.setUf(etUf.getEditText().getText().toString());
        try {
            contact.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse(etBirthday.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        View[] textInputList = new View[]{etName,etCpf,tlBirthday};

        for(int i = 0; i < textInputList.length; i++){
                try {
                    ((TextInputLayout) textInputList[i]).setError("");
                    validators.get(i).validate(contact);
                }
                catch(IllegalArgumentException e){
                    if(textInputList[i] instanceof TextInputLayout){
                        ((TextInputLayout) textInputList[i]).setError(e.getMessage());
                        return;
                }
            }
        }


        mViewModel.createNewContact(contact,mAdapter.getItemList());
    }
}