package profileconvertor.com.example.android.profileconvertor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.HashMap;

public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
            // send message to activity
        HashMap<String,String> profilepairs=new HashMap<>();

        final AudioManager mobilemode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        SmsMessage[] s= Telephony.Sms.Intents.getMessagesFromIntent(intent);
        String PhNum=s[s.length-1].getOriginatingAddress();
        //Log.e(TAG, "onReceive: "+s[s.length-1].getDisplayOriginatingAddress());
        if(MainActivity.registeredNumbers.contains(PhNum)){
            String messageBody = s[s.length-1].getMessageBody();
            String[] pairs=messageBody.split("-");
            profilepairs.put(pairs[0],pairs[1]);
            String ringer=profilepairs.get("Ringer");


            if(ringer.equalsIgnoreCase("vibrate")){

                if(mobilemode.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE){
                    Toast.makeText(context,"Already in Vibrate mode",Toast.LENGTH_SHORT).show();
                }
                else{
                    mobilemode.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                }
            }
            if(ringer.equalsIgnoreCase("loud")){

                if(mobilemode.getRingerMode()==AudioManager.RINGER_MODE_NORMAL){
                    Toast.makeText(context,"Already in Loud mode",Toast.LENGTH_SHORT).show();
                }
                else{
                    mobilemode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }

        }
        else{
            //Toast.makeText(context,"Number not Registered",Toast.LENGTH_LONG).show();
        }

        //Log.i(TAG, "onReceive: "+messageBody+" "+PhNum);
        //String[] lines=messageBody.split("\\R");
        //for(int i=0;i<lines.length;i++){



       // Log.i(TAG, "onReceive: "+messageBody);


        //if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){

        //}
    }
}
