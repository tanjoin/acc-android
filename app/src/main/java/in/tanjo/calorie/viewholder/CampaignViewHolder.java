package in.tanjo.calorie.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import in.tanjo.calorie.R;
import in.tanjo.calorie.model.Campaign;

public class CampaignViewHolder extends AbsViewHolder<Campaign> {

    @BindView(R.id.viewholder_main_title)
    TextView titleView;

    @BindView(R.id.viewholder_main_description)
    TextView descriptionView;

    @BindView(R.id.viewholder_main_label)
    TextView labelView;

    private Listener listener;

    private int position;

    public CampaignViewHolder(@NonNull View itemView, @NonNull Listener listener) {
        super(itemView);
        this.listener = listener;
    }

    @Override
    public void bind(@Nullable Campaign item, int position) {
        this.position = position;
        if (item != null) {
            titleView.setText(item.getTitle());
            descriptionView.setText(item.getDescription());
            descriptionView.setVisibility(View.VISIBLE);
            labelView.setText(item.getServiceTitle());
            labelView.setVisibility(View.VISIBLE);
        } else {
            titleView.setText("");
            descriptionView.setText("");
            descriptionView.setVisibility(View.GONE);
            labelView.setText("");
            labelView.setVisibility(View.GONE);
        }
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