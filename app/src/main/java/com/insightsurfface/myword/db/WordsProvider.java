package com.insightsurfface.myword.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class WordsProvider extends ContentProvider {
  private Context mContext;
  DbHelper mDbHelper = null;
  SQLiteDatabase db = null;
  public static final String AUTOHORITY = "com.insightsurfface.myword";
  // 设置ContentProvider的唯一标识
  public static final int Word_Code = 1;
  // UriMatcher类使用:在ContentProvider 中注册URI
  private static final UriMatcher mMatcher;

  static {
    mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    // 初始化
    mMatcher.addURI(AUTOHORITY, DbHelper.WORDS_TABLE_NAME, Word_Code);
    // 若URI资源路径 = content://cn.scu.myprovider/user ，则返回注册码User_Code
    // 若URI资源路径 = content://cn.scu.myprovider/job ，则返回注册码Job_Code
  }

  // 以下是ContentProvider的6个方法

  /**
   * 初始化ContentProvider
   */
  @Override
  public boolean onCreate() {

    mContext = getContext();
    // 在ContentProvider创建时对数据库进行初始化
    // 运行在主线程，故不能做耗时操作,此处仅作展示
    mDbHelper = new DbHelper(getContext());
    db = mDbHelper.getWritableDatabase();
    return true;
  }

  /**
   * 添加数据
   */

  @Override
  public Uri insert(Uri uri, ContentValues values) {

    // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
    // 该方法在最下面
    String table = getTableName(uri);

    // 向该表添加数据
    db.insert(table, null, values);

    // 当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
    mContext.getContentResolver().notifyChange(uri, null);

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

    return uri;
  }

  /**
   * 查询数据
   */
  @Override
  public Cursor query(Uri uri, String[] projection, String selection,
                      String[] selectionArgs, String sortOrder) {
    // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
    // 该方法在最下面
    String table = getTableName(uri);

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

    // 查询数据
    return db.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
  }

  /**
   * 更新数据
   */
  @Override
  public int update(Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {
    return db.update(getTableName(uri), values, selection, selectionArgs);
  }

  /**
   * 删除数据
   */
  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    //参数1：表名   参数2：约束删除列的名字   参数3：具体行的值
    return db.delete(getTableName(uri), selection, selectionArgs);
  }

  @Override
  public String getType(Uri uri) {
    // 由于不展示,此处不作展开
    return null;
  }

  /**
   * 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
   */
  private String getTableName(Uri uri) {
    String tableName = null;
    switch (mMatcher.match(uri)) {
      case Word_Code:
        tableName = DbHelper.WORDS_TABLE_NAME;
        break;
    }
    return tableName;
  }
}