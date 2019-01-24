package com.maple.decide;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.maple.decide.bean.Question;
import com.maple.decide.bean.QuestionMap;

import java.util.ArrayList;
import java.util.List;

/**
 * @author maple on 2019/1/21 11:39.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    boolean mDelMode;//删除模式开启
    List<Question> questions;
    int selected;
    private Listener mListener;
    private QuestionMap mMap;

    public QuestionAdapter(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public QuestionAdapter(QuestionMap questions) {
        if (questions == null || questions.getQuestions() == null) {
            this.questions = new ArrayList<>();
            this.selected = 0;
        } else {
            this.questions = questions.getQuestions();
            this.selected = questions.getLastUsed();
        }

    }

    public void enableDel(boolean delmode) {
        this.mDelMode = delmode;
        notifyDataSetChanged();
    }

    public void resetData(QuestionMap mMap) {
        this.mMap = mMap;
        if (mMap == null || mMap.getQuestions() == null) {
            this.questions = new ArrayList<>();
            this.selected = 0;
        } else {
            this.questions = mMap.getQuestions();
            this.selected = mMap.getLastUsed();
        }
        notifyDataSetChanged();
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (position == getItemCount() - 1) {
            holder.mTvTitle.setText("  创建新决定");
            holder.mTvTitle.setTextColor(Latte.getApplicationContext().getResources().getColor(R.color.colorAccent));
            holder.mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) mListener.onBottomClicked();
                }
            });
            holder.mRb.setVisibility(View.GONE);
            holder.mImgDel.setVisibility(View.GONE);
            holder.mCard.setCardBackgroundColor(Latte.getApplicationContext().getResources().getColor(
                    R.color.white));
            return;
        }
        holder.mImgDel.setVisibility(mDelMode ? View.VISIBLE : View.GONE);
        holder.mImgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.remove(position);

                if (selected >= position) {
                    selected--;
                    if (selected == -1) selected = 0;
                }
                mMap.setLastUsed(selected);
                LattePreference.setJson(MainActivity.QUESTIONS, mMap);
                notifyDataSetChanged();
            }
        });
        holder.mRb.setVisibility(View.VISIBLE);
        Question question = questions.get(position);
        holder.mTvTitle.setText(question.getTitle());
        holder.mTvTitle.setTextColor(Latte.getApplicationContext().getResources().getColor(R.color.textMain));
        holder.mCard.setCardBackgroundColor(Latte.getApplicationContext().getResources().getColor(
                selected == position ? R.color.colorAccent : R.color.white));
        ColorStateList colorStateList = ColorStateList.valueOf(
                Latte.getApplicationContext().getResources().getColor(
                        selected == position ? R.color.white : R.color.colorAccent)
        );
        holder.mRb.setImageTintList(colorStateList);
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selected == position) return;
                selected = position;
                notifyDataSetChanged();
                if (mListener != null) mListener.onClick(position);
            }
        });
        holder.mRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) mListener.onEditClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return questions.size() + 1;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        ImageView mRb;
        CardView mCard;
        ImageView mImgDel;

        ViewHolder(View itemView) {
            super(itemView);
            mCard = itemView.findViewById(R.id.card);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mRb = itemView.findViewById(R.id.rb_selected);
            mImgDel = itemView.findViewById(R.id.img_del);
        }
    }

    interface Listener {
        void onBottomClicked();

        void onClick(int position);

        void onEditClick(int position);
    }
}
