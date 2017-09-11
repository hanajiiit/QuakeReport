package com.example.android.quakereport;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hp on 24/08/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";
    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes){
        super(context, 0, earthquakes);
      }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }




        Earthquake currentEarthquake = getItem(position);

        String primaryLocation ;
        String locationOffset ;

        String originalLocation = currentEarthquake.getLocation();

        if(originalLocation.contains(LOCATION_SEPARATOR)){
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }



        TextView offsetTextView = (TextView) listItemView.findViewById(R.id.location_offset);

        offsetTextView.setText(locationOffset);

        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);

        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());

        magnitudeView.setText(formattedMagnitude);

        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location);

        primaryLocationTextView.setText(primaryLocation);

        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        // Format the date string (i.e. "Mar 3, 1984")

        String formattedDate = formatDate(dateObject);

        dateView.setText(formattedDate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        String formattedTime = formatTime(dateObject);

        timeView.setText(formattedTime);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        magnitudeCircle.setColor(magnitudeColor);



        return listItemView;

    }

    public String formatDate(Date dateObject){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);
        return dateToDisplay;
    }

    public String formatTime(Date dateObject){
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        String dateToDisplay = timeFormatter.format(dateObject);
        return dateToDisplay;
    }

    public String formatMagnitude(double magnitude){
        DecimalFormat formatter = new DecimalFormat("0.0");
        String decimalMagnitude = formatter.format(magnitude);
        return decimalMagnitude;
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

    return ContextCompat.getColor(getContext(), magnitudeColorResourceId);}


}
