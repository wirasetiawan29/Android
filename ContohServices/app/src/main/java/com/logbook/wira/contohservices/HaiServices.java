package com.logbook.wira.contohservices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wira on 1/13/16.
 */
public class HaiServices extends Service {

    private static final String TAG = "HaiService";

    private boolean isRunning  = false;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service telah dibuat", Toast.LENGTH_LONG).show();

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service di jalankan", Toast.LENGTH_LONG).show();

        //Membuat thread baru untuk servicenya
        new Thread(new Runnable() {
            @Override
            public void run() {

                //Isikan logika untuk service yang diinginkan
                //Disini saya looping service 10x untuk mencontohkan notfikasi sebanyak 10 kali untuk 1000 milidetik
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }

                    if(isRunning){

                        Log.i(TAG, "Service berjalan");
                    }
                }

                //Service di berhentikan ketika akhir looping
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;
        Toast.makeText(this, "Service di Destroy", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Service berhenti");
    }

}
