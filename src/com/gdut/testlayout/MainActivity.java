package com.gdut.testlayout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private AbsoluteLayout.LayoutParams lp ;
	private AbsoluteLayout ly;
	private View view;
	private WindowManager wm;
	private Button btn_start;
	private WindowManager.LayoutParams  wmParams=null;
	
	private View handle;
	private View contain;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		btn_start=(Button) this.findViewById(R.id.start);
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent service = new Intent(MainActivity.this,com.gdut.testlayout.handleservice.class);
				MainActivity.this.startService(service);
			}
		});
		StringTest t = new StringTest();
		Toast.makeText(this, t.getstring(), Toast.LENGTH_LONG).show();
		//dosomthine();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		wm = (WindowManager) this.getApplicationContext().getSystemService("window");
		wmParams =new WindowManager.LayoutParams();
		wmParams.type=WindowManager.LayoutParams.TYPE_STATUS_BAR_OVERLAY;
		//wmParams.type=WindowManager.LayoutParams.TYPE_APPLICATION ;
		wmParams.flags|=8;
		wmParams.gravity = Gravity.LEFT | Gravity.TOP;
		wmParams.x=100;
		wmParams.y=1920;
		wmParams.width=WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
		wmParams.format=1;
		
		handle = LayoutInflater.from(this).inflate(R.layout.handle, null);
		handle.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				switch(ev.getAction()){
				case MotionEvent.ACTION_DOWN:
					Log.d("zhiqiang","handle down");
					wmParams.x=100;
					wmParams.y=100;
					wm.addView(contain, wmParams);
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					break;
				
				}
				return true;
			}
		});
		contain = LayoutInflater.from(this).inflate(R.layout.controlcenter, null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.d("zhiqiang","onTouchEvent");
		//if()
		/*lp.x=(int) event.getRawX()/2;
		lp.y=(int) event.getRawY()/2;
		//ly.addView(view);
		view.setLayoutParams(lp);
		view.invalidate();*/
		return false;
	}

	public void dosomthine(){
		ly = (AbsoluteLayout) this.findViewById(R.id.mainlayout);
		view = LayoutInflater.from(this).inflate(R.layout.fglayout, null);
		lp = (AbsoluteLayout.LayoutParams) ly.getLayoutParams();
		lp.x=20;
		lp.y=100;
		ly.addView(view);
		view.setLayoutParams(lp);
		view.invalidate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}



	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	

}
