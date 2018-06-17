package com.example.neera.nfcbanking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //This method logs the user in to the application
    public void fnUserLogin(View view) {

        TextView userName = (TextView) findViewById(R.id.userName);
        TextView password = (TextView) findViewById(R.id.password);

        // Authentication done here
        if (("user1".equals(userName.getText().toString()) && "password".equals(password.getText().toString())) ||
                "user2".equals(userName.getText().toString()) && "password".equals(password.getText().toString())){
            final Context context = this;
            Intent intent = new Intent(context, HomeActivity.class);
            startActivity(intent);
        } else {
            // Display error message if username or password wrong
            TextView errorMessage = (TextView) findViewById(R.id.errorMessage);
            errorMessage.setVisibility(View.VISIBLE);
        }
    }
}
