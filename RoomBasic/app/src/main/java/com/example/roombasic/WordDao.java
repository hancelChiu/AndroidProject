package com.example.roombasic;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao      //Database acess object
public interface WordDao {
    @Insert
    void insertWords(Word...words);

    @Update
    void updateWords(Word...words);

    @Delete
    void deleteWords(Word...words);

    @Query("delete from word")
    void deleteAll();

    @Query("select * from word  order by id desc")
//    List<Word> getAllWords();
    LiveData<List<Word>> getAllWordsLive();
}


