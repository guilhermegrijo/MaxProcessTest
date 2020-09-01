package com.example.maxprocesstest.ui.createContact;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maxprocesstest.R;
import com.example.maxprocesstest.model.Contact;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItemList;
    private OnItemClickListener listener;

    void setItemList(final List<String> itemList) {
        mItemList = itemList;
        Log.d("RECYCLERVIEW", "setData");
    }

    List<String> getItemList() {
        return mItemList;
    }


    void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).populateItemRows((ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Contact item, ImageView sharedImageView);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.phone_editText)
        TextInputLayout etPhone;

        @BindView(R.id.remove_phone_btn)
        Button btnremovePhone;

        @BindView(R.id.add_phone_btn)
        Button btnAddPhone;


        private int position;


        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.add_phone_btn)
        void addPhoneAction() {
            Log.d("Position", String.valueOf(position));
            String str = etPhone.getEditText().getText().toString().replaceAll("[^\\d]", "");
            if (str.length() >= 10) {
                notifyDataSetChanged();
                mItemList.add(position + 1, "");
                etPhone.setError("");
            } else {
                etPhone.setError("Primeiro insira um número corretamente aqui para adicionar outro");
            }


        }

        @OnClick(R.id.remove_phone_btn)
        void removePhoneAction() {
            Log.d("Position", String.valueOf(position));
            mItemList.remove(position);
            notifyDataSetChanged();
        }


        void populateItemRows(ItemViewHolder viewHolder, int position) {
            this.position = position;


            if ((position + 1) == mItemList.size()) {
                btnremovePhone.setVisibility(View.GONE);
                btnAddPhone.setVisibility(View.VISIBLE);
            } else {
                btnremovePhone.setVisibility(View.VISIBLE);
                btnAddPhone.setVisibility(View.GONE);
            }

            viewHolder.etPhone.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String str = editable.toString().replaceAll("[^\\d]", "");
                    if (str.length() >= 10) {
                        mItemList.set(viewHolder.getAdapterPosition(), str);
                        return;
                    }
                }
            });


            viewHolder.etPhone.getEditText().setText(mItemList.get(position));
        }
    }
}