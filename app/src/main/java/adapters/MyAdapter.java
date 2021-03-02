package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ok_restaurant.R;

import java.util.ArrayList;

import db.DBHelper;
import view.UpdateActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList id, cognome, data, orario, numPersone, richiesta;

    public void toastMessage(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public MyAdapter(Activity activity, Context context, ArrayList id, ArrayList cognome, ArrayList data, ArrayList orario, ArrayList numPersone, ArrayList richiesta) {

        this.activity = activity;
        this.context = context;
        this.id = id;
        this.cognome = cognome;
        this.data = data;
        this.orario = orario;
        this.numPersone = numPersone;
        this.richiesta = richiesta;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        holder.id.setText(String.valueOf(id.get(position)));
        holder.myText1.setText(String.valueOf(cognome.get(position)));
        holder.myText2.setText(String.format("%s %s", data.get(position), orario.get(position)));
        holder.myText3.setText(String.format("%s Persone", numPersone.get(position)));
        holder.listLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("ID", String.valueOf(id.get(position)));
            intent.putExtra("Cognome", String.valueOf(cognome.get(position)));
            intent.putExtra("Data", String.valueOf(data.get(position)));
            intent.putExtra("Orario", String.valueOf(orario.get(position)));
            intent.putExtra("Numero di persone", String.valueOf(numPersone.get(position)));
            intent.putExtra("Richiesta", String.valueOf(richiesta.get(position)));
            activity.startActivityForResult(intent, 1);
        });

        holder.listLayout.setOnLongClickListener(v -> {

            String posizione = (String) id.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(android.R.drawable.ic_delete);
            builder.setTitle("Cancellazione");
            builder.setMessage("Cancellare questa prenotazione?");
            builder.setCancelable(false);
            builder.setPositiveButton("Si", (dialog, which) -> {
                DBHelper myDB = new DBHelper(context);
                boolean result = myDB.deleteData(posizione);
                if (result) {
                    toastMessage("Prenotazione cancellata");
                } else {
                    toastMessage("Errore");
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {

        return cognome.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout listLayout;
        TextView id, myText1, myText2, myText3;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            id = itemView.findViewById(R.id.id);
            myText1 = itemView.findViewById(R.id.textView1);
            myText2 = itemView.findViewById(R.id.textView2);
            myText3 = itemView.findViewById(R.id.textView3);
            listLayout = itemView.findViewById(R.id.listLayout);
        }
    }
}