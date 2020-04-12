package com.example.tpfinalmoviles;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tpfinalmoviles.Model.AgregarRodeo;
import com.example.tpfinalmoviles.Model.AgregarRodeoAlerta;
import com.example.tpfinalmoviles.Model.AgregarVaca;
import com.example.tpfinalmoviles.Model.AgregarVacaAlerta;
import com.example.tpfinalmoviles.Model.ConsultarRodeo;
import com.example.tpfinalmoviles.Model.ConsultarRodeoAlerta;
import com.example.tpfinalmoviles.Model.ConsultarVaca;
import com.example.tpfinalmoviles.Model.ConsultarVacaAlerta;
import com.example.tpfinalmoviles.Model.GenerarBCS;
import com.example.tpfinalmoviles.Utils.ToastHandler;

import static android.app.Notification.DEFAULT_SOUND;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private CardView idAgregarVaca, idAgregarRodeo, idAgregarAlertaVaca, idAgregarAlertaRodeo
                    ,idConsultarVaca, idConsultarRodeo, idGenerarBCS, idInfo, idGetAlertaVacas
                    ,idGetAlertaRodeos;
    private PendingIntent siPendingIntent;
    private PendingIntent noPendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    public final static int NOTIFICACION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idAgregarVaca = (CardView) findViewById(R.id.idAgregarVaca);
        idAgregarRodeo = (CardView) findViewById(R.id.idAgregarRodeo);
        idAgregarAlertaRodeo = (CardView) findViewById(R.id.idAgregarAlertaRodeo);
        idAgregarAlertaVaca = (CardView) findViewById(R.id.idAgregarAlertaVaca);
        idConsultarVaca = (CardView) findViewById(R.id.idConsultarVaca);
        idConsultarRodeo = (CardView) findViewById(R.id.idConsultarRodeo);
        idGenerarBCS = (CardView) findViewById(R.id.idGenerarBCS);
        idInfo = (CardView) findViewById(R.id.idInfo);
        idGetAlertaRodeos = (CardView) findViewById((R.id.idGetAlertaRodeos));
        idGetAlertaVacas = (CardView) findViewById((R.id.idGetAlertaVacas));

        idAgregarVaca.setOnClickListener(this);
        idAgregarRodeo.setOnClickListener(this);
        idAgregarAlertaRodeo.setOnClickListener(this);
        idAgregarAlertaVaca.setOnClickListener(this);
        idConsultarVaca.setOnClickListener(this);
        idConsultarRodeo.setOnClickListener(this);
        idGenerarBCS.setOnClickListener(this);
        idInfo.setOnClickListener(this);
        idGetAlertaRodeos.setOnClickListener(this);
        idGetAlertaVacas.setOnClickListener(this);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.cancel(NOTIFICACION_ID);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.idAgregarVaca:
                i = new Intent(this, AgregarVaca.class);
                startActivity(i);
                break;
            case R.id.idAgregarRodeo:
                i = new Intent(this, AgregarRodeo.class);
                startActivity(i);
                break;
            case R.id.idAgregarAlertaRodeo:
                i = new Intent(this, AgregarRodeoAlerta.class);
                startActivity(i);
                break;
            case R.id.idAgregarAlertaVaca:
                i = new Intent(this, AgregarVacaAlerta.class);
                startActivity(i);
                break;
            case R.id.idConsultarVaca:
                i = new Intent(this, ConsultarVaca.class);
                startActivity(i);
                break;
            case R.id.idConsultarRodeo:
                i = new Intent(this, ConsultarRodeo.class);
                startActivity(i);
                break;
            case R.id.idGenerarBCS:
                i = new Intent(this, GenerarBCS.class);
                startActivity(i);
                break;
            case R.id.idInfo:
                ToastHandler.get().showToast(getApplicationContext(), "APP Desarrollada por: Gramuglia Lautaro - Stampone Juan Manuel", Toast.LENGTH_SHORT);
                break;
            case R.id.idGetAlertaRodeos:
                siPendingIntent(ConsultarRodeoAlerta.class);
                noPendingIntent();
                createNotificationChannel();
                createNotification("Alerta/s Rodeo/s");
                break;
            case R.id.idGetAlertaVacas:
                siPendingIntent(ConsultarVacaAlerta.class);
                noPendingIntent();
                createNotificationChannel();
                createNotification("Alerta/s Vaca/s");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    private void siPendingIntent(Class clase){
        Intent intent = new Intent(this, clase);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(clase);
        stackBuilder.addNextIntent(intent);
        siPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void noPendingIntent(){
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        noPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion Alertas";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(String titulo){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_warning_black_24dp);
        builder.setContentTitle(titulo);
        builder.setContentText("¿Quiere ver en detalle las alertas activas?");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000});
        builder.setDefaults(DEFAULT_SOUND);
        builder.setContentIntent(siPendingIntent);
        builder.addAction(R.drawable.ic_warning_black_24dp, "Si", siPendingIntent);
        builder.addAction(R.drawable.ic_warning_black_24dp, "No", noPendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
}

