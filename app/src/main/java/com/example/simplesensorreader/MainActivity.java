package com.example.simplesensorreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.Sensor;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SensorManager sm = null;
    TextView tv = null;
    List myList;
    boolean sensorRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.valueDisplay);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);

        myList = sm.getSensorList(Sensor.TYPE_LIGHT);
        if(myList.size()>0){
            sm.registerListener(sel, (Sensor) myList.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            tv.setText("Error: Light Sensor not found");
        }

        final Button button = findViewById(R.id.theButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sensorRunning){
                    button.setText("START");
                    sensorRunning = false;
                    tv.setText("Now Stopped !!!");
                }
                else{
                    button.setText("STOP");
                    sensorRunning = true;
                    tv.setText("Now Running !!!");
                }
            }
        });
    }

    SensorEventListener sel = new SensorEventListener(){
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            tv.setText(Float.toString(values[0]));
        }
    };

    @Override
    protected void onStop() {
        if(myList.size()>0){
            sm.unregisterListener(sel);
        }
        super.onStop();
    }
}
