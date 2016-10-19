package in.tanjo.calorie.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;
import in.tanjo.calorie.R;
import in.tanjo.calorie.model.Done;

public class DoneViewHolder extends AbsViewHolder<Done> {

    private final Listener listener;

    @BindView(R.id.viewholder_done_title)
    TextView titleView;

    @BindView(R.id.viewholder_done_description)
    TextView descriptionView;

    @BindView(R.id.viewholder_done_completedat)
    TextView completedAtView;

    private int position;

    public DoneViewHolder(@NonNull View itemView, @NonNull Listener listener) {
        super(itemView);
        this.listener = listener;
    }

    @Override
    public void bind(@Nullable Done item, int position) {
        this.position = position;
        if (item != null) {
            titleView.setText(item.getTitle());
            descriptionView.setText(item.getDescription());
            completedAtView.setText(item.getCompletedAtString());
        } else {
            titleView.setText("");
            descriptionView.setText("");
            descriptionView.setVisibility(View.GONE);
            completedAtView.setText("");
            completedAtView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.viewholder_done_cardview)
    void onCardViewClick(View view) {
        if (listener != null) {
            listener.onCardViewClick(position);
        }
    }

    @OnLongClick(R.id.viewholder_done_cardview)
    boolean onCardViewLongClick(View view) {
        if (listener != null) {
            listener.onCardViewLongClick(position);
        }
        return true;
    }

    public interface Listener {

        void onCardViewClick(int position);

        void onCardViewLongClick(int position);
    }
}
