package in.tanjo.calorie.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import in.tanjo.calorie.R;
import in.tanjo.calorie.model.Purchase;

public class PurchaseViewHolder extends AbsViewHolder<Purchase> {

    @BindView(R.id.viewholder_purchase_title)
    TextView titleView;

    @BindView(R.id.viewholder_purchase_price)
    TextView priceView;

    @BindView(R.id.viewholder_purchase_date)
    TextView dateView;

    private Listener listener;

    private int position;

    public PurchaseViewHolder(@NonNull View itemView, Listener listener) {
        super(itemView);
        this.listener = listener;
    }

    @Override
    public void bind(@Nullable Purchase item, int position) {
        this.position = position;
        if (item != null) {
            titleView.setText(item.getStore());
            priceView.setText(""); // TODO: ViewModel をつくる
            priceView.setVisibility(View.VISIBLE);
            if (item.getDate() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
                dateView.setText(simpleDateFormat.format(item.getDate()));
                dateView.setVisibility(View.VISIBLE);
            } else {
                dateView.setText("");
                dateView.setVisibility(View.GONE);
            }
        } else {
            titleView.setText("");
            priceView.setText("");
            priceView.setVisibility(View.GONE);
            dateView.setText("");
            dateView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.viewholder_purchase_cardview)
    void onCardViewClick() {
        if (listener != null) {
            listener.onCardViewClick(position);
        }
    }

    public interface Listener {

        void onCardViewClick(int position);
    }
}
