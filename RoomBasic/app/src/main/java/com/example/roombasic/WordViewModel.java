package com.example.roombasic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
   private WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);

    }
//调用仓库类中定义的数据
     LiveData<List<Word>> getAllWordsLive() {
        return wordRepository.getAllWordsLive();
    }
//调用仓库类中定义的操作
    void InsertWords(Word... words) {
        wordRepository.InsertWords(words);
    }

    void UpdateWords(Word... words) {
        wordRepository.UpdateWords(words);
    }

    void DeleteAWords(Word... words) {
        wordRepository.DeleteAWords(words);
    }

    void DeleteAllWords() {
        wordRepository.DeleteAllWords();
    }
}
