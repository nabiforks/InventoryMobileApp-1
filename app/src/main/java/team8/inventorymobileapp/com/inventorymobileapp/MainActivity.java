package team8.inventorymobileapp.com.inventorymobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import team8.inventorymobileapp.com.inventorymobileapp.BusinessLogic.StoreSupervisor;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StoreSupervisor s = new StoreSupervisor();
    }
}
