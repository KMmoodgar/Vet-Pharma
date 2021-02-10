package com.example.doctor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointViewHolder> {

    List<Appointment> appointmentList;
    Context context;
    FirebaseFirestore db;
    String doctorContact;


    public AppointmentAdapter(List<Appointment> appointmentList, Context context)
    {
        this.appointmentList = appointmentList;
        this.context = context;
    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycle_appointments,parent,false);
        return new AppointmentAdapter.AppointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointViewHolder holder, int position) {

        final Appointment appointment = appointmentList.get(position);
        holder.docName.setText(appointment.getDocname());
        holder.owner.setText(appointment.getOwnername());
        holder.animalType.setText(appointment.getAnimaltype());
        holder.animalAge.setText(appointment.getAnimalage());
        holder.date.setText(appointment.getDate());
        holder.session.setText(appointment.getTime());
        holder.current.setText(appointment.getCurrent());

        /*
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                db = FirebaseFirestore.getInstance();

                CharSequence options[] = new CharSequence[]
                        {
                                "Clear All",
                                "Cancel"
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Appointments");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(which == 0)
                        {

                            db.collection("DoctorView").document(doctorContact).collection("Appointment").document(appointment.getId()).delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(context,"Removed successfully",Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(context,YourAppointments.class);
                                                context.startActivity(intent);
                                                ((AppCompatActivity) context).finish();
                                            }

                                        }
                                    });
                        }
                        else if (which == 1)
                        {

                        }
                        else
                        {
                            ((AppCompatActivity) context).finish();
                        }
                    }
                });
                builder.show();
            }
        });

         */

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class AppointViewHolder extends RecyclerView.ViewHolder {

        TextView docName;
        TextView owner;
        TextView animalType;
        TextView animalAge;
        TextView date;
        TextView session;
        TextView current;
        ConstraintLayout layout;

        public AppointViewHolder(@NonNull View itemView) {
            super(itemView);
            docName = itemView.findViewById(R.id.doctorNametextViewCustomAppoint);
            owner = itemView.findViewById(R.id.ownerNametextViewCustomAppoint);
            animalType = itemView.findViewById(R.id.animalTypetextViewCustomAppoint);
            animalAge = itemView.findViewById(R.id.animalAgetextViewCustomAppoint);
            date = itemView.findViewById(R.id.datetextViewCustomAppoint);
            session = itemView.findViewById(R.id.sessiontextViewCustomAppoint);
            current = itemView.findViewById(R.id.currentDatetextViewCustomAppointment);
            layout = itemView.findViewById(R.id.constraintLayoutAppoint);
        }
    }
}
