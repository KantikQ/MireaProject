package ru.mirea.zhurin.d.r.mireaproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {

    private String[] places;
    private String[] descriptions;

    public PlacesAdapter() {
        places = new String[]{"Dante", "Oblivion"};
        descriptions = new String[]{"Ресторан, улица Кузнецкий Мост, 3с2, 55.761490, 37.617519", "ресторан, Бакунинская улица, 23-41, 55.775554, 37.683853"};
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        holder.placeTextView.setText(places[position]);
        holder.descriptionTextView.setText(descriptions[position]);
    }

    @Override
    public int getItemCount() {
        return places.length;
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView placeTextView;
        TextView descriptionTextView;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            placeTextView = itemView.findViewById(R.id.placeTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
