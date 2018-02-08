package io.github.satriya.refactory_test_app1.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.satriya.refactory_test_app1.Model.Photo;
import io.github.satriya.refactory_test_app1.R;

/**
 * Created by satriya on 07/02/18.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> photoList;
    private Context mContext;

    public PhotoAdapter(Context mContext, List<Photo> photoList) {
        this.photoList = photoList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist,parent,false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder holder, int position) {

        Photo currentPhoto = photoList.get(position);

        holder.tvAlbumId.setText("Album Id" + Integer.toString(currentPhoto.getAlbumId()));
        holder.tvID.setText("Id" + Integer.toString(currentPhoto.getId()));
        holder.tvTitle.setText(currentPhoto.getTitle());

        if(!TextUtils.isEmpty(currentPhoto.getThumbnailUrl())){
            Picasso.with(mContext).load(currentPhoto.getThumbnailUrl()).fit().into(holder.ivThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return (photoList != null ? photoList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivThumbnail;
        TextView tvAlbumId, tvID, tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivThumbnail = itemView.findViewById(R.id.item_photo_image);
            this.tvAlbumId = itemView.findViewById(R.id.item_photo_album);
            this.tvID = itemView.findViewById(R.id.item_photo_Id);
            this.tvTitle = itemView.findViewById(R.id.item_photo_title);
        }
    }

}
