package ga.anggach.kontek;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ga.anggach.kontek.data.Contact;

import static ga.anggach.kontek.MainActivity.SHARED_PREFS;

/**
 * Created by master on 11/17/2017.
 */

public class AddContactActivity extends AppCompatActivity {

    private TextInputEditText name;
    private TextInputEditText phoneNumber;
    private TextInputEditText emailAddress;
    private AppCompatButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setUpViews();
    }



    protected void addContact(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String saved_contacts = preferences.getString(SHARED_PREFS,"");
        ArrayList<Contact> contacts = gson.fromJson(saved_contacts, new TypeToken<ArrayList<Contact>>() {}.getType());
        if(contacts == null){
            contacts = new ArrayList<>();
        }
        Contact newContact = new Contact(name.getText().toString(), phoneNumber.getText().toString(), emailAddress.getText().toString());
        contacts.add(newContact);

        String to_be_saved_contacts = gson.toJson(contacts);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SHARED_PREFS, to_be_saved_contacts);
        editor.commit();
    }

    protected boolean emptyInput(){
        boolean name = this.name.getText().toString().equalsIgnoreCase("");
        boolean email = this.emailAddress.getText().toString().equalsIgnoreCase("");
        boolean phone = this.phoneNumber.getText().toString().equalsIgnoreCase("");
        return name || email || phone;
    }

    protected void setUpViews(){
        name = (TextInputEditText) findViewById(R.id.text_input_name);
        phoneNumber = (TextInputEditText) findViewById(R.id.text_input_phone);
        emailAddress = (TextInputEditText) findViewById(R.id.text_input_email);
        addButton = (AppCompatButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emptyInput()) {
                    Toast.makeText(AddContactActivity.this, "INPUT CANNOT BE EMPTY!", Toast.LENGTH_SHORT).show();
                    return;
                }
                addContact();
                AlertDialog.Builder builder = new AlertDialog.Builder(AddContactActivity.this);
                builder.setTitle("CONTACT ADDED SUCCESSFULLY!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.show();
            }
        });
    }

}