package com.programacionparadispositivosmoviles.lectordesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MensajeRecibido extends BroadcastReceiver {

    private static final String SMS_RECEIVED ="android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG ="SmsBroadcastReceiver";
    String mensaje ,NumeroTelefono ="";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"Intent Recibido" + intent.getAction());
        if(intent.getAction()== SMS_RECEIVED){

            Bundle dataBundle = intent.getExtras();
            if(dataBundle != null){

                Object[] mypdu = (Object[])dataBundle.get("pdus");

                final SmsMessage[] messages = new SmsMessage[mypdu.length];

                for(int i = 0; i < mypdu.length; i++){

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                        String format = dataBundle.getString("format");

                        messages[i] = SmsMessage.createFromPdu((byte[])mypdu[i], format);

                    }
                    else
                    {
                        messages[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                    }

                     mensaje = messages[i].getMessageBody();
                    NumeroTelefono = messages[i].getOriginatingAddress();
                }

                Toast.makeText(context, "Mensaje: " + mensaje + "\n Numero: " + NumeroTelefono, Toast.LENGTH_LONG).show();
            }

        }
    }
}
