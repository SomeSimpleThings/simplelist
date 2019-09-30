package com.somethingsimple.simplelist.swipeInteractions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


public class SwipeCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperActions itemTouchHelperActions;
    private final SwipeCallbackListener swipeCallbackListener;

    public SwipeCallback(SwipeCallbackListener swipeCallbackListener,
                         ItemTouchHelperActions itemTouchHelperActions) {
        this.itemTouchHelperActions = itemTouchHelperActions;
        this.swipeCallbackListener = swipeCallbackListener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        itemTouchHelperActions.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                         int direction) {
        itemTouchHelperActions.onItemDismiss(viewHolder.getAdapterPosition());
        swipeCallbackListener.onSwipe();
    }
}
