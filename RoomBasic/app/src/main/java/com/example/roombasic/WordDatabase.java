package com.example.roombasic;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//singleton 单例模式
@Database(entities = {Word.class},version = 5, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;
    static synchronized WordDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")
//                    .fallbackToDestructiveMigration()\
                    .addMigrations(MIGRATION_4_5)
            .build();
        }
        return INSTANCE;

    }
    public abstract WordDao getWordDao();

    //创建迁移策略
    final static Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table word add column bar_data INTEGER not null default 1");
        }
    };
    final static Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("create table word_temp(id INTEGER primary key not null, english_word TEXT," +
                    "chinese_meaning TEXT)");
            database.execSQL("insert into word_temp(id,english_word,chinese_meaning)select id,english_word,chinese_meaning from word");
            database.execSQL("drop table word");
            database.execSQL("alter table word_temp rename to word");
        }
    };
    final static Migration MIGRATION_4_5 = new Migration(4,5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table word add column chinese_invisible INTEGER not null default 0");
        }
    };
}

//数据库操作比较耗费系统资源，所以要设置成单例模式，只能声明一个连接对象，多个对象连接会自动排队