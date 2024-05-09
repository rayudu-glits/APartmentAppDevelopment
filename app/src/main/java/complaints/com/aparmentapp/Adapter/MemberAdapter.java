package complaints.com.aparmentapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import complaints.com.aparmentapp.Models.MemberModel;
import complaints.com.aparmentapp.R;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {

    Context context;
    ArrayList<MemberModel> visitorList;
    String fragment;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_uname,tv_flat,tv_reason,tv_time,tv_mobile;
        Button accept,reject_btn;
        LinearLayout ll_accept,ll_pending;
        ImageView profile,profile_dummy,call;
        public MyViewHolder(View view) {
            super(view);
            tv_uname = (TextView) view.findViewById(R.id.tv_name);
            tv_flat = (TextView) view.findViewById(R.id.tv_flat);
            tv_reason = (TextView) view.findViewById(R.id.tv_visit);
            tv_mobile = (TextView) view.findViewById(R.id.tv_mobile);

        }
    }
    public MemberAdapter(Context visitorlistFragment, ArrayList<MemberModel> visitorList) {
        this.visitorList = visitorList;
        context = visitorlistFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.members_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MemberModel tag = visitorList.get(position);
        holder.tv_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+tag.getMobile_no()));

               /* if (ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }*/
                context.startActivity(callIntent);
            }
        });
        holder.tv_uname.setText("Name :"+tag.getFirst_name());
        holder.tv_flat.setText("Flat.No :"+tag.getAddr1());
        holder.tv_reason.setText("No.of Visitors :"+tag.getVisitorCount());
        holder.tv_mobile.setText(tag.getMobile_no());
    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }
}
