package com.example.kamusbahasalampung.ui.istilah;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamusbahasalampung.MainActivity;
import com.example.kamusbahasalampung.R;
import com.example.kamusbahasalampung.ui.add.AddFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManajemenIstilahFragment extends Fragment {
    View root;
    private HomeAdapterIstilah adapter;
    private List<DataItemIstilah> exampleList = new ArrayList<>();
    FragmentManager fm;
    private Dialog customDialog;

    DatabaseReference fb = FirebaseDatabase.getInstance().getReference().child("Data");

    private void filterData() {
        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Sedang Mengambil data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String bahasa_lampung = ds.child("bahasa_lampung").getValue().toString();
                    String bahasa_indonesia = ds.child("bahasa_indonesia").getValue().toString();
                    String dialek = ds.child("dialek").getValue().toString();
                    String key = ds.getKey().toString();
                    DataItemIstilah item = new DataItemIstilah(R.drawable.ic_baseline_book_24, bahasa_lampung, bahasa_indonesia, dialek, key);
                    exampleList.add(item);
                }

                RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycleView);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                adapter = new HomeAdapterIstilah(exampleList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                progress.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progress.dismiss();
            }
        });
    }


    public void EditData(String bhs_indo, String bhs_lampung, String dialek, String key) {

        if (bhs_indo.isEmpty()) {
            Toast.makeText(getActivity(), "Istilah Bahasa Indonesia tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (bhs_lampung.isEmpty()) {
            Toast.makeText(getActivity(), "Istilah Bahasa Lampung tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dialek.equals("Pilih Salah Satu") || dialek.isEmpty()) {
            Toast.makeText(getActivity(), "Pilih Salah satu dialek A/O!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Sedang mengupdate data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        Query applesQuery = fb.orderByKey().equalTo(key);
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        String key = datas.getKey();
                        fb.child(key).child("bahasa_lampung").setValue(bhs_lampung);
                        fb.child(key).child("bahasa_indonesia").setValue(bhs_indo);
                        fb.child(key).child("dialek").setValue(dialek);
                    }

                    progress.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progress.dismiss();
                Log.i("TAG", "onCancelled", databaseError.toException());
            }

        });

    }

    public void HapusData(String id) {
        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Sedang menghapus data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        Query applesQuery = fb.orderByChild("bahasa_lampung").equalTo(id);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }

                progress.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progress.dismiss();
                Log.i("TAG", "onCancelled", databaseError.toException());
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_manajemen_istilah, container, false);
        //floating button
        FloatingActionButton fab;
        fab = root.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFragment addFragment = new AddFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, addFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        filterData();
        SearchView searchView = (SearchView) root.findViewById(R.id.searchViewIstilah);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fm = getFragmentManager();
    }
}