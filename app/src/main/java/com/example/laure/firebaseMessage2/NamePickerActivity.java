package com.example.laure.firebaseMessage2;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

import static com.example.laure.firebaseMessage2.UserStorage.saveUserInfo;

/**
 * Created by Laure on 04/12/2017.
 *
 * Choisir un nom et email avant de valider
 */

public class NamePickerActivity extends AppCompatActivity {

    public static final String TAG = NamePickerActivity.class.getSimpleName();

    // MEMO

    // Scrollview autour linearLayout et layout height : wrap_content


    EditText	mNameEditText;
    EditText	mEmailEditText;
    Button     mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namepicker);

        Log.d(TAG, "Activity NamePicker created");

        mNameEditText = findViewById(R.id.editTextName);
        mEmailEditText = findViewById(R.id.editTextMail);
        mSubmitButton = findViewById(R.id.buttonGo);

        // peut etre try catch
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                mEmailEditText.setText(possibleEmail);
                break;
            }
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Anonyme
                if(saveUserData()){
                    gotToMainActivity();
                }
            }
        });

    }

    private void gotToMainActivity(){
        // Contexte qui ouvre, ouvre quelle activity
        Intent intent = new Intent(NamePickerActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean saveUserData(){
        saveUserInfo(getApplicationContext(), mNameEditText.getText().toString(), mEmailEditText.getText().toString());
        return true;
    }
}
