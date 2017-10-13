package profileconvertor.com.example.android.profileconvertor;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by This Pc on 10/12/2017.
 */

public class MyService extends Service {
    public final String TAG="Service";
    //private MyThread t;
    private BroadcastReceiver b;
    private IntentFilter broadcastIntent;
    /*public class MyThread extends Thread{
        @Override
        public void run() {

        }
    }*/

    @Override
    public void onCreate() {
        //t=new MyThread();
        broadcastIntent=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        b=new SmsListener();
        Log.i(TAG, "onCreate: service created");
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        //Log.i(TAG, "onStartCommand: "+startId);
        registerReceiver(b,broadcastIntent );
        //t.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(b);
        //Log.i(TAG, "onCreate: service destroyed");
        super.onDestroy();
    }

    @Nullable

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onCreate: service bound");
        return null;
    }
}
