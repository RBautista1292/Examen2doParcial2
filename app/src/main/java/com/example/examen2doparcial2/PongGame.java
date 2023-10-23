package com.example.examen2doparcial2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Looper;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class PongGame extends View implements SensorEventListener {
    Paint pincel = new Paint();
    int alto, ancho;
    int radio = 30;
    int lifes = 3;
    float ejeX = 50, ejeY = 50, vectorX = 5, vectorY = 5, ejeRLX = 40, ejeRRX = 220, ejeRTY, ejeRBY;
    public PongGame(Context context) {
        super(context);
        SensorManager smAdministrador = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor snsRotacion = smAdministrador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        smAdministrador.registerListener(this, snsRotacion, SensorManager.SENSOR_DELAY_FASTEST);
        Display pantalla = ((WindowManager) getContext() .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        ancho = pantalla.getWidth();
        alto = pantalla.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        pincel.setColor(Color.RED);
        canvas.drawCircle(ejeX, ejeY, radio, pincel);
        pincel.setColor(Color.WHITE);
        pincel.setTextSize(60);
        canvas.drawText("Vidas: "+lifes, ancho/2-150, 60, pincel);
        ejeRTY = alto - 140;
        ejeRBY = alto - 110;
        pincel.setColor(Color.DKGRAY);
        canvas.drawRect(ejeRLX, ejeRTY, ejeRRX, ejeRBY, pincel);
        movePelota();
    }

    private Runnable playG = new Runnable() {
        @Override
        public void run() {
            invalidate();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public void movePelota(){
        ejeX += vectorX;
        ejeY += vectorY;
        if (ejeX <= radio || ejeX >= getWidth() - radio) {
            vectorX = -vectorX;
        }
        if (ejeY <= radio) {
            if (vectorY > 0){
                vectorY += 1;
            }
            else {
                vectorY -=1;
            }
            vectorY = -vectorY;
        }
        if (ejeY >= getHeight()) {
            ejeX = 50;
            ejeY = 50;
            vectorY = vectorX = 5;
            lifes--;
            if (lifes == 0) {
                Toast.makeText(getContext(), "Juego terminado. Reiniciando...", Toast.LENGTH_SHORT).show();
                lifes = 3;
                playG.run();
            }
        }
        if (ejeX >= ejeRLX && ejeX <= ejeRRX && ejeY >= ejeRTY - radio && ejeY <= ejeRBY) {
            vectorY += 1;
            vectorY = -vectorY;
        }
        invalidate();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ejeRLX -= sensorEvent.values[0];
        ejeRRX -= sensorEvent.values[0];
        if (ejeRRX >= ancho) {
            ejeRRX = ancho;
            ejeRLX = ejeRRX-180;
        }
        if (ejeRLX <= 0) {
            ejeRLX = 0;
            ejeRRX = 180;
        }
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
