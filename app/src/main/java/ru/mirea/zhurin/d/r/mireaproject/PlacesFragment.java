package ru.mirea.zhurin.d.r.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.BoundingBox;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.transport.TransportFactory;

public class PlacesFragment extends Fragment {

    private MapView mapView;
    private RecyclerView recyclerView;
    private PlacesAdapter placesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, container, false);

        mapView = view.findViewById(R.id.mapView);
        recyclerView = view.findViewById(R.id.recyclerView);

        MapKitFactory.setApiKey("c41822cf-d97b-4fe6-b18d-ab8391be7733");
        MapKitFactory.initialize(getActivity());
        TransportFactory.initialize(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        placesAdapter = new PlacesAdapter();
        recyclerView.setAdapter(placesAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        loadMapObjects();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    private void loadMapObjects() {
        Map map = mapView.getMap();
        MapObjectCollection mapObjects = map.getMapObjects();

        Point point1 = new Point(55.761507, 37.617515);
        mapObjects.addPlacemark(point1);

        Point point2 = new Point(55.775530, 37.683845);
        mapObjects.addPlacemark(point2);
    }
}