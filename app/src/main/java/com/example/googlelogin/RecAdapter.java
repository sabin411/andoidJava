package com.example.googlelogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class RecAdapter extends FirebaseRecyclerAdapter<RecViewDataHolder,RecAdapter.myviewHolder>
{
    public RecAdapter(@NonNull FirebaseRecyclerOptions<RecViewDataHolder> options) {
        super(options);
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
                       Map<String,Object> map=new HashMap<>();


                   }
               });




            }
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
        ImageView editJournal, shareJournal, deleteJournal;

        public myviewHolder(@NonNull View itemView) {
            super(itemView);
          imageJournal = (ImageView) itemView.findViewById(R.id.imageJournal);
          titleJournal = (TextView) itemView.findViewById(R.id.titleJournal);
          dateJournal = (TextView) itemView.findViewById(R.id.dateJournal);
          locationJournal = (TextView) itemView.findViewById(R.id.locationJournal);
          editJournal = (ImageView) itemView.findViewById(R.id.editJournal);
          shareJournal = (ImageView) itemView.findViewById(R.id.shareJournal);
          deleteJournal = (ImageView) itemView.findViewById(R.id.deleteJournal);

        }
    }
}


