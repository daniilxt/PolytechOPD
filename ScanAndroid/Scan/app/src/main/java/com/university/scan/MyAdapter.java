package com.university.scan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    List<String> data1;
    List<String> data2;
    List<Integer> images;
    List<String> data1All;
    List<String> data2All;
    List<Integer> imagesAll;
    List<Integer> fil;
    Context context;
    int count = 0;

    public MyAdapter(Context ct, List<String> s1, List<String> s2, List<Integer> img){
        context = ct;
        data1All = s1;
        data2All = s2;
        imagesAll = img;
        data1 = new ArrayList<>(s1);
        data2 = new ArrayList<>(s2);
        images = new ArrayList<>(img);
        fil = new ArrayList<Integer>();
    }

    public void print() {
        System.out.println(data1);
        System.out.println(data2);
    }

    public void remove(int position){
        data1.remove(position);
        data2.remove(position);
        images.remove(position);
        print();
    }

    public void insert(Container cont, int position){
        data1.add(position, cont.getString1());
        data2.add(position, cont.getString2());
        images.add(position, cont.getImage());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.myText1.setText(data1.get(position));
        holder.myText2.setText(data2.get(position));
        holder.myImage.setImageResource(images.get(position));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("data1",data1.get(position));
                intent.putExtra("data2",data2.get(position));
                intent.putExtra("myImage",images.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Integer> filterList = new ArrayList<Integer>();


            if (constraint.toString().isEmpty()){
                for (Integer i = 0; i < data1All.size(); i++){
                    filterList.add(i);
                }
            } else {
                for (Integer i = 0; i < data1All.size(); i++){
                    if (data1All.get(i).toLowerCase().contains(constraint.toString().toLowerCase())
                    || data2All.get(i).toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        filterList.add(i);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            fil.clear();
            fil.addAll((Collection<? extends Integer>) results.values);
            data1.clear();
            data2.clear();
            images.clear();
            for (Integer i:fil){
                data1.add(data1All.get(i));
                data2.add(data2All.get(i));
                images.add(imagesAll.get(i));
            }
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2;
        ImageView myImage;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.myText1);
            myText2 = itemView.findViewById(R.id.myText2);
            myImage = itemView.findViewById(R.id.myImageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
