package com.insightsurfface.myword.utils;

import android.util.Log;

public class Logger {
  public static void d(String d) {
    Log.d("myword", d);
  }

  public static void i(String i) {
    Log.i("myword", i);
  }

  public static void d(Double d) {

    d(String.valueOf(d));
  }

  public static void d(int d) {
    d(String.valueOf(d));
  }
}