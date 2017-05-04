package com.cjx.nexwork.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjx.nexwork.R;
import com.cjx.nexwork.adapters.FriendListAdapter;
import com.cjx.nexwork.adapters.WorkListAdapter;
import com.cjx.nexwork.managers.friend.FriendCallback;
import com.cjx.nexwork.managers.friend.FriendManager;
import com.cjx.nexwork.model.Friend;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.model.Work;

import java.util.List;

/**
 * Created by Xavi on 04/04/2017.
 */

public class FragmentFriendList extends Fragment implements FriendCallback{

    private RecyclerView recyclerView;
    private View view;
    private List<User> friendList;
    private FriendListAdapter friendListAdapter;

    private String loginUser;
    private Boolean userConected;

    public static FragmentFriendList newInstance() {
        return new FragmentFriendList();
    }

    public FragmentFriendList() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public FragmentFriendList(String login, Boolean userConected) {
        this.loginUser = login;
        this.userConected = userConected;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_friend, container, false);

        FriendManager.getInstance().getFriendsUser(loginUser, this);

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_friend);

        return view;
    }

    @Override
    public void onSuccess(List<User> userList) {

        friendList = userList;

        recyclerView = (RecyclerView) view.findViewById(R.id.list_friend);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        friendListAdapter = new FriendListAdapter(friendList, this, getContext());
        recyclerView.setAdapter(friendListAdapter);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
