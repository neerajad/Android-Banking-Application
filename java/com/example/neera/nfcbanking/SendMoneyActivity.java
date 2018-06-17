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
import android.widget.TextView;
import android.widget.Toast;

public class SendMoneyActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {
    TextView amountToSend;
    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        amountToSend = (TextView) findViewById(R.id.amountToSendId);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "nfcAdapter==null, no NFC adapter exists", Toast.LENGTH_LONG).show();
        } else {
            nfcAdapter.setNdefPushMessageCallback(this, this);
            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        onNewIntent(getIntent());
    }

    // This method takes the user to the home page on click of Home icon
    public void FnHome(View view) {
        final Context context = this;
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String action = intent.getAction();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage inNdefMessage = (NdefMessage)parcelables[0];
            NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
            NdefRecord NdefRecord_0 = inNdefRecords[0];
            String msgReceived = new String(NdefRecord_0.getPayload());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        final String eventString = "Amount sent successfully";
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), eventString, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String stringOut = amountToSend.getText().toString();
        byte[] bytesOut = stringOut.getBytes();

        NdefRecord appRecord = NdefRecord.createApplicationRecord("com.example.neera.nfcbanking");
        NdefRecord ndefRecordOut = NdefRecord.createMime("text/html",bytesOut);
        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut);
        return ndefMessageout;
    }
}
