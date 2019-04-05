package com.devhima.xcrypto.Fragment;


import android.app.*;
import android.graphics.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.devhima.xcrypto.*;
import com.devhima.xcrypto.ViewPager.*;
import com.securityinnovation.jNeo.*;
import com.securityinnovation.jNeo.Random;
import java.util.*;

import android.support.v4.app.Fragment;
import java.io.*;
import android.content.*;

/**
 * KeysFragment subclass.
 */
 
public class KeysFragment extends Fragment {

    public KeysFragment() {
		// Required empty public constructor
    }
	
	// Define KeysFragment vars:
	private EditText fragmentkeysEditTextKeyName;
	private Spinner fragmentkeysSpinnerOID;
	private Button fragmentkeysButtonGenKeys;
	private Button fragmentkeysButtonChoosePath;
	private TextView fragmentkeysTextViewPathChoosed;
	private ArrayList<String> OID_DATA = new ArrayList<>();
	private String currentOID, choosedPath;
	
	// Initializing KeysFragment vars, contents & methods
	private void initKeysFragmentContents(View view){
		// VARs & Contents
		this.fragmentkeysEditTextKeyName = view.findViewById(R.id.fragmentkeysEditTextKeyName);
		this.fragmentkeysSpinnerOID = view.findViewById(R.id.fragmentkeysSpinnerOID);
		this.fragmentkeysButtonGenKeys = view.findViewById(R.id.fragmentkeysButtonGenKeys);
		this.fragmentkeysButtonChoosePath = view.findViewById(R.id.fragmentkeysButtonChoosePath);
		this.fragmentkeysTextViewPathChoosed = view.findViewById(R.id.fragmentkeysTextViewPathChoosed);
		
		// Set values & defaults
		for (OID oid : OID.values()){
			OID_DATA.add(oid.toString());
		}
		fragmentkeysSpinnerOID.setAdapter(new ArrayAdapter<String>(MainActivity.baseContext, android.R.layout.simple_spinner_dropdown_item, OID_DATA));
		((ArrayAdapter)fragmentkeysSpinnerOID.getAdapter()).notifyDataSetChanged();
		fragmentkeysTextViewPathChoosed.setText("×");
		fragmentkeysTextViewPathChoosed.setTextColor(Color.parseColor("#FF0F00"));
		
		// Methods
		
		// fragmentkeysSpinnerOID - onItemSelected method
		fragmentkeysSpinnerOID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				currentOID = _param1.getItemAtPosition(_param3).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> _param1) {

			}
		});
		
		// fragmentkeysButtonChoosePath - onClick method
		fragmentkeysButtonChoosePath.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					MainActivity.OFD = new OpenFileDialog(MainActivity.MAContext);
					MainActivity.OFD.setOnlyFoldersFilter();
					MainActivity.OFD.setOpenDialogListener(new OpenFileDialog.OpenDialogListener(){
							@Override
							public void OnSelectedFile(String fileName)
							{
								if(xCryptoUtil.checkPath(fileName) == true){
									choosedPath = fileName;
									fragmentkeysTextViewPathChoosed.setText("√");
									fragmentkeysTextViewPathChoosed.setTextColor(Color.parseColor("#349A31"));
								}
							}
						});
					MainActivity.OFD.show();
				}
			});
		
		// fragmentkeysButtonGenKeys - onClick method
		fragmentkeysButtonGenKeys.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					if(!fragmentkeysEditTextKeyName.getText().toString().trim().equals("") && !choosedPath.equals("") && !currentOID.equals("")){
						new keyGenTask().execute();
					} else {
						Toast.makeText(MainActivity.MAContext, "Error: please input all fields.", Toast.LENGTH_SHORT).show();
					}
				}
			});
	}

	private void doGenKeys()
	{
		String fName = fragmentkeysEditTextKeyName.getText().toString();
		String pubkeyFile = choosedPath + "/" + fName + "_public.xcq";
		String privkeyFile = choosedPath + "/" + fName + "_private.xcp";
		Random prng = xCryptoUtil.createSeededRandom();
		OID oid = xCryptoUtil.parseOIDName(currentOID);
		try
		{
			xCryptoUtil.setupNtruEncryptKey(prng, oid, pubkeyFile, privkeyFile);
		}
		catch (NtruException e)
		{
			Toast.makeText(MainActivity.MAContext, "Key generation error.", Toast.LENGTH_SHORT).show();
		}
		catch (IOException e)
		{
			Toast.makeText(MainActivity.MAContext, "File error.", Toast.LENGTH_SHORT).show();
		}
		catch (Exception e)
		{
			Toast.makeText(MainActivity.MAContext, "Error.", Toast.LENGTH_SHORT).show();
		}
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
        View view =  inflater.inflate(R.layout.fragment_keys, container, false);
		initKeysFragmentContents(view);
		return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_keys_fragment, menu);
            super.onCreateOptionsMenu(menu, inflater);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_generate:
				//Generate keys
				fragmentkeysButtonGenKeys.callOnClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	private class keyGenTask extends AsyncTask<Void, Void, Void>
	{
		private ProgressDialog pd;

		@Override
		protected void onPreExecute(){ 
			super.onPreExecute();

			pd = new ProgressDialog(MainActivity.MAContext);
			pd.setIndeterminate(true);
			pd.setMessage("Generating Keys...");
			pd.show();    
		}

		@Override
		protected Void doInBackground(Void... params) {
			doGenKeys();
			return null;
		}

		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			pd.dismiss();
			Toast.makeText(MainActivity.MAContext, "Done.", Toast.LENGTH_SHORT).show();
		}
	}

}
