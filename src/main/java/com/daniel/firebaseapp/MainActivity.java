package com.daniel.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
  private FirebaseDatabase database;
  private DatabaseReference reference;
  private Button button;
  private EditText edit;
  private TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.database = FirebaseDatabase.getInstance();
        this.reference = database.getReference("donnees");
        // Read from the database
        this.reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                content.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                content.setText("impossible de se connecter à la base de données");
            }
        });



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.init_asset();
        this.event();
    }

    private void event(){
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Enregistrement sur Firebase", Toast.LENGTH_SHORT).show();

                setData(edit.getText().toString());
            }
        });

    }
    private void init_asset(){
       this.button = (Button)findViewById(R.id.save);
       this.edit = (EditText)findViewById(R.id.edit);
       this.content = (TextView)findViewById(R.id.content);
       this.content.setText("chargement en cours ...");
    }

    public  void setData(String data){
        // Write a message to the database
        this.reference.setValue(data);
    }
}