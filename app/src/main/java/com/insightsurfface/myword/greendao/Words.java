package com.insightsurfface.myword.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Words {
    @Id(autoincrement = true)//设置自增长
    private long id;
    private long fk_bookId;//外键
    @Index(unique = true)//设置唯一性
    private String word;
    private int recognize_time;
    private String translate;
    private int review_time;
    @ToOne(joinProperty = "fk_bookId")
    private WordsBook wordsBook;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1243070387)
    private transient WordsDao myDao;

    @Generated(hash = 1324748466)
    public Words(long id, long fk_bookId, String word, int recognize_time,
                 String translate, int review_time) {
        this.id = id;
        this.fk_bookId = fk_bookId;
        this.word = word;
        this.recognize_time = recognize_time;
        this.translate = translate;
        this.review_time = review_time;
    }

    @Generated(hash = 796553661)
    public Words() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFk_bookId() {
        return this.fk_bookId;
    }

    public void setFk_bookId(long fk_bookId) {
        this.fk_bookId = fk_bookId;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getRecognize_time() {
        return this.recognize_time;
    }

    public void setRecognize_time(int recognize_time) {
        this.recognize_time = recognize_time;
    }

    public String getTranslate() {
        return this.translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public int getReview_time() {
        return this.review_time;
    }

    public void setReview_time(int review_time) {
        this.review_time = review_time;
    }

    @Generated(hash = 360147688)
    private transient Long wordsBook__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1879994065)
    public WordsBook getWordsBook() {
        long __key = this.fk_bookId;
        if (wordsBook__resolvedKey == null
                || !wordsBook__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WordsBookDao targetDao = daoSession.getWordsBookDao();
            WordsBook wordsBookNew = targetDao.load(__key);
            synchronized (this) {
                wordsBook = wordsBookNew;
                wordsBook__resolvedKey = __key;
            }
        }
        return wordsBook;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 894093782)
    public void setWordsBook(@NotNull WordsBook wordsBook) {
        if (wordsBook == null) {
            throw new DaoException(
                    "To-one property 'fk_bookId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.wordsBook = wordsBook;
            fk_bookId = wordsBook.getId();
            wordsBook__resolvedKey = fk_bookId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 862469939)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWordsDao() : null;
    }
}
