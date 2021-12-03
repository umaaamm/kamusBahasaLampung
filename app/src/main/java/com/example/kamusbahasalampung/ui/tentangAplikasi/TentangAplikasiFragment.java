package com.example.kamusbahasalampung.ui.tentangAplikasi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamusbahasalampung.MainActivity;
import com.example.kamusbahasalampung.R;
import com.example.kamusbahasalampung.ui.login.ActivityLogin;
import com.google.firebase.auth.FirebaseAuth;

public class TentangAplikasiFragment extends Fragment {

   private Button btnKeluar, btnSendMail;
   private ImageButton btnLogin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tentang_aplikasi, container, false);

        btnKeluar = (Button) root.findViewById(R.id.buttonKeluar);
        btnSendMail = (Button) root.findViewById(R.id.emailAdminBtn);
        btnLogin = (ImageButton) root.findViewById(R.id.imageButtonLoginTentang);
        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Selamat anda berhasil keluar dari aplikasi.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(getActivity(), ActivityLogin.class));
                }else {
                    Toast.makeText(getActivity(), "Anda Sudah Login.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail();
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            btnKeluar.setVisibility(View.GONE);
        }

        return root;
    }


    public void composeEmail() {
        Intent email= new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:databasekamus@gmail.com"));
        email.putExtra(Intent.EXTRA_SUBJECT, "Perubahan Atau Penambahan");
        email.putExtra(Intent.EXTRA_TEXT, "Silahkan isi pesan anda");
        startActivity(email);
    }
}