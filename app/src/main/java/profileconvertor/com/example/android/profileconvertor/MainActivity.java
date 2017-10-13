package profileconvertor.com.example.android.profileconvertor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //private BroadcastReceiver b;
    private  final String TAG = this.getClass().toString();
    private final String DEFAULT="N/A";
    public static ArrayList<String> registeredNumbers=new ArrayList<>();
    private  Button registration,startService,stopService;
    private EditText PhoneNum;
    private ListView registeredList;
    private ArrayAdapter<String> listitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=getSharedPreferences("Registered_Numbers",Context.MODE_PRIVATE);
        Log.i(TAG, "onCreate: "+sharedPreferences);
        int i=0;
            while (sharedPreferences.contains(Integer.toString(i + 1))) {
                String s =sharedPreferences.getString(Integer.toString(i + 1), DEFAULT);
                registeredNumbers.add(s);
                i++;

        }

        //b=new SmsListener();
        registeredList=(ListView)findViewById(R.id.registeredList);
        listitems=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,registeredNumbers);
        registeredList.setAdapter(listitems);
        registeredList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                registeredNumbers.remove(position);
                listitems.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"Item successfully removed from the registered list",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        registration=(Button)findViewById(R.id.register);
        startService=(Button)findViewById(R.id.startService);
        stopService=(Button)findViewById(R.id.stopService);
        stopService.setEnabled(false);
        PhoneNum=(EditText)findViewById(R.id.phoneNum);

        registration.setOnClickListener(this);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        //Log.i(TAG, "onCreate: ");

        //registerReceiver(new SmsListener(), new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

    }

    @Override
    protected void onPause() {
        SharedPreferences sharedPreferences=getSharedPreferences("Registered_Numbers",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        for(int i=0;i<registeredNumbers.size();i++){
            editor.putString(Integer.toString(i+1),registeredNumbers.get(i));
        }
        editor.commit();
        super.onPause();
    }

    @Override
    protected void onStart() {
        //IntentFilter i=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        //registerReceiver(b,i);
        //Log.i(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onStop() {
        //unregisterReceiver(b);
        super.onStop();
    }



    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id)
        {
            case R.id.register:
                String s="+91"+PhoneNum.getText().toString();
                registeredNumbers.add(s);
                listitems.notifyDataSetChanged();
                Toast.makeText(this,"Number Registered Successfully",Toast.LENGTH_LONG).show();
                //Log.i(TAG, "onClick: Number Registered");
                //displayNumbers.append(registeredNumbers.get(PhoneNum.getText().toString()).toString());
                break;
            case R.id.startService: startService(new Intent(this,MyService.class));
                startService.setEnabled(false);
                stopService.setEnabled(true);
                break;
            case R.id.stopService:  stopService(new Intent(this,MyService.class));
                startService.setEnabled(true);
                stopService.setEnabled(false);
                break;
        }
    }
}
