package com.example.notifapp.view;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notifapp.R;
import com.example.notifapp.model.MessageModel;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {
    ArrayList<MessageModel> arrayList = new ArrayList<>();

    public MessageAdapter(ArrayList<MessageModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_view, parent, false);
        return new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder holder, int position) {
        MessageModel messageModel  =arrayList.get(position);
        Log.i("cfdsfdsfsf",messageModel.getBenutzername()+"dfvgdg");

        holder.message.setText(messageModel.getNachricht());
        holder.time.setText(messageModel.getTime());
        switch (messageModel.getSeverity()) {
            case "OK":
                holder.statusLayout.setBackgroundColor(Color.GREEN);
                break;
            case "WARNING":
                holder.statusLayout.setBackgroundColor(Color.YELLOW);
                break;
            case "CRITICAL":
                holder.statusLayout.setBackgroundColor(Color.RED);
                break;
            case "Recovery":
                holder.statusLayout.setBackgroundColor(Color.WHITE);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MessageAdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView message;
        public TextView time;
        public LinearLayout statusLayout;

        public MessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            statusLayout = itemView.findViewById(R.id.statusLayout);


        }
    }
}
