package com.emergingit.mealrecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {
    public List<TestClass> testLists;

    public AdapterClass(List<TestClass> testlists) {
        testLists = testlists;
    }

    @NonNull
    @Override
    public AdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.ViewHolder holder, int position) {
        String username = testLists.get(position).getName();
        String status = testLists.get(position).getStatus();
        String address = testLists.get(position).getAddress();

        holder.setData(username, status, address);
    }

    @Override
    public int getItemCount() {
        return testLists.size();
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

        public void setData(String username, String status, String address) {
            tvUserName.setText(username);
            tvStatus.setText(status);
            tvAddress.setText(address);
        }
    }
}
