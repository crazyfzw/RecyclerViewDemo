package com.crazyfzw.recyclerviewdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //显示垂直的RecyclerView
        initVerticalRecyclerView();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_swiperefreshlayout);
        //设置进度条颜色，最多设置4种循环显示
        swipeRefreshLayout.setColorSchemeResources(android.R.color.white,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initVerticalRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        //创建一个垂直的线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        //找到RecyclerView，并设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);

        //如果确定每个item项的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        //创建或取得数据集，数据推荐还是时有List集合
        final String[] datas = new String[20];
        for(int i=0; i<datas.length; i++){
            datas[i]="item"+i;
        }

        //创建adapter并且制定数据集
        adapter = new MyRecyclerViewAdapter(datas);

        //为RecyclerView绑定Adapter
        mRecyclerView.setAdapter(adapter);

        //在Adapter中添加好事件后，变可以在这里注册事件实现监听了
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int positon) {
                Toast.makeText(getBaseContext(), "item"+positon, Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public void onRefresh() {
        // 刷新时模拟数据的变化
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                for (int i=0; i<11; i++) {
                    int temp = (int) (Math.random() * 10);
                    adapter.datass[i] = "new" + temp;
                }
                adapter.notifyDataSetChanged();
            }
            }, 1000);

    }
}
