package com.wyjson.actionbardemo.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.view.SuperActionBar;

/**
 * @author Wyjson
 * @version 1
 * @date 2021/9/30 5:29 PM
 */
public class BaseFragment extends Fragment {

    protected View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected @IdRes
    int getSuperActionBarId() {
        return R.id.super_action_bar;
    }

    protected SuperActionBar getSuperActionBar() {
        return rootView.findViewById(getSuperActionBarId());
    }

}
