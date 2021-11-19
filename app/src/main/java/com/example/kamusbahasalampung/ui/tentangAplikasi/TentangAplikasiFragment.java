package com.example.kamusbahasalampung.ui.tentangAplikasi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamusbahasalampung.R;

public class TentangAplikasiFragment extends Fragment {

    private TentangAplikasiViewModel tentangAplikasiViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tentangAplikasiViewModel =
                new ViewModelProvider(this).get(TentangAplikasiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tentang_aplikasi, container, false);

        return root;
    }
}