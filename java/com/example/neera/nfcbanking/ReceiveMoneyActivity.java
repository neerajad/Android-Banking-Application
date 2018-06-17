package com.example.neera.nfcbanking;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This file takes care of sending user account details to the sender
 */
public class ReceiveMoneyActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {

    NfcAdapter nfcAdapter;
    EditText firstName;
    EditText lastName;
    EditText accNum;
    EditText mobileNum;
    EditText emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_money);

        firstName = (EditText)findViewById(R.id.receivefirstNameId);
        lastName = (EditText)findViewById(R.id.receivelastNameId);
        accNum = (EditText)findViewById(R.id.receiveaccountNumId);
        mobileNum = (EditText)findViewById(R.id.receivemobileNumId);
        emailId = (EditText)findViewById(R.id.receiveemailId);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "nfcAdapter==null, no NFC adapter exists", Toast.LENGTH_LONG).show();
        } else {
            nfcAdapter.setNdefPushMessageCallback(this, this);
            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        onNewIntent(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    // This methos is called after the account details are sent to the other user as an NDEF message
    @Override
    public void onNdefPushComplete(NfcEvent event) {
        final String eventString = "Account Details sent successfully";
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), eventString, Toast.LENGTH_LONG).show();
            }
        });
        final Context context = this;
        Intent intent = new Intent(context, WaitingMoneyActivity.class);
        startActivity(intent);
    }

    // This method converts the user account details as NDEF message and sends through NFC onnection
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String firstNameStr = firstName.getText().toString();
        String lastNameStr = lastName.getText().toString();
        String accNumStr = accNum.getText().toString();
        String mobileNumStr = mobileNum.getText().toString();
        String emailIdStr = emailId.getText().toString();

        String messageStr = "FirstName:" + firstNameStr + ",LastName:" + lastNameStr +
                ",AccNum:" + accNumStr + ",MobileNum:" + mobileNumStr + ",EmailId:" + emailIdStr;

        byte[] bytesOut = messageStr.getBytes();

        NdefRecord appRecord = NdefRecord.createApplicationRecord("com.example.neera.nfcbanking");
        NdefRecord ndefRecordOut = NdefRecord.createMime("text/plain",bytesOut);
        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut, appRecord);
        return ndefMessageout;
    }

    // This method takes the user to the home page on click of Home icon
    public void FnHome(View view) {
        final Context context = this;
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }
}
