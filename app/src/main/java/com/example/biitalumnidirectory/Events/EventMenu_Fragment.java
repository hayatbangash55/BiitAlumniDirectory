package com.example.biitalumnidirectory.Events;


import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biitalumnidirectory.MainActivity;
import com.example.biitalumnidirectory.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventMenu_Fragment extends Fragment {

    Intent intent;
    CardView postedEvents, createEvent,unPostedEvents;

    public EventMenu_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_event_menu, container, false);

        ((MainActivity) getActivity()).setActionBarTitle("Event Menu");

        postedEvents = v.findViewById(R.id.postedEvents);
        postedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), Posted_Event_Activity.class);
                startActivity(intent);
            }
        });

        createEvent = v.findViewById(R.id.createEvent);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), CreateEvent_Activity.class);
             //   intent.putExtra("EventId","");
                startActivity(intent);
            }
        });

        unPostedEvents = v.findViewById(R.id.unPostedEvents);
        unPostedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), UnPosted_Event_Activity.class);
                startActivity(intent);
            }
        });


        return v;
    }

}
