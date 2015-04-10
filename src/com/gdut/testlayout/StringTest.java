package com.gdut.testlayout;

import android.util.Log;

public class StringTest {

	static {
		System.loadLibrary("StringTest");
	}
	
	private native String jnitest();
	
	public String getstring(){
		Log.d("zhiqiang", "jni");
		return jnitest();
	}
}
