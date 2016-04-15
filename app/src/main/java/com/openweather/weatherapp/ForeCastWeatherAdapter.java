package com.openweather.weatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.openweather.weatherapp.model.WeatherDetails;

import java.util.List;

/**
 * Adapter for MainActivity List view.
 */
public class ForeCastWeatherAdapter extends BaseAdapter {

    private Context context;
    private List<WeatherDetails> weatherList;

    public ForeCastWeatherAdapter(Context context, List<WeatherDetails> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    static class ViewHolder {
        private TextView foreCast, day, minTemp, maxTemp;
        private ImageView foreCastImage;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return weatherList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.temp_list_row, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.foreCast = (TextView) rowView.findViewById(R.id.list_item_forecastTV);
            viewHolder.day = (TextView) rowView.findViewById(R.id.list_item_dateTV);
            viewHolder.minTemp = (TextView) rowView.findViewById(R.id.list_item_lowTempTV);
            viewHolder.maxTemp = (TextView) rowView.findViewById(R.id.list_item_highTempTV);
            viewHolder.foreCastImage = (ImageView) rowView
                    .findViewById(R.id.list_item_iconIV);
            rowView.setTag(viewHolder);

            ViewHolder holder = (ViewHolder) rowView.getTag();

            String foreCast = weatherList.get(position).getForeCast();
            Log.d("Adapter", foreCast);
            holder.foreCast.setText(foreCast);
            holder.day.setText(weatherList.get(position).getTime());
            holder.maxTemp.setText((Math.round((Double.parseDouble(weatherList.get(position).getMaxTemp()) - 273.15))) + "°C");
            holder.minTemp.setText((Math.round((Double.parseDouble(weatherList.get(0).getMinTemp()) - 273.15))) + "°C");

            // setting the image from drawable based on the forecast.
            if (foreCast.contains("rain") || foreCast.contains("Rain")) {
                viewHolder.foreCastImage.setImageResource(R.drawable.img_rain);
            } else if (foreCast.contains("clouds") || foreCast.contains("Clouds")) {
                viewHolder.foreCastImage.setImageResource(R.drawable.img_cloud);
            } else if (foreCast.contains("snow") || foreCast.contains("Snow")) {
                viewHolder.foreCastImage.setImageResource(R.drawable.img_snow);
            } else {
                viewHolder.foreCastImage.setImageResource(R.drawable.img_sun);
            }


        }

        return rowView;
    }
}
