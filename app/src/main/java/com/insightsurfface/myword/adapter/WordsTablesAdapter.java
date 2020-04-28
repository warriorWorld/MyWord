package com.insightsurfface.myword.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.greendao.DbController;
import com.insightsurfface.myword.greendao.WordsBook;
import com.insightsurfface.myword.listener.OnAddClickListener;
import com.insightsurfface.myword.listener.OnRecycleItemClickListener;
import com.insightsurfface.myword.listener.OnRecycleItemLongClickListener;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator on 2017/11/15.
 * 还款页的还款计划
 */
public class WordsTablesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context context;
    private final int TYPE_NORMAL = 0;
    private final int TYPE_END = 1;
    private List<WordsBook> list;
    private OnRecycleItemClickListener mOnRecycleItemClickListener;
    private OnRecycleItemClickListener mOnRefreshClickListener;
    private OnRecycleItemLongClickListener mOnRecycleItemLongClickListener;
    private OnAddClickListener mOnAddClickListener;

    public WordsTablesAdapter(Context context) {
        this.context = context;
    }

    // 创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_END) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_table, viewGroup, false);
            return new ListEndViewHolder(view);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_table, viewGroup, false);
            return new NormalViewHolder(view);
        }
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            WordsBook item = list.get(position);
            NormalViewHolder vh = (NormalViewHolder) viewHolder;
            vh.nameTv.setText(item.getName());
            String size;
            if (null == item.getWords() || item.getWords().size() == 0) {
                size = "0";
            } else {
                size = DbController.getInstance(context.getApplicationContext()).querryWordsByBook
                        (item.getId()).size() + "/" + item.getWords().size() + "";
            }
            vh.sizeTv.setText(size);
            vh.tableRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnRecycleItemClickListener) {
                        mOnRecycleItemClickListener.onItemClick(position);
                    }
                }
            });
            vh.tableRl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (null != mOnRecycleItemLongClickListener) {
                        mOnRecycleItemLongClickListener.onItemLongClick(position);
                    }
                    return true;
                }
            });
            vh.refreshIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnRefreshClickListener) {
                        mOnRefreshClickListener.onItemClick(position);
                    }
                }
            });
        } else {
            ListEndViewHolder evh = (ListEndViewHolder) viewHolder;
            evh.addRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnAddClickListener) {
                        mOnAddClickListener.onClick();
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null == list || list.size() == 0) {
            return TYPE_END;
        } else if (position == list.size()) {
            return TYPE_END;
        } else {
            return TYPE_NORMAL;
        }
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (null == list || list.size() == 0) {
            return 1;
        } else {
            return list.size() + 1;
        }
    }

    public void setList(List<WordsBook> list) {
        this.list = list;
    }

    public void setOnRecycleItemClickListener(OnRecycleItemClickListener onRecycleItemClickListener) {
        mOnRecycleItemClickListener = onRecycleItemClickListener;
    }

    public void setOnAddClickListener(OnAddClickListener onAddClickListener) {
        mOnAddClickListener = onAddClickListener;
    }

    public void setOnRecycleItemLongClickListener(OnRecycleItemLongClickListener onRecycleItemLongClickListener) {
        mOnRecycleItemLongClickListener = onRecycleItemLongClickListener;
    }

    public void setOnRefreshClickListener(OnRecycleItemClickListener onRefreshClickListener) {
        mOnRefreshClickListener = onRefreshClickListener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class NormalViewHolder extends RecyclerView.ViewHolder {
        private View tableRl;
        private TextView nameTv;
        private TextView sizeTv;
        private View refreshIv;

        public NormalViewHolder(View view) {
            super(view);
            tableRl = (View) view.findViewById(R.id.table_rl);
            nameTv = (TextView) view.findViewById(R.id.table_name_tv);
            sizeTv = (TextView) view.findViewById(R.id.table_size_tv);
            refreshIv = view.findViewById(R.id.refresh_iv);
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ListEndViewHolder extends RecyclerView.ViewHolder {
        private View addRl;

        public ListEndViewHolder(View view) {
            super(view);
            addRl = (View) view.findViewById(R.id.add_table_rl);
        }
    }
}
