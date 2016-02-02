package com.perez.marcos.startedserviceexample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

    Thread t;
    Boolean started = false;
    public MyService() {
    }


     @Override
    public void onCreate() {
         Log.v("service","onCreate" );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("service", "onStartCommand");
        final long totalTime = 1000 * 60;
        if (!started) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    started = true;
                    long currentTime = 0;
                    while (currentTime <= totalTime && started) {
                        try {
                            Toast.makeText(getApplicationContext(),"I'M ALIVE :D!", Toast.LENGTH_SHORT).show();
                            Thread.sleep(3000);
                            currentTime += 3000;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.start();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }



    @Override
    public void onDestroy() {
        Log.v("service", "onDestroy");
        started = false;
        t.interrupt();
        stopSelf();

    }
}
