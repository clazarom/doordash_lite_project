package com.catlaz.doordash_lit_cl.ui.main;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.catlaz.doordash_lit_cl.R;
import com.catlaz.doordash_lit_cl.data.RestaurantDetail;
import com.catlaz.doordash_lit_cl.data.UpdatedValues;
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
public class DetailFragment extends Fragment implements APIBroadcastListener{
    private static final String _TAG= "DETAIL_FRAGMENT";

    //Details on UI
    private int id;
    private String name;
    private String description;
    private TextView telephoneView;
    private TextView addressView;

    //Communicate with the server
    private RestClient restClient;
    APIUpdateBroadcastReceiver receiver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.d(_TAG, "onCreateView");

        //Update the info we already have of the restaurant
        Bundle arguments = this.getArguments();
        assert arguments != null;
        id =  arguments.getInt("id", -1);
        name = arguments.getString("name", "restaurant");
        description = arguments.getString("description", "");


        return inflater.inflate(R.layout.fragment_rest_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(_TAG, "onViewCreated");

        //Update UI with info we have received
        //name
        TextView tv = view.findViewById(R.id.restaurant_name_detail);
        tv.setText(name);
        //description
        tv = view.findViewById(R.id.restaurant_description_detail);
        tv.setText(description);
        //image
        ImageView image = view.findViewById(R.id.restaurant_image_detail);
        image.setImageBitmap(UpdatedValues.Instance().getRestaurantImageMap().get(id));

        //telephone
        telephoneView = view.findViewById(R.id.restaurant_telephone_number);
        addressView = view.findViewById(R.id.restaurant_address);

        //Use the Restaurant's id to retrieve more data
        restClient = new RestClient(getContext());
        restClient.getRestaurantDetail(id);
        receiver = new APIUpdateBroadcastReceiver(this); // Create the receiver
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, new IntentFilter(RestClient._BROADCAST_API_UPDATE)); // Register
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(_TAG, "onPause");
        //Clean remote disposables
        if (restClient != null)
            restClient.destroyDisposables();

        //Unregister Receiver
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver); // Unregister

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(_TAG, "onResume");

        //Register broadcast receiver
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, new IntentFilter(RestClient._BROADCAST_API_UPDATE)); // Register
    }

    @Override
    public void updateUI(){
        //Update UI
        RestaurantDetail rDetail = UpdatedValues.Instance().getRestaurantDetailMap().get(id);
        telephoneView.setText(rDetail.getPhone_number());
        addressView.setText(rDetail.getAddress().toString());
    }

}
