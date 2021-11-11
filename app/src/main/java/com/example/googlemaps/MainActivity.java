package com.example.googlemaps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText editTextLocal;
    EditText editTextLatitude;
    EditText editTextLongitude;
    Button btnAdd;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextLocal = findViewById(R.id.editTextLocal);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        btnAdd = findViewById((R.id.btn_add));
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        loadSpinnerData();
    }
    private void loadSpinnerData() {
        DatabaseHandler db = new
                DatabaseHandler(getApplicationContext());
        List<LocalMaps> labels = db.getAllLabels();
        // Creating adapter for spinner
        ArrayAdapter<LocalMaps> dataAdapter = new
                ArrayAdapter<LocalMaps>(this, android.R.layout.simple_spinner_item,
                labels);
        // Drop down layout style - list view with radio button

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    public void addLocal(View view) {

        LocalMaps localMaps = new LocalMaps(editTextLocal.getText().toString(),
                Double.parseDouble(editTextLatitude.getText().toString()),
                Double.parseDouble(editTextLongitude.getText().toString()));
        if (localMaps.getLocal().trim().length() > 0) {
            DatabaseHandler db = new
                    DatabaseHandler(getApplicationContext());
            db.insertLabel(localMaps);
            // making input filed text to blank
            editTextLocal.setText("");
            editTextLatitude.setText("");
            editTextLongitude.setText("");
            // Hiding the keyboard
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editTextLocal.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(editTextLatitude.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(editTextLongitude.getWindowToken(), 0);
            // loading spinner with newly added data
            loadSpinnerData();
        } else {
            Toast.makeText(getApplicationContext(), "Please enter label name",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void goMaps(View view) {
        editTextLocal = findViewById(R.id.editTextLocal);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);

        LocalMaps localMaps = new LocalMaps("Finland", 61.99110244210922, 26.27076298108069);


        if (editTextLocal.getText().toString().isEmpty()) {
            editTextLocal.setText(localMaps.getLocal());
        } else {
            localMaps.setLocal(editTextLocal.getText().toString());
        }

        if (editTextLatitude.getText().toString().isEmpty()) {
            editTextLatitude.setText(localMaps.getLatitude().toString());
        } else {
            localMaps.setLatitude(Double.parseDouble(editTextLatitude.getText().toString()));
        }

        if (editTextLongitude.getText().toString().isEmpty()) {
            editTextLongitude.setText(localMaps.getLongitude().toString());
        } else {
            localMaps.setLongitude(Double.parseDouble(editTextLongitude.getText().toString()));
        }


        Bundle bundle = new Bundle();
        bundle.putString("local", localMaps.getLocal());
        bundle.putDouble("latitude", localMaps.getLatitude());
        bundle.putDouble("longitude", localMaps.getLongitude());

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

        editTextLocal.setText("");
        editTextLatitude.setText("");
        editTextLongitude.setText("");


    }


    public void exit(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        LocalMaps localMaps = (LocalMaps) parent.getItemAtPosition(position);

        editTextLocal.setText(localMaps.getLocal());
        editTextLatitude.setText(localMaps.getLatitude().toString());
        editTextLongitude.setText(localMaps.getLongitude().toString());
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + localMaps, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
// TODO Auto-generated method stub
    }
}