package complaints.com.aparmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import complaints.com.aparmentapp.databinding.ActivityInstantApprovalSecurityBinding;
import complaints.com.aparmentapp.databinding.ActivitySecurityDashBoardBinding;

public class InstantApprovalSecurity extends AppCompatActivity {
    private ActivityInstantApprovalSecurityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instant_approval_security);
        binding = ActivityInstantApprovalSecurityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstantApprovalSecurity.this, MainActivity2.class));

            }
        });
    }
}