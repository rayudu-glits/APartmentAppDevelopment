package complaints.com.aparmentapp;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import complaints.com.aparmentapp.databinding.ActivityInstantApprovalSecurityBinding;

public class InstantApprovalSecurity extends AppCompatActivity {
    private ActivityInstantApprovalSecurityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instant_approval_security);
    }
}