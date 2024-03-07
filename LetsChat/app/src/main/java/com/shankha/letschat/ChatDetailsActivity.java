package com.shankha.letschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shankha.letschat.Adapter.ChatAdapter;
import com.shankha.letschat.Models.MessageModel;
import com.shankha.letschat.databinding.ActivityChatDetailsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailsActivity extends AppCompatActivity {
    ActivityChatDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database= FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        final String senderId= auth.getUid();
        String recivedId= getIntent().getStringExtra("userId");
        String userName=getIntent().getStringExtra("userName");
        String profilepic= getIntent().getStringExtra("profilepic");

        binding.UserName.setText(userName);
        Picasso.get().load(profilepic).placeholder(R.drawable.person).into(binding.profileImage);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ChatDetailsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<MessageModel> messageModels= new ArrayList<>();
        final ChatAdapter adapter= new ChatAdapter(messageModels,this,recivedId);
        binding.chatRecycleView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecycleView.setLayoutManager(layoutManager);

        final String senderRoom= senderId+recivedId;
        final String reciverRoom= recivedId+senderId;

        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for( DataSnapshot snapshot1:snapshot.getChildren()){
                    MessageModel model= snapshot1.getValue(MessageModel.class);
                    model.setMessageId(snapshot1.getKey());
                    messageModels.add(model);
                }
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edMessage.getText().toString().isEmpty()){
                    binding.edMessage.setError("Enter Your message");
                    return;
                }

                String message= binding.edMessage.getText().toString();
                final MessageModel model=new MessageModel(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.edMessage.setText("");

                database.getReference().child("chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                       database.getReference().child("chats").child(reciverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {

                           }
                       }) ;
                    }
                });
            }
        });







    }
}