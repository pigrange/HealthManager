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

    public UserDataAdapter(List<UserData> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);

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

        public ViewHolder(View itemView) {
            super(itemView);
            dataOwner = itemView.findViewById(R.id.dataOwner);
            createdTime = itemView.findViewById(R.id.createdTime);
        }
    }
}
