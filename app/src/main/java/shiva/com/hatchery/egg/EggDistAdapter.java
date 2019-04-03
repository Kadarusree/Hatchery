package shiva.com.hatchery.egg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import shiva.com.hatchery.R;

/**
 * Created by srikanthk on 4/3/2019.
 */

public class EggDistAdapter extends BaseAdapter {
    ArrayList<EggDistPojo> egg_dist;
    Activity mActivity;
    public EggDistAdapter(ArrayList<EggDistPojo> egg_dist,Activity mActivity) {
        this.egg_dist = egg_dist;
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return egg_dist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater  = LayoutInflater.from(mActivity);
        View v= inflater.inflate(R.layout.egg_dist_adapter,null);
        TextView tv1 = v.findViewById(R.id.tv1);
        TextView tv2 = v.findViewById(R.id.tv2);
        TextView tv3= v.findViewById(R.id.tv3);

        tv1.setText(egg_dist.get(position).getTray());
        tv2.setText(egg_dist.get(position).getVolume());
        tv3.setText(egg_dist.get(position).getNumber());

        return v;
    }
}
