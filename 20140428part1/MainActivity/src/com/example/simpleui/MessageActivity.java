package com.example.simpleui;



import com.parse.ParseObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MessageActivity extends Activity {

	private TextView textView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message);
		textView = (TextView) findViewById(R.id.textView1);
		String text = getIntent().getStringExtra("text");

		ParseObject testObject = new ParseObject("MessageObject");
		testObject.put("text", text);
		testObject.saveInBackground();
		textView.setText(text);
	}

	

}
