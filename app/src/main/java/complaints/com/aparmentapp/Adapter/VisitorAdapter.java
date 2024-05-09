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

import complaints.com.aparmentapp.Models.VisitorModel;
import complaints.com.aparmentapp.R;
import complaints.com.aparmentapp.Sharedpreferences.BaseUrlClass;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.MyViewHolder> {

    Context context;
    ArrayList<VisitorModel> visitorList;
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
            tv_reason = (TextView) view.findViewById(R.id.tv_comptype);
            tv_mobile = (TextView) view.findViewById(R.id.tv_mobile);

        }
    }
    public VisitorAdapter(Context visitorlistFragment, ArrayList<VisitorModel> visitorList) {
        this.visitorList = visitorList;
        context = visitorlistFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitor_lis_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VisitorModel tag = visitorList.get(position);
        final String url= BaseUrlClass.getBaseUrl()+"uploads/";
        /*if(fragment.equals("1")) {
            holder.ll_pending.setVisibility(View.GONE);
            holder.ll_accept.setVisibility(View.VISIBLE);
        }else{
            holder.ll_pending.setVisibility(View.VISIBLE);
            holder.ll_accept.setVisibility(View.GONE);
        }*/
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
        holder.tv_flat.setText("Flat.No :"+tag.getOwnership());
        holder.tv_reason.setText("Address :"+tag.getAddr1());
        holder.tv_mobile.setText(tag.getMobile_no());

       // holder.tv_time.setText("In :"+tag.getVis_in_time()+" "+tag.getVis_date());
       // holder.tv_mobile.setVisibility(View.VISIBLE);
        /*if(tag.getVis_image()!=null ){
            if(!tag.getVis_image().equals("")){
                Glide.with(context).load(url+tag.getVis_image())
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.profile);
                holder.profile.setVisibility(View.VISIBLE);
                holder.profile_dummy.setVisibility(View.GONE);
            }else{
                holder.profile.setVisibility(View.GONE);
                holder.profile_dummy.setVisibility(View.VISIBLE);
            }

        }else{
            holder.profile.setVisibility(View.GONE);
            holder.profile_dummy.setVisibility(View.VISIBLE);

        }*/

    }

    @Override
    public int getItemCount() {
        return visitorList.size();
    }
}
