package com.insightsurfface.myword.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class DbController {
    private final String DB_NAME = "words.db";
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private WordsDao mWordsDao;
    private WordsBookDao mWordsBookDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context) {
        if (mDbController == null) {
            synchronized (DbController.class) {
                if (mDbController == null) {
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public DbController(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        mDaoMaster = new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        mWordsDao = mDaoSession.getWordsDao();
        mWordsBookDao = mDaoSession.getWordsBookDao();
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     */
    public void insertOrReplace(WordsBook wordsBook) {
        mWordsBookDao.insertOrReplace(wordsBook);
    }

    /**
     * 插入一条记录，表里面要没有与之相同的记录
     */
    public long insert(WordsBook wordsBook) {
        return mWordsBookDao.insert(wordsBook);
    }

    /**
     * 更新数据
     */
    public void update(WordsBook wordsBook) {
        WordsBook mOldPersonInfor = mWordsBookDao.queryBuilder().where(WordsBookDao.Properties.Id.eq(wordsBook.getId())).build().unique();//拿到之前的记录
        if (mOldPersonInfor != null) {
            mOldPersonInfor.setName("张三");
            mWordsBookDao.update(mOldPersonInfor);
        }
    }

    /**
     * 按条件查询数据
     */
//    public List<WordsBook> searchByWhere(String wherecluse) {
//        List<PersonInfor> personInfors = (List<PersonInfor>) personInforDao.queryBuilder().where(PersonInforDao.Properties.Name.eq(wherecluse)).build().unique();
//        return personInfors;
//    }

    /**
     * 查询所有数据
     */
    public List<WordsBook> querryAllWordsBook() {
        return mWordsBookDao.queryBuilder().list();
    }

    /**
     * 删除数据
     */
    public void deleteWordsBookById(long id) {
        mWordsBookDao.queryBuilder().where(WordsBookDao.Properties.Id.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}
