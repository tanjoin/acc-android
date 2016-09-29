package in.tanjo.calorie.adapter;

import com.google.common.collect.Lists;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.tanjo.calorie.viewholder.AbsViewHolder;

public abstract class AbsAdapter<T, VH extends AbsViewHolder<T>> extends RecyclerView.Adapter<VH> {

    private List<T> items = Lists.newArrayList();

    @Nullable
    protected T getItem(int position) {
        try {
            return items.get(position);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void setItems(@NonNull List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), parent, false);
        return createViewHolder(view);
    }

    @LayoutRes
    abstract protected int getLayoutRes();

    @NonNull
    abstract protected VH createViewHolder(View view);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }
}
