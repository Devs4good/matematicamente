package com.example.matematicamente.contact;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.matematicamente.R;
import com.example.matematicamente.config.Configuration;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    EditText emailBodyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        emailBodyText = findViewById(R.id.contactText);
    }

    public void onSendClick(View view) {
        // enviar email utilizando mailto
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", Configuration.EMAIL_TO, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, Configuration.EMAIL_SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBodyText.getText());
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
