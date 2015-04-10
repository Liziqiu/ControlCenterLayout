package com.gdut.testlayout;

import com.gdut.adil.AIDLBitmap;
import com.gdut.adil.PhoneService;
import com.gdut.graphics.GaussianBlur;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;

public class handleservice extends Service{

	private WindowManager wm;
	private WindowManager.LayoutParams  wmParams=null;
	
	private View handle;
	private View contain;
	private View BlackView;
	private LinearLayout center_my;
	
	private ClipDrawable clip=null;
	private GaussianBlur GBlur;
	private AbsoluteLayout abslay;
	private AbsoluteLayout.LayoutParams lp ;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		bindAidlService(this);
		GBlur = new GaussianBlur(15f,0.25f,this);
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
					wmParams.width=WindowManager.LayoutParams.MATCH_PARENT;
					wmParams.height=WindowManager.LayoutParams.MATCH_PARENT;
					AIDLBitmap bitmap=null;
					try {
						bitmap = mService.ScreenCap(360, 640);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(bitmap != null){
						clip = new ClipDrawable( new BitmapDrawable(GBlur.androidblur(bitmap.getBitmap())), Gravity.BOTTOM, 2);
						contain.setBackground(clip);
					}
					wm.addView(contain, wmParams);
					break;
				case MotionEvent.ACTION_MOVE:
					Log.d("zhiqiang","handle ACTION_MOVE:"+ev.getRawY());
					containmove((int) ev.getRawY());
					break;
				case MotionEvent.ACTION_UP:
					Log.d("zhiqiang","handle ACTION_UP");
					toggle((int) ev.getRawY());
					break;
				
				}
				return true;
			}
		});
		contain = LayoutInflater.from(this).inflate(R.layout.controlcenter, null);
		abslay = (AbsoluteLayout) contain.findViewById(R.id.contain);
		center_my = (LinearLayout) contain.findViewById(R.id.center_my);
		Button g = new Button(this);
		g.setText("add");
		g.setAlpha(0.2f);
		center_my.addView(g);
		g.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				wm.removeViewImmediate(contain);
				
			}
		});
		lp = (AbsoluteLayout.LayoutParams) center_my.getLayoutParams();
	}

	@Override
	public void onDestroy() {
		unBundAidlService(this);
		super.onDestroy();
	}

	protected void toggle(int rawY) {
		if(contain.getHeight()-rawY>=center_my.getHeight()/2){
			containmove(contain.getHeight()-center_my.getHeight());
		}else{
			wm.removeViewImmediate(contain);
		}
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("zhiqiang","onStartCommand");
		wm.addView(handle, wmParams);
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void containmove(int move){
		Log.d("zhiqiang", "move:"+move);
		int whilo = contain.getHeight();
		int content = center_my.getHeight();
		Log.d("zhiqiang", "whilo ="+whilo+" and content:"+content);
		int dis = whilo-content;
		int show=0;
		if(move>=dis){
			if(whilo != 0){
				show = (whilo-move)*10000/whilo;
			}else{
				show = 0;
			}
			Log.d("zhiqiang", "show:"+show);
			if(clip != null){
				clip.setLevel(show);
			}
			lp.x=1;
			lp.y=move;
			Log.d("zhiqiang","handle MOVE:"+center_my.getHeight());
			
			contain.setLayoutParams(lp);
			contain.invalidate();
		}
	}
	private  ServiceConnection mConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder mbinder) {
			Log.d("zhiqiang","onServiceConnected");
			mService = PhoneService.Stub.asInterface(mbinder);
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.d("zhiqiang","onServiceDisconnected");

		}
		
	};
	private  PhoneService mService;
	public  void bindAidlService(Context context){// throws IllegalStateException{
			Intent i = new Intent("com.gdut.adil.mAIDLService");
			context.bindService(i, mConnection, Context.BIND_AUTO_CREATE);

	}
	public  void unBundAidlService(Context context){
			context.unbindService(mConnection);
	}
}
