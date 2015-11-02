package me.otarola.imagesearch.adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import me.otarola.imagesearch.R;
import me.otarola.imagesearch.models.ImageResult;

/**
 * Created by aotarolaalvarad on 10/27/15.
 */
public class ImageResultAdapter extends ArrayAdapter<ImageResult> {

    Activity activity;
    int resource;

    public ImageResultAdapter(Activity context, int resource, List<ImageResult> images){
        super(context, R.layout.item_image_result, images);
        this.activity = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        final ItemHolder holder;

        if(row == null){
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new ItemHolder();
            holder.ivImage = (DynamicHeightImageView) row.findViewById(R.id.ivImage);
            holder.tvTitle = (TextView) row.findViewById(R.id.tvTitle);
            holder.tvContent = (TextView) row.findViewById(R.id.tvContent);

            row.setTag(holder);
        }
        else{
            holder = (ItemHolder) row.getTag();
        }

        final ImageResult imageInfo = getItem(position);

        Picasso.with(getContext())
                .load(imageInfo.thumbUrl)
                .into(holder.ivImage);

        holder.ivImage.setHeightRatio(1.0);
        holder.tvTitle.setText(Html.fromHtml(imageInfo.title));
        holder.tvContent.setText(Html.fromHtml(imageInfo.content));

        return row;
    }


    static class ItemHolder {
        DynamicHeightImageView ivImage;
        TextView tvTitle;
        TextView tvContent;
    }
}
