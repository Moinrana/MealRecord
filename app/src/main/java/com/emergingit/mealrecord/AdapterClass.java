package com.emergingit.mealrecord;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {
    public List<Weeklydata> weeklydata;
    private User user;

    public AdapterClass(List<Weeklydata> testlists) {
        weeklydata = testlists;
    }

    public void setWeeklydata(List<Weeklydata> testlists) {
        weeklydata = testlists;
    }

    @NonNull
    @Override
    public AdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.ViewHolder holder, int position) {
//        String username = weeklydata.get(position).get();
//        String status = weeklydata.get(position).getStatus();
//        String address = weeklydata.get(position).getAddress();

        int totalMeal = weeklydata.get(position).getTotalMeal();
        int totalPrice = weeklydata.get(position).getTotalPrice();
        List<User> user = weeklydata.get(position).getUser();

        holder.setData(totalPrice, totalMeal, user.get(0));
    }

    @Override
    public int getItemCount() {
        if (weeklydata == null) {
            return 0;
        }
        return weeklydata.size();
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

        public void setData(int tp, int tm, User user) {
            tvUserName.setText(user.getUname());
            tvStatus.setText("Meal: " + String.valueOf(tm));
            tvAddress.setText("Amount: " + String.valueOf(tp));
        }
    }
}
