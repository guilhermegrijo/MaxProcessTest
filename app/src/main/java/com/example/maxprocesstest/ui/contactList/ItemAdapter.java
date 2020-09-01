package com.example.maxprocesstest.ui.contactList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.example.maxprocesstest.R;
import com.example.maxprocesstest.model.Contact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Contact> mItemList;
    private OnItemClickListener listener;


    void setItemList(final List<Contact> itemList) {
        mItemList = itemList;

        notifyDataSetChanged();

        Log.d("RECYCLERVIEW", "setData");
    }


    void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
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
        void onItemClick(Long contactId);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.contact_name)
        TextView name;
        @BindView(R.id.contact_uf)
        TextView uf;
        @BindView(R.id.avatarLetters)
        ImageView companyPhoto;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public int getPrevious(int position) {
            if (position <= 0) return 0;
            return position - 1;
        }

        void populateItemRows(ItemViewHolder viewHolder, int position) {
            Log.d("name", mItemList.get(position).getName());
            viewHolder.name.setText(mItemList.get(position).getName());
            viewHolder.uf.setText(mItemList.get(position).getUf());

            String initials = "";
            for (String s : mItemList.get(position).getName().split(" ")) {
                initials += s.charAt(0);
            }
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(mItemList.get(position).getName());

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(initials.substring(0, (initials.length() > 2 ? 2 : initials.length())), color);
            Glide.with(companyPhoto.getContext()).load("http://aaaaaaaaaaaaaa")
                    .placeholder(drawable)
                    .into(companyPhoto);

            itemView.setOnClickListener(v -> listener.onItemClick(mItemList.get(position).getContactId()));
        }
    }
}