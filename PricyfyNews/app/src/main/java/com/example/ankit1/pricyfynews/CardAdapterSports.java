package com.example.ankit1.pricyfynews;

/**
 * Created by Ankit1 on 13-10-2017.
 */
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 10/29/2015.
 */
public class CardAdapterSports extends RecyclerView.Adapter<CardAdapterSports.ViewHolder> {

    List<Values> items;

    public CardAdapterSports(String[] author,String[] title,String[] description,String[] url,String[] urlToImage,String[] publishedAt,Bitmap[] images){
        super();
        items = new ArrayList<Values>();
        for(int i =0; i<author.length; i++){
            Values item = new Values();
            item.setAuthor(author[i]);
            item.setTitle(title[i]);


            item.setDescription(description[i]);
            item.setUrl(url[i]);
            item.setUrlToImage(urlToImage[i]);
            item.setPublishedAt(publishedAt[i]);
            item.setImage(images[i]);
            items.add(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Values list =  items.get(position);
        holder.imageView.setImageBitmap(list.getImage());
        holder.title.setText(list.getTitle());
        holder.description.setText(list.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView title;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.cardview_image2);
            title = (TextView) itemView.findViewById(R.id.cardview_list_title2);
            description=(TextView) itemView.findViewById(R.id.short_description2);
        }
    }
}
