package view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ok_restaurant.R;

public class MainActivity extends AppCompatActivity {

    ImageButton insPrenotazione, elencoPrenotazioni, ricette;
    Button info;

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insPrenotazione = findViewById(R.id.btnInsPrenotazione);
        elencoPrenotazioni = findViewById(R.id.btnElencoPrenot);
        ricette = findViewById(R.id.btnRicette);
        info = findViewById(R.id.btnInfo);
        MediaPlayer campanella = MediaPlayer.create(this, R.raw.campanella);
        MediaPlayer persone = MediaPlayer.create(this, R.raw.persone_ristorante);

        insPrenotazione.setOnClickListener(v -> {
            campanella.start();
            Intent intent = new Intent(MainActivity.this, AddPrenotazioneActivity.class);
            startActivity(intent);
        });

        elencoPrenotazioni.setOnClickListener(v -> {
            persone.start();
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
        });

        ricette.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.giallozafferano.it"));
            startActivity(browserIntent);
        });

        info.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("OK Restaurant");
            builder.setMessage("Realizzata da Roberto Conte\nTel:3664095028\nE-Mail: roberto.cnt@outlook.it");
            builder.show();
        });
    }
}