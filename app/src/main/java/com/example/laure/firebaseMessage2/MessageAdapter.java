package com.example.laure.firebaseMessage2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Display chat messages
 * <p>
 * Created by Hugo Gresse on 26/11/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    private List<Message> mData;

    public MessageAdapter(List<Message> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Message> data) {
        mData = data;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mUserTextView;
        TextView mContentTextView;

        ViewHolder(View itemView) {
            super(itemView);

            mUserTextView = itemView.findViewById(R.id.userTextView);
            mContentTextView = itemView.findViewById(R.id.contentTextView);
        }

        void setData(Message message) {

            // on récupère l'item où on écrit le contenu du message
            mContentTextView = itemView.findViewById(R.id.contentTextView);

            // Récupération de la ImageView
            ImageView mImageView = (ImageView) itemView.findViewById(R.id.userImageView);

            // Avatar dans la mImageView
            Glide.with(mImageView.getContext()).load(Constant.GRAVATAR_PREFIX + Utils.md5(message.userMail)).apply( RequestOptions.circleCropTransform()).into(mImageView);

            // formate le timestamp du message en vraie date
            SimpleDateFormat formater = new SimpleDateFormat( "dd/MM '-' HH:mm" );
            Date date = new Date(message.timestamp);

            // on affiche le contenu textuel du message
            mContentTextView.setText(formater.format(date) + " : " + message.content);
        }
    }
}
