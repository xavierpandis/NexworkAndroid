package com.cjx.nexwork.fragments.management;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cjx.nexwork.EditPasswordFragment;
import com.cjx.nexwork.EditProfileFragment;
import com.cjx.nexwork.R;
import com.cjx.nexwork.activities.CreateCompanyActivity;
import com.cjx.nexwork.fragments.FragmentFriendList;
import com.cjx.nexwork.fragments.study.FragmentListStudy;
import com.cjx.nexwork.fragments.work.FragmentListWork;
import com.cjx.nexwork.managers.TokenStoreManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagementFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ManagementFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ManagementFragment newInstance() {
        ManagementFragment fragment = new ManagementFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_management, container, false);

        CardView manageWorks = (CardView) view.findViewById(R.id.manageWorks);
        manageWorks.setOnClickListener(this);

        CardView manageStudies = (CardView) view.findViewById(R.id.manageStudies);
        manageStudies.setOnClickListener(this);

        CardView manageContacts = (CardView) view.findViewById(R.id.manageContacts);
        manageContacts.setOnClickListener(this);

        CardView manageUser = (CardView) view.findViewById(R.id.manageUser);
        manageUser.setOnClickListener(this);

        CardView managePass = (CardView) view.findViewById(R.id.managePass);
        managePass.setOnClickListener(this);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Management");

        return view;
    }

    private Drawable tintDrawable(Drawable drawable) {
        int tint = Color.parseColor("#3f51b5"); // R.color.blue;
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_ATOP;

        drawable.setColorFilter(tint,mode);

        return drawable;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manageWorks:

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.swap_in_bottom, R.anim.swap_out_bottom)
                        .replace(R.id.content_main, new FragmentListWork(TokenStoreManager.getInstance().getUsername(), true))
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.manageStudies:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.swap_in_bottom, R.anim.swap_out_bottom)
                        .replace(R.id.content_main, new FragmentListStudy(TokenStoreManager.getInstance().getUsername(), true))
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.manageContacts:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.swap_in_bottom, R.anim.swap_out_bottom)
                        .replace(R.id.content_main, new FragmentFriendList(TokenStoreManager.getInstance().getUsername(), true))
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.manageUser:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.swap_in_bottom, R.anim.swap_out_bottom)
                        .replace(R.id.content_main, new EditProfileFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.managePass:
                /*getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.swap_in_bottom, R.anim.swap_out_bottom)
                        .replace(R.id.content_main, new EditPasswordFragment())
                        .addToBackStack(null)
                        .commit();*/

                Intent intent = new Intent(getActivity(), CreateCompanyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
