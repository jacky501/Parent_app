package org.atctech.parent.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.atctech.parent.R;
import org.atctech.parent.model.MessagesAll;

import java.util.List;

/**
 * Created by Jacky on 3/25/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>
{
    private Context context;
    private List<MessagesAll> allMessages;

    public ChatAdapter(Context context, List<MessagesAll> allMessages) {
        this.context = context;
        this.allMessages = allMessages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_enquiry,null,false);

        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {


        holder.subject.setText(allMessages.get(position).getSubject());
        holder.message.setText(allMessages.get(position).getMessage());
        holder.reply.setText(Html.fromHtml(allMessages.get(position).getReply()));
        holder.date.setText(allMessages.get(position).getOn_date());

        if (allMessages.get(position).getReply().equalsIgnoreCase(""))
        {
            holder.replyview.setVisibility(View.GONE);
        }else
        {
            holder.replyview.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return (null != allMessages ? allMessages.size() : 0 );
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{

        TextView subject;
        TextView message;
        TextView reply;
        TextView date;
        LinearLayout replyview;

        public ChatViewHolder(View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.subject);

            message =itemView.findViewById(R.id.message);

            reply = itemView.findViewById(R.id.reaply);

            date = itemView.findViewById(R.id.date);

            replyview = itemView.findViewById(R.id.replyview);
        }
    }
}
