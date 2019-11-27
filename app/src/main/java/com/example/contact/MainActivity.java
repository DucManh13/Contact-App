package com.example.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    ListView lvContacts;
    FloatingActionButton btnAddContact;
    int index;

    ArrayList<Contact> contacts;
    CustomAdapter customAdapter;
    private MyDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearch=findViewById(R.id.edt_search);
        lvContacts=findViewById(R.id.lv_contacts);
        btnAddContact=findViewById(R.id.btn_add);

        contacts=new ArrayList<>();

        db=new MyDatabase(this);
        contacts=db.getAllContact();
        customAdapter=new CustomAdapter(
                this, R.layout.row_listview, contacts);
        lvContacts.setAdapter(customAdapter);

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index=i;
                Bundle bundle = new Bundle();
                bundle.putSerializable("contact3",contacts.get(i));
                Intent intent= new Intent(MainActivity.this,EditContactActivity.class);
                intent.putExtra("package3",bundle);
                startActivityForResult(intent,1);
            }
        });

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(MainActivity.this,AddContactActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = intent.getBundleExtra("package1");
                Contact contact = (Contact) bundle.getSerializable("contact1");
                db.updateContact(contact);
                contacts.set(index, contact);
                customAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK) {
                try {
                    Bundle bundle = intent.getBundleExtra("package2");
                    Contact contact = (Contact) bundle.getSerializable("contact2");
                    db.addContact(contact);
                    contacts.add(contact);
                    customAdapter.notifyDataSetChanged();
                } catch (NullPointerException e) {
                }
            }
        }
    }
}
