package com.example.contactapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>  {
    private ArrayList<Contact> contactList;
    private ArrayList<Contact> contactListOld;
    private Context mContext;

    //    public ContactsAdapter(List<Contact> contactList) {
//        this.contactList = contactList;
//    }
    public ContactsAdapter(Context context, ArrayList<Contact> contactList) {
        this.mContext = context;
        this.contactList = contactList;
        this.contactListOld = contactList;
    }
//    public void setData(List<Contact> a) {
//        this.contactList = a;
//        this.contactListOld = a;
//        notifyDataSetChanged();
//
//    }

    @NonNull
    @Override
    //ViewHolder nắm giữ giao diện và cách render data của mỗi row
    //Recycle View có rất nhiều row (row_item), row_item chính là ViewHolder => class giúp mình render giao diện
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Contact contact = contactList.get(position);
        holder.tvName.setText(contact.getFullname());
        Bitmap bitmap = DataConvert.convertByteArrayToImage(contact.getAvatar());
        if (bitmap != null) {
            holder.ivAvatar.setImageBitmap(bitmap);
            holder.tvAvatar.setVisibility(View.INVISIBLE);
        } else {
            Drawable drawable = holder.tvAvatar.getBackground();

            //drawable.setColorFilter(, PorterDuff.Mode.SRC_IN);
            holder.tvAvatar.setText(contact.getFirstName().substring(0,1));
            holder.tvAvatar.setBackground(drawable);
            holder.ivAvatar.setVisibility(View.INVISIBLE);

        }
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToDetail(contactList.get(position));
            }
        });
    }
    private void onClickGoToDetail(Contact contact) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contact", contact);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String strSearch = constraint.toString();
//                if (strSearch.isEmpty()) {
//                    contactList = contactListOld;
//                } else {
//                    List<Contact> list = new ArrayList<>();
//                    for (Contact contact : contactListOld) {
//                        if (contact.getFirstName().toLowerCase().contains(strSearch.toLowerCase())) {
//                            list.add(contact);
//                        }
//                    }
//                    contactList = list;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = contactList;
//                return filterResults;
//
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                contactList = (List<Contact>) results.values;
//                notifyDataSetChanged();
//
//            }
//
//        };
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvAvatar;
        public ImageView ivAvatar;
        public CardView layoutItem;

        @SuppressLint("ResourceType")

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvAvatar = (TextView) view.findViewById(R.id.tv_avatar);
            ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
            layoutItem = (CardView) view.findViewById(R.id.layout_item);
        }


    }
}
