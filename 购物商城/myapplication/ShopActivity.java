package com.example.administrator.myapplication;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ShopActivity extends Activity implements View.OnClickListener{
    private ListView mListView;
    private ImageView add,car;
    //商品名称与价格数据集合
    private String[] titles = {"西里汉梗幼犬", "牛头梗", "英国猎狐犬", "猎狐梗", "波利犬",
            "凯利蓝梗", "维持罗威士梗", "拉萨犬犬", "京巴狗", "柯基犬"};

    private String[] prices = {"4399元", "2899元", "2500元", "1500元", "3100元",
            "1599元", "1790元", "3599元", "3799元", "1799元"};

    private int[] icons = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,
            R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.i,R.drawable.j};

    SQLiteHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        mListView = (ListView) findViewById(R.id.lv);//初始化ListView控件
        add = (ImageView) findViewById(R.id.add);
        car = (ImageView) findViewById(R.id.car);
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        mListView.setAdapter(mAdapter);
        initData();
    }
    protected void initData(){sqLiteHelper = new SQLiteHelper(this);}

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.car:
                Intent intent = new Intent(ShopActivity.this,CarActivity.class);
                startActivityForResult(intent,1);
        }
    }
     class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null){
                convertView = View.inflate(ShopActivity.this,R.layout.shop_item,null);
                vh = new ViewHolder();
                vh.tvShopTitle = (TextView) convertView.findViewById(R.id.title);
                vh.tvShopPrice = (TextView) convertView.findViewById(R.id.price);
                vh.tvShopiv = (ImageView) convertView.findViewById(R.id.iv);
                vh.tvShopadd = (ImageView) convertView.findViewById(R.id.add);
                convertView.setTag(vh);
                final ViewHolder finalVh = vh;
                vh.tvShopadd.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        String CarTitle = finalVh.tvShopTitle.getText().toString();
                        String CarPrice = finalVh.tvShopPrice.getText().toString();
                        if (sqLiteHelper.insertData(CarTitle,CarPrice)){
                            Toast.makeText(ShopActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ShopActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                vh = (ViewHolder)convertView.getTag();
            }
            vh.tvShopTitle.setText(titles[position]);
            vh.tvShopPrice.setText(prices[position]);
            vh.tvShopiv.setBackgroundResource(icons[position]);
            return convertView;
        }
        class ViewHolder {
            TextView tvShopTitle;
            TextView tvShopPrice;
            ImageView tvShopadd;
            ImageView tvShopiv;
        }
    }
}

