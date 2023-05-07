package hu.mobilalk.allaskereso.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import hu.mobilalk.allaskereso.R;
import hu.mobilalk.allaskereso.activity.OffersMenuActivity;
import hu.mobilalk.allaskereso.model.JobOffer;

public class JobOfferElementAdapter extends RecyclerView.Adapter<JobOfferElementAdapter.ViewHolder> {
    private final ArrayList<JobOffer> offers;
    private final Context context;
    private int lastPosition = -1;

    public JobOfferElementAdapter(Context context, ArrayList<JobOffer> offers)
    {
        this.offers = offers;
        this.context = context;
    }

    @Override
    public JobOfferElementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.offer, parent, false));
    }

    @Override
    public void onBindViewHolder(JobOfferElementAdapter.ViewHolder viewHolder, int position)
    {
        viewHolder.bindTo(offers.get(position));

        if (viewHolder.getAdapterPosition() > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_up);
            viewHolder.itemView.startAnimation(animation);
            lastPosition = viewHolder.getAdapterPosition();
        }

        viewHolder.cardView.setOnLongClickListener(view -> {
            viewHolder.itemView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
            return true;
        });
    }

    @Override
    public int getItemCount()
    {
        return offers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView name;
        private final TextView employerName;
        private final TextView offerDescription;
        private final TextView applied;
        private final Button applyButton;
        private final Button deleteApplicationButton;

        private final FirebaseUser user;

        public CardView cardView;

        ViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.offer_name);
            employerName = itemView.findViewById(R.id.employer_name);
            offerDescription = itemView.findViewById(R.id.offer_description);
            applied = itemView.findViewById(R.id.applied);
            applyButton = itemView.findViewById(R.id.apply_button);
            deleteApplicationButton = itemView.findViewById(R.id.delete_application_button);

            user = FirebaseAuth.getInstance().getCurrentUser();

            cardView = itemView.findViewById(R.id.card);
        }

        void bindTo(JobOffer offer)
        {
            name.setText(offer.getName());
            employerName.setText(offer.getEmployerName());
            offerDescription.setText(offer.getDescription());
            applyButton.setVisibility(offer.isApplied() ? View.GONE : View.VISIBLE);
            deleteApplicationButton.setVisibility(offer.isApplied() ? View.VISIBLE : View.GONE);
            applied.setVisibility(offer.isApplied() ? View.VISIBLE : View.GONE);

            itemView.findViewById(R.id.delete_application_button).setOnClickListener(view -> ((OffersMenuActivity) context).delete(offer));
            itemView.findViewById(R.id.apply_button).setOnClickListener(view -> ((OffersMenuActivity) context).add(offer));
        }
    }
}
