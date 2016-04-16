package net.groupe_efrei.projetandroid_henrydevilleneuve_maung.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.R;
import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.activities.EventDetails;
import net.groupe_efrei.projetandroid_henrydevilleneuve_maung.model.Event;

import java.util.List;

/**
 * Created by groupe-efrei on 08/04/16.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

    private List<Event> contactList;
    private Context context;
    private String parentactivity;

    public EventAdapter(List<Event> eventList, Context context, String parentactivity) {
        this.contactList = eventList;
        this.context = context;
        this.parentactivity = parentactivity;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, final int i) {
        Event ci = contactList.get(i);

        eventViewHolder.vDescription.setText(ci.getShortDescription());
        eventViewHolder.vTitle.setText(ci.getTitle());
        Picasso.with(context).load(ci.getImageUrl()).into(eventViewHolder.vImage);

        eventViewHolder.vCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetails.class);
                intent.putExtra("event", contactList.get(i));
                Log.i("INFO", parentactivity);
                intent.putExtra("caller", parentactivity);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cardview_list_music, viewGroup, false);

        return new EventViewHolder(itemView);
    }


    public static class EventViewHolder extends RecyclerView.ViewHolder {
        protected TextView vTitle;
        protected ImageView vImage;
        protected TextView vDescription;
        protected CardView vCard;
        protected com.github.ivbaranov.mfb.MaterialFavoriteButton vFavorite;

        public EventViewHolder(View v) {
            super(v);
            vDescription = (TextView)  v.findViewById(R.id.description);
            vImage = (ImageView)  v.findViewById(R.id.imageevent);
            vTitle = (TextView) v.findViewById(R.id.title);
            vCard = (CardView) v.findViewById(R.id.card_view);
            vFavorite = (com.github.ivbaranov.mfb.MaterialFavoriteButton) v.findViewById(R.id.favorite);

        }

    }


}
