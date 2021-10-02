package com.example.tutorial_06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity3 extends AppCompatActivity {

    EditText et_Category;
    Button btn_addCategory, btn_displayCategory, btn_updateCategory, btn_deleteCategory;
    DatabaseReference dbSquadron;
    Category Cate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        et_Category = findViewById(R.id.et_subCategory);

        btn_addCategory = findViewById(R.id.btn_addSubcategory);
        btn_displayCategory = findViewById(R.id.btn_displaySub);
        btn_updateCategory = findViewById(R.id.btn_updateSub);
        btn_deleteCategory = findViewById(R.id.btn_deleteSub);

        Cate = new Category();

        btn_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSquadron = FirebaseDatabase.getInstance().getReference().child("Category");
                if (TextUtils.isEmpty(et_Category.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Input Category", Toast.LENGTH_SHORT).show();
                else {
                    Cate.setCategoryName(et_Category.getText().toString().trim());

                    dbSquadron.push().setValue(Cate);

                    Toast.makeText(getApplicationContext(), "Category added successfully", Toast.LENGTH_SHORT).show();
                    clearControls();
                }
            }
        });

        btn_displayCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Category").child("Cate");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            et_Category.setText(snapshot.child("categoryName").getValue().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "No category to display", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_updateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Category");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("SubC")) {
                            Cate.setCategoryName(et_Category.getText().toString().trim());

                            dbSquadron = FirebaseDatabase.getInstance().getReference().child("Category").child("Cate");
                            dbSquadron.setValue(Cate);

                            clearControls();

                            Toast.makeText(getApplicationContext(), "Category updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No category to update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_deleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Category");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("Cate")) {
                            dbSquadron = FirebaseDatabase.getInstance().getReference().child("Category").child("Cate");
                            dbSquadron.removeValue();

                            clearControls();

                            Toast.makeText(getApplicationContext(), "Category Deleted Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No category to Delete", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void clearControls() {
        et_Category.setText("");
    }
}