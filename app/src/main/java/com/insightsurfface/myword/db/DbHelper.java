package com.insightsurfface.myword.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
  private Context xcontext;
  // 数据库名
  private static final String DATABASE_NAME = "myword.db";
  // 表名
  public static final String WORDS_TABLE_NAME = "WordsBook";
  private static final int DATABASE_VERSION = 1;
  public static final String WORDS_BOOK = "create table if not exists " + WORDS_TABLE_NAME + " ("
          + "id integer primary key autoincrement,"
          + "word text," + "time integer," + "kill_time integer," + "update_time long," + "example_path text," + "createdtime TimeStamp NOT NULL DEFAULT (datetime('now','localtime')))";

  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    xcontext = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(WORDS_BOOK);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}