package com.example.pemanasan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HasilKuis extends AppCompatActivity {

    private TextView tvNilai, tvMassage, tvhasil, tvDetailJawaban;
    private Button btnUlangi;
    private Button btnShare;
    ArrayList<String> jawabanBenarList;
    ArrayList<String> jawabanUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hasil_kuis);

        tvNilai = findViewById(R.id.tvNilai);
        tvMassage = findViewById(R.id.tvMassage);
        btnUlangi = findViewById(R.id.btnUlangi);
        tvhasil = findViewById(R.id.tvhasil);
        btnShare = findViewById(R.id.btnShare);
        tvDetailJawaban = findViewById(R.id.tvDetailJawaban);

        int nilai = getIntent().getExtras().getInt("nilai");
        int benar = getIntent().getExtras().getInt("benar");
        int salah = getIntent().getExtras().getInt("salah");

        tvMassage.setText("Jawaban Benar: " + benar + " , " + "Jawaban Salah: " + salah);
        tvNilai.setText(String.valueOf(nilai));

        if (nilai == 100) {
            tvhasil.setText("Kamu Mendapatkan Nilai A, Kamu Lulus!!");
        } else if (nilai >= 80) {
            tvhasil.setText("Kamu Mendapatkan Nilai B, Kamu Lulus!!");
        } else if (nilai >= 60) {
            tvhasil.setText("Kamu Mendapatkan Nilai C, Coba Lagi!!");
        } else if (nilai >= 40) {
            tvhasil.setText("Kamu Mendapatkan Nilai D, Coba Lagi!!");
        } else if (nilai >= 20) {
            tvhasil.setText("Kamu Mendapatkan Nilai E, Coba Lagi!!");
        } else if (nilai == 0) {
            tvhasil.setText("Kamu Mendapatkan Nilai F, Coba Lagi!!");
        }

        jawabanBenarList = getIntent().getStringArrayListExtra("jawabanBenarList");
        jawabanUserList = getIntent().getStringArrayListExtra("jawabanUserList");

        TextView tvDetailJawaban = findViewById(R.id.tvDetailJawaban);

        StringBuilder detail = new StringBuilder();
        for (int i = 0; i < jawabanBenarList.size(); i++) {
            detail.append("Soal ").append(i + 1).append(":\n")
                    .append("Jawaban Benar: ").append(jawabanBenarList.get(i)).append("\n")
                    .append("Jawaban Anda: ").append(jawabanUserList.get(i)).append("\n\n");
        }

        tvDetailJawaban.setText(detail.toString());


        btnUlangi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Halo, Saya Lulus Mengerjakan Quiz!. Ayo Tunjukkan Seberapa Luas Wawasanmu Tentang Luar Negeri!!");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Bagikan ke:"));
            }
        });
    }
}