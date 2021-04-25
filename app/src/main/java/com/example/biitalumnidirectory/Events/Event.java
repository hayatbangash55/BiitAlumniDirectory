package com.example.biitalumnidirectory.Events;

public class Event {
    public String EventId;
    public String Title;
    public String Date;
    public String Time;
    public String Venue;
    public String Description;

    public Event(String eventId, String title, String date, String time, String venue, String description) {
        EventId = eventId;
        Title = title;
        Date = date;
        Time = time;
        Venue = venue;
        Description = description;
    }
}
