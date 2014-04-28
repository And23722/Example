package com.example.simpleui;



import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MessageActivity extends Activity {

	private TextView textView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);
		boolean isChecked = getIntent().getBooleanExtra("checkBox", false); 
		textView = (TextView) findViewById(R.id.textView1);
		String text = getIntent().getStringExtra("text");

		ParseObject testObject = new ParseObject("MessageObject");
		testObject.put("text", text);
		testObject.put("checkBox", isChecked);
		testObject.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e){
				
				if (e == null) {
					
					Log.d("debug", "OK");
					
				}else {
					
					e.printStackTrace();
				}
				
				ok();
			}

		});
		
		textView.setText(text);
	}

	private void ok() {
		
		Toast.makeText(this, "done.", Toast.LENGTH_SHORT).show();
		
	}

}
