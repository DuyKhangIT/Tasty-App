package com.example.doanandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.doanandroid.Model.OnboardingItem;
import com.example.doanandroid.R;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {
    private List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_onboarding,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingItems.get(position));

    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private LottieAnimationView animation1;
        private TextView tvTextTitle;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTextTitle = itemView.findViewById(R.id.tvTextTittle);
            animation1 = itemView.findViewById(R.id.animation1);
        }
        void setOnboardingData(OnboardingItem onboardingItem){
            tvTextTitle.setText(onboardingItem.getTvTextTitle());
            animation1.setAnimation(onboardingItem.getAnimation1());
        }
    }
}
