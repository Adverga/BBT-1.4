package com.example.bbt;

import android.media.Image;
import android.util.Log;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> implements Filterable {

    private static final String TAG = "MessageAdapter";

    private List<Messages> userMessagesList;
    private List<Messages> userMessagesListFull;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    public MessageAdapter(List<Messages> userMessagesList, List<Messages> userMessagesListFull){
        this.userMessagesList = userMessagesList;
        this.userMessagesListFull = userMessagesListFull;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView senderMessageText, receiverMessageText, messageProfileName;
        public ImageView checklist;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.sender_message_text);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
            messageProfileName = (TextView) itemView.findViewById(R.id.message_profile_name);
            checklist = (ImageView) itemView.findViewById(R.id.checklist);

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

        holder.receiverMessageText.setText("dummy");
        holder.senderMessageText.setText("dummy");

        holder.receiverMessageText.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.receiverMessageText.requestLayout();

        holder.senderMessageText.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.senderMessageText.requestLayout();

        if(fromUserID.equals(messageSenderID)){

            holder.receiverMessageText.setVisibility(View.GONE);
            holder.messageProfileName.setVisibility(View.GONE);
            holder.checklist.setVisibility(View.GONE);


            int banyakhuruf = 0;

            /*if(fromMod.equals("true")){
                //holder.senderMessageText.setBackgroundResource(R.drawable.messages_layout_admin);
            }*/

            //holder.senderMessageText.setBackgroundResource(R.drawable.text_bubble_sender_smallest2);
            holder.senderMessageText.setText(messages.getMessage());

            banyakhuruf = holder.senderMessageText.getText().toString().length();

            if(banyakhuruf>37){
                holder.senderMessageText.getLayoutParams().width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
                holder.senderMessageText.requestLayout();
            }
        }
        else{
            int banyakhuruf = 0;
            holder.senderMessageText.setVisibility(View.GONE);

            if(fromMod.equals("false")){
                //holder.receiverMessageText.setBackgroundResource(R.drawable.messages_layout_admin);
                //holder.messageProfileName.setBackgroundResource(R.drawable.messages_layout_admin);
                holder.checklist.setVisibility(View.GONE);
            }
            /*else{
                //holder.receiverMessageText.setBackgroundResource(R.drawable.receiver_messages_layout);
                //holder.messageProfileName.setBackgroundResource(R.drawable.messages_layout_user);
            }*/

            //holder.receiverMessageText.setBackgroundResource(R.drawable.text_bubble_receiver_smallest2);
            holder.receiverMessageText.setText(messages.getMessage());
            holder.messageProfileName.setText(messages.getNama());

            /*if(holder.receiverMessageText.toString().length()>37){
                holder.receiverMessageText.setWidth(0);
            }*/
            banyakhuruf = holder.receiverMessageText.getText().toString().length();

            if(banyakhuruf>37){
                holder.receiverMessageText.getLayoutParams().width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
                holder.receiverMessageText.requestLayout();
            }
        }
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

    @Override
    public Filter getFilter() {
        return messageFilter;
    }

    private Filter messageFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Messages> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(userMessagesListFull);
                Log.d(TAG, userMessagesListFull.toString()+" ***** " + userMessagesList.toString());
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(Messages item : userMessagesListFull){
                    if(item.getMessage().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                        Log.d(TAG, userMessagesListFull.toString()+" ***** " + userMessagesList.toString());
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userMessagesList.clear();
            userMessagesList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
