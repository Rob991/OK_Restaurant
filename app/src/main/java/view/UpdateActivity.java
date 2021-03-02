package view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ok_restaurant.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import db.DBHelper;

public class UpdateActivity extends AppCompatActivity {

    EditText setCognome, setNumPersone, richiesta;
    TextView setData, setOrario;
    CardView salva;
    String id, cognomeStr, dataStr, orarioStr, numPersoneStr, richiestaStr;
    int minuto, ora;
    DBHelper myDB;

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_prenotazione);

        setCognome = findViewById(R.id.setCognome2);
        setNumPersone = findViewById(R.id.setNumPersone2);
        setData = findViewById(R.id.setData2);
        setOrario = findViewById(R.id.setOrario2);
        salva = findViewById(R.id.btnSalva2);
        richiesta = findViewById(R.id.setNota2);
        Calendar calendar = Calendar.getInstance();
        final int anno = calendar.get(Calendar.YEAR);
        final int mese = calendar.get(Calendar.MONTH);
        final int giorno = calendar.get(Calendar.DAY_OF_MONTH);

        getAndSetIntentData();

        setData.setOnClickListener(v -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActivity.this, (view, year, month, dayOfMonth) -> {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                setData.setText(date);
            }, anno, mese, giorno);
            datePickerDialog.show();
        });

        setOrario.setOnClickListener(v -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, (view, hourOfDay, minute) -> {

                ora = hourOfDay;
                minuto = minute;
                String time = ora + ":" + minuto;
                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                try {
                    Date date = f24Hours.parse(time);
                    setOrario.setText(f24Hours.format(date));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 24, 0, true
            );
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            timePickerDialog.updateTime(ora, minuto);
            timePickerDialog.show();
        });

        salva.setOnClickListener(v -> {

            myDB = new DBHelper(UpdateActivity.this);
            cognomeStr = setCognome.getText().toString();
            dataStr = setData.getText().toString();
            orarioStr = setOrario.getText().toString();
            numPersoneStr = setNumPersone.getText().toString();
            richiestaStr = richiesta.getText().toString();
            boolean result = myDB.updateData(id, cognomeStr, dataStr, orarioStr, numPersoneStr, richiestaStr);
            if (result) {
                toastMessage("Prenotazione modificata");
            } else {
                toastMessage("Errore");
            }
        });
    }

    private void getAndSetIntentData() {

        if (getIntent().hasExtra("ID") &&

                getIntent().hasExtra("Cognome") &&
                getIntent().hasExtra("Data") &&
                getIntent().hasExtra("Orario") &&
                getIntent().hasExtra("Numero di persone") &&
                getIntent().hasExtra("Richiesta")) {
            id = getIntent().getStringExtra("ID");
            cognomeStr = getIntent().getStringExtra("Cognome");
            dataStr = getIntent().getStringExtra("Data");
            orarioStr = getIntent().getStringExtra("Orario");
            numPersoneStr = getIntent().getStringExtra("Numero di persone");
            richiestaStr = getIntent().getStringExtra("Richiesta");

            setCognome.setText(cognomeStr);
            setData.setText(dataStr);
            setOrario.setText(orarioStr);
            setNumPersone.setText(numPersoneStr);
            richiesta.setText(richiestaStr);
        }
    }
}