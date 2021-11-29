package com.example.kamusbahasalampung.ui.istilah;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamusbahasalampung.MainActivity;
import com.example.kamusbahasalampung.R;
import com.example.kamusbahasalampung.ui.add.AddFragment;

import com.example.kamusbahasalampung.ui.edit.*;
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
    private List<DataItemIstilah> exampleList=new ArrayList<>();

    DatabaseReference fb = FirebaseDatabase.getInstance().getReference().child("Data");

    private void filterData(){
        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String bahasa_lampung= ds.child("bahasa_lampung").getValue().toString();
                    String bahasa_indonesia= ds.child("bahasa_indonesia").getValue().toString();
                    String dialek= ds.child("dialek").getValue().toString();
                    DataItemIstilah item=new DataItemIstilah(R.drawable.ic_baseline_book_24,bahasa_lampung, bahasa_indonesia,dialek);
                    exampleList.add(item);
                }

                RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycleView);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                adapter = new HomeAdapterIstilah(exampleList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void GotoEdit(String bhs_indo, String bhs_lampung, String dialek){
        Bundle bundle = new Bundle();
        bundle.putString("bhs_indo", bhs_indo);
        bundle.putString("bhs_lampung", bhs_lampung);
        bundle.putString("dialek", dialek);
        EditFragment editFragment = new EditFragment();
        editFragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, editFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }


    public void HapusData(String id){
        Query applesQuery = fb.orderByChild("bahasa_lampung").equalTo(id);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("TAG", "onCancelled", databaseError.toException());
            }
        });    }

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
        SearchView searchView=(SearchView)root.findViewById(R.id.searchViewIstilah);
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