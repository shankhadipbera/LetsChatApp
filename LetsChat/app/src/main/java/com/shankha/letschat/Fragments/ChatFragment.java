package com.shankha.letschat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shankha.letschat.Adapter.UserAdapter;
import com.shankha.letschat.Models.Users;
import com.shankha.letschat.R;
import com.shankha.letschat.databinding.FragmentChatBinding;

import java.util.ArrayList;


public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }
    FragmentChatBinding binding;
    ArrayList<Users>  list= new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= FragmentChatBinding.inflate(inflater,container,false);

        UserAdapter adapter= new UserAdapter(list,getContext());
        binding.chatRecycleView.setAdapter(adapter);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
        binding.chatRecycleView.setLayoutManager(layoutManager);
        database= FirebaseDatabase.getInstance();

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());

                    if(!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){
                    list.add(users);}
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        return binding.getRoot();
    }
}