package com.support.johnpig.healthmanager;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder> {

    private List<UserData> mList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public UserDataAdapter(List<UserData> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view, onItemClickListener);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserData userData = mList.get(position);
        holder.dataOwner.setText(userData.account);
        holder.createdTime.setText(userData.createdTime);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dataOwner;
        TextView createdTime;

        public ViewHolder(final View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(itemView, getAdapterPosition());
                    }
                }
            });
            dataOwner = itemView.findViewById(R.id.dataOwner);
            createdTime = itemView.findViewById(R.id.createdTime);
        }
    }

    interface OnItemClickListener {
        void onClick(View view, int pos);
    }

}
