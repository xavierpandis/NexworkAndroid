package com.cjx.nexwork.util;

/**
 * Created by Xavi on 28/03/2017.
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}