package com.example.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class YourAppointments extends AppCompatActivity {
    RecyclerView recyclerView;
    AppointmentAdapter appointmentAdapter;
    List<Appointment> appointmentList;
    FirebaseFirestore fstore;
    String doctorContact;
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_appointments);

        Toolbar toolbar = findViewById(R.id.toolbarYourAppointments);
        setSupportActionBar(toolbar);

       // clear = findViewById(R.id.clearAllButtonAppointment);

        Paper.init(this);

        doctorContact = getIntent().getStringExtra("Contact");

        recyclerView = findViewById(R.id.recyclerViewAppoint);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appointmentList = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(appointmentList, this);

        recyclerView.setAdapter(appointmentAdapter);

        fstore = FirebaseFirestore.getInstance();

        fstore.collection("DoctorView").document(doctorContact).collection("Appointment").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                Appointment a = d.toObject(Appointment.class);
                                a.setId(d.getId());
                                appointmentList.add(a);
                            }

                            appointmentAdapter.notifyDataSetChanged();
                        }
                    }
                });

        /*
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                fstore.collection("DoctorView").document(doctorContact).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(YourAppointments.this, "Cleared successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(YourAppointments.this,YourAppointments.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });

         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbardesc, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            Paper.book().destroy();
            Intent intent = new Intent(YourAppointments.this, DoctorLogin.class);
            startActivity(intent);
            finish();
        }

        return true;
    }
}

