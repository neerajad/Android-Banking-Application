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

/**
 * Control will be redirected to this page after the user sends account details and waits for the amount
 */
public class WaitingMoneyActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {

    int accBal = 1000;
    TextView currAccBal;
    TextView amtReceivedMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_money);

        currAccBal = (TextView) findViewById(R.id.currAccBal);
        amtReceivedMsg = (TextView) findViewById(R.id.detailsSentMsgId);
        currAccBal.setText("$" + accBal);
    }

    // This method receives the amount from the other user and display message and updates the current balance of the user
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
            int newBal = accBal + Integer.parseInt(msgReceived);
            amtReceivedMsg.setText("$" + msgReceived + " received successfully!!");
            currAccBal.setText("$" + newBal);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    // The methods onNdefPushComplete and createNdefMessage will be abstract methods in this class
    @Override
    public void onNdefPushComplete(NfcEvent event) {}

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }

    // This method takes the user to the home page on click of Home icon
    public void FnHome(View view) {
        final Context context = this;
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }
}
