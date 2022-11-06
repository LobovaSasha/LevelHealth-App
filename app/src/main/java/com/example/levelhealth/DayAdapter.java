package com.example.levelhealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayAdapterViewHolder> {

    private Context context;
    private List<Day> days;

    public DayAdapter(Context context, List<Day> days) {
        this.context = context;
        this.days = days;
    }

    @NonNull
    @Override
    public DayAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View dayItems = LayoutInflater.from(context).inflate(R.layout.day_item, parent, false);
        return new DayAdapterViewHolder(dayItems);
    }

    @Override
    public void onBindViewHolder(@NonNull DayAdapterViewHolder holder, int position) {
        holder.weekDay.setText(days.get(position).getWeekDay());
        holder.mouthDay.setText(days.get(position).getMouthDay());

    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public static final class DayAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView weekDay;
        TextView mouthDay;

        public DayAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            weekDay = itemView.findViewById(R.id.weekDay);
            mouthDay = itemView.findViewById(R.id.mouthDay);

        }
    }

}







