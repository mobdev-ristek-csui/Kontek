package ga.anggach.kontek;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import ga.anggach.kontek.data.Contact;
import ga.anggach.kontek.data.DataAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DataAdapter.OnItemClickListener {

    private ArrayList<Contact> contacts;
    public static final String SHARED_PREFS = "contacts";
    private DataAdapter adapter;
    private TextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String saved_contacts = preferences.getString(SHARED_PREFS,"");
        contacts = gson.fromJson(saved_contacts, new TypeToken<ArrayList<Contact>>() {}.getType());
        if(contacts == null){
            contacts = new ArrayList<>();
        }

        adapter = new DataAdapter(contacts);
        Log.i("angga","contacts "+ adapter.getItemCount() );
        adapter.setOnItemClickListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        search = (EditText) findViewById(R.id.search_bar);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String saved_contacts = preferences.getString(SHARED_PREFS,"");
        contacts = gson.fromJson(saved_contacts, new TypeToken<ArrayList<Contact>>() {}.getType());
        adapter.setData(contacts);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddContactActivity.class);

        Gson gson = new Gson();
        String extra = gson.toJson(contacts);
        intent.putExtra("contact", extra);

        startActivity(intent);
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(this, ContactDetailActivity.class);
        Contact contact = adapter.getItem(position);
        Gson gson = new Gson();
        String extra = gson.toJson(contact);

        intent.putExtra("contact", extra);
        intent.putExtra("index", position);
        startActivity(intent);
    }
}
