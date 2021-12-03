package com.example.kamusbahasalampung.ui.istilah;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamusbahasalampung.R;


import java.util.ArrayList;
import java.util.List;

public class HomeAdapterIstilah extends
        RecyclerView.Adapter<HomeAdapterIstilah.ExampleViewHolder> implements Filterable {
    private List<DataItemIstilah> exampleList;
    private List<DataItemIstilah> exampleListFull;
    private Context context;
    private View root;
    Dialog customDialog;
    private FragmentManager mFragmentManager;
    EditText Bhs_indo_edit, Bhs_lamp_edit;
    Button ButtonEdit;
    ImageButton btnCancel;
    private Spinner spinner;
    private static final String[] paths = {"Pilih Salah Satu", "Dialek A", "Dialek O"};


    public HomeAdapterIstilah(FragmentManager fm) {
        mFragmentManager = fm;
    }

    @Override
    public Filter getFilter() {
        return examplefilter;
    }

    public HomeAdapterIstilah(Context c) {
        this.context = c;
    }

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView2, textView3, textView4;
        ImageButton btnHapus, btnEdit;
        String keyFB;

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
        holder.keyFB = currentItem.getKeyFB();

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                ManajemenIstilahFragment manajemenIstilahFragment = new ManajemenIstilahFragment();
                manajemenIstilahFragment.HapusData(currentItem.getText2());
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog = new Dialog(v.getContext());
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.dialog_edit);
                customDialog.setCancelable(true);
                ButtonEdit = customDialog.findViewById(R.id.buttonEdit);
                Bhs_indo_edit = customDialog.findViewById(R.id.editTextBahasaIndoEdit);
                Bhs_lamp_edit = customDialog.findViewById(R.id.editTextBahasaLampungEdit);
                btnCancel = customDialog.findViewById(R.id.btnCancel);
                spinner = customDialog.findViewById(R.id.spinnerEdit);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),
                        android.R.layout.simple_spinner_item, paths);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.getSelectedItem();
                String compareValue = currentItem.getText4();

                if (compareValue != null) {
                    int spinnerPosition = adapter.getPosition(compareValue);
                    spinner.setSelection(spinnerPosition);
                }


                Window window = customDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                customDialog.setCancelable(false);
                customDialog.setCanceledOnTouchOutside(false);
                Bhs_lamp_edit.setText(currentItem.getText2());
                Bhs_indo_edit.setText(currentItem.getText3());

                customDialog.show();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });

                ButtonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refresh();
                        ManajemenIstilahFragment manajemenIstilahFragment = new ManajemenIstilahFragment();
                        manajemenIstilahFragment.EditData(Bhs_lamp_edit.getText().toString(), Bhs_indo_edit.getText().toString(), spinner.getSelectedItem().toString(), currentItem.getKeyFB());

                        customDialog.dismiss();
                    }
                });
            }
        });
    }

    public void refresh(){
        exampleList.clear();
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    private Filter examplefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataItemIstilah> filterlist = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterlist.addAll(exampleListFull);
            } else {
                String pattrn = constraint.toString().toLowerCase().trim();
                for (DataItemIstilah item : exampleListFull) {
                    if (item.getText2().toLowerCase().contains(pattrn)) {
                        filterlist.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterlist;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
