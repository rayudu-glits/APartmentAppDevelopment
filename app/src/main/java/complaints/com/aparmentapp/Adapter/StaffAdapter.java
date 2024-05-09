package complaints.com.aparmentapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import complaints.com.aparmentapp.Models.CustomerListModel;
import complaints.com.aparmentapp.R;

public class StaffAdapter extends BaseAdapter implements Filterable {

    static Context context;
    ArrayList<CustomerListModel> tagsList =new ArrayList<>();
    ArrayList<CustomerListModel> originalList;
    private Filter filter = new CustomFilter();

    public StaffAdapter(Context playlistFragment, ArrayList<CustomerListModel> vList) {
        originalList = vList;
        context = playlistFragment;
    }

    @Override
    public int getCount() {
        // int count = super.getCount();
        //return statesList.size() > 0 ? statesList.size() - 1 : statesList.size();
        return tagsList.size();
    }
    /*
        @Override
        public Object getItem(int position) {
            return cchList.get(position);
        }*/
    @Override
    public Object getItem(int position) {
        return tagsList.get(position).getCust_id();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.staff_adapter_list, parent, false);
        TextView tv_statename = (TextView) view.findViewById(R.id.tv_Statename);
        String getStateName="";
        if(tagsList.get(position).getFirst_name()!=null){
            getStateName = tagsList.get(position).getMobile_no() + "," + tagsList.get(position).getAddr1() + "," + tagsList.get(position).getFirst_name()+ "," + tagsList.get(position).getCust_id();
        }else {
            getStateName = tagsList.get(position).getMobile_no() + "," + tagsList.get(position).getCust_id();
        }
        tv_statename.setText(getStateName);
        return view;
    }
    @Override
    public Filter getFilter() {
        return filter;
    }

    /**
     * Our Custom Filter Class.
     */
    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            tagsList.clear();

            if (originalList != null && constraint != null) { // Check if the Original List and Constraint aren't null.
                for (int i = 0; i < originalList.size(); i++) {
                    if(originalList.get(i).getCust_id()!=null) {
                        if (originalList.get(i).getFirst_name().toLowerCase().contains(constraint)
                                || originalList.get(i).getMobile_no().toLowerCase().contains(constraint) || originalList.get(i).getAddr1().toLowerCase().contains(constraint)|| originalList.get(i).getCust_id().toLowerCase().contains(constraint)) { // Compare item in original list if it contains constraints.
                            tagsList.add(originalList.get(i)); // If TRUE add item in Suggestions.
                        }
                    }else{
                        if (originalList.get(i).getFirst_name().toLowerCase().contains(constraint)
                                || originalList.get(i).getMobile_no().toLowerCase().contains(constraint)) { // Compare item in original list if it contains constraints.
                            tagsList.add(originalList.get(i)); // If TRUE add item in Suggestions.
                        }
                    }
                }
            }
            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
            results.values = tagsList;
            results.count = tagsList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

}
