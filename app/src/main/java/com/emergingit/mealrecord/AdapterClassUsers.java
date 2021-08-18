package com.emergingit.mealrecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterClassUsers extends RecyclerView.Adapter<AdapterClassUsers.ViewHolder> {

    List<User> users;

    public AdapterClassUsers(List<User> list) {
        users = list;
    }

    public void setUsersList(List<User> list) {
        users = list;
    }

    @NonNull
    @Override
    public AdapterClassUsers.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String username = users.get(position).getUname();
        String email = users.get(position).getEmail();
        holder.setData(username, email);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName;
        private TextView tvStatus;
        private TextView tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }

        public void setData(String username, String email) {
            tvUserName.setText(username);
            tvStatus.setText(email);
            tvAddress.setText("");
        }
    }
}
