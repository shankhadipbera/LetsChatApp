package com.shankha.letschat.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.shankha.letschat.Models.MessageModel;
import com.shankha.letschat.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

        ArrayList<MessageModel> messageModels;
        Context context;
        String recId;
        int SENDER_VIEW_TYPE=1;
        int RECIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
      else{
                View view= LayoutInflater.from(context).inflate(R.layout.sample_reciver,parent,false);
                return new ReciverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }else{
            return RECIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel=messageModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("Delete").setMessage("Are You Sure To Delete The Message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String sender= FirebaseAuth.getInstance().getUid()+recId;
                                database.getReference().child("chats").child(sender).child(messageModel.getMessageId()).setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                return false;
            }
        });

        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMessage.setText(messageModel.getMessage());
        }else{
            ((ReciverViewHolder)holder).reciverMessage.setText(messageModel.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReciverViewHolder extends RecyclerView.ViewHolder{
        TextView reciverMessage, reciverTime;
        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            reciverMessage=itemView.findViewById(R.id.reciverText);
            reciverTime=itemView.findViewById(R.id.reciverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMessage, senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage=itemView.findViewById(R.id.sendertext);
            senderTime=itemView.findViewById(R.id.senderTime);
        }
    }

}
