package complaints.com.aparmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import complaints.com.aparmentapp.databinding.ActivityDailyhelpBinding;
import complaints.com.aparmentapp.databinding.ActivityInstantApprovalSecurityBinding;

public class Dailyhelp extends AppCompatActivity {
    ActivityDailyhelpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dailyhelp);
        binding = ActivityDailyhelpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dailyhelp.this, MainActivity2.class));
            }
        });

    }
}