package com.devhima.xcrypto.Fragment;

import android.content.*;
import android.net.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.devhima.xcrypto.*;
import com.devhima.xcrypto.ViewPager.*;


/**
 * AboutFragment subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }
	
	// Define AboutFragment vars:
	private Button fragmentaboutButtonVDW;
	private TextView fragmentaboutTextViewNtruLink;
	
	// Initializing AboutFragment vars, contents & methods
	private void initAboutFragmentContents(View view){
		// VARs & Contents
		this.fragmentaboutButtonVDW = view.findViewById(R.id.fragmentaboutButtonVDW);
		this.fragmentaboutTextViewNtruLink = view.findViewById(R.id.fragmentaboutTextViewNtruLink);
		
		// Methods
		
		// fragmentaboutButtonVDW - onClick method
		fragmentaboutButtonVDW.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https://devhima.tk/"));
					startActivity(browse);
				}
			});
			
		// fragmentaboutTextViewNtruLink - onClick method
		fragmentaboutTextViewNtruLink.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/NTRUOpenSourceProject/ntru-crypto"));
					startActivity(browse);
				}
			});
	}
	
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
		initAboutFragmentContents(view);
		return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_about_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_visit:
               //Visit website
				fragmentaboutButtonVDW.callOnClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
