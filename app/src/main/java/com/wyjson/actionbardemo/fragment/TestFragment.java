package com.wyjson.actionbardemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.view.SuperActionBar;

public class TestFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return rootView = inflater.inflate(R.layout.fragment_test, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initActionBar();
    }

    @Override
    protected int getSuperActionBarId() {
        return R.id.action_bar_fragment_test;
    }

    private void initActionBar() {
        getSuperActionBar().initActionBarForBothTxt("标题", R.string.icon_setting, new SuperActionBar.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "设置按钮点击事件", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
