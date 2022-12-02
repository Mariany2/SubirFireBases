package com.example.subirfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainCrear extends AppCompatActivity {
    Button btn_add;
    EditText nombre;
    EditText edad;
    EditText color;
    private FirebaseFirestore mFirestore; // Agrega DePendencia Con.Google.firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_crear);

        this.setTitle("Crear Mascotas");  // Titulo De Banner
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFirestore = FirebaseFirestore.getInstance(); //Apunta a La Base De Datos

        //Genera Casting
        nombre = findViewById(R.id.addnombre);
        edad = findViewById(R.id.addedad);
        color = findViewById(R.id.addcolor);
        btn_add = findViewById(R.id.btn_Resgistrar);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view){
                String n = nombre.getText().toString();
                String e = edad.getText().toString();
                String c = color.getText().toString();

                if (n.isEmpty() && e.isEmpty() && c.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingresar Los Datos", Toast.LENGTH_SHORT).show();
                }else{
                    postPet(n, e, c);
                }
            }
        });
    }
    private void postPet(String n, String e, String c){
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre.getText().toString());
        map.put("edad", edad.getText().toString());
        map.put("color", color.getText().toString());

        mFirestore.collection("pet").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
            @Override
            public void onSuccess(DocumentReference documentReference ){
                Toast.makeText(getApplicationContext(),"Creado Exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error Al Ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onSupporNavigateUp(){ //Flecha Hacia Atrars
        onBackPressed();
        return false;
    }
}