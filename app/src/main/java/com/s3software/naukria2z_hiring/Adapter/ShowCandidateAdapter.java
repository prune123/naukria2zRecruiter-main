package com.s3software.naukria2z_hiring.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.s3software.naukria2z_hiring.Activity.ShowCandidate;
import com.s3software.naukria2z_hiring.Model.ViewCandidate;
import com.s3software.naukria2z_hiring.R;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class ShowCandidateAdapter extends RecyclerView.Adapter<ShowCandidateAdapter.ViewHolder> {
    Context context;
    ArrayList<ViewCandidate> postData;
    public ShowCandidateAdapter(ShowCandidate showCandidate, ArrayList<ViewCandidate> datumPost) {
    this.context=showCandidate;
    this.postData=datumPost;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_candidate_layout, parent, false);
        ShowCandidateAdapter.ViewHolder mh = new ShowCandidateAdapter.ViewHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String Name=postData.get(position).getName();
        holder.name.setText(Name);
        holder.email.setText(postData.get(position).getUserEmail());
        holder.mobileno.setText(postData.get(position).getMobileNo());
        holder.qualification.setText(postData.get(position).getQualification());
        holder.experience.setText(postData.get(position).getExperience());

      holder.resume.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String resume=postData.get(position).getResume();

              Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(resume));
              context.startActivity(browserIntent);

          }
      });
      holder.call.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String phone = postData.get(position).getMobileNo();
              Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
              context.startActivity(intent);
          }
      });
      holder.whatsapp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              PackageManager pm = context.getPackageManager();
              try {

                  String url = "https://api.whatsapp.com/send?phone=+91"+postData.get(position).getAlternateMobileNo();
                  Intent i = new Intent(Intent.ACTION_VIEW);
                  i.setData(Uri.parse(url));
                  context.startActivity(i);

              } catch (Exception e) {
                  Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                          .show();
              }
          }
      });
    }



    @Override
    public int getItemCount() {
        return postData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,mobileno,qualification,experience;
        Button call,whatsapp,sms,resume;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.email);
            mobileno=itemView.findViewById(R.id.mobile);
            qualification=itemView.findViewById(R.id.qualification);
            experience=itemView.findViewById(R.id.experience);
            call=itemView.findViewById(R.id.call);
            whatsapp=itemView.findViewById(R.id.whatsapp);
            resume=itemView.findViewById(R.id.resume);
        }
    }
}