package com.example.laure.firebaseMessage2;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import static com.example.laure.firebaseMessage2.UserStorage.isLogged;

public class MainActivity extends AppCompatActivity implements ValueEventListener {


    // Permet de console log
    public static final String TAG = MainActivity.class.getSimpleName();

    EditText       mInputEditText;
    ImageButton mSendButton;
    MessageAdapter mMessageAdapter;

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isLogged(this)){
            // S'il n'est pas log, lance l'activity namePicker
            Intent intent = new Intent(this, NamePickerActivity.class);
            startActivity(intent);
            //Quitte activity
            finish();
            return;
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mInputEditText = findViewById(R.id.inputEditText);
        mSendButton = findViewById(R.id.sendButton);

        mMessageAdapter = new MessageAdapter(new ArrayList<Message>());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMessageAdapter);

        // Scroll en bas par défaut
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        connectAndListenToFirebase();

        // Si on tente d'envoyer un message vite, il ne se passe rien, sinon on envoie le message
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mInputEditText.getText().toString().isEmpty()){
                    return;
                }

                envoyerMessage();

            }
        });
    }

    /**
     * Supprime les données de login de l'utilisateur.
     * Supprime ses préférences.
     */
    private void delete(){

        // supprime les préférences, donc le nom et le mail de l'utilisateur
        PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();
        // puis lance l'activité pour se logguer
        Intent intent = new Intent(this, NamePickerActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Ajoute les entrées de menu à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Gère le click sur l'icon de suppression
        switch (item.getItemId()){
            case R.id.action_delete:
                delete();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDatabaseReference.removeEventListener(this);
    }

    private void connectAndListenToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("chat/messages");

        mDatabaseReference.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.d(TAG, "dataChange : " + dataSnapshot);
        List<Message> items = new ArrayList<>();
        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
            items.add(messageSnapshot.getValue(Message.class));
        }
        mMessageAdapter.setData(items);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void envoyerMessage(){

        // Récupère les infos user
        User monUser = UserStorage.getUser(this);

        String mail = monUser.getEmail();

        // Génération du timestamp
        Long timeStamp = System.currentTimeMillis()/1000;

        // Envoi du message, remise à vide du champ textInput
        DatabaseReference newData = mDatabaseReference.push();
        newData.setValue(
                new Message(mInputEditText.getText().toString(), monUser.getName(), monUser.getEmail(), timeStamp));
        mInputEditText.setText("");
    }
}
