package com.example.takephoto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;
import android.provider.MediaStore;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

@SuppressLint("ValidFragment")
public class MainActivity extends ActionBarActivity {

	private static final int REQUEST_CODE_PHOTO = 55566;

	private ImageView imageView;
	private TextView textView;
	private Uri outputFileUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		Parse.initialize(this, "7nPKBJx6Q0HYZ0LImZUjOKGG6U2d0novRlZKIsXZ",
				"GxZNZx4F1YPpBq0FzSJHSRqx6A17nu4T47ygwI3e");
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
		if (id == R.id.action_photo) {
			outputFileUri = Uri.fromFile(getTargetFile());
			Intent intent = new Intent();
			intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			startActivityForResult(intent, REQUEST_CODE_PHOTO);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		Log.d("debug", "onActivityResult");

		if (requestCode == REQUEST_CODE_PHOTO) {

			if (resultCode == RESULT_OK) {
				// Bitmap bitmap = intent.getParcelableExtra("data");
				// imageView.setImageBitmap(bitmap);
				// save(bitmap);
				imageView.setImageURI(outputFileUri);
				textView.setText(outputFileUri.getPath());
				saveToParse();
				Log.d("debug", "OK");

			} else if (resultCode == RESULT_CANCELED) {

				Log.d("debug", "CANCELED");

			} else {

			}
		}
	}

	private File getTargetFile() {
		File imageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		if (imageDir.exists() == false) {
			imageDir.mkdir();
		}
		return new File(imageDir, "photo.png");
	}

	private void saveToParse() {

		File file = getTargetFile();
		byte[] data = new byte[(int) file.length()];

		try {

			FileInputStream fis = new FileInputStream(file);
			fis.read(data);

			int offset = 0;
			int numRead = 0;

			while ((numRead = fis.read(data, offset, data.length - offset)) != -1) {

				offset += numRead;

				fis.close();

			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		final ParseFile parseFile = new ParseFile("photo.png", data);

		// parseFile.saveInBackground(new SaveCallback() {

		ParseObject object = new ParseObject("photo");
		object.put("file", parseFile);
		object.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException arg0) {

				Log.d("debug", parseFile.getUrl());

			}

		});

	}

	private void save(Bitmap bitmap) {
		// TODO Auto-generated method stub

		File imageFile = getTargetFile();
		try {
			FileOutputStream fos = new FileOutputStream(imageFile);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		textView.setText(imageFile.getPath());

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public class PlaceholderFragment extends Fragment {

		@SuppressLint("ValidFragment")
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			imageView = (ImageView) rootView.findViewById(R.id.imageView1);
			textView = (TextView) rootView.findViewById(R.id.textView1);
			return rootView;
		}
	}

}
