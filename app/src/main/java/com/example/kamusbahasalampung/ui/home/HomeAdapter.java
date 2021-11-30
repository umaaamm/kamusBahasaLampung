package com.example.kamusbahasalampung.ui.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.example.kamusbahasalampung.R;

import java.util.ArrayList;

public class HomeAdapter extends
        RecyclerView.Adapter<HomeAdapter.ExampleViewHolder> implements  Filterable{
    private List<DataItem> exampleList;
    private List<DataItem> exampleListFull;

    @Override
    public Filter getFilter() {
        return examplefilter;
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView2, textView3, textView4;
        String keyFB;
        ExampleViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            textView2 = itemView.findViewById(R.id.hsl_lampung);
            textView3 = itemView.findViewById(R.id.hsl_indonesia);
            textView4 = itemView.findViewById(R.id.hsl_dialek);
        }
    }

    HomeAdapter(List<DataItem> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        DataItem currentItem = exampleList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView2.setText(currentItem.getText2());
        holder.textView3.setText(currentItem.getText3());
        holder.textView4.setText(currentItem.getText4());
        holder.keyFB = currentItem.getKeyFB();
    }
    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    private Filter examplefilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataItem> filterlist=new ArrayList<>();
            if(constraint==null|| constraint.length()==0){
                filterlist.addAll(exampleListFull);
            }
            else{
                String pattrn=constraint.toString().toLowerCase().trim();
                for(DataItem item :exampleListFull){
                    if(item.getText2().toLowerCase().contains(pattrn)){
                        filterlist.add(item);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filterlist;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
