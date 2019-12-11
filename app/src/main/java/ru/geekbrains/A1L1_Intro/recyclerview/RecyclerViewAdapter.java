package ru.geekbrains.A1L1_Intro.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.geekbrains.A1L1_Intro.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<DataClass> data = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(ArrayList<DataClass> data) {
        if(data != null) {
            this.data = data;
        }
    }

    void addItem(DataClass dataClass) {
        data.add(1, dataClass);
        notifyItemInserted(1);
        //notifyDataSetChanged(); // - перерисует сразу весь список
    }

    void removeItem() {
        data.remove(0);
        notifyItemRemoved(0);
    }

    void moveBtn() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_temperatures, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.date.setText(data.get(position).date);
        holder.temperature.setText(data.get(position).temperature);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView temperature;

        ViewHolder(View view) {
            super(view);

            date = itemView.findViewById(R.id.dateTextView);
            temperature = itemView.findViewById(R.id.tempTextView);
        }
    }
}
