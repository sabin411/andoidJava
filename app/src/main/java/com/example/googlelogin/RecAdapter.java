package com.example.googlelogin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class RecAdapter extends FirebaseRecyclerAdapter<RecViewDataHolder,RecAdapter.myviewHolder>
{
    private Context context;

    public RecAdapter(@NonNull FirebaseRecyclerOptions<RecViewDataHolder> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewHolder holder, final int position, @NonNull final RecViewDataHolder model)
    {
        holder.titleJournal.setText(model.getTitle());
        holder.dateJournal.setText(model.getDate());
        holder.locationJournal.setText(model.getLocation());
        Glide.with(holder.imageJournal.getContext()).load(model.getImage()).into(holder.imageJournal);

        // to update or edit the journal
        holder.editJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.imageJournal.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_journal))
                        .setExpanded(true,1550)
                        .create();
                View myview=dialogPlus.getHolderView();
                TextInputEditText updateDate=myview.findViewById(R.id.updateDate);
                AppCompatEditText updateLocation=myview.findViewById(R.id.updateLocation);
                ImageView updateimageView=myview.findViewById(R.id.updateimageView);
                AppCompatEditText updateTitle=myview.findViewById(R.id.updateTitle);
                AppCompatEditText updateDescription=myview.findViewById(R.id.updateDescription);
                AppCompatButton updateJournal=myview.findViewById(R.id.updateJournal);
                FloatingActionButton updateCamera=myview.findViewById(R.id.updateCamera);
                FloatingActionButton updateGallery=myview.findViewById(R.id.updateGallery);

                Picasso.get().load(model.getImage()).into(updateimageView);
                updateDate.setText(model.getDate());
                updateLocation.setText(model.getLocation());
//                Picasso.get().load(model.getImage()).into(updateimageView);
                updateTitle.setText(model.getTitle());
                updateDescription.setText(model.getDescription());
                dialogPlus.show();

                updateCamera.setOnClickListener(view1 -> {

                });

                updateGallery.setOnClickListener(view1 -> {

                });

               updateJournal.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Toast.makeText(holder.imageJournal.getContext(), model.getUserId()+model.getJournalKey(), Toast.LENGTH_SHORT).show();
                       RecViewDataHolder updatedJournal = new RecViewDataHolder(
                               updateDate.getText().toString(),
                               updateTitle.getText().toString().trim(),
                               updateDescription.getText().toString().trim(),
                               updateLocation.getText().toString().trim(),
                               model.getImage(),
                               model.getUserId(),
                               model.getJournalKey()
                       );

                       FirebaseDatabase.getInstance().getReference().child("JournalEntries/"+model.getUserId()+"/"+model.getJournalKey())
                               .setValue(updatedJournal)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       Toast.makeText(holder.imageJournal.getContext(), "The journal has been edited!", Toast.LENGTH_SHORT).show();
                                       updateDate.setText("");
                                       updateTitle.setText("");
                                       updateDescription.setText("");
                                       updateLocation.setText("");
                                       updateimageView.setImageURI(null);

                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(holder.imageJournal.getContext(), "Error occurred while updating the journal :(", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
               });




            }
        });

//        function to delete journal starts from here
        holder.deleteJournal.setOnClickListener(view -> {
            FirebaseDatabase.getInstance().getReference().child("JournalEntries/"+model.getUserId()+"/"+model.getJournalKey()).removeValue();
            Toast.makeText(holder.titleJournal.getContext(), "The journal has been deleted!", Toast.LENGTH_SHORT).show();
        });
        //function to share journal
        holder.shareJournal.setOnClickListener(view -> {
            String placeName = holder.titleJournal.getText().toString().trim();
            String travelledDate = holder.dateJournal.getText().toString().trim();
            String placeDescription = model.getDescription();
            String location = holder.locationJournal.getText().toString().trim();

            Uri placeImage = Uri.parse(holder.imageJournal.getContext().toString());
            Log.d("TAG", "onBindViewHolder: "+ placeImage);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_STREAM, placeImage);
            sendIntent.setType("image/*");
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Visited to: "+placeName);
//            sendIntent.setType("text/html");
//
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Date: "+travelledDate);
//            sendIntent.setType("text/html");
//
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "About Journey: "+placeDescription);
//            sendIntent.setType("text/html");
//
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "At: "+location);
//            sendIntent.setType("text/html");

            Intent shareIntent = Intent.createChooser(sendIntent, null);

            context.startActivity(shareIntent);
        });

    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new myviewHolder(view);
    }

    class myviewHolder extends RecyclerView.ViewHolder {

        ImageView imageJournal;
        TextView titleJournal, dateJournal, locationJournal;
        ImageButton editJournal, shareJournal, deleteJournal;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
          imageJournal = (ImageView) itemView.findViewById(R.id.imageJournal);
          titleJournal = (TextView) itemView.findViewById(R.id.titleJournal);
          dateJournal = (TextView) itemView.findViewById(R.id.dateJournal);
          locationJournal = (TextView) itemView.findViewById(R.id.locationJournal);
          editJournal = (ImageButton) itemView.findViewById(R.id.editJournal);
          shareJournal = (ImageButton) itemView.findViewById(R.id.shareJournal);
          deleteJournal = (ImageButton) itemView.findViewById(R.id.deleteJournal);

        }
    }
}


