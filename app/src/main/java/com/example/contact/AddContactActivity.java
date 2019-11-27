package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends AppCompatActivity {
    EditText edtName,edtMobile;
    Button btnCancel,btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        edtName=findViewById(R.id.edt_name);
        edtMobile=findViewById(R.id.edt_mobile);
        btnCancel=findViewById(R.id.btn_cancel);
        btnFinish=findViewById(R.id.btn_finish);



        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                Bundle bundle= new Bundle();
                String txtName=(edtName.getText().toString());
                String txtPhone=(edtMobile.getText().toString());
                Contact contact= new Contact(txtName,txtPhone);
                bundle.putSerializable("contact2",contact);
                intent.putExtra("package2",bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
