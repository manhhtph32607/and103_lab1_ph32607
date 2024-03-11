package com.manhhtph32607.myapp.ui.theme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;
import com.manhhtph32607.myapp.R;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
TextView tvKQ;
FirebaseFirestore database;
Context context=this;
ToDo toDo=null;
String strKQ="";
@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvKQ=findViewById(R.id.tvKQ);
        database=FirebaseFirestore.getInstance();
        insert();
    }
    void insert(){
     String id = UUID.randomUUID().toString();
     toDo =new ToDo(id,"title 12","content 12");
     database.collection("ToDo")
             .document(id)
             .set(toDo.toHashMap())
             .addOnSuccessListener(new OnSuccessListener<Void>() {
                 @Override
                 public void onSuccess(Void unused) {
                     Toast.makeText(context, "INSERT THANH CONG", Toast.LENGTH_SHORT).show();
                 }
             })
             .addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     Toast.makeText(context, "insert that bai", Toast.LENGTH_SHORT).show();
                 }
             });

    }
    void  update(){
    String id="";
    toDo= new ToDo(id,"title  update 12","content update 12");
    database.collection("ToDo")
            .document(id)
            .update(toDo.toHashMap())
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "UPDATE THANH CONG", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "UPDATE that bai", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void  delete(){
    String id="";
    database.collection("toDo")
            .document(id)
            .delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "xoa thang coong", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "XOA that bai", Toast.LENGTH_SHORT).show();
                }
            });
    }
    ArrayList<ToDo> select(){
    ArrayList<ToDo> list= new ArrayList<>();
    database.collection("ToDo")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        strKQ="";
                        for(QueryDocumentSnapshot  doc:task.getResult()){
                            ToDo t =doc.toObject(ToDo.class);
                            strKQ+= "id: "+t.getId()+"\n";
                            strKQ+="title: "+t.getTitle()+"\n";
                            strKQ+="content: "+t.getContent()+"\n";

                        }
                        tvKQ.setText(strKQ);
                    }else {
                        Toast.makeText(context, "select that bai", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    return  list;
    }
}