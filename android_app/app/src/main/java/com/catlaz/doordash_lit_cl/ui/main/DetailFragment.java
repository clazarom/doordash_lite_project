package com.catlaz.doordash_lit_cl.ui.main;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.catlaz.doordash_lit_cl.R;
import com.catlaz.doordash_lit_cl.remote.RestClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * A fragment containing a list of restaurants close to DoorDash HQ (37.422740, -122.139956)
 *
 * @author Caterina lazaro
 * @version 1.0 Jan 2021
 */
public class DetailFragment extends Fragment {

    private int id;
    private String name;
    private String description;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //Update the info we already have of the restaurant
        Bundle arguments = this.getArguments();
        id =  arguments.getInt("id", -1);
        name = arguments.getString("name", "restaurant");
        description = arguments.getString("description", "");

        return inflater.inflate(R.layout.fragment_rest_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //name
        TextView tv = view.findViewById(R.id.restaurant_name_detail);
        tv.setText(name);
        //description
        tv = view.findViewById(R.id.restaurant_description_detail);
        tv.setText(description);

        //Use the Restaurant's id to retrieve more data
        RestClient rClient = new RestClient(getContext());
        rClient.getRestaurantDetail(id);
        UpdatesBroadcastReceiver receiver = new UpdatesBroadcastReceiver(new Handler()); // Create the receiver
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter(RestClient._BROADCAST_API_UPDATE)); // Register
    }

}
