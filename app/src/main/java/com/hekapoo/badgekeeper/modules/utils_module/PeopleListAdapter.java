package com.hekapoo.badgekeeper.modules.utils_module;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hekapoo.badgekeeper.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.ViewHolder> {

    private ArrayList<PeopleListModel> peopleFound;

    public PeopleListAdapter(ArrayList<PeopleListModel> charts) {
        this.peopleFound = charts;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public TextView username,lastUsed,clockedAt,leftToWork;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            this.username = view.findViewById(R.id.item_username);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.people_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //populate with data here i suppose
        PeopleListModel people = peopleFound.get(position);
        holder.username.setText(people.getUsername());
        holder.lastUsed.setText(people.getLastUsedTime());
        holder.clockedAt.setText(people.getCheckedInAt());
        holder.leftToWork.setText(people.getLeftToWork());
    }


    @Override
    public int getItemCount() {
        return peopleFound == null ? 0 : peopleFound.size();
    }
}