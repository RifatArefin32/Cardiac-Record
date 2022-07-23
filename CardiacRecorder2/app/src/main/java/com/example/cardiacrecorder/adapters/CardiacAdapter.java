package com.example.cardiacrecorder.adapters;
        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.cardiacrecorder.DataModel;
        import com.example.cardiacrecorder.Home;
        import com.example.cardiacrecorder.R;

        import java.util.ArrayList;

public class CardiacAdapter extends RecyclerView.Adapter<CardiacAdapter.CardiacViewholder> {
    private  Context context;
    private ArrayList<DataModel> dataModelArrayList;
    private ClickListener clickListener;
    private RecyclerViewClickListener listener;

    public  CardiacAdapter(Context context, ArrayList<DataModel>mclass, RecyclerViewClickListener listener) {
        this.dataModelArrayList= mclass;
        this.context = context;
        this.listener = listener;
    }


    class CardiacViewholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView tv1,tvSys,tvDias,tvHR, tvcom, tvTime,tvDate;
        Button editButton,deleteButton;

        public CardiacViewholder(@NonNull View itemView) {
            super(itemView);
           // tv1= itemView.findViewById(R.id.tvDate);
            tvDias= itemView.findViewById(R.id.tvDiastolic);
            tvSys=itemView.findViewById(R.id.tvSystolic);
            tvHR=itemView.findViewById(R.id.tvHeartRate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDate = itemView.findViewById(R.id.tvDate);

            editButton=itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            itemView.setOnClickListener(this);
//            itemView.setOnLongClickListener(this);


        }

        @Override
        public void onClick(View view) {
            clickListener.customOnClick(getAbsoluteAdapterPosition(), view);

        }


        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
    public void setClickListener(ClickListener clickL)
    {
        this.clickListener = clickL;
    }



    @NonNull
    @Override
    public CardiacViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext())  ;
        View view= inflater.inflate(R.layout.recordlayout,parent,false);
        return new CardiacViewholder (view);
    }

    public interface ClickListener {
        void customOnClick(int position, View v);

        void onDeleteClick(int position);

        void onEditClick(int position);

    }

    @Override
    public void onBindViewHolder(@NonNull CardiacAdapter.CardiacViewholder holder, @SuppressLint("RecyclerView") int position) {

        if(Home.dataModelArrayList.size()>position){
            holder.tvSys.setText(String.valueOf(Home.dataModelArrayList.get(position).getSystolic()));
            holder.tvDias.setText(String.valueOf(Home.dataModelArrayList.get(position).getDiastolic()));
            holder.tvHR.setText(String.valueOf(Home.dataModelArrayList.get(position).getHeartRate()));
            holder.tvTime.setText(Home.dataModelArrayList.get(position).getTime());
            holder.tvDate.setText(Home.dataModelArrayList.get(position).getDate());
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onDeleteClick(position);
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onEditClick(position);

            }
        });
    }

    /**
     * This getItemCount() function returns the number of items in array list.
     * @return
     *  Return the no of items in dataModelArrayList
     */
    @Override
    public int getItemCount() {
        return dataModelArrayList.size() ;
    }

    public  interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

}
