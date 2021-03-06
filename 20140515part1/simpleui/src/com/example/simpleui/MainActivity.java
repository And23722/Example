package com.example.simpleui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;
import android.provider.Settings.Secure;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.PushService;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		Parse.initialize(this, "ZTzbLIoe6Ii7h7Ec7K2GbtgaldLB7Xqci6YWBuOR", "BJhCOzHvfIiWbqGT7npctVLfarCvME7ToFK7WpZO");
		PushService.setDefaultPushCallback(this, MainActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
		PushService.subscribe(this, "all", MainActivity.class);
		PushService.subscribe(this, "id_" + getDeviceId(), MainActivity.class);
		//���U
		register();
	
	}

	@Override
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private String getDeviceId() {
		return Secure.getString(getContentResolver(), Secure.ANDROID_ID);
	}
	
	private void register() {
		String id = getDeviceId();
		ParseObject object = new ParseObject("DeviceId");
		object.put("deviceId", id);
		object.saveInBackground();
	}



	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private Button button;
		private EditText editText;
		private CheckBox checkBox;
		private SharedPreferences sp;
		private SharedPreferences.Editor editor;
		private Spinner spinner;

		public PlaceholderFragment() {
		}

		private void send() {
			String text = editText.getText().toString();
			if (checkBox.isChecked()) {
				text = "***********";
			}
			Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
			editText.setText("");
			
			String deviceId = (String) spinner.getSelectedItem();
			
			ParsePush push = new ParsePush();
			push.setChannel("id_" + deviceId);
			push.setMessage(text);
			push.sendInBackground();
			
			Intent intent = new Intent();
			intent.setClass(getActivity(), MessageActivity.class);
			intent.putExtra("text", text);
			intent.putExtra("checkBox", checkBox.isChecked());
			getActivity().startActivity(intent);
		}
		
		private void loadDeviceId() {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("DeviceId");
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> objects, ParseException e) {

					Set<String> set = new HashSet<String>();
					for (int i = 0; i < objects.size(); i++) {
						set.add(objects.get(i).getString("deviceId"));
					}
					String[] deviceId = new String[set.size()];

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							getActivity(),
							android.R.layout.simple_spinner_item, set
									.toArray(deviceId));
					spinner.setAdapter(adapter);
				}
			});
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			sp = getActivity()
					.getSharedPreferences("settings", Context.MODE_PRIVATE);
			editor = sp.edit();
			
			button = (Button) rootView.findViewById(R.id.button1);
			editText = (EditText) rootView.findViewById(R.id.editText1);
			checkBox = (CheckBox) rootView.findViewById(R.id.checkBox1);
			
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					editor.putBoolean("checkBox", isChecked);
					editor.commit();
					
				}
				
			});

			editText.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {

					if (event.getAction() == KeyEvent.ACTION_DOWN
							&& keyCode == KeyEvent.KEYCODE_ENTER) {

						send();
						return true;

					}else{
						editor.putString("text", editText.getText().toString());
						editor.commit();
					}

					return false;
					// TODO Auto-generated method stub

				}

			});
			button.setText("Send");

			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					send();
				}

			});
			
			editText.setText(sp.getString("text",""));
			checkBox.setChecked(sp.getBoolean("checkBox",false));
			
			spinner = (Spinner) rootView.findViewById(R.id.spinner1);
			loadDeviceId();

			return rootView;
		}
	}

}
