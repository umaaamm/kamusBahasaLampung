package com.example.kamusbahasalampung.ui.istilah;

import android.app.DownloadManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamusbahasalampung.R;
import com.example.kamusbahasalampung.ui.add.AddFragment;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapterIstilah extends
        RecyclerView.Adapter<HomeAdapterIstilah.ExampleViewHolder> implements  Filterable{
    private List<DataItemIstilah> exampleList;
    private List<DataItemIstilah> exampleListFull;
    private Context context;

    @Override
    public Filter getFilter() {
        return examplefilter;
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView2, textView3, textView4;
        ImageButton btnHapus, btnEdit;
        ExampleViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            textView2 = itemView.findViewById(R.id.hsl_lampung);
            textView3 = itemView.findViewById(R.id.hsl_indonesia);
            textView4 = itemView.findViewById(R.id.hsl_dialek);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            btnEdit = itemView.findViewById(R.id.btn_edit);
        }
    }

    HomeAdapterIstilah(List<DataItemIstilah> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item_istilah, parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        DataItemIstilah currentItem = exampleList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView2.setText(currentItem.getText2());
        holder.textView3.setText(currentItem.getText3());
        holder.textView4.setText(currentItem.getText4());

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManajemenIstilahFragment manajemenIstilahFragment = new ManajemenIstilahFragment();
                manajemenIstilahFragment.HapusData(currentItem.getText2());

                exampleList.clear();

            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManajemenIstilahFragment manajemenIstilahFragment = new ManajemenIstilahFragment();
                manajemenIstilahFragment.GotoEdit(currentItem.getText2(),currentItem.getText3(),currentItem.getText4());
            }
        });
    }
    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    private Filter examplefilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataItemIstilah> filterlist=new ArrayList<>();
            if(constraint==null|| constraint.length()==0){
                filterlist.addAll(exampleListFull);
            }
            else{
                String pattrn=constraint.toString().toLowerCase().trim();
                for(DataItemIstilah item :exampleListFull){
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
