package com.cjx.nexwork.fragments.management;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cjx.nexwork.EditProfileFragment;
import com.cjx.nexwork.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagementFragment extends Fragment {
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

        LinearLayout reledituser = (LinearLayout) view.findViewById(R.id.reledituser);
        reledituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.content_main, new EditProfileFragment(), "editFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

}
