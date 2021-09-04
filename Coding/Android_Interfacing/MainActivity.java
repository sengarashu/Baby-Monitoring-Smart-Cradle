package com.example.riya.bmsc;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef2;
    DatabaseReference myRef3;
    DatabaseReference myRef4;
    DatabaseReference myRef5;
    Button Motor;
    Button Next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database=FirebaseDatabase.getInstance();
        Motor=(Button)findViewById(R.id.motor);
        Next=(Button)findViewById(R.id.next);
        myRef=database.getReference("MyMessage");
        myRef5=database.getReference("Motor").child("Values");
        myRef3=database.getReference("sensor").child("dht");
        myRef4=database.getReference("sensor").child("sound");
        final DateFormat dateformat=new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Main3Activity.class);
                startActivity(i);
            }
        });

        Motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date=new Date();
                myRef5.child(dateformat.format(date).toString()).setValue("yes");
            }
        });


        myRef3.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                sendNotification(dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef4.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                sendNotification2(dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value=dataSnapshot.getValue(String.class);
                TextView textview=(TextView)findViewById(R.id.textview);
                textview.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> map=(Map<String,String>)dataSnapshot.getValue();
                String name=map.get("Name");
                String contac=map.get("Contact");
                Log.v("Name",name);
                Log.v("Contact",contac);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    private void sendNotification(String body) {
        Intent intent=new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifiBuilder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("HUMIDITY")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notifiBuilder.build());

    }
    private void sendNotification2(String body) {
        Intent intent=new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifiBuilder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("CRY")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notifiBuilder.build());

    }


    public void buttonClicked(View view){

        //myRef.setValue("Hello there");

        EditText editText = (EditText)findViewById(R.id.message);
        String message=editText.getText().toString();
        myRef.setValue(message);
    }

    public void buttonClickedforName(View view){
        EditText editTextName = (EditText)findViewById(R.id.name);
        EditText contact=(EditText)findViewById(R.id.contact);
        //myRef2=database.getReference("Users");
        //myRef2.child(editTextName.getText().toString()).push().setValue(editTextName.getText().toString());
        String child=editTextName.getText().toString();
        myRef2=database.getReference("Users").child(child);
        myRef2.child("Name").setValue(editTextName.getText().toString());
        myRef2.child("Contact").setValue(contact.getText().toString());


    }
}
