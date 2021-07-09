 package com.example.ebookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    int count;
    //List<String> imagesList = new ArrayList<>();

    Context context = null;
    List<ImageView> imageViewList;
    ImageView bookView1, bookView2, bookView3;
    Button nextButton, prevButton;
    ProgressBar simpleProgressBar;
    //Bitmap bitmapLeft , bitmap , bitmapRight;
    List<String> pagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

        bookView1 = findViewById(R.id.bookImage1);
        bookView2 = findViewById(R.id.bookImage2);
        bookView3 = findViewById(R.id.bookImage3);

        count = 0;

        pagesList =new ArrayList<>();

        imageViewList =new ArrayList<>();
        imageViewList.add(bookView1);
        imageViewList.add(bookView2);
        imageViewList.add(bookView3);

        simpleProgressBar = (ProgressBar) findViewById(R.id.progress_horizontal); // initiate the progress bar
        int progressValue = simpleProgressBar.getProgress();
        simpleProgressBar.setProgress(progressValue);

        context=this;

    /* Glide.with(this).load(storageReference).into(bookView);
        Log.d("hello",storageReference.getPath());
        try {
            final File localFile=File.createTempFile("gaurav","jpeg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    bookView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,"Gaurav Error Occured",Toast.LENGTH_LONG);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        StorageReference pathReference = storageReference.child("pictures/a1.jpeg");
        StorageReference path =  FirebaseStorage.getInstance().getReferenceFromUrl("pictures/a1.jpeg");
        path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("Path ref" , uri.toString())
            }
        });
        String hello=storageReference.;
         Log.d("pathgg",hello.toString());
        ImageView imageView = findViewById(R.id.bookImage);
        Glide.with(this).load(pathReference).into(imageView);
          Glide.with(this).load(storageReference.getDownloadUrl()).into(imageView);

         Download directly from StorageReference using Glide
         (See MyAppGlideModule for Loader registration)
         Glide.with(this  context ).load("https://firebasestorage.googleapis.com/v0/b/ebookapp-c40ab.appspot.com/o/Book1%2Fa1.jpeg?alt=media&token=b8e397b4-69a8-4f98-b46e-f09f43534615").into(imageView);
        Glide.with(this context ).using(new FirebaseImageLoader()).load(storageReference).into(bookView);
         */

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("");
        //DatabaseReference mych = database.getReference().child("a1").getParent();

        myRef.addValueEventListener(new ValueEventListener() {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                //Load only one Image Implementation
                //String link = dataSnapshot.getValue(String.class).toString();
                //loadimage(link);
                //linklist.clear();
                Log.d("Entering DataChange " ,"methoddd");
                Log.d("Data children" , dataSnapshot.getValue() + "");
                Log.d("DatabaseSnapshot", dataSnapshot.getChildrenCount()+"");

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    // Data parsing is being done within the extending classes.
                    Log.d("DataSnapshot" , "parsing children");
                    Log.d("DataValueS",data.getValue(String.class).toString()+"");
                    pagesList.add(data.getValue(String.class).toString());
                    Log.d("LinkedList print" , pagesList.size() + "");
                }

                loadImage(pagesList.get(0));
                cache();
                //loadimagehelper(linklist.get(0) , 0); //current view populate
                //loadimagehelper(linklist.get(1) , 2); //right view populate
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.d("GG", "Failed to read value.");
            }
        });
        Log.d("LinkedList print Final","Initialize Enter");

        Log.d("LinkedList print Final" , pagesList.size() + "");
    }

    /* Implementation with bitmap
    public void loadimagehelper(String url,int j)  {
         runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 if(j==1){
                     Picasso.with(context).load(url).into(bookView2);
                     try {
                         bitmapLeft = Picasso.with(context).load(url).get();
                     } catch (IOException e) {
                         e.printStackTrace();
                         Log.d("Gaurav1",e.getMessage());
                     }
                 }
                 else if(j==2){
                     Picasso.with(context).load(url).into(bookView3);
                     try {
                         bitmapRight = Picasso.with(context).load(url).get();
                     } catch (IOException e) {
                         e.printStackTrace();
                         Log.d("Gaurav2",e.getMessage());
                     }
                 }
                 else{
                     Picasso.with(context).load(url).into(bookView1);
                     try {
                         bitmap = Picasso.with(context).load(url).get();
                     } catch (IOException e) {
                         e.printStackTrace();
                         Log.d("Gaurav3",e.getMessage());
                     }
                 }
             }
         });

    }*/
    public void cache(){
        if(count<4)
            Picasso.with(this).load(pagesList.get(count+1)).into(imageViewList.get((count+1)%3));
        if(count>=1)
            Picasso.with(this).load(pagesList.get(count-1)).into(imageViewList.get((count-1)%3));
    }

    public void loadImage(String len){
        Picasso.with(this).load(len).into(bookView3);
    }
    public int getProgress(){
        return count;
    }

    public void previousPage(View view) throws IOException {
        if(count>=1){
            imageViewList.get(count%3).setVisibility(View.INVISIBLE);
            imageViewList.get((count-1)%3).setVisibility(View.VISIBLE);
            count--;
            cache();
            //TODO
           // Load respective imageview
        }
        else{
            System.out.println("Prev inside else " + count);
            Toast.makeText(getApplicationContext(),"No more pages" , Toast.LENGTH_SHORT).show();
        }
        simpleProgressBar.setProgress(count);
        System.out.println("Prev inside if " + count);

        /*if(count >= 1){
            // 1. Make visibility of leftview visible
            bookViewLeft.setVisibility(bookViewLeft.VISIBLE);
            bookView.setVisibility(bookView.INVISIBLE);



       //BITMAP IMPLEMENTATION
             bookViewRight.setImageBitmap(bitmap);
             bookView.setImageBitmap(bitmapLeft);
        //BITMAP IMPLEMENTATION
            loadimagehelper(linklist.get(count-1),1);
            count--;



            //STATIC IMPLEMETATION
             Context context = bookView.getContext();
              int id = context.getResources().getIdentifier("a"+String.valueOf(count) , "drawable", context.getPackageName());
              bookView.setImageResource(id);

        }
        else {
            System.out.println("Prev inside else " + count);
            Toast.makeText(getApplicationContext(),"No more pages" , Toast.LENGTH_LONG).show();
        }*/
    }

    public void nextPage(View view) throws IOException {
        Log.d("LinkedList print Final" , pagesList.size() + "");
        if(count<4){
            imageViewList.get(count%3).setVisibility(View.INVISIBLE);
            imageViewList.get((count+1)%3).setVisibility(View.VISIBLE);
            count++;
            cache();
            //TODO  Load respectve imageview

        }
        else{
            System.out.println("Next inside else " + count);
            Toast.makeText(getApplicationContext(),"No more pages" , Toast.LENGTH_SHORT).show();
        }
        simpleProgressBar.setProgress(count);
        System.out.println("Next inside if " + count);


        /*if(count < 4) {
              bookViewLeft = bookView;
              bookView = bookViewRight;

            bookViewLeft.setImageBitmap(bitmap);
            bookView.setImageBitmap(bitmapRight);

            loadimagehelper(linklist.get(count+1) , 2);
            count++;




//            Context context = bookView.getContext();
//            int id = context.getResources().getIdentifier("a"+String.valueOf(count) , "drawable", context.getPackageName());
//            bookView.setImageResource(id);

        }
        else {
            System.out.println("Next inside else " + count);
            Toast.makeText(getApplicationContext(),"No more pages" , Toast.LENGTH_LONG).show();
        } */
    }

}
