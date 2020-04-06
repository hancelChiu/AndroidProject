package com.example.roombasic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buttonInsert, buttonUpdate, buttonClear, buttonDelete;
    WordViewModel wordViewModel;
    RecyclerView recyclerView;
    MyAdapter myAdapter1,myAdapter2;
    Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        recyclerView = findViewById(R.id.recyclerview);
        aSwitch = findViewById(R.id.switch1);
        myAdapter1 = new MyAdapter(false, wordViewModel);
        myAdapter2 = new MyAdapter(true, wordViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter1);//默认正常显示

        //监听按钮状态，设置显示形式
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    recyclerView.setAdapter(myAdapter2);
                }else{
                    recyclerView.setAdapter(myAdapter1);
                }
            }
        });

        //使用livedate的observe取代每次的updateview
        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapter1.getItemCount();
                myAdapter1.setAllWords(words);
                myAdapter2.setAllWords(words);
                if(temp != words.size()){
                    myAdapter1.notifyDataSetChanged();
                    myAdapter2.notifyDataSetChanged();
                }
            }
        });
        buttonInsert = findViewById(R.id.button_insert);
        buttonUpdate = findViewById(R.id.button_update);
        buttonClear = findViewById(R.id.button_clear);
        buttonDelete = findViewById(R.id.button_delete);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] english = {
                        "Hello",
                        "World",
                        "Android",
                        "Google",
                        "Studio",
                        "Project",
                        "Database",
                        "Recycler",
                        "View",
                        "String",
                        "Value",
                        "Integer"
                };
                String[] chinese = {
                        "你好",
                        "世界",
                        "安卓系统",
                        "谷歌公司",
                        "工作室",
                        "项目",
                        "数据库",
                        "回收站",
                        "视图",
                        "字符串",
                        "价值",
                        "整数类型"
                };
                for(int i=0;i<english.length;i++){
                    wordViewModel.InsertWords(new Word(english[i],chinese[i]));
                }
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("nihao", "你好啊");
                word.setId(110);
                wordViewModel.UpdateWords(word);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.DeleteAllWords();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("nihao", "你好啊");
                word.setId(110);
                wordViewModel.DeleteAWords(word);
            }
        });

    }
}
/**
 * 1.引入livedata管理数据，管理跟踪数据状态
 * 2.将database修改为singleton模式，保证只有一个实例
 * 3.引入AsyncTask后台执行数据操作
 * 4.使用ViewModel管理数据，使mainactivity中数据精简
 * 5.使用仓库类进一步精简ViewModel类
 */

