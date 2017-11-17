package ga.anggach.kontek;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import ga.anggach.kontek.data.Contact;

import static ga.anggach.kontek.MainActivity.SHARED_PREFS;

/**
 * Created by master on 11/17/2017.
 */

public class ContactDetailActivity extends AppCompatActivity {

    private TextView name;
    private TextView phoneNumber;
    private TextView emailAddress;
    private FloatingActionButton callButton;
    private FloatingActionButton smsButton;
    private FloatingActionButton deleteButton;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Gson gson = new Gson();
        Intent intent = getIntent();
        String extras = intent.getStringExtra("contact");
        index = intent.getIntExtra("index",0);
        Contact contact = gson.fromJson(extras, Contact.class);

        setUpViews();
        attachDataToViews(contact);
    }

    protected void attachDataToViews(Contact contact){
        name.setText(contact.getName());
        phoneNumber.setText("Phone : "+contact.getPhone());
        emailAddress.setText("Email : "+contact.getEmail());
        callButton = (FloatingActionButton) findViewById(R.id.call_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCall();
            }
        });
        smsButton = (FloatingActionButton) findViewById(R.id.sms_button);
        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSms();
            }
        });
        deleteButton = (FloatingActionButton) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactDetailActivity.this);
                builder.setTitle("ARE YOU SURE YOU WANT TO DELETE?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteContact();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }

    protected void deleteContact(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String saved_contacts = preferences.getString(SHARED_PREFS,"");
        ArrayList<Contact> contacts = gson.fromJson(saved_contacts, new TypeToken<ArrayList<Contact>>() {}.getType());

        contacts.remove(index);

        String to_be_saved_contacts = gson.toJson(contacts);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SHARED_PREFS, to_be_saved_contacts);
        editor.commit();

        AlertDialog.Builder builder = new AlertDialog.Builder(ContactDetailActivity.this);
        builder.setTitle("CONTACT DELETED SUCCESSFULLY!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    protected void doCall(){
        String number = phoneNumber.getText().toString();
        Uri call = Uri.parse("tel:" + number);
        Intent surf = new Intent(Intent.ACTION_DIAL, call);
        startActivity(surf);
    }

    protected void doSms(){
        String number = phoneNumber.getText().toString();
        Uri call = Uri.parse("smsto:" + number);
        Intent surf = new Intent(Intent.ACTION_SENDTO, call);
        startActivity(surf);
    }

    protected void setUpViews(){
        name = (TextView) findViewById(R.id.name);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
        emailAddress = (TextView) findViewById(R.id.email_address);
    }

}
