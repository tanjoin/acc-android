package in.tanjo.calorie.viewholder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import in.tanjo.calorie.R;
import in.tanjo.calorie.model.Campaign;

public class MainViewHolder extends AbsViewHolder<Campaign> {

    @BindView(R.id.viewholder_main_title)
    TextView titleView;

    @BindView(R.id.viewholder_main_description)
    TextView descriptionView;

    @BindView(R.id.viewholder_main_label)
    TextView labelView;

    private Listener listener;

    private int position;

    public MainViewHolder(View itemView, Listener listener) {
        super(itemView);
        this.listener = listener;
    }

    @Override
    public void bind(@NonNull Campaign item, int position) {
        this.position = position;
        titleView.setText(item.getTitle());
        descriptionView.setText(item.getDescription());
        labelView.setText(item.getServiceTitle());
    }

    @OnClick(R.id.viewholder_main_cardview)
    void onCardViewClick() {
        if (listener != null) {
            listener.onCardViewClick(position);
        }
    }

    public interface Listener {
        void onCardViewClick(int position);
    }
}