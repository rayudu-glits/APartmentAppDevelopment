package complaints.com.aparmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Preapprovescreen extends AppCompatActivity {
    ImageView back;
    LinearLayout Guest,Addguest,preapprove,delivery,adddelivery,cab,addcab,dailyhelp,Settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preapprovescreen);
        back=findViewById(R.id.back_btn);
        Guest=findViewById(R.id.guestll);
        Addguest=findViewById(R.id.addguest);
        adddelivery=findViewById(R.id.adddelivery);
        delivery=findViewById(R.id.deliveryll);
        cab=findViewById(R.id.cabs);
        addcab=findViewById(R.id.addcab);
        Addguest.setVisibility(View.GONE);
        adddelivery.setVisibility(View.GONE);
        addcab.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preapprovescreen.this, TenantmainScreen.class));

            }
        });
        Guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addguest.setVisibility(View.VISIBLE);
                adddelivery.setVisibility(View.GONE);
                addcab.setVisibility(View.GONE);
            }
        });
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adddelivery.setVisibility(View.VISIBLE);
                Addguest.setVisibility(View.GONE);
                addcab.setVisibility(View.GONE);
            }
        });
        cab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addguest.setVisibility(View.GONE);
                adddelivery.setVisibility(View.GONE);
                addcab.setVisibility(View.VISIBLE);
            }
        });
    }
}