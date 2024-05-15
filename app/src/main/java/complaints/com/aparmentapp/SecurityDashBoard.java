package complaints.com.aparmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import complaints.com.aparmentapp.databinding.ActivitySecurityDashBoardBinding;

public class SecurityDashBoard extends AppCompatActivity {
    ActivitySecurityDashBoardBinding binding;
    AlertDialog alertDialog;
    ImageView Cancel;
    LinearLayout Guest,Addguest,preapprove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_dash_board);
        binding = ActivitySecurityDashBoardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(SecurityDashBoard.this, InstantApprovalSecurity.class));
                Log.d("slect","onclick");
                AlertDialog.Builder dialog=new AlertDialog.Builder(SecurityDashBoard.this);
                View view_alert= LayoutInflater.from(SecurityDashBoard.this).inflate(R.layout.preapprove,null);
                Log.d("slect","click");
                Cancel=findViewById(R.id.close_btn);
                Guest=findViewById(R.id.guestll);
                Addguest=findViewById(R.id.addguest);
                Guest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Addguest.setVisibility(View.VISIBLE);
                    }
                });

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                    }
                });
                dialog.setView(view_alert);
                dialog.setCancelable(false);
                alertDialog = dialog.create();
                alertDialog.show();
            }
        });
        binding.approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecurityDashBoard.this, InstantApprovalSecurity.class));

            }
        });

    }
}