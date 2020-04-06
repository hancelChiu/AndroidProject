package com.example.roombasic;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Word> allWords =  new ArrayList<>();
    boolean useCardView;
    WordViewModel wordViewModel;

    public MyAdapter(boolean useCardView, WordViewModel wordViewModel) {
        this.useCardView = useCardView;
        this.wordViewModel = wordViewModel;
    }

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if(useCardView){
            itemView = layoutInflater.inflate(R.layout.cell_card_2, parent,false);
        }else{
            itemView = layoutInflater.inflate(R.layout.cell_normal_2, parent,false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Word word = allWords.get(position);
        holder.textViewNumber.setText(String.valueOf(position+1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());

        //设置开关初始值为空
        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(null);
        //根据chinese_invisible值进行页面操作
        if(word.isChineseInvisible()){
            holder.textViewChinese.setVisibility(View.GONE);
            holder.aSwitchChineseInvisible.setChecked(true);
        }else{
            holder.textViewChinese.setVisibility(View.VISIBLE);
            holder.aSwitchChineseInvisible.setChecked(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://m.youdao.com/dict?le=eng&q=" + holder.textViewEnglish.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);


            }
        });
        //监听开关动作
        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    holder.textViewChinese.setVisibility(View.GONE);
                    word.setChineseInvisible(true);
                    wordViewModel.UpdateWords(word);//使修改的数据通过view model正确同步到数据库中
                }else{
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setChineseInvisible(false);
                    wordViewModel.UpdateWords(word);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNumber,textViewEnglish,textViewChinese;
        Switch aSwitchChineseInvisible;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            textViewChinese = itemView.findViewById(R.id.textViewChinese);
            aSwitchChineseInvisible = itemView.findViewById(R.id.switchChineseInvisible);
        }
    }
}
