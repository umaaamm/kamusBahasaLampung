package com.example.kamusbahasalampung.ui.edit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kamusbahasalampung.MainActivity;
import com.example.kamusbahasalampung.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static MainActivity mainActivity;
    EditText Bhs_indo_edit, Bhs_lamp_edit;
    Button ButtonEdit;
    private Spinner spinner;
    DatabaseReference fb;
    View root;
    private static final String[] paths = {"Pilih Salah Satu","Dialek A", "Dialek O"};

    public static EditFragment newInstance(MainActivity activity) {
        EditFragment chooseGoldSavingsFragment = new EditFragment();
        mainActivity = activity;
        return chooseGoldSavingsFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.dialog_edit, container, false);

        Bhs_indo_edit = root.findViewById(R.id.editTextBahasaIndoEdit);
        Bhs_lamp_edit = root.findViewById(R.id.editTextBahasaLampungEdit);
        ButtonEdit = root.findViewById(R.id.buttonEdit);

        fb = FirebaseDatabase.getInstance().getReference();
        spinner = (Spinner)root.findViewById(R.id.spinnerEdit);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        ButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.v("item wwkwk", (String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
