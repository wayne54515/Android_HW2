package c2.android_hw2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class ViewAdapter extends BaseAdapter {

    private LayoutInflater mLayInf;
    List<Map<String, Object>> mItemList;
    TextView txtView;
    public ViewAdapter(Context context,  List<Map<String, Object>> itemList)
    {
        mLayInf = LayoutInflater.from(context);
        mItemList = itemList;
    }

    @Override
    public int getCount()
    {
        //取得 ListView 列表 Item 的數量
        return mItemList.size();
    }

    @Override
    public Object getItem(int position)
    {
        //取得 ListView 列表於 position 位置上的 Item
        return mItemList.get(position).get("id");
    }

    @Override
    public long getItemId(int position)
    {
        //取得 ListView 列表於 position 位置上的 Item 的 ID
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //設定與回傳 convertView 作為顯示在這個 position 位置的 Item 的 View。
        convertView = mLayInf.inflate(R.layout.adapter, parent, false);

        txtView = (TextView) convertView.findViewById(R.id.name);

        txtView.setText(mItemList.get(position).get("object").toString());

        return convertView;
    }

}



