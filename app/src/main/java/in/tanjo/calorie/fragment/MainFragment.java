package in.tanjo.calorie.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.tanjo.calorie.R;
import in.tanjo.calorie.adapter.MainAdapter;
import in.tanjo.calorie.api.CampaignService;
import in.tanjo.calorie.model.AbsGsonModel;
import in.tanjo.calorie.model.Campaign;
import in.tanjo.calorie.model.response.CampaignsResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MainAdapter.Listener,
        Callback<CampaignsResponse> {

    @BindView(R.id.fragment_main_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_main_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MainAdapter mainAdapter;

    private Unbinder unbinder;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        onRefresh();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mainAdapter = new MainAdapter(this);
        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tanjo.in/")
                .addConverterFactory(GsonConverterFactory.create(AbsGsonModel.createGson()))
                .build();

        CampaignService service = retrofit.create(CampaignService.class);
        Call<CampaignsResponse> call = service.getCampaigns();
        call.enqueue(this);
    }

    @Override
    public void onCardViewClick(@NonNull Campaign campaign, int position) {
        if (campaign.getUrls().size() > 0) {
            openUrl(campaign.getUrls().get(0));
        }
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onResponse(Call<CampaignsResponse> call, Response<CampaignsResponse> response) {
        swipeRefreshLayout.setRefreshing(false);
        if (response.body() != null) {
            mainAdapter.setItems(response.body().getCampaigns());
        }
    }

    @Override
    public void onFailure(Call<CampaignsResponse> call, Throwable t) {
        swipeRefreshLayout.setRefreshing(false);
    }
}
