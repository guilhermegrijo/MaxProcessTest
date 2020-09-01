package com.example.maxprocesstest.ui.contactList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maxprocesstest.R;
import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.model.Response;
import com.example.maxprocesstest.ui.createContact.CreateContactActivity;
import com.example.maxprocesstest.ui.updateContact.UpdateContactActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import dagger.android.support.AndroidSupportInjection;

public class ContactListFragment extends Fragment {

    private ContactListViewModel mViewModel;

    private View mView;

    @Inject
    ContactListViewModelFactory factory;

    @BindView(R.id.empty_state_frame)
    View placeholder;


    private ItemAdapter mAdapter;

    @BindView(R.id.contact_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.new_contact_btn)
    ExtendedFloatingActionButton new_contact_btn;

    @BindView(R.id.search_editText)
    EditText search_editText;


    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.contact_list_fragment, container, false);
        ButterKnife.bind(this, mView);
        setupView();
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        mViewModel = new ViewModelProvider(this, factory).get(ContactListViewModel.class);
        mViewModel.response().observe(getViewLifecycleOwner(), this::processResponse);


    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getAllContacts();
    }

    @OnClick(R.id.new_contact_btn)
    void new_contact_action() {
        Intent intent = new Intent(getActivity(), CreateContactActivity.class);
        startActivity(intent);
    }

    private void setupView() {
        mAdapter = new ItemAdapter();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setListener((contactId) -> {
            Intent intent = new Intent(getActivity(), UpdateContactActivity.class);
            intent.putExtra("ContactId", contactId);
            startActivity(intent);
        });
        search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();
                mViewModel.filterContacts(input);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnEditorAction(R.id.search_editText)
    protected boolean search(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String input = search_editText.getText().toString();
                mViewModel.filterContacts(input);
                keyboardDismiss();
                return true;
        }
        return false;
    }

    private void keyboardDismiss() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(mView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public void processResponse(Response<List<Contact>> response) {
        switch (response.status) {
            case SUCCESS:
                mAdapter.setItemList(response.data);
                placeholder.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                placeholder.setVisibility(View.GONE);
                break;
            case EMPTYQUERY:
                mAdapter.setItemList(response.data);
                break;
            case EMPTY:
                placeholder.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                break;
        }

    }
}