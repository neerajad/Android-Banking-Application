package com.example.neera.nfcbanking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    // This method calls the activity on click of send money button
    public void fnMakeTransferActivity(View view) {
        final Context context = this;
        Intent intent = new Intent(context, MakeTransferActivity.class);
        startActivity(intent);
    }

    // This method calls the activity on click of receive money button
    public void fnReceiveMoneyActivity(View view) {
        final Context context = this;
        Intent intent = new Intent(context, ReceiveMoneyActivity.class);
        startActivity(intent);
    }

    // This method performs the log out functionality
    public void fnLogout(View view) {
        this.finish();
        final Context context = this;
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}
