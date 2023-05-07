package hu.mobilalk.allaskereso.activity;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import hu.mobilalk.allaskereso.model.JobApplication;
import hu.mobilalk.allaskereso.model.JobOffer;
import hu.mobilalk.allaskereso.recyclerview.JobOfferElementAdapter;

import hu.mobilalk.allaskereso.R;

public class OffersMenuActivity extends AppCompatActivity {
    private static final String LOG_TAG = OffersMenuActivity.class.getName();

    private ArrayList<JobOffer> offers;
    private JobOfferElementAdapter jobOfferElementAdapter;
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offers_menu);

        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            finish();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2));

        offers = new ArrayList<>();

        jobOfferElementAdapter = new JobOfferElementAdapter(this, offers);
        recyclerView.setAdapter(jobOfferElementAdapter);

        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        selectOffers();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void selectOffers()
    {
        offers.clear();
        ArrayList<String> myApplications = new ArrayList<>();
        firestore.collection("application").whereEqualTo("seekerId", user.getUid()).get().addOnSuccessListener(
            documents -> documents.forEach(
                 doc ->  myApplications.add(((String)doc.get("jobId")).trim())
            )
        ).addOnSuccessListener(None -> firestore.collection("offers").orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots ->
        {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                String name = (String) document.get("name");
                String description = (String) document.get("description");
                String employerId = (String) document.get("employerId");
                boolean applied = false;
                for (String s: myApplications) {
                    if (document.getId().equals(s)) {
                        applied = true;
                        break;
                    }
                }
                assert employerId != null;
                boolean finalApplied = applied;
                firestore.collection("employer").document(employerId).get().addOnSuccessListener(
                        doc -> {
                            String employerName = (String) doc.get("name");
                            JobOffer offer = new JobOffer(document.getId(), name, employerName, description, finalApplied);
                            offers.add(offer);
                            jobOfferElementAdapter.notifyDataSetChanged();
                        }
                );
            }
        }));
    }

    public void delete(JobOffer application)
    {
        new DeleteApplictaion().execute(application);
        Toast.makeText(this, "Jelentkezés sikeresen kitörölve!", Toast.LENGTH_LONG).show();
    }

    public void add(JobOffer application)
    {
        new AddApplication().execute(application);
        Toast.makeText(this, "Jelentkezés sikeresen hozzáadva!", Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("deprecation")
    public class DeleteApplictaion extends AsyncTask<JobOffer, Void, Void> {
        @Override
        protected Void doInBackground(JobOffer... jobs)
        {
            JobOffer offer = jobs[0];

            firestore.collection("application").whereEqualTo("seekerId", user.getUid()).whereEqualTo("jobId", offer.getId()).get().addOnSuccessListener(
                documents -> documents.forEach(doc -> doc.getReference().delete())
            ).addOnSuccessListener(
                None -> selectOffers()
            ).addOnFailureListener(
                exception -> Log.w(LOG_TAG, "Error getting documents: ", exception)
            );

            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public class AddApplication extends AsyncTask<JobOffer, Void, Void> {
        @Override
        protected Void doInBackground(JobOffer... jobs)
        {
            JobOffer offer = jobs[0];
            JobApplication application = new JobApplication(offer.getId(), user.getUid());

            firestore.collection("application").add(application).addOnFailureListener(
                exception -> Log.w(LOG_TAG, "Error getting documents: ", exception)
            );

            return null;
        }

        @Override
        protected void onPostExecute(Void unused)
        {
            Log.d(LOG_TAG, "OnPostExecute");
            selectOffers();
        }
    }
}
