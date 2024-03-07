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
import com.shankha.letschat.databinding.ActivityGroupChatBinding;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GroupChatActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        final String senderId= FirebaseAuth.getInstance().getUid();
        binding.UserName.setText("Group Chat");

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels =new ArrayList<>();

        final ChatAdapter adapter= new ChatAdapter(messageModels,this);
        binding.chatRecycleView.setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecycleView.setLayoutManager(layoutManager);

        database.getReference().child("Group Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModels.clear();
                for( DataSnapshot datasnapshot:snapshot.getChildren()){
                    MessageModel model= datasnapshot.getValue(MessageModel.class);
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
                    binding.edMessage.setError("Enter Your Message");
                    return;
                }

                final String message= binding.edMessage.getText().toString();
                final MessageModel model= new MessageModel(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.edMessage.setText("");

                database.getReference().child("Group Chat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
            }
        });



    }
}