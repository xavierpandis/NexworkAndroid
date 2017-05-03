package com.cjx.nexwork.fragments.work;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cjx.nexwork.R;
import com.cjx.nexwork.adapters.WorkListAdapter;
import com.cjx.nexwork.managers.work.WorkCallback;
import com.cjx.nexwork.managers.work.WorkManager;
import com.cjx.nexwork.model.Work;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FragmentListWork extends Fragment implements WorkCallback, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private View view;
    private List<Work> works;
    private FloatingActionButton floatingActionButton;
    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback;
    private WorkListAdapter workListAdapter;
    private Paint p = new Paint();
    private Toolbar toolbar;
    private ActionBar actionBar;

    private String loginUser;
    private Boolean userConected;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListWork() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentListWork(String login, Boolean userConected) {
        this.loginUser = login;
        this.userConected = userConected;
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentListWork newInstance() {
        FragmentListWork fragment = new FragmentListWork();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_work,container, false);

        WorkManager.getInstance().getWorksUser(loginUser, this);

        if(actionBar != null){
            actionBar.setTitle("Your works");
        }

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_work);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.btnAddJob);
        floatingActionButton.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.list_work);
        workListAdapter = new WorkListAdapter(works, this, getContext());
        recyclerView.setAdapter(workListAdapter);

        workListAdapter.notifyDataSetChanged();

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
        workListAdapter.notifyDataSetChanged();
        super.onDetach();
    }

    @Override
    public void onSuccess(List<Work> workList) {
        Log.d("nxw", workList.toString());

        /*SharedPreferences mPrefs = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(workList); // myObject - instance of MyObject
        prefsEditor.putString("worksUserLogged", json);
        prefsEditor.apply();

        String jsonLoaded = mPrefs.getString("worksUserLogged", "");
        if(!json.equals("")){

        }
        MyObject obj = gson.fromJson(json, MyObject.class);*/

        for(Work work: workList){
            Log.d("nxw", "Company -> "+work.getCompany());
        }

        works = workList;

        recyclerView = (RecyclerView) view.findViewById(R.id.list_work);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        workListAdapter = new WorkListAdapter(works, this, getContext());
        recyclerView.setAdapter(workListAdapter);

        workListAdapter.notifyDataSetChanged();
        initSwipe();

    }

    private void removeView(){
        if(view.getParent()!=null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
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

                    Work work = works.get(position);

                    if (direction == ItemTouchHelper.LEFT){
                        workListAdapter.removeItem(position);
                    }
                    else if(direction == ItemTouchHelper.RIGHT){
                        Log.d("nxw", "editar");
                        Log.d("nxw", work.getCargo());

                        Bundle args = new Bundle();
                        args.putLong(FragmentEditWork.WORK, work.getId());

                        Fragment fragment = new FragmentEditWork();
                        fragment.setArguments(args);

                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                                .add(R.id.content_main, fragment, "editWork")
                                .addToBackStack(null)
                                .commit();

                        workListAdapter.notifyDataSetChanged();

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
            workListAdapter.notifyDataSetChanged();
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

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        inflater.inflate(R.menu.menu_work, menu);
        final WorkCallback workCallback = this;
        final SearchView vwSearch = (SearchView) menu.findItem(R.id.search_work).getActionView();
        vwSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do something on Submit
                WorkManager.getInstance().searchWorkByName(query, workCallback);
                Log.d("nxw", "INPUT SEARCH SUBMIT");
                vwSearch.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do something on change
                Log.d("nxw", "INPUT SEARCH CHANGE");
                if(newText.isEmpty()){
                    WorkManager.getInstance().getWorksUser(loginUser, workCallback);
                }
                return true;
            }


        });

        vwSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //Do something on collapse Searchview
                Log.d("nxw", "SEARCH CLOSED");
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.create_work) {

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit)
                    .replace(R.id.fragment_work, new FragmentCreateWork(), "listWorks")
                    .addToBackStack(null)
                    .commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddJob:
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.fragment_work, new FragmentCreateWork())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
