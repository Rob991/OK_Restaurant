package view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ok_restaurant.R;

import java.util.ArrayList;

import adapters.MyAdapter;
import db.DBHelper;

public class ListActivity extends AppCompatActivity {

    DBHelper myDB;
    RecyclerView recyclerView;
    ArrayList<String> id, cognome, data, orario, numPersone, richiesta;
    MyAdapter myAdapter;

    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        myDB = new DBHelper(this);
        id = new ArrayList<>();
        cognome = new ArrayList<>();
        data = new ArrayList<>();
        orario = new ArrayList<>();
        numPersone = new ArrayList<>();
        richiesta = new ArrayList<>();

        storeDataInArray();

        myAdapter = new MyAdapter(ListActivity.this, this, id, cognome, data, orario, numPersone, richiesta);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    private void storeDataInArray() {

        Cursor cursor = myDB.getData();
        if (cursor.getCount() == 0) {
            toastMessage("Nessuna prenotazione");
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                cognome.add(cursor.getString(1));
                data.add(cursor.getString(2));
                orario.add(cursor.getString(3));
                numPersone.add(cursor.getString(4));
                richiesta.add(cursor.getString(5));
            }
        }
    }
}