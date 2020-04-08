package com.skewapps.app.oneshop;

import android.graphics.Canvas;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;

import com.skewapps.app.oneshop.Interface.RecyclerItemTouchHelperListner;
import com.skewapps.app.oneshop.Viewholder.CartViewHolder;

public class RecyclerTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListner recyclerListener;

    public RecyclerTouchHelper(int dragDirs, int swipeDirs,RecyclerItemTouchHelperListner recyclerListener) {
        super(dragDirs, swipeDirs);
        this.recyclerListener = recyclerListener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (recyclerListener!=null)
            recyclerListener.onSwiped(viewHolder,i,viewHolder.getAdapterPosition());
    }


    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }


    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foregroundView = ((CartViewHolder) viewHolder).foreGround;

        getDefaultUIUtil().clearView(foregroundView);

    }


    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = ((CartViewHolder) viewHolder).foreGround;

        getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);



    }


    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

        if (viewHolder != null) {
            View foregroundView = ((CartViewHolder) viewHolder).foreGround;

            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = ((CartViewHolder) viewHolder).foreGround;

        getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
    }
}
