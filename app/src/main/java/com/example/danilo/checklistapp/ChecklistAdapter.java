package com.example.danilo.checklistapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danilo.checklistapp.model.Checklist;

import java.util.List;

/**
 * Created by danilo on 09/05/18.
 */

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolderChecklist>{

    private List<Checklist> mData;
    private Context context;


    public ChecklistAdapter(List<Checklist> data){
        mData = data;
    }

    public List<Checklist> getData(){
        return mData;
    }

    @NonNull
    @Override
    public ViewHolderChecklist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();

        View view = layoutInflater.inflate(R.layout.list_view_checklist,parent,false);

        ViewHolderChecklist holderChecklist = new ViewHolderChecklist(view,parent.getContext());

        return holderChecklist;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderChecklist holder, int position) {

        if(mData != null && mData.size()>0) {
            Checklist checklist = mData.get(position);

            holder.mDescription.setText(checklist.getDescription());
            if (checklist.isActive()) {
                holder.mActive.setText("Ativo");
                holder.mActive.setTextColor(context.getResources().getColor(R.color.colorActiveOK));
            } else {
                holder.mActive.setText("Inativo");
                holder.mActive.setTextColor(context.getResources().getColor(R.color.colorActiveFalse));
            }
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderChecklist extends RecyclerView.ViewHolder{
        public TextView mDescription;
        public TextView mActive;

        public ViewHolderChecklist(View itemView, final Context context) {
            super(itemView);
            mDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            mActive = (TextView) itemView.findViewById(R.id.textViewActive);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mData.size()>0) {
                        Checklist checklist = mData.get(getLayoutPosition());
                        //Toast.makeText(context,"Msg: "+checklist.getDescription(),Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(context, ChecklistAdd.class);
                        it.putExtra("CHECKLIST",checklist);
                        ((AppCompatActivity) context).startActivityForResult(it, 0);

                    }
                }
            });
        }
    }

}
