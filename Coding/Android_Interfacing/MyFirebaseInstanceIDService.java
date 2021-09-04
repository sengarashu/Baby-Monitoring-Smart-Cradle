package com.example.riya.bmsc.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Riya on 05-02-2018.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    private static final String TAG="MyFirebaseIDService";

    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }
    private void sendRegistrationToServer(String token){

    }
}
