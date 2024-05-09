package complaints.com.aparmentapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import complaints.com.aparmentapp.Models.ChargesModel;
import complaints.com.aparmentapp.R;


public class StatesAdapter extends BaseAdapter {

    static Context context;
    ArrayList<ChargesModel> statesList;

    public StatesAdapter(Context playlistFragment, ArrayList<ChargesModel> vList) {
        statesList = vList;
        context = playlistFragment;
    }

    @Override
    public int getCount() {
        // int count = super.getCount();
        //return statesList.size() > 0 ? statesList.size() - 1 : statesList.size();
        return statesList.size();
    }

    @Override
    public Object getItem(int position) {
        return statesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.states_adapter_item, parent, false);
        TextView tv_statename = (TextView) view.findViewById(R.id.tv_Statename);
        String getStateName = statesList.get(position).getChargeName();
        tv_statename.setText(getStateName);
        return view;
    }
}
