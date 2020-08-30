package com.example.maxprocesstest.ui.contactDetail;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.maxprocesstest.R;
import com.example.maxprocesstest.model.Contact;
import com.example.maxprocesstest.utils.MaskEditUtil;
import com.example.maxprocesstest.utils.PhoneWatcher;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItemList;
    private OnItemClickListener listener;


    void setItemList(final List<String> itemList) {
        mItemList = itemList;

        notifyDataSetChanged();

        Log.d("RECYCLERVIEW", "setData");
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
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Contact item, ImageView sharedImageView);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.phone_editText)
        TextInputLayout phone;
        @BindView(R.id.remove_phone)
        Button remove_phone;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void populateItemRows(ItemViewHolder viewHolder, int position) {
            viewHolder.phone.getEditText().addTextChangedListener(MaskEditUtil.mask(viewHolder.phone.getEditText(),MaskEditUtil.FORMAT_FONE));
            viewHolder.phone.getEditText().addTextChangedListener(PhoneWatcher.watcher(mItemList));
        }
    }
}