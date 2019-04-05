package com.devhima.xcrypto.ViewPager;

import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.view.*;
import com.devhima.xcrypto.*;
import com.devhima.xcrypto.Fragment.*;

public class MainActivity extends AppCompatActivity {
	
    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
	
	//Open file dialog
	public static OpenFileDialog OFD;
	public static Context MAContext;
	public static Context baseContext;
    //Fragments

    KeysFragment keysFragment;
    CryptoFragment cryptoFragment;
    AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
		
        setupViewPager(viewPager);
		MAContext = MainActivity.this;
		baseContext = getBaseContext();
		
		// The request code used in ActivityCompat.requestPermissions()
		// and returned in the Activity's onRequestPermissionsResult()
		int PERMISSION_ALL = 1; 
		String[] PERMISSIONS = {
			android.Manifest.permission.READ_EXTERNAL_STORAGE,
			android.Manifest.permission.WRITE_EXTERNAL_STORAGE
		};

		if(!hasPermissions(this, PERMISSIONS)){
			ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
		}
    }
	
	// check for permissions
	public static boolean hasPermissions(Context context, String... permissions) {
		if (context != null && permissions != null) {
			for (String permission : permissions) {
				if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
					return false;
				}
			}
		}
		return true;
	}

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        // Associate searchable configuration with the SearchView
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_exit:
                android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);
				return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        cryptoFragment=new CryptoFragment();
        keysFragment=new KeysFragment();
        aboutFragment=new AboutFragment();
        adapter.addFragment(cryptoFragment,"Crypto");
        adapter.addFragment(keysFragment,"Keys");
        adapter.addFragment(aboutFragment,"About");
        viewPager.setAdapter(adapter);
    }

}
