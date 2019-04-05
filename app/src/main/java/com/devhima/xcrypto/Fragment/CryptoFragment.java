package com.devhima.xcrypto.Fragment;


import android.app.*;
import android.graphics.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.CompoundButton.*;
import com.devhima.xcrypto.*;
import com.devhima.xcrypto.ViewPager.*;
import com.securityinnovation.jNeo.*;
import com.securityinnovation.jNeo.ntruencrypt.*;
import java.io.*;

import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;


/**
 * CryptoFragment subclass.
 */
public class CryptoFragment extends Fragment {
	
    public CryptoFragment() {
        // Required empty public constructor
    }
	
	// Define CryptoFragment vars:
	private RadioButton fragmentcryptoRadioButtonEncMode;
	private RadioButton fragmentcryptoRadioButtonDecMode;
	private Button fragmentcryptoButtonChooseKey;
	private TextView fragmentcryptoTextViewKeyChoosed;
	private Button fragmentcryptoButtonChooseFile;
	private TextView fragmentcryptoTextViewFileChoosed;
	private TextView fragmentcryptoTextViewFileName;
	private TextView fragmentcryptoTextViewKeyName;
	private Button fragmentcryptoButtonStart;
	private CryptoMode currentCryptoMode;
	private String choosedFilePath, choosedKeyPath;

	private enum CryptoMode{ENCRYPTION_MODE, DECRYPTION_MODE}

	private void setCryptoMode(CryptoMode cm){
		if(cm.equals(cm.ENCRYPTION_MODE)){
			fragmentcryptoRadioButtonEncMode.setChecked(true);
			fragmentcryptoButtonChooseKey.setText("CHOOSE PUBLIC KEY");
			fragmentcryptoTextViewKeyChoosed.setText("×");
			fragmentcryptoTextViewKeyChoosed.setTextColor(Color.parseColor("#FF0F00"));
			fragmentcryptoButtonChooseFile.setText("CHOOSE FILE TO ENCRYPT");
			fragmentcryptoTextViewFileChoosed.setText("×");
			fragmentcryptoTextViewFileChoosed.setTextColor(Color.parseColor("#FF0F00"));
			fragmentcryptoTextViewFileName.setText("File: None");
			fragmentcryptoTextViewKeyName.setText("Key: None");
			fragmentcryptoButtonStart.setText("Encrypt");
			choosedFilePath = "";
			choosedKeyPath = "";
			currentCryptoMode = CryptoMode.ENCRYPTION_MODE;
		} else if(cm.equals(cm.DECRYPTION_MODE)){
			fragmentcryptoRadioButtonDecMode.setChecked(true);
			fragmentcryptoButtonChooseKey.setText("CHOOSE PRIVATE KEY");
			fragmentcryptoTextViewKeyChoosed.setText("×");
			fragmentcryptoTextViewKeyChoosed.setTextColor(Color.parseColor("#FF0F00"));
			fragmentcryptoButtonChooseFile.setText("CHOOSE FILE TO DECRYPT");
			fragmentcryptoTextViewFileChoosed.setText("×");
			fragmentcryptoTextViewFileChoosed.setTextColor(Color.parseColor("#FF0F00"));
			fragmentcryptoTextViewFileName.setText("File: None");
			fragmentcryptoTextViewKeyName.setText("Key: None");
			fragmentcryptoButtonStart.setText("Decrypt");
			choosedFilePath = "";
			choosedKeyPath = "";
			currentCryptoMode = CryptoMode.DECRYPTION_MODE;
		}
	}

	// Initializing CryptoFragment vars, contents & methods
	private void initCryptoFragmentContents(View view){
		// VARs & Contents
		this.fragmentcryptoRadioButtonEncMode = view.findViewById(R.id.fragmentcryptoRadioButtonEncMode);
		this.fragmentcryptoRadioButtonDecMode = view.findViewById(R.id.fragmentcryptoRadioButtonDecMode);
		this.fragmentcryptoButtonChooseKey = view.findViewById(R.id.fragmentcryptoButtonChooseKey);
		this.fragmentcryptoTextViewKeyChoosed = view.findViewById(R.id.fragmentcryptoTextViewKeyChoosed);
		this.fragmentcryptoButtonChooseFile = view.findViewById(R.id.fragmentcryptoButtonChooseFile);
		this.fragmentcryptoTextViewFileChoosed = view.findViewById(R.id.fragmentcryptoTextViewFileChoosed);
		this.fragmentcryptoTextViewFileName = view.findViewById(R.id.fragmentcryptoTextViewFileName);
		this.fragmentcryptoTextViewKeyName = view.findViewById(R.id.fragmentcryptoTextViewKeyName);
		this.fragmentcryptoButtonStart = view.findViewById(R.id.fragmentcryptoButtonStart);
		
		// Set Encryption mode as a default mode
		setCryptoMode(CryptoMode.ENCRYPTION_MODE);

		// Methods

		// fragmentcryptoRadioButtonEncMode - onCheckedChanged method
		fragmentcryptoRadioButtonEncMode.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if(fragmentcryptoRadioButtonEncMode.isChecked()){
						setCryptoMode(CryptoMode.ENCRYPTION_MODE);
						Toast.makeText(MainActivity.MAContext, "Encryption mode is enabled.", Toast.LENGTH_SHORT).show();
					} else if(fragmentcryptoRadioButtonDecMode.isChecked()){
						setCryptoMode(CryptoMode.DECRYPTION_MODE);
						Toast.makeText(MainActivity.MAContext, "Decryption mode is enabled.", Toast.LENGTH_SHORT).show();
					}
				}
			});

		// fragmentcryptoRadioButtonDecMode - onCheckedChanged method
		fragmentcryptoRadioButtonDecMode.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					if(fragmentcryptoRadioButtonDecMode.isChecked()){
						setCryptoMode(CryptoMode.DECRYPTION_MODE);
						Toast.makeText(MainActivity.MAContext, "Decryption mode is enabled.", Toast.LENGTH_SHORT).show();
					} else if(fragmentcryptoRadioButtonEncMode.isChecked()){
						setCryptoMode(CryptoMode.ENCRYPTION_MODE);
						Toast.makeText(MainActivity.MAContext, "Encryption mode is enabled.", Toast.LENGTH_SHORT).show();
					}
				}
			});
			
		// fragmentcryptoButtonChooseKey - onClick method
		fragmentcryptoButtonChooseKey.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					MainActivity.OFD = new OpenFileDialog(MainActivity.MAContext);
					if(currentCryptoMode.equals(CryptoMode.ENCRYPTION_MODE)){
						MainActivity.OFD.setFilter(".xcq");
					} else if(currentCryptoMode.equals(CryptoMode.DECRYPTION_MODE)){
						MainActivity.OFD.setFilter(".xcp");
					}
					MainActivity.OFD.setOpenDialogListener(new OpenFileDialog.OpenDialogListener(){
						@Override
						public void OnSelectedFile(String fileName)
						{
							if(xCryptoUtil.checkPath(fileName) == true){
								choosedKeyPath = fileName;
								fragmentcryptoTextViewKeyName.setText("Key: " + xCryptoUtil.getFileName(choosedKeyPath));
								fragmentcryptoTextViewKeyChoosed.setText("√");
								fragmentcryptoTextViewKeyChoosed.setTextColor(Color.parseColor("#349A31"));
							}
						}
					});
					MainActivity.OFD.show();
				}
		});
		
		// fragmentcryptoButtonChooseFile - onClick method
		fragmentcryptoButtonChooseFile.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					MainActivity.OFD = new OpenFileDialog(MainActivity.MAContext);
					if(currentCryptoMode.equals(CryptoMode.DECRYPTION_MODE)){
						MainActivity.OFD.setFilter(".xcef");
					}
					MainActivity.OFD.setOpenDialogListener(new OpenFileDialog.OpenDialogListener(){
							@Override
							public void OnSelectedFile(String fileName)
							{
								if(xCryptoUtil.checkPath(fileName) == true){
									choosedFilePath = fileName;
									fragmentcryptoTextViewFileName.setText("File: " + xCryptoUtil.getFileName(choosedFilePath));
									fragmentcryptoTextViewFileChoosed.setText("√");
									fragmentcryptoTextViewFileChoosed.setTextColor(Color.parseColor("#349A31"));
								}
							}
						});
					MainActivity.OFD.show();
				}
		});
		
		// fragmentcryptoButtonStart - onClick method
		fragmentcryptoButtonStart.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					if(!choosedFilePath.equals("") && !choosedKeyPath.equals("")){
						new startCryptoTask().execute();
					} else {
						Toast.makeText(MainActivity.MAContext, "Error: please input all fields.", Toast.LENGTH_SHORT).show();
					}
				}
		});
	}
	
	private void doStartCrypto(){
		if(currentCryptoMode.equals(CryptoMode.ENCRYPTION_MODE)){
			Random prng = xCryptoUtil.createSeededRandom();
			try
			{
				NtruEncryptKey pubKey = xCryptoUtil.loadKey(choosedKeyPath);
				xCryptoUtil.encryptFile(pubKey, prng, choosedFilePath, choosedFilePath + ".xcef");
			}
			catch (NtruException e)
			{
				Toast.makeText(MainActivity.MAContext, "Encryption error.", Toast.LENGTH_SHORT).show();
			}
			catch (IOException e)
			{
				Toast.makeText(MainActivity.MAContext, "File error.", Toast.LENGTH_SHORT).show();
			}
			catch (Exception e)
			{
				Toast.makeText(MainActivity.MAContext, "Error.", Toast.LENGTH_SHORT).show();
			}

		} else if(currentCryptoMode.equals(CryptoMode.DECRYPTION_MODE)){
			try
			{
				NtruEncryptKey privKey = xCryptoUtil.loadKey(choosedKeyPath);
				xCryptoUtil.decryptFile(privKey, choosedFilePath, choosedFilePath.replace(".xcef",""));
			}
			catch (NtruException e)
			{
				Toast.makeText(MainActivity.MAContext, "Decryption error.", Toast.LENGTH_SHORT).show();
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
	}
	
	private class startCryptoTask extends AsyncTask<Void, Void, Void>
	{
		private ProgressDialog pd;

		@Override
		protected void onPreExecute(){ 
			super.onPreExecute();

			pd = new ProgressDialog(MainActivity.MAContext);
			pd.setIndeterminate(true);
			pd.setMessage("Processing file with(NTRU algorithm)...");
			pd.show();    
		}

		@Override
		protected Void doInBackground(Void... params) {
			doStartCrypto();
			return null;
		}

		@Override
		protected void onPostExecute(Void result){
			super.onPostExecute(result);
			pd.dismiss();
			Toast.makeText(MainActivity.MAContext, "Done.", Toast.LENGTH_SHORT).show();
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
        View view = inflater.inflate(R.layout.fragment_crypto, container, false);
		initCryptoFragmentContents(view);
		return view;
    }
	
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_crypto_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
			case R.id.action_encrypt:
				//Encrypt
				setCryptoMode(CryptoMode.ENCRYPTION_MODE);
                return true;
			case R.id.action_decrypt:
				//Decrypt
				setCryptoMode(CryptoMode.DECRYPTION_MODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	

}
