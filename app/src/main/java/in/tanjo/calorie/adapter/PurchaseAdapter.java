package in.tanjo.calorie.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import in.tanjo.calorie.R;
import in.tanjo.calorie.model.Purchase;
import in.tanjo.calorie.viewholder.PurchaseViewHolder;

public class PurchaseAdapter extends AbsAdapter<Purchase, PurchaseViewHolder> implements PurchaseViewHolder.Listener {

    private Listener listener;

    public PurchaseAdapter(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.viewholder_purchase;
    }

    @NonNull
    @Override
    protected PurchaseViewHolder createViewHolder(View view) {
        return new PurchaseViewHolder(view, this);
    }

    @Override
    public void onCardViewClick(int position) {
        if (listener != null) {
            Purchase purchase = getItem(position);
            if (purchase != null) {
                listener.onCardViewClick(purchase, position);
            }
        }
    }

    public interface Listener {

        void onCardViewClick(@NonNull Purchase purchase, int position);
    }
}
