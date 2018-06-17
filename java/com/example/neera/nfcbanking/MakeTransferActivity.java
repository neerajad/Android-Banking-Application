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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity takes care of sending amount functionality
 */
public class MakeTransferActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback {

    NfcAdapter nfcAdapter;
    EditText firstName;
    EditText lastName;
    EditText accNum;
    EditText mobileNum;
    EditText emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_transfer);

        firstName = (EditText)findViewById(R.id.firstNameId);
        lastName = (EditText)findViewById(R.id.lastNameId);
        accNum = (EditText)findViewById(R.id.accountNumId);
        mobileNum = (EditText)findViewById(R.id.mobileNumId);
        emailId = (EditText)findViewById(R.id.emailId);

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

    // This method is called on click of Proceed button after populating user details
    public void proceedToSendMoney(View view) {
        final Context context = this;
        Intent intent = new Intent(context, SendMoneyActivity.class);
        startActivity(intent);
    }

    // This method is called after an NDEF message is received (account deatils of receiver) and populates user details fields
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

            // Split the received message and populate the fields accordingly
            String[] msgArr = msgReceived.split(",");
            for (int i = 0; i < msgArr.length; i++) {
                String[] detailsArr = msgArr[i].split(":");

                if ("FirstName".equals(detailsArr[0])) {
                    firstName.setText(detailsArr[1]);
                } else if ("LastName".equals(detailsArr[0])) {
                    lastName.setText(detailsArr[1]);
                } else if ("AccNum".equals(detailsArr[0])) {
                    accNum.setText(detailsArr[1]);
                } else if ("MobileNum".equals(detailsArr[0])) {
                    mobileNum.setText(detailsArr[1]);
                } else if ("EmailId".equals(detailsArr[0])) {
                    emailId.setText(detailsArr[1]);
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    // onNdefPushComplete and createNdefMessage will be abstract methods in this class
    @Override
    public void onNdefPushComplete(NfcEvent event) {}

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return null;
    }
}
