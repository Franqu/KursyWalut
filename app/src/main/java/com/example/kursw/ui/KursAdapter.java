package com.example.kursw.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kursw.R;
import com.example.kursw.model.Kurs;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class KursAdapter extends RecyclerView.Adapter<KursAdapter.ViewHolder> {


    private ArrayList<Kurs> localDataSet;
    private boolean isBuy;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvSymbol;
        private final TextView tvValue;
        private final ImageView ivFlag;

        public ViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tvName);
            tvSymbol = view.findViewById(R.id.tvSymbol);
            tvValue = view.findViewById(R.id.tvValue);
            ivFlag = view.findViewById(R.id.ivFlag);
        }

        public TextView getTvName() {
            return tvName;
        }

        public TextView getTvSymbol() {
            return tvSymbol;
        }

        public TextView getTvValue() {
            return tvValue;
        }

        public ImageView getIvFlag() {
            return ivFlag;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     * @param isBuy   boolean representing if dataset should show sell value or buy value
     */
    public KursAdapter(ArrayList<Kurs> dataSet, boolean isBuy) {
        localDataSet = dataSet;
        this.isBuy = isBuy;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.kurs_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTvName().setText(localDataSet.get(position).getName());
        viewHolder.getTvSymbol().setText(localDataSet.get(position).getSymbol());
        if(isBuy)
            viewHolder.getTvValue().setText(String.valueOf(localDataSet.get(position).getValueBuy()));
        else
            viewHolder.getTvValue().setText(String.valueOf(localDataSet.get(position).getValueSell()));
        viewHolder.getIvFlag().setImageDrawable(localDataSet.get(position).getFlag());
    }
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}