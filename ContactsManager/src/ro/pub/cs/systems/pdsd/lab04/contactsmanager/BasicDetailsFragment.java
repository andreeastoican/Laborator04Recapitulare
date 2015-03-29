package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BasicDetailsFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_basic_details, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Button showHideButton = (Button) getActivity().findViewById(R.id.show_hide);
		Button saveButton = (Button) getActivity().findViewById(R.id.save_button);
		Button cancelButton = (Button) getActivity().findViewById(R.id.cancel_button);
		
		showHideButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				
				AdditionalDetailsFragment additionalDetailsFragment = (AdditionalDetailsFragment)fragmentManager.findFragmentById(R.id.containerBottom);
				
				if(additionalDetailsFragment== null) {
					fragmentTransaction.add(R.id.containerBottom, new AdditionalDetailsFragment());
					fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
					fragmentTransaction.commit();
					
					((Button)v).setText("Hide Additional Fileds");
				} else {
					fragmentTransaction.remove(additionalDetailsFragment);
					fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
					fragmentTransaction.commit();
					
					((Button)v).setText("Show Additional Fileds");
				}
				
			}
		});
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String name = null, phone = null,email = null,address = null,jobTitle = null,company = null,website = null,im = null;
				
				EditText nameEditText = (EditText) getActivity().findViewById(R.id.name);
				if(nameEditText!=null) {
					name=nameEditText.getText().toString();
				}
				
				EditText phoneEditText = (EditText) getActivity().findViewById(R.id.phone_number);
				if(phoneEditText!=null) {
					phone=phoneEditText.getText().toString();
				}
				
				EditText emailEditText = (EditText) getActivity().findViewById(R.id.email);
				if(emailEditText!=null) {
					email=emailEditText.getText().toString();
				}
				
				EditText addressEditText = (EditText) getActivity().findViewById(R.id.address);
				if(addressEditText!=null) {
					address=addressEditText.getText().toString();
				}
				
				EditText jobEditText = (EditText) getActivity().findViewById(R.id.job_title);
				if(jobEditText!=null) {
					jobTitle=jobEditText.getText().toString();
				}
				
				EditText companyEditText = (EditText) getActivity().findViewById(R.id.company);
				if(companyEditText!=null) {
					company=companyEditText.getText().toString();
				}
				
				EditText websiteEditText = (EditText) getActivity().findViewById(R.id.website);
				if(websiteEditText!=null) {
					website=websiteEditText.getText().toString();
				}
				
				EditText imEditText = (EditText) getActivity().findViewById(R.id.im);
				if(imEditText!=null) {
					im=imEditText.getText().toString();
				}
				
				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
				if (name != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
				}
				if (phone != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
				}
				if (email != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
				}
				if (address != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
				}
				if (jobTitle != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
				}
				if (company != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
				}
				ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
				if (website != null) {
				  ContentValues websiteRow = new ContentValues();
				  websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
				  websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
				  contactData.add(websiteRow);
				}
				if (im != null) {
				  ContentValues imRow = new ContentValues();
				  imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
				  imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
				  contactData.add(imRow);
				}
				intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
				//getActivity().startActivity(intent);
				
				getActivity().startActivityForResult(intent, 2014);
				
			}
		});
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();
				
				getActivity().setResult(Activity.RESULT_CANCELED, new Intent());

			}
		});
		
		Intent intent = getActivity().getIntent();
		if(intent!=null) {
			 String phone = intent.getStringExtra("ro.pub.cs.systems.pdsd.lab04.phoneNumber");
			 
			 EditText phoneEditText = (EditText) getActivity().findViewById(R.id.phone_number);
			 
			 if (phone != null) {
			    phoneEditText.setText(phone);
			  } else {
			    Activity activity = getActivity();
			    Toast.makeText(activity, "error", Toast.LENGTH_LONG).show();
			  }
		}
	}
}
