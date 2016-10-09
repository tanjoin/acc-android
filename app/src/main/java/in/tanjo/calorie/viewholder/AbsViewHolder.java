package in.tanjo.calorie.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;


public abstract class AbsViewHolder<T> extends RecyclerView.ViewHolder {

    public AbsViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    abstract public void bind(@Nullable T item, int position);
}
