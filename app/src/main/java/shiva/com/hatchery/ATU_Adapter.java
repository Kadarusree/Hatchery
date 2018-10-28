package shiva.com.hatchery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import shiva.com.hatchery.pojos.ATUmodel;


/**
 * Created by srikanthk on 7/26/2018.
 */

public class ATU_Adapter extends RecyclerView.Adapter<ATU_Adapter.MyViewHolder>

{

    private Context mContext;
    private List<ATUmodel> mAtuList;
    int selected_position = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, temp, atu, initial;


        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.atu_date);
            temp = (TextView) view.findViewById(R.id.atu_temp);
            atu = (TextView) view.findViewById(R.id.atu_atu);
            initial = (TextView) view.findViewById(R.id.initial);
        }
    }


    public ATU_Adapter(Context mContext, List<ATUmodel> mAtuList) {
        this.mContext = mContext;
        this.mAtuList = mAtuList;
    }

    @Override
    public ATU_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atu_item, parent, false);

        return new ATU_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ATUmodel mAtu = mAtuList.get(position);

        holder.date.setText(mAtu.getDate());
        holder.atu.setText(mAtu.getAtu());
        holder.temp.setText(mAtu.getTemperature());
        holder.initial.setText(mAtu.getInitial());


    }


    @Override
    public int getItemCount() {
        return mAtuList.size();
    }


}
