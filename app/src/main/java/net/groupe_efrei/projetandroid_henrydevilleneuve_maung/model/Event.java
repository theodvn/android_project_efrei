package net.groupe_efrei.projetandroid_henrydevilleneuve_maung.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by groupe-efrei on 08/04/16.
 */
public class Event implements Parcelable{
    private String id;
    private String title;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;
    private String price;
    private String adress;
    private String link;
    private String date;


    public Event(String id, String title, String shortDescription, String longDescription, String imageUrl, String price, String adress, String link, String date) {
        this.id = id;
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imageUrl = imageUrl;
        this.price = price;
        this.adress = adress;
        this.link = link;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price='" + price + '\'' +
                ", adress='" + adress + '\'' +
                ", link='" + link + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(shortDescription);
        dest.writeString(longDescription);
        dest.writeString(imageUrl);
        dest.writeString(price);
        dest.writeString(adress);
        dest.writeString(link);
        dest.writeString(date);
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>()
    {
        @Override
        public Event createFromParcel(Parcel source)
        {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size)
        {
            return new Event[size];
        }
    };

    public Event(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.shortDescription = in.readString();
        this.longDescription = in.readString();
        this.imageUrl = in.readString();
        this.price = in.readString();
        this.adress = in.readString();
        this.link = in.readString();
        this.date = in.readString();
    }

}
