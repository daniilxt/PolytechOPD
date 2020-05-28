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

import com.university.scan.SQL.LocalSQL;
import com.university.scan.SQL.Record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    //List<String> data1;
    //List<String> data2;
    //List<Integer> images;
    List<String> data1All;
    List<String> data2All;
    List<Integer> imagesAll;
    DataBase dataBase;
    List<Integer> fil;
    Context context;
    int count = 0;
    private LocalSQL sql;

    private List<Record> records;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Record record);
    }

    public MyAdapter(Context ct, LocalSQL sql){
        context = ct;
        this.sql = sql;

        this.records = sql.getRecords();
        fil = new ArrayList<Integer>();
    }

    /*public void print() {
        System.out.println(data1);
        System.out.println(data2);
    }*/


    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.bind(records.get(i));

    }

    public void setItems(Collection<Record> records) {
        records.addAll(records);
        notifyDataSetChanged();
    }

    public void clearItems() {
        records.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void remove(int position) {
        Record record = records.remove(position);
        sql.deleteCard(record.getId());
        notifyItemRemoved(position);
    }

//    public void remove(int position){
//        //data1.remove(position);
//        //data2.remove(position);
//        //images.remove(position);
//        dataBase.delete(position);
//        //print();
//    }
//
//    public void insert(Container cont, int position){
//        //data1.add(position, cont.getString1());
//        //data2.add(position, cont.getString2());
//        //images.add(position, cont.getImage());
//        dataBase.insert(position, cont.getString1(), cont.getString2(), cont.getImage());
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
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
                for (Integer i = 0; i < dataBase.getS1().size(); i++){
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
            dataBase.clearDB();
            //data1.clear();
            //data2.clear();
            //images.clear();
            for (Integer i:fil){
                dataBase.insert(i, data1All.get(i), data2All.get(i), imagesAll.get(i));
                //data1.add(data1All.get(i));
                //data2.add(data2All.get(i));
                //images.add(imagesAll.get(i));
            }
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText1, myText2, company;
        ImageView myImage;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.firstName);
            myText2 = itemView.findViewById(R.id.lastName);
            company = itemView.findViewById(R.id.company);
            myImage = itemView.findViewById(R.id.myImageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }

        public void bind(Record record) {
            myText1.setText(record.getFirstName());
            myText2.setText(record.getLastName());
            company.setText(record.getCompanyName());
        }
    }
}
