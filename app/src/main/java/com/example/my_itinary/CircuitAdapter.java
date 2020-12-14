package com.example.my_itinary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_itinary.schema.Circuit;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CircuitAdapter extends FirestoreRecyclerAdapter<Circuit, CircuitAdapter.circuitHolder> {
    private Context mContext;


    public CircuitAdapter(@NonNull FirestoreRecyclerOptions<Circuit> options, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull circuitHolder holder, int position, @NonNull Circuit model) {
        holder.city.setText(model.getCity());
        holder.country.setText(model.getCountry());
        holder.user.setText(model.getUsername());

        Glide.with(mContext).load(model.getPicture()).into(holder.image);


    }

    @NonNull
    @Override
    public circuitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_template, parent, false);
        return new circuitHolder(v);
    }

    class circuitHolder extends RecyclerView.ViewHolder
    {
        TextView city;
        TextView country;
        TextView user;
        ImageView image;

        public circuitHolder(View itemView)
        {
            super(itemView);
            city = itemView.findViewById(R.id.text_View_city);
            country = itemView.findViewById(R.id.text_view_country);
            user = itemView.findViewById(R.id.text_view_user);
            image = itemView.findViewById(R.id.image_View);
        }
    }


}
