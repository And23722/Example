package com.example.calculator;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

@SuppressWarnings("unused")
public class MainActivity extends ActionBarActivity {

	//public static final OnClickListener compute = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		// 宣告0~9 , 小數點
		private Button but0, but1, but2, but3, but4, but5, but6, but7, but8,
				but9, butdot;
		// 加,減,乘,除 四大按鈕
		private Button butPlus, butMinus, butMultiply, butExcept;
		// 清除,等於
		private Button butClear, butEqual;
		// 顯是數值
		private EditText editText1;

		private double s;

		private double n1;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			butdot = (Button) rootView.findViewById(R.id.butdot);
			but0 = (Button) rootView.findViewById(R.id.but0);
			but1 = (Button) rootView.findViewById(R.id.but1);
			but2 = (Button) rootView.findViewById(R.id.but2);
			but3 = (Button) rootView.findViewById(R.id.but3);
			but4 = (Button) rootView.findViewById(R.id.but4);
			but5 = (Button) rootView.findViewById(R.id.but5);
			but6 = (Button) rootView.findViewById(R.id.but6);
			but7 = (Button) rootView.findViewById(R.id.but7);
			but8 = (Button) rootView.findViewById(R.id.but8);
			but9 = (Button) rootView.findViewById(R.id.but9);

			butPlus = (Button) rootView.findViewById(R.id.butPlus);
			butMinus = (Button) rootView.findViewById(R.id.butMinus);
			butMultiply = (Button) rootView.findViewById(R.id.butMultiply);
			butExcept = (Button) rootView.findViewById(R.id.butExcept);

			butClear = (Button) rootView.findViewById(R.id.butClear);
			butEqual = (Button) rootView.findViewById(R.id.butEqual);

			editText1 = (EditText) rootView.findViewById(R.id.editText1);

			butdot.setOnClickListener(InputNumber);
			but0.setOnClickListener(InputNumber);
			but1.setOnClickListener(InputNumber);
			but2.setOnClickListener(InputNumber);
			but3.setOnClickListener(InputNumber);
			but4.setOnClickListener(InputNumber);
			but5.setOnClickListener(InputNumber);
			but6.setOnClickListener(InputNumber);
			but7.setOnClickListener(InputNumber);
			but8.setOnClickListener(InputNumber);
			but9.setOnClickListener(InputNumber);

			butPlus.setOnClickListener(compute);
			butMinus.setOnClickListener(compute);
			butMultiply.setOnClickListener(compute);
			butExcept.setOnClickListener(compute);

			butEqual.setOnClickListener(Equal);
			butClear.setOnClickListener(Clear);

			return rootView;
		}
		
		// 按鈕0-9 及 小數點的輸入
		private Button.OnClickListener InputNumber = new Button.OnClickListener() {
			
			public void onClick(View v) {// 偵測按下哪個按鍵
			
				String key = editText1.getText().toString();

				switch (v.getId()) {
				
				case R.id.butdot: {
					editText1.setText(key + ".");
					break;
				}
				
				case R.id.but0: {
					editText1.setText(key + "0");
					break;
				}
				
				case R.id.but1: {
					editText1.setText(key + "1");
					break;
				}
				
				case R.id.but2: {
					editText1.setText(key + "2");
					break;
				}
				
				case R.id.but3: {
					editText1.setText(key + "3");
					break;
				}
				
				case R.id.but4: {
					editText1.setText(key + "4");
					break;
				}
				
				case R.id.but5: {
					editText1.setText(key + "5");
					break;
				}
				
				case R.id.but6: {
					editText1.setText(key + "6");
					break;
				}
				
				case R.id.but7: {
					editText1.setText(key + "7");
					break;
				}
				
				case R.id.but8: {
					editText1.setText(key + "8");
					break;
				}
				
				case R.id.but9: {
					editText1.setText(key + "9");
					break;
				}
				

				}

			}

		};
		
		//判斷按下 加,減,乘,除
		private Button.OnClickListener compute = new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				n1 = Double.parseDouble(editText1.getText().toString());
				switch (v.getId()) {
				case R.id.butPlus: {
					s = 1;
					editText1.setText("");
					break;
				}
				case R.id.butMinus: {
					s = 2;
					editText1.setText("");
					break;
				}
				case R.id.butMultiply: {
					s = 3;
					editText1.setText("");
					break;
				}
				case R.id.butExcept: {
					s = 4;
					editText1.setText("");
					break;
				}
				}

			}
		};

		//按下等於鈕後 做運算
		private Button.OnClickListener Equal = new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				double n2 = Double.parseDouble(editText1.getText().toString());
				DecimalFormat ft = new DecimalFormat("0");
				if (s == 1) {
					editText1.setText(ft.format(n1 + n2));
				} else if (s == 2) {
					editText1.setText(ft.format(n1 - n2));
				} else if (s == 3) {
					editText1.setText(ft.format(n1 * n2));
				} else if (s == 4) {
					editText1.setText(ft.format(n1 / n2));
				}

			}
		};
		
		//清空
		private Button.OnClickListener Clear = new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editText1.setText("");
			}
		};
	}

}