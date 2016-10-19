package in.tanjo.calorie.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import in.tanjo.calorie.R;
import in.tanjo.calorie.model.Done;
import in.tanjo.calorie.viewholder.DoneViewHolder;

public class DoneAdapter extends AbsAdapter<Done, DoneViewHolder> implements DoneViewHolder.Listener {

    private final Listener listener;

    public DoneAdapter(@NonNull Listener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.viewholder_done;
    }

    @NonNull
    @Override
    protected DoneViewHolder createViewHolder(View view) {
        return new DoneViewHolder(view, this);
    }

    @Override
    public void onCardViewClick(int position) {
        listener.onCardViewClick(getItem(position), position);
    }

    @Override
    public void onCardViewLongClick(int position) {
        listener.onCardViewLongClick(getItem(position), position);
    }

    public interface Listener {

        void onCardViewClick(Done done, int position);

        void onCardViewLongClick(Done done, int position);
    }
}
