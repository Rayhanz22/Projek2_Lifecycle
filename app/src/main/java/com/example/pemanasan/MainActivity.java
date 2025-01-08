package com.example.pemanasan;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvSoal, tvTime;
    private Button btnSelanjutnya;
    private RadioGroup rgPilihan;
    private RadioButton rbA, rbB, rbC, rbD;
    private ImageView ivImage;
    private List<String> jawabanBenarList = new ArrayList<>();
    private List<String> jawabanUserList = new ArrayList<>();
    int nomor = 0;
    int score;
    int benar = 0, salah = 0;

    int gambar[] = new int[]{
            R.drawable.tokyo,
            R.drawable.madinah,
            R.drawable.washington_dc,
            R.drawable.madrid,
            R.drawable.jakarta
    };

    String Soal[] = new String[]{
            "1. Tempat Pada Gambar Di atas Terletak Di?",
            "2. Tempat Pada Gambar Di atas Terletak Di?",
            "3. Tempat Pada Gambar Di atas Terletak Di?",
            "4. Tempat Pada Gambar Di atas Terletak Di?",
            "5. Tempat Pada Gambar Di atas Terletak Di?"
    };

    String Option[] = new String[]{
            "Tokyo-Jepang", "Paris-Prancis", "Manila-Philipina", "Yogyakarta-Indonesia",
            "Mekkah-Arab Saudi", "Madinah-Arab Saudi", "Istiqlal-Indonesia", "Taj Mahal-India",
            "Barcelona-Spanyol", "Berlin-Jerman", "Lisbon-Portugal", "Washington DC-Amerika Serikat",
            "Madrid-Spanyol", "Lisbon-Portugal", "Roma-Italia", "Paris-Prancis",
            "Lisbon-Portugal", "Jakarta-Indonesia", "Beijing-China", "Kuala Lumpur-Malaysia"
    };

    String Jawaban[] = new String[]{
            "Tokyo-Jepang",
            "Madinah-Arab Saudi",
            "Washington DC-Amerika Serikat",
            "Madrid-Spanyol",
            "Jakarta-Indonesia"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSoal = findViewById(R.id.tvSoal);
        tvTime = findViewById(R.id.tvTime);
        btnSelanjutnya = findViewById(R.id.btnSelanjutnya);
        rgPilihan = findViewById(R.id.rgPilihan);
        rbA = findViewById(R.id.rbA);
        rbB = findViewById(R.id.rbB);
        rbC = findViewById(R.id.rbC);
        rbD = findViewById(R.id.rbD);
        ivImage = findViewById(R.id.ivImage);

        rgPilihan.check(0);

        tvSoal.setText(Soal[nomor]);
        rbA.setText(Option[0]);
        rbB.setText(Option[1]);
        rbC.setText(Option[2]);
        rbD.setText(Option[3]);

        new CountDownTimer(30000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {  //mengupdate waktu mundur setiap detik ke tvTime
                tvTime.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {  //saat waktu habis, langsung pindah ke halaman hasil kuis
                tvTime.setText("Waktu Habis!");
                score = benar * 20;
                Intent next = new Intent(getApplicationContext(), HasilKuis.class);
                next.putExtra("nilai", score);
                next.putExtra("benar", benar);
                next.putExtra("salah", salah);
                startActivity(next);
            }
        }.start();

        btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbA.isChecked() || rbB.isChecked() || rbC.isChecked() || rbD.isChecked()){

                    RadioButton Pilihan_User = findViewById(rgPilihan.getCheckedRadioButtonId());
                    String Jawaban_User = Pilihan_User.getText().toString();

                    jawabanBenarList.add(Jawaban[nomor]);
                    jawabanUserList.add(Jawaban_User);

                    rgPilihan.check(0);

                    if (Jawaban_User.equalsIgnoreCase(Jawaban[nomor])){
                        benar++;
                    } else{
                        salah++;
                    }
                    nomor++;
                    if (nomor < Soal.length){
                        tvSoal.setText(Soal[nomor]);
                        ivImage.setImageResource(gambar[nomor]);

                        rbA.setText(Option[(nomor * 4) +0]);
                        rbB.setText(Option[(nomor * 4) +1]);
                        rbC.setText(Option[(nomor * 4) +2]);
                        rbD.setText(Option[(nomor * 4) +3]);

                    } else{
                        score = benar * 20;
                        Intent next = new Intent(getApplicationContext(), HasilKuis.class);
                        next.putExtra("nilai", score);
                        next.putExtra("benar", benar);
                        next.putExtra("salah", salah);
                        next.putStringArrayListExtra("jawabanBenarList", (ArrayList<String>) jawabanBenarList);
                        next.putStringArrayListExtra("jawabanUserList", (ArrayList<String>) jawabanUserList);
                        startActivity(next);
                    }
                } else{
                    Toast.makeText(MainActivity.this, "Silahkan Pilih Jawaban Terlebih Dahulu!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}