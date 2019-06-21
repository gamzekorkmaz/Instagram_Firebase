package com.gamzekorkmaz.instagramfirebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostClass extends ArrayAdapter<String>{

    private final ArrayList<String> userEmail;
    private final ArrayList<String> userComment;
    private final ArrayList<String> userImage;
    private final Activity context;
    //array list ler içinde firebase den çekeceğimiz veriler olacak

    public PostClass(ArrayList<String> userEmail, ArrayList<String> userComment, ArrayList<String> userImage, Activity context) {
        super(context,R.layout.custom_view,userEmail);
        this.userEmail = userEmail;
        this.userComment = userComment;
        this.userImage = userImage;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //custom view içerisindeki elemanları verilerimizle doldurmak için burayı yazdık
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view,null,true ); //custom view burada bağlandı
        //böylece custom view içerisindeki tüm elemanlara ulaşabiliriz
        TextView userEmailText = customView.findViewById(R.id.userEmailTextViewCustomView);
        TextView commentText = customView.findViewById(R.id.commentTextViewCustomView);
        ImageView imageView = customView.findViewById(R.id.imageViewCustomView);

        userEmailText.setText(userEmail.get(position));
        commentText.setText(userComment.get(position));
        Picasso.get().load(userImage.get(position)).into(imageView); //hangi url yi nereye yükleyeceksek picasso ile yapabiliriz

        return customView;
    }
}
