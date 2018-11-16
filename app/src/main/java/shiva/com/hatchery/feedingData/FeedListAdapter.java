package shiva.com.hatchery.feedingData;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.StringTokenizer;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;
import shiva.com.hatchery.pojos.ATUmodel;


/**
 * Created by srikanthk on 7/26/2018.
 */

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.MyViewHolder>

{

    private Context mContext;
    int selected_position = 0;
    List<DocumentSnapshot> mDocuments;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView s1, s2, s3, s4, s5, s6, s7;
        public TextView f1, f2, f3, f4, f5, f6, f7;

        public TextView date, initials, total, notes;


        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.f_adp_date);
            initials = view.findViewById(R.id.f_adp_initials);
            total = (TextView) view.findViewById(R.id.f_total);
            notes = view.findViewById(R.id.f_notes);

            s1 = view.findViewById(R.id.f_adp_size1);
            s2 = view.findViewById(R.id.f_adp_size2);
            s3 = view.findViewById(R.id.f_adp_size3);
            s4 = view.findViewById(R.id.f_adp_size4);
            s5 = view.findViewById(R.id.f_adp_size5);
            s6 = view.findViewById(R.id.f_adp_size6);
            s7 = view.findViewById(R.id.f_adp_size7);


            f1 = view.findViewById(R.id.f_amt_1);
            f2 = view.findViewById(R.id.f_amt_2);
            f3 = view.findViewById(R.id.f_amt_3);
            f4 = view.findViewById(R.id.f_amt_4);
            f5 = view.findViewById(R.id.f_amt_5);
            f6 = view.findViewById(R.id.f_amt_6);
            f7 = view.findViewById(R.id.f_amt_7);


        }
    }


    public FeedListAdapter(Context mContext, List<DocumentSnapshot> mDocuments) {
        this.mContext = mContext;
        this.mDocuments = mDocuments;
    }

    @Override
    public FeedListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_adapter, parent, false);

        return new FeedListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

       /* holder.date.setText(mAtu.getDate());
        holder.atu.setText(mAtu.getAtu());
        holder.temp.setText(mAtu.getTemperature());
        holder.initial.setText(mAtu.getInitial());*/


       if (mDocuments.get(position).get("Tank_ID").equals(Constants.TANK_NUMBER)){
           holder.initials.setText("Initials : " + mDocuments.get(position).get("Initials").toString());
           holder.date.setText("Date : " + mDocuments.get(position).get("Date").toString());
           holder.total.setText(mDocuments.get(position).get("Total").toString());
           holder.notes.setText(mDocuments.get(position).get("Notes").toString());


           String data1 = mDocuments.get(position).get("F1").toString();
           if (!data1.equalsIgnoreCase("mm_")) {
               StringTokenizer mStringTokenizer = new StringTokenizer(data1, "_");
               holder.s1.setText(mStringTokenizer.nextToken());
               holder.f1.setText(mStringTokenizer.nextToken());
           }

           String data2 = mDocuments.get(position).get("F2").toString();
           if (!data2.equalsIgnoreCase("mm_")) {
               StringTokenizer mStringTokenizer = new StringTokenizer(data2, "_");
               holder.s2.setText(mStringTokenizer.nextToken());
               holder.f2.setText(mStringTokenizer.nextToken());
           }

           String data3 = mDocuments.get(position).get("F3").toString();
           if (!data3.equalsIgnoreCase("mm_")) {
               StringTokenizer mStringTokenizer = new StringTokenizer(data3, "_");
               holder.s3.setText(mStringTokenizer.nextToken());
               holder.f3.setText(mStringTokenizer.nextToken());
           }

           String data4 = mDocuments.get(position).get("F4").toString();
           if (!data4.equalsIgnoreCase("mm_")) {
               StringTokenizer mStringTokenizer = new StringTokenizer(data4, "_");
               holder.s4.setText(mStringTokenizer.nextToken());
               holder.f4.setText(mStringTokenizer.nextToken());
           }

           String data5 = mDocuments.get(position).get("F5").toString();
           if (!data5.equalsIgnoreCase("mm_")) {
               StringTokenizer mStringTokenizer = new StringTokenizer(data5, "_");
               holder.s5.setText(mStringTokenizer.nextToken());
               holder.f5.setText(mStringTokenizer.nextToken());
           }

           String data6 = mDocuments.get(position).get("F6").toString();
           if (!data6.equalsIgnoreCase("mm_")) {
               StringTokenizer mStringTokenizer = new StringTokenizer(data6, "_");
               holder.s6.setText(mStringTokenizer.nextToken());
               holder.f6.setText(mStringTokenizer.nextToken());
           }

           String data7 = mDocuments.get(position).get("F7").toString();
           if (!data7.equalsIgnoreCase("mm_")) {
               StringTokenizer mStringTokenizer = new StringTokenizer(data7, "_");
               holder.s7.setText(mStringTokenizer.nextToken());
               holder.f7.setText(mStringTokenizer.nextToken());
           }
       }


    }


    @Override
    public int getItemCount() {
        return mDocuments.size();
    }


}
