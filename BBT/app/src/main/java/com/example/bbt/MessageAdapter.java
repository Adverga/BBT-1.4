package com.example.bbt;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final String TAG = "MessageAdapter";

    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    public MessageAdapter(List<Messages> userMessagesList){
        this.userMessagesList = userMessagesList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView senderMessageText, receiverMessageText, messageProfileName;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.sender_message_text);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
            messageProfileName = (TextView) itemView.findViewById(R.id.message_profile_name);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_messages_layout, parent, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        String messageSenderID = mAuth.getCurrentUser().getUid();
        Messages messages = userMessagesList.get(position);

        String fromUserID = messages.getId();
        String fromUserName = messages.getNama();
        String fromMod = messages.getMod();

        Log.d(TAG, fromUserID + " ##### " + messageSenderID);

        holder.receiverMessageText.setVisibility(View.INVISIBLE);
        holder.messageProfileName.setVisibility(View.INVISIBLE);

        if(fromUserID.equals(messageSenderID)){
            holder.senderMessageText.setVisibility(View.VISIBLE);

            if(fromMod.equals("true")){
                holder.senderMessageText.setBackgroundResource(R.drawable.messages_layout_admin);
            }
            else{
                holder.senderMessageText.setBackgroundResource(R.drawable.sender_messages_layout);
            }

            holder.senderMessageText.setText(messages.getMessage());
        }
        else{
            holder.senderMessageText.setVisibility(View.INVISIBLE);
            holder.messageProfileName.setVisibility(View.VISIBLE);
            holder.receiverMessageText.setVisibility(View.VISIBLE);

            if(fromMod.equals("true")){
                holder.receiverMessageText.setBackgroundResource(R.drawable.messages_layout_admin);
            }
            else{
                holder.receiverMessageText.setBackgroundResource(R.drawable.receiver_messages_layout);
            }

            holder.receiverMessageText.setText(messages.getMessage());
            holder.messageProfileName.setText(messages.getNama());
        }
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

}
