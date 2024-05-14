package complaints.com.aparmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import complaints.com.aparmentapp.databinding.ActivityInstantApprovalSecurityBinding;
import complaints.com.aparmentapp.databinding.ActivitySecurityDashBoardBinding;

public class SecurityDashBoard extends AppCompatActivity {
    ActivitySecurityDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_security_dash_board);
        binding = ActivitySecurityDashBoardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SecurityDashBoard.this, InstantApprovalSecurity.class));

            }
        });

    }
}