package com.example.kamusbahasalampung.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamusbahasalampung.MainActivity;
import com.example.kamusbahasalampung.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    View root;
    private HomeAdapter adapter;
    private List<DataItem> exampleList = new ArrayList<>();

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
                    String bahasa_lampung= ds.child("bahasa_lampung").getValue().toString();
                    String bahasa_indonesia= ds.child("bahasa_indonesia").getValue().toString();
                    String dialek= ds.child("dialek").getValue().toString();
                    String key = ds.getKey().toString();
                    DataItem item=new DataItem(R.drawable.ic_baseline_book_24,bahasa_lampung, bahasa_indonesia,dialek,key);
                    exampleList.add(item);
                }

                RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycleView);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                adapter = new HomeAdapter(exampleList);
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        filterData();
        SearchView searchView = (SearchView) root.findViewById(R.id.searchView);
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
}