package com.example.rmd;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class Home extends Fragment {
    private Bundle mArguments;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Bundle data;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     */
    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mArguments = getArguments();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_home, container, false);
        ImageView heart,lung;
        TextView name;

        ImageView imageView;
        DoctorAdapter adapter;



//        listSubscription = new ArrayList<>();
//        donationAdapter= new DonationAdapter(this,listDonation);

        name=view.findViewById(R.id.name);
        //heart=view.findViewById(R.id.event_description);
        //lung=view.findViewById(R.id.event_description1);
        //imageView=view.findViewById(R.id.profilepicture);
        TextView viewn= view.findViewById(R.id.a);
        String dom="";
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        ArrayList<HashMap<String,Object>>  list= new ArrayList<>();
//        listSubscription = new ArrayList<>();
//        donationAdapter= new DonationAdapter(this,listDonation);
        adapter = new DoctorAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);



        Bundle data= getArguments();
        if(data != null){
            dom= data.getString("Domain");
        }
        DatabaseReference FBDB = FirebaseDatabase.getInstance().getReference("Patient");
        FBDB.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HashMap<String, Object> _map = dataSnapshot.getValue(_ind);
                    //check status & update by sub id
                    list.add(_map);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  view;

    }
    class DoctorAdapter extends  RecyclerView.Adapter<DoctorAdapter.ViewHolder>{
        ArrayList<HashMap<String,Object>> list;
        Context context;

        public DoctorAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.paitent_list,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.name.setText(list.get(position).get("Name").toString());
            holder.spl.setText(list.get(position).get("City").toString());
            holder.time.setText(list.get(position).get("Time").toString());

            //holder.date.setText(list.get(position).get("date").toString());
            String profileurl= list.get(position).get("Surl").toString();
            Glide.with(holder.imageview.getContext()).load(profileurl).into(holder.imageview);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageview;
            TextView name,spl,time;
            CardView cardView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                cardView = itemView.findViewById(R.id.main_layout);
                imageview=itemView.findViewById(R.id.imageview12);
                name=itemView.findViewById(R.id.name);
                spl=itemView.findViewById(R.id.city);
                time=itemView.findViewById(R.id.time);

            }
        }
    }


}