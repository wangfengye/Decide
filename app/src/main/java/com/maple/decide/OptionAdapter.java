package com.maple.decide;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ascend.wangfeng.latte.app.Latte;

import java.util.ArrayList;

/**
 * @author maple on 2019/1/21 13:16.
 * @version v1.0
 * @see 1040441325@qq.com
 */
public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {
    private RecyclerView mRv;
    ArrayList<String> data;
    private int focusedId = -1;
    private int needFocusedId = -1;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            data.set(focusedId, editable.toString());
        }
    };

    public OptionAdapter(ArrayList<String> options, RecyclerView mRv) {
        this.data = options;
        this.mRv = mRv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eidt, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (position >= data.size()) {//尾部
            holder.imgDel.setVisibility(View.GONE);
            holder.etOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.add("");
                    needFocusedId = position;
                    notifyDataSetChanged();
                    mRv.scrollToPosition(position+1);
                    notifyDataSetChanged();
                }
            });
            holder.etOption.setFocusableInTouchMode(false);
            holder.etOption.setText("创建新的选项");
            holder.etOption.setGravity(Gravity.CENTER);
            holder.etOption.setTextColor(Latte.getApplicationContext().getResources().getColor(R.color.colorAccent));
        } else {
            holder.imgDel.setVisibility(View.VISIBLE);
            holder.imgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.remove(position);
                    needFocusedId = position - 1;
                    notifyDataSetChanged();
                }
            });
            holder.etOption.setOnClickListener(null);
            holder.etOption.setFocusableInTouchMode(true);
            holder.etOption.setText(data.get(position));
            holder.etOption.setGravity(Gravity.START);
            holder.etOption.setTextColor(Latte.getApplicationContext().getResources().getColor(R.color.textMain));
            if (needFocusedId == position){
                holder.etOption.requestFocus();
                focusedId =position;
            }
            holder.etOption.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b) focusedId = position;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.etOption.addTextChangedListener(textWatcher);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.etOption.removeTextChangedListener(textWatcher);
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDel;
        AppCompatEditText etOption;

        public ViewHolder(View itemView) {
            super(itemView);
            imgDel = itemView.findViewById(R.id.img_del);
            etOption = itemView.findViewById(R.id.et_option);
        }
    }
}
