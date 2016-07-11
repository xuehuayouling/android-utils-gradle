package com.android.ysq.widget;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ysq.theme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysq on 16/7/9.
 */
public class ChoiceDialogFragment extends DialogFragment {
    public static final String KEY_ARGUMENT = "KEY_ARGUMENT";
    private OnItemChoiceListener mOnItemChoiceListener;
    private ChoiceAdapter mAdapter;
    private List<ChoiceItem> mData;

    public void setOnItemChoiceListener(OnItemChoiceListener listener) {
        mOnItemChoiceListener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_choice_dialog, null);
        try {
            mData = getArguments().getParcelableArrayList(KEY_ARGUMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter = new ChoiceAdapter(getActivity(), mData);
        ListView listView = (ListView) view.findViewById(R.id.lv_items);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnItemChoiceListener != null) {
                    mOnItemChoiceListener.onItemChoice(mData.get(position));
                }
                getDialog().hide();
            }
        });
        return view;
    }

    public interface OnItemChoiceListener {
        public void onItemChoice(ChoiceItem item);
    }

    public interface ChoiceItem extends Parcelable {
        public String getShowName();
    }

    public class ChoiceAdapter extends BaseAdapter {
        private List<ChoiceItem> mData;
        private Context mContext;

        public ChoiceAdapter(Context context, List<ChoiceItem> data) {
            mContext = context;
            mData = data;
            if (mData == null) {
                mData = new ArrayList<>();
            }
        }

        public void setData(List<ChoiceItem> data) {
            this.mData = data;
            if (mData == null) {
                mData = new ArrayList<>();
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                ViewHolder holder = new ViewHolder();
                holder.tvTitle = (TextView) view.findViewById(android.R.id.text1);
                view.setTag(holder);
            } else {
                view = convertView;
            }
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.tvTitle.setText(mData.get(position).getShowName());
            return view;
        }

        public class ViewHolder {
            TextView tvTitle;
        }
    }
}
