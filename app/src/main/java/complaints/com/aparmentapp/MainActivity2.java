package complaints.com.aparmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import complaints.com.aparmentapp.Fragments.CollectionsFragment;
import complaints.com.aparmentapp.Fragments.ComplaintsFragment;
import complaints.com.aparmentapp.Fragments.EventsFragment;
import complaints.com.aparmentapp.Fragments.ExpensesFragment;
import complaints.com.aparmentapp.Fragments.MembersFragment;
import complaints.com.aparmentapp.Fragments.PromotionsFragment;
import complaints.com.aparmentapp.Fragments.StaffFragment;
import complaints.com.aparmentapp.Fragments.VendorsFragment;
import complaints.com.aparmentapp.Fragments.VisitorsFragment;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;
import complaints.com.aparmentapp.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    TextView tv_userName,tv_email;
    ImageView add,Cancel,notification,user;
    AlertDialog alertDialog;
    LinearLayout Guest,Addguest,preapprove,delivery,adddelivery,cab,addcab,dailyhelp,Settings;

    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        add=findViewById(R.id.plus);
        preapprove=findViewById(R.id.preApproval);
        dailyhelp=findViewById(R.id.dailyhelp);
        Settings=findViewById(R.id.setting);
        user=findViewById(R.id.user);
        notification=findViewById(R.id.notification);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, InstantApprovalSecurity.class));
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, ProfileActivity.class));
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, Settings.class));
            }
        });
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, complaints.com.aparmentapp.Settings.class));
            }
        });
        dailyhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, Dailyhelp.class));

            }
        });
        preapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("slect","onclick");
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity2.this);
                View view_alert= LayoutInflater.from(MainActivity2.this).inflate(R.layout.preapprovallist,null);
                Log.d("slect","click");
                Cancel=view_alert.findViewById(R.id.cancel_btn);
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


        initNavigationDrawer();
    }
    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        startActivity(new Intent(MainActivity2.this, MainActivity2.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.profile:
                        startActivity(new Intent(MainActivity2.this, Settings.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.preApproval:
                                Log.d("slect","onclick");
                                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity2.this);
                                View view_alert= LayoutInflater.from(MainActivity2.this).inflate(R.layout.preapprovallist,null);
                                Log.d("slect","click");
                                Cancel=view_alert.findViewById(R.id.cancel_btn);
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
                        drawer.closeDrawers();
                        break;
                    case R.id.post_approval:
                        startActivity(new Intent(MainActivity2.this, preapproval2.class));
                        drawer.closeDrawers();
                        break;
                    case R.id.rv_Logout:
                        SaveAppData.saveEmpLoginData(null);
                        Intent i = new Intent(MainActivity2.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                        drawer.closeDrawers();
                        break;

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        tv_email = (TextView)header.findViewById(R.id.tv_email);
        tv_userName = (TextView)header.findViewById(R.id.tv_userName);
//        tv_userName.setText(SaveAppData.getLoginData().getEmp_first_name());

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity2.this,drawer,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }
            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}