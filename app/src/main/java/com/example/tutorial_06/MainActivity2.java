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

public class MainActivity2 extends AppCompatActivity {

    EditText et_category, et_subCategory;
    Button btn_addSubcategory, btn_displaySub, btn_updateSub, btn_deleteSub;
    DatabaseReference dbSquadron;
    Sub_Category SubC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        et_category = findViewById(R.id.et_category);
        et_subCategory = findViewById(R.id.et_subCategory);

        btn_addSubcategory = findViewById(R.id.btn_addSubcategory);
        btn_displaySub = findViewById(R.id.btn_displaySub);
        btn_updateSub = findViewById(R.id.btn_updateSub);
        btn_deleteSub = findViewById(R.id.btn_deleteSub);

        SubC = new Sub_Category();

        btn_addSubcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSquadron = FirebaseDatabase.getInstance().getReference().child("Sub_Category");
                    if (TextUtils.isEmpty(et_category.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Input Category", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(et_subCategory.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Input subcategory Name", Toast.LENGTH_SHORT).show();
                    else {
                        SubC.setCategory(et_category.getText().toString().trim());
                        SubC.setSubCategoryName(et_subCategory.getText().toString().trim());

                        dbSquadron.push().setValue(SubC);

                        Toast.makeText(getApplicationContext(), "Subcategory added successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                }
        });

        btn_displaySub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Sub_Category").child("SubC");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            et_category.setText(snapshot.child("category").getValue().toString());
                            et_subCategory.setText(snapshot.child("subCategoryName").getValue().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "No data to display", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_updateSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Sub_Category");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild("SubC")) {
                                SubC.setCategory(et_category.getText().toString().trim());
                                SubC.setSubCategoryName(et_subCategory.getText().toString().trim());

                                dbSquadron = FirebaseDatabase.getInstance().getReference().child("Sub_Category").child("SubC");
                                dbSquadron.setValue(SubC);

                                clearControls();

                                Toast.makeText(getApplicationContext(), "Subcategory updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No Subcategory to update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_deleteSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Sub_Category");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("SubC")) {
                            dbSquadron = FirebaseDatabase.getInstance().getReference().child("Sub_Category").child("SubC");
                            dbSquadron.removeValue();

                            clearControls();

                            Toast.makeText(getApplicationContext(), "Subcategory Deleted Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No Subcategory to Delete", Toast.LENGTH_SHORT).show();
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
        et_category.setText("");
        et_subCategory.setText("");
    }
}