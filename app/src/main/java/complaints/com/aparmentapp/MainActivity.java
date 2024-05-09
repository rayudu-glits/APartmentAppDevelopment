package complaints.com.aparmentapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import complaints.com.aparmentapp.Adapter.PromotionsAdapter;
import complaints.com.aparmentapp.Fragments.CollectionsFragment;
import complaints.com.aparmentapp.Fragments.ComplaintsFragment;
import complaints.com.aparmentapp.Fragments.EventsFragment;
import complaints.com.aparmentapp.Fragments.ExpensesFragment;
import complaints.com.aparmentapp.Fragments.MembersFragment;
import complaints.com.aparmentapp.Fragments.PromotionsFragment;
import complaints.com.aparmentapp.Fragments.StaffFragment;
import complaints.com.aparmentapp.Fragments.VendorsFragment;
import complaints.com.aparmentapp.Fragments.VisitorsFragment;
import complaints.com.aparmentapp.Sharedpreferences.MarshmallowPermissions;
import complaints.com.aparmentapp.Sharedpreferences.SaveAppData;

import static complaints.com.aparmentapp.Adapter.PromotionsAdapter.bitmap2;
import static complaints.com.aparmentapp.Adapter.PromotionsAdapter.mImage_of_camera;
import static complaints.com.aparmentapp.Adapter.PromotionsAdapter.promo_image;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    MarshmallowPermissions marshMallowPermission=new MarshmallowPermissions(MainActivity.this);
    Toolbar toolbar;
    private LocationManager locationManager;
    private int PERMISSION_CAMERA = 1002;
    private String provider;


    private int CAMERA_Select = 220;
    int RESULT_CODE = 120;
    int count = 0;
    public static final int MEDIA_TYPE_IMAGE = 1;
    Uri fileUri;
    private static final String IMAGE_DIRECTORY_NAME = "HOMES";
    Button btn_uploadfiles;
    RadioButton rbtn_checkgps,rbtn_nocheckgps;
    ImageView promo_ad;
    DrawerLayout drawer;
    TextView tv_userName,tv_email;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        Intent myIntent;
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                /*selectedFragment = CollectionsFragment.newInstance();*/
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_profile:
                                break;
                            case R.id.action_logout:
                                SaveAppData.saveEmpLoginData(null);
                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        //transaction.replace(R.layout.app_bar_main, myIntent);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.replace(R.layout.app_bar_main,myAccountIntent );
        transaction.commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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
                    case R.id.rv_complaints:
                        viewPager.setCurrentItem(1);
                        drawer.closeDrawers();
                        break;
                    case R.id.rv_Staff:
                        viewPager.setCurrentItem(2);
                        drawer.closeDrawers();
                        break;
                    case R.id.rv_Promotions:
                        viewPager.setCurrentItem(3);
                        drawer.closeDrawers();
                        break;
                    case R.id.rv_Visitors:
                        viewPager.setCurrentItem(4);
                        drawer.closeDrawers();
                        break;
                    case R.id.rv_Members:
                        viewPager.setCurrentItem(5);
                        drawer.closeDrawers();
                        break;
                    case R.id.rv_Expenses:
                        viewPager.setCurrentItem(6);
                        drawer.closeDrawers();
                        break;
                    case R.id.rv_Events:
                        viewPager.setCurrentItem(7);
                        drawer.closeDrawers();
                        break;
                    case R.id.rv_Vendors:
                        viewPager.setCurrentItem(8);
                        drawer.closeDrawers();
                        break;
                    case R.id.rv_Logout:
                        SaveAppData.saveEmpLoginData(null);
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
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

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawer,toolbar,R.string.drawer_open,R.string.drawer_close){
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
   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 112) {
                //iv_adproof.setImageURI(mImage_of_camera);
                try {
                    InputStream ims = getContentResolver().openInputStream(mImage_of_camera);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 14;
                    bitmap2 = BitmapFactory.decodeStream(ims, null, options);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                int nh = (int) (bitmap2.getHeight() * (512.0 / bitmap2.getWidth()));
                bitmap2 = Bitmap.createScaledBitmap(bitmap2, 512, nh, true);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                PromotionsAdapter.encodedimage2 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                promo_image.setVisibility(View.VISIBLE);
                // btn_adproof.setVisibility(View.GONE);
                promo_image.setImageBitmap(bitmap2);
                PromotionsAdapter.imgname2 = System.currentTimeMillis() + ".png";
            } else if (requestCode == 216) {
                Uri selectedImage = data.getData();
                try {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 14;
                    bitmap2 = BitmapFactory.decodeFile(imgDecodableString,
                            options);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    PromotionsAdapter.encodedimage2 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    promo_image.setVisibility(View.VISIBLE);
                    // btn_adproof.setVisibility(View.GONE);
                    promo_image.setImageBitmap(bitmap2);
                    PromotionsAdapter.imgname2 = System.currentTimeMillis() + ".png";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
   /* *//*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CollectionsFragment(), "Collections");
        adapter.addFrag(new ComplaintsFragment(), "Complaints");
        adapter.addFrag(new StaffFragment(), "Staff");
        adapter.addFrag(new PromotionsFragment(), "Promotions");
        adapter.addFrag(new VisitorsFragment(), "Visitors");
        adapter.addFrag(new MembersFragment(), "Members");
        adapter.addFrag(new ExpensesFragment(), "Expenses");
        adapter.addFrag(new EventsFragment(), "Events");
        adapter.addFrag(new VendorsFragment(), "Vendors");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!marshMallowPermission.checkPermissionForCamera()) {
            marshMallowPermission.requestPermissionForCamera();
        }
        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage();
        }
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);*/
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        while (true) {
            if (resultCode == RESULT_OK) {
                if (requestCode == 112) {
                    try {
                        InputStream ims = this.getContentResolver().openInputStream(mImage_of_camera);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 14;
                        bitmap2 = BitmapFactory.decodeStream(ims, null, options);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    int nh = (int) (bitmap2.getHeight() * (512.0 / bitmap2.getWidth()));
                    bitmap2 = Bitmap.createScaledBitmap(bitmap2, 512, nh, true);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    encodedimage2 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    promo_image.setVisibility(View.VISIBLE);
                    promo_ad.setVisibility(View.VISIBLE);
                    promo_ad.setImageBitmap(bitmap2);
                    promo_image.setImageBitmap(bitmap2);
                    imgname2 = System.currentTimeMillis() + ".png";
                } else if (requestCode == 216) {
                    Uri selectedImage = data.getData();
                    try {
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = this.getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String imgDecodableString = cursor.getString(columnIndex);
                        cursor.close();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 14;
                        bitmap2 = BitmapFactory.decodeFile(imgDecodableString,
                                options);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        encodedimage2 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        promo_image.setVisibility(View.VISIBLE);
                        promo_ad.setVisibility(View.VISIBLE);
                        promo_ad.setImageBitmap(bitmap2);
                        promo_image.setImageBitmap(bitmap2);
                        imgname2 = System.currentTimeMillis() + ".png";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }*/
}
