package com.example.kamusbahasalampung.ui.add;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.kamusbahasalampung.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class AddFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    DatabaseReference fb;
    EditText Bhs_indo, Bhs_lamp;
    Button ButtonSimpan;
    private Spinner spinner;
    private static final String[] paths = {"Pilih Salah Satu","Dialek A", "Dialek O"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_add, container, false);

        Bhs_indo = root.findViewById(R.id.editTextBahasaIndo);
        Bhs_lamp = root.findViewById(R.id.editTextBahasaLampung);
        ButtonSimpan = root.findViewById(R.id.buttonLogin);

        fb = FirebaseDatabase.getInstance().getReference();
        spinner = (Spinner)root.findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        ButtonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanDataIstilah(v.getRootView());
            }
        });

        return root;
    }

    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }


    void SimpanDataIstilah(View view) {


        String bahasa_indonesia = Bhs_indo.getText().toString();
        String bahasa_lampung = Bhs_lamp.getText().toString();
        String dialek = spinner.getSelectedItem().toString();
        HashMap<String, String> data = new HashMap<>();
        data.put("bahasa_indonesia", bahasa_indonesia);
        data.put("bahasa_lampung", bahasa_lampung);
        data.put("dialek", dialek);

        if (bahasa_indonesia.isEmpty()){
            Toast.makeText(getActivity(), "Istilah Bahasa Indonesia tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (bahasa_lampung.isEmpty()){
            Toast.makeText(getActivity(), "Istilah Bahasa Lampung tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dialek.equals("Pilih Salah Satu") || dialek.isEmpty()){
            Toast.makeText(getActivity(), "Pilih Salah satu dialek A/O!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Sedang menyimpan data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        fb.child("Data").child(getRandomString(32)).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "Data Berhasil disimpan!", Toast.LENGTH_SHORT).show();
                progress.dismiss();
                getFragmentManager().popBackStack();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progress.dismiss();
                Toast.makeText(getActivity(), "Gagal Disimpan!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.v("item wwkwk", (String) parent.getItemAtPosition(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
