package com.cjx.nexwork.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjx.nexwork.R;
import com.cjx.nexwork.model.User;
import com.cjx.nexwork.util.CustomProperties;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xavi on 18/02/2017.
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private List<User> friendList;
    Fragment fragmentOne;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final View mView;
        public ImageView friendImage;
        public TextView nameFriend;
        public User friend;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            friendImage = (ImageView) v.findViewById(R.id.imageFriend);
            nameFriend = (TextView) v.findViewById(R.id.nameFriend);
        }
    }

    Context context;

    public void removeItem(int position) {
        friendList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, friendList.size());
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FriendListAdapter(List<User> friends, Fragment fragmentOne, Context cntxt) {
        friendList = friends;
        this.fragmentOne = fragmentOne;
        context = cntxt;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list, parent, false);



        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.friend = friendList.get(position);

        holder.nameFriend.setText(holder.friend.getFirstName().concat(holder.friend.getLastName()));

        Picasso
                .with(context)
                .load(CustomProperties.baseUrl+"/"+holder.friend.getImagen())
                .into(holder.friendImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fragment = new FragmentDetailWork();
                Bundle args = new Bundle();
                args.putLong(FragmentDetailWork.WORK_ID, holder.work.getId());
                fragment.setArguments(args);
                FragmentTransaction transaction = fragmentOne.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.enter, R.anim.exit);
                transaction.replace(R.id.fragment_work, fragment, "detailWork");
                transaction.addToBackStack(null);
                transaction.commit();*/
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return friendList.size();
    }
}
