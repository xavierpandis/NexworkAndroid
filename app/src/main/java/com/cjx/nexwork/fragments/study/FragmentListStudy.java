package com.cjx.nexwork.fragments.study;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cjx.nexwork.R;
import com.cjx.nexwork.adapters.StudyListAdapter;
import com.cjx.nexwork.adapters.WorkListAdapter;
import com.cjx.nexwork.fragments.work.FragmentCreateWork;
import com.cjx.nexwork.managers.study.StudyCallback;
import com.cjx.nexwork.managers.study.StudyManager;
import com.cjx.nexwork.managers.work.WorkCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Study;

import java.util.ArrayList;
import java.util.List;

public class FragmentListStudy extends Fragment implements StudyCallback, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private View view;
    private FloatingActionButton floatingActionButton;

    private List<Study> studies;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Paint p = new Paint();
    private String loginUser;
    private Boolean userConected;

    public FragmentListStudy() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentListStudy(String login, Boolean userConected) {
        this.loginUser = login;
        this.userConected = userConected;
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentListStudy newInstance() {
        FragmentListStudy fragment = new FragmentListStudy();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //setHasOptionsMenu(true);
    }

    SwipeRefreshLayout mSwipeRefreshLayout;
    Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_study,container, false);

        StudyManager.getInstance().getUserStudies(loginUser, this);

        List<Study> studyList = new ArrayList<>();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        fragment = this;

        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.material_blue500,
                R.color.material_blue700,
                R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.create_study_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.content_main, new FragmentCreateStudy(), "createWorkFrag")
                        .addToBackStack(null)
                        .commit();
            }
        });

        if(userConected != null && userConected){
            fab.setVisibility(View.VISIBLE);
        }
        else{
            fab.setVisibility(View.GONE);
        }

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_study);

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private StudyListAdapter studyListAdapter;

    @Override
    public void onSuccess(List<Study> studyList) {
        Log.d("nxw", studyList.toString());
        studies = studyList;

        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.list_study);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        /*GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);*/

        studyListAdapter = new StudyListAdapter(studies, this, getContext());

        recyclerView.setAdapter(studyListAdapter);

        if(userConected){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("YOUR WORKS");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.create_study_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.content_main, FragmentCreateStudy.newInstance(), "createStudyFrag")
                        .addToBackStack(null)
                        .commit();
            }
        });

        if(userConected) fab.setVisibility(View.VISIBLE);
        else fab.setVisibility(View.GONE);

        initSwipe();

    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.menu_work, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.create_work) {

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.content_main, new FragmentCreateWork(), "listStudies")
                    .addToBackStack(null)
                    .commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        StudyManager.getInstance().getUserStudies(loginUser, this);
    }

    @SuppressLint("NewApi")
    private void initSwipe(){

        if(userConected){
            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int position = viewHolder.getAdapterPosition();

                    Study study = studies.get(position);

                    if (direction == ItemTouchHelper.LEFT){
                        studyListAdapter.removeItem(position);
                    }
                    else if(direction == ItemTouchHelper.RIGHT){

                        Bundle args = new Bundle();
                        args.putLong(FragmentEditStudy.STUDY, study.getId());

                        Fragment fragment = new FragmentEditStudy();
                        fragment.setArguments(args);

                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .replace(R.id.content_main, fragment, "editStudy")
                                .commit();

                        studyListAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                    Log.d("nxw", ""+dX);
                    Bitmap icon;

                    Drawable drawableDelete = getResources().getDrawable(R.drawable.delete_icon, null);
                    Drawable drawableEdit = getResources().getDrawable(R.drawable.pencil_icon, null);

                    if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                        View itemView = viewHolder.itemView;
                        float height = (float) itemView.getBottom() - (float) itemView.getTop();
                        float width = height / 3;

                        if(dX > 0){
                            p.setColor(Color.parseColor("#388E3C"));
                            RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                            c.drawRect(background,p);
                            RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                            icon = drawableToBitmap(drawableEdit);
                            c.drawBitmap(icon,null,icon_dest,p);
                        } else {
                            p.setColor(Color.parseColor("#D32F2F"));
                            RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                            c.drawRect(background,p);
                            RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                            icon = drawableToBitmap(drawableDelete);
                            c.drawBitmap(icon,null,icon_dest,p);
                        }
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(this.recyclerView);
            studyListAdapter.notifyDataSetChanged();
        }
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
