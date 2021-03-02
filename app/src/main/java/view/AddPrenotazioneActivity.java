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

public class AddPrenotazioneActivity extends AppCompatActivity {

    EditText setCognome, setnumPersone, richiesta;
    TextView setData, setOrario;
    CardView salva;
    int minuto, ora;
    DBHelper myDB;

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_prenotazione);

        setCognome = findViewById(R.id.setCognome);
        setData = findViewById(R.id.setData);
        setOrario = findViewById(R.id.setOrario);
        salva = findViewById(R.id.btnSalva);
        setnumPersone = findViewById(R.id.setNumPersone);
        richiesta = findViewById(R.id.setNota);
        Calendar calendar = Calendar.getInstance();
        final int anno = calendar.get(Calendar.YEAR);
        final int mese = calendar.get(Calendar.MONTH);
        final int giorno = calendar.get(Calendar.DAY_OF_MONTH);
        myDB = new DBHelper(this);

        setData.setOnClickListener(v -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddPrenotazioneActivity.this, (view, year, month, dayOfMonth) -> {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                setData.setText(date);
            }, anno, mese, giorno);
            datePickerDialog.show();
        });

        setOrario.setOnClickListener(v -> {

            TimePickerDialog timePickerDialog = new TimePickerDialog(AddPrenotazioneActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, (view, hourOfDay, minute) -> {

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

            try {
                String cognome = setCognome.getText().toString().substring(0, 1).toUpperCase() + setCognome.getText().toString().substring(1, setCognome.length()).toLowerCase();
                String data = setData.getText().toString();
                String orario = setOrario.getText().toString();
                String numPersone2 = setnumPersone.getText().toString();
                String richiesta2 = richiesta.getText().toString();
                    boolean result = myDB.insertData(cognome, data, orario, numPersone2, richiesta2);
                    if(result){
                    setCognome.getText().clear();
                    setData.setText("");
                    setOrario.setText("");
                    setnumPersone.getText().clear();
                    toastMessage("Prenotazione effettuata");
                }
            }catch (Exception e) {
                toastMessage("Attributi mancanti");
            }
        });
    }
}