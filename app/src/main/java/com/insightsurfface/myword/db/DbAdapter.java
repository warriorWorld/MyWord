package com.insightsurfface.myword.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.insightsurfface.myword.bean.WordsBookBean;
import com.insightsurfface.myword.config.ShareKeys;
import com.insightsurfface.myword.utils.FileSpider;
import com.insightsurfface.myword.utils.SharedPreferencesUtils;

import java.util.ArrayList;


public class DbAdapter {
  private DbHelper dbHelper;
  private SQLiteDatabase db;
  private Context mContext;

  public DbAdapter(Context context) {
    mContext = context;
    dbHelper = new DbHelper(context);
    db = dbHelper.getWritableDatabase();
  }

  /**
   * 插入一条生词信息
   */
  public void insertWordsBookTb(String word, String examplePath) {
    int time = queryQueryedTime(word);
    if (time > 0) {
      //如果查过这个单词 那就update 并且time+1
      time++;
      updateTimeTOWordsBook(word, examplePath, time);
    } else {
      db.execSQL(
              "insert into WordsBook (word,example_path,time) values (?,?,?)",
              new Object[]{word, examplePath, 1});
    }
  }

  /**
   * 更新生词信息
   */
  public void updateTimeTOWordsBook(String word, String examplePath, int time) {
    db.execSQL("update WordsBook set time=? where word=?",
            new Object[]{time, word});
    db.execSQL("update WordsBook set example_path=? where word=?",
            new Object[]{time, examplePath});
  }

  /**
   * 查询所有生词
   *
   * @return
   */
  public ArrayList<WordsBookBean> queryAllWordsBook() {
    ArrayList<WordsBookBean> resBeans = new ArrayList<WordsBookBean>();
    Cursor cursor = db
            .query("WordsBook", null, null, null, null, null, "createdtime desc");
    long currentTime = System.currentTimeMillis();
    int killPeriod = SharedPreferencesUtils.getIntSharedPreferencesData(mContext, ShareKeys.KILL_PERIOD_KEY, 6);
    int minGapTime = killPeriod * 60 * 60 * 1000;

    while (cursor.moveToNext()) {
      String word = cursor.getString(cursor.getColumnIndex("word"));
      int time = cursor
              .getInt(cursor.getColumnIndex("time"));
      String examplePath = cursor.getString(cursor.getColumnIndex("example_path"));
      long lastKillTime = 0;
      try {
        lastKillTime = cursor.getLong(cursor.getColumnIndex("update_time"));
      } catch (Exception e) {
        e.printStackTrace();
      }
      int killTime = 0;
      try {
        killTime = cursor.getInt(cursor.getColumnIndex("kill_time"));
      } catch (Exception e) {
        e.printStackTrace();
      }
      //随着kill次数 间隔时长乘数递增
      long minTime = currentTime - minGapTime * killTime;
      if (killTime > 0 && minTime < lastKillTime) {
        //不显示 kill过的并且时间未超过时长的
      } else {
        WordsBookBean item = new WordsBookBean();
        item.setWord(word);
        item.setTime(time);
        item.setExample_path(examplePath);
        item.setUpdate_time(lastKillTime);
        item.setKill_time(killTime);
        resBeans.add(item);
      }
    }
    cursor.close();
    return resBeans;
  }

  /**
   * 查询是否查询过
   */
  public boolean queryQueryed(String word) {
    Cursor cursor = db.rawQuery(
            "select word from WordsBook where word=?",
            new String[]{word});
    int count = cursor.getCount();
    cursor.close();
    if (count > 0) {
      return true;
    } else {
      return false;
    }
  }

  public int queryQueryedTime(String word) {
    int res = 0;
    Cursor cursor = db.rawQuery(
            "select time from WordsBook where word=?",
            new String[]{word});
    int count = cursor.getCount();
    if (count > 0) {
      while (cursor.moveToNext()) {
        res = cursor.getInt(cursor.getColumnIndex("time"));
      }
    }
    cursor.close();
    return res;
  }

  public String queryExamplePath(String word) {
    String res;
    Cursor cursor = db.rawQuery(
            "select example_path from WordsBook where word=?",
            new String[]{word});
    res = cursor.getString(cursor.getColumnIndex("example_path"));
    cursor.close();
    return res;
  }

  public int queryKilledTime(String word) {
    int res = 0;
    Cursor cursor = null;
    try {
      cursor = db.rawQuery(
              "select kill_time from WordsBook where word=?",
              new String[]{word});
      int count = cursor.getCount();
      if (count > 0) {
        while (cursor.moveToNext()) {
          res = cursor.getInt(cursor.getColumnIndex("kill_time"));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != cursor) {
        cursor.close();
      }
    }
    return res;
  }

  public void killWordByWord(String word) {
    int time = queryKilledTime(word);
    time++;
    int killableTime = SharedPreferencesUtils.getIntSharedPreferencesData(mContext, ShareKeys.KILLABLE_TIME_KEY, 3);
    if (time >= killableTime) {
      deleteWordByWord(word);
    } else {
      db.execSQL("update WordsBook set kill_time=?,update_time=? where word=?",
              new Object[]{time, System.currentTimeMillis(), word});
    }
  }

  /**
   * 删除生词
   */
  public void deleteWordByWord(String word) {
    FileSpider.getInstance().deleteFile(queryExamplePath(word));
    db.execSQL("delete from WordsBook where word=?",
            new Object[]{word});
  }

  public void closeDb() {
    if (null != db) {
      db.close();
    }
  }
}