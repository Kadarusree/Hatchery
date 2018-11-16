package shiva.com.hatchery.feedingData;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import shiva.com.hatchery.ATU_Activity;
import shiva.com.hatchery.ATU_Adapter;
import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;

public class FeedingHistory extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView feedList;
    FeedListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_history);
        feedList = findViewById(R.id.feed_list);


        getSupportActionBar().setTitle("Feeding History : Tank "+ Constants.TANK_NUMBER);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        feedList.setLayoutManager(mLayoutManager);
        feedList.addItemDecoration(new FeedingHistory.GridSpacingItemDecoration(1, dpToPx(2), true));
        feedList.setItemAnimator(new DefaultItemAnimator());
        db = FirebaseFirestore.getInstance();




        db.collection("DAILY_FEEDING_DATA").whereEqualTo("Tank_ID",Constants.TANK_NUMBER).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> mDocuments = task.getResult().getDocuments();

                if(mDocuments.size()>0){
                    mAdapter = new FeedListAdapter(FeedingHistory.this, mDocuments);
                    feedList.setAdapter(mAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();
                }


            }
        });
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
