package com.study.okhttpdemo.xutils.utils;

import android.util.Log;


/** 日志 */
public class LogUtil {
	
	private static boolean DEBUG = true;

	/**
	 * @author zzg
	 * @param PAGETAG
	 * @param msg
	 */
	public static void i(String PAGETAG, String msg){
		if(DEBUG && msg != null ){
			Log.i(PAGETAG, msg);
		}
	}

	/**
	 * @author zzg
	 * @param PAGETAG
	 * @param msg
	 * @param e
	 */
	public static void i(String PAGETAG, String msg, Throwable e) {
		if(DEBUG && msg != null ){
			Log.i(PAGETAG, msg, e);
		}
	}

	/**
	 * @author zzg
	 * @param obj 当前类的实例
	 * @param msg
	 */
	public static void i(Object obj, String msg) {
		if(DEBUG && msg != null && obj != null){
			String PAGETAG = "PAGETAG" ;
			PAGETAG = StringUtils.subFromEndToStart(obj.getClass().getName(), ".") ;
			Log.i(PAGETAG, msg);
		}
	}

	/**
	 * @author zzg
	 * @param PAGETAG
	 * @param msg
	 */
	public static void d(String PAGETAG, String msg) {
		if(DEBUG && msg != null ){
			Log.d(PAGETAG, msg);
		}
	}

	/**
	 * @author zzg
	 * @param obj 当前类的实例
	 * @param msg
	 */
	public static void d(Object obj, String msg) {
		if(DEBUG && msg != null && obj != null){
			String PAGETAG = "PAGETAG" ;
			PAGETAG = StringUtils.subFromEndToStart(obj.getClass().getName(), ".") ;
			Log.d(PAGETAG, msg);
		}
	}

	/**
	 * @author zzg
	 * @param PAGETAG = LogE = "zzg_EXCEPTION" ;
	 * @param msg
	 */
	public static void e(String PAGETAG, String msg) {

		if(DEBUG && msg != null ){
			Log.e(PAGETAG, msg);
		}
	}

	/**
	 * @author zzg
	 * @param msg
	 */
	public static void e(Object obj, String msg) {
		if(DEBUG && msg != null && obj != null){
			String PAGETAG = "PAGETAG" ;
			PAGETAG = StringUtils.subFromEndToStart(obj.getClass().getName(), ".") ;
			Log.e(PAGETAG, msg);
		}
	}

	/**
	 * @author zzg
	 * @param PAGETAG
	 * @param msg
	 * @param e
	 */
	public static void e(String PAGETAG, String msg, Throwable e) {

		if(DEBUG && msg != null ){
			Log.e(PAGETAG, msg, e);
		}
	}


	/**
	 * @author zzg
	 * @param PAGETAG
	 * @param msg
	 */
	public static void v(String PAGETAG, String msg) {
		if(DEBUG && msg != null ){
			Log.v(PAGETAG, msg);
		}
	}

	/**
	 * @author zzg
	 * @param PAGETAG
	 * @param msg
	 * @param e
	 */
	public static void w(String PAGETAG, String msg, Exception e) {
		if(DEBUG && msg != null ){
			Log.w(PAGETAG, msg, e);
		}
	}

	/**
	 * @author zzg
	 * @param PAGETAG
	 * @param msg
	 */
	public static void w(String PAGETAG, String msg) {
		if(DEBUG && msg != null ){
			Log.w(PAGETAG, msg) ;
		}
	}
	/**
	 * @author zzg
	 * @param msg
	 */
	public static void w(Object obj, String msg) {
		if(DEBUG && msg != null && obj != null){
			String PAGETAG = "PAGETAG" ;
			PAGETAG = StringUtils.subFromEndToStart(obj.getClass().getName(), ".") ;
			Log.w(PAGETAG, msg);
		}
	}

	/**
	 * @author zzg
	 * @param PAGETAG
	 * @param msg
	 * @param cause 原因
	 */
	public static void w(String PAGETAG, String msg, Throwable cause) {
		if(DEBUG && msg != null ){
			Log.w(PAGETAG, msg, cause);
		}
	}

}
