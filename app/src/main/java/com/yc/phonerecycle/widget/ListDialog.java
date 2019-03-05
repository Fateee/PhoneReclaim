package com.yc.phonerecycle.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import com.yc.phonerecycle.R;
import com.yc.phonerecycle.utils.DensityUtil;
import com.yc.phonerecycle.utils.StringUtils;

import java.util.List;

public class ListDialog extends Dialog {

    Context mContext;

    public ListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    String mTitle;

    @NonNull
    List<String> itemTitles;

    @Nullable
    List<String> itemColors;

    ListView.OnItemClickListener mItemsOnClick;
    ListView mListViewContent;
    TextView mTextviewTitle;
    LinearLayout llTitle;

    public ListDialog(@NonNull Context context, @NonNull List<String> itemTitles, String title,
                      @Nullable List<String> itemColors, ListView.OnItemClickListener itemsOnClick) {
        super(context, R.style.grid_dialog);
        this.mContext = context;
        this.itemColors = itemColors;
        this.itemTitles = itemTitles;
        this.mTitle = title;
        this.mItemsOnClick = itemsOnClick;
    }

    public ListDialog(@NonNull Context context, @NonNull List<String> itemTitles, String title, ListView.OnItemClickListener itemsOnClick) {
        this(context, itemTitles, title, null, itemsOnClick);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_list, null);
        setContentView(layout);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = DensityUtil.getWidth();
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);

        initView();
    }

    protected void initView() {
        mTextviewTitle = (TextView) findViewById(R.id.title);
        llTitle = (LinearLayout) findViewById(R.id.linearlayout_title);
        if (!TextUtils.isEmpty(mTitle)) {
            llTitle.setVisibility(View.VISIBLE);
            mTextviewTitle.setText(mTitle);
        } else {
            llTitle.setVisibility(View.GONE);
        }
        mListViewContent = (ListView) findViewById(R.id.listview_content);
        mListViewContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mItemsOnClick != null) {
                    mItemsOnClick.onItemClick(adapterView, view, i, l);
                }
                dismiss();
            }
        });
        mListViewContent.setAdapter(new ListViewAdapter());
    }

    public ListView getListview() {
        return mListViewContent;
    }

    public void dismiss() {
        try {
            super.dismiss(); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class ListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return itemTitles.size();
        }

        @Override
        public Object getItem(int position) {
            return itemTitles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Nullable
        @Override
        public View getView(int position, @Nullable View convertView, ViewGroup parent) {
            TextView tv;
            if (null == convertView) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_list, null);
                tv = (TextView) convertView.findViewById(R.id.textview_content);
            } else {
                tv = (TextView) convertView.getTag();
            }

            tv.setText(itemTitles.get(position));
            convertView.setTag(tv);

            if (itemColors == null || itemColors.size() != getCount() || StringUtils.isBlank(itemColors.get(position))) {
                if (position == getCount() - 1)
                    convertView.setBackgroundColor(0xfffafafa);
                else
                    convertView.setBackgroundColor(0xffffffff);
            } else {
                convertView.setBackgroundColor(Color.parseColor("#" + itemColors.get(position)));
            }

            return convertView;
        }
    }

}
