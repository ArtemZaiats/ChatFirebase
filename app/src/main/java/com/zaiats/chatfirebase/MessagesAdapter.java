package com.zaiats.chatfirebase;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private static final int outgoingMessage = 0;
    private static final int incomingMessage = 1;

    private List<Message> messages;
    private Context context;

    public MessagesAdapter(Context context) {
        messages = new ArrayList<>();
        this.context = context;
    }


    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == outgoingMessage) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outgoing_message_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incoming_message_item, parent, false);
        }
        return new MessagesViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        String author = message.getAuthor();
        if (author != null && author.equals(PreferenceManager.getDefaultSharedPreferences(context).getString("author", "Anonim"))) {
            return outgoingMessage;
        } else {
            return incomingMessage;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        String author = messages.get(position).getAuthor();
        String textOfMessage = messages.get(position).getTextOfMessage();
        String urlToImage = messages.get(position).getImageUrl();
        if (urlToImage == null || urlToImage.isEmpty()) {
            holder.imageViewImage.setVisibility(View.GONE);
        } else {
            holder.imageViewImage.setVisibility(View.VISIBLE);
        }
        holder.textViewAuthor.setText(author);
        if (textOfMessage != null && !textOfMessage.isEmpty()) {
            holder.textViewTextOfMessage.setText(textOfMessage);
            holder.textViewTextOfMessage.setVisibility(View.VISIBLE);
        } else {
            holder.textViewTextOfMessage.setVisibility(View.GONE);
        }
        if (urlToImage != null && !urlToImage.isEmpty()) {
            Picasso.get().load(urlToImage).into(holder.imageViewImage);
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor;
        private TextView textViewTextOfMessage;
        private ImageView imageViewImage;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewTextOfMessage = itemView.findViewById(R.id.textViewTextOfMessage);
            imageViewImage = itemView.findViewById(R.id.imageViewImage);
        }
    }
}
