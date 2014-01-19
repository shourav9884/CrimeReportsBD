package com.example.crimereportsbd;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeMapFragment extends Fragment {
	
	private GoogleMap map;
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	
	
	View view;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.map_fragment, container, false);
		
        
        map = ((MapFragment)getFragmentManager().findFragmentById(R.id.fragment_map)).getMap();
		
		if(map==null){
			Toast.makeText(getActivity(), "Map not Loaded", Toast.LENGTH_LONG).show();
		}else {
			Toast.makeText(getActivity(), "Map Loaded", Toast.LENGTH_LONG).show();
		}
		Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
		Marker keil = map.addMarker(new MarkerOptions().position(KIEL)
				.title("KIEL")
				.snippet("Kiel is cool")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
		
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		
		
		return view;
		
    }
}
