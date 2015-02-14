package com.o3dr.android.client;

import android.location.Location;
import android.os.Bundle;

import com.o3dr.android.client.follow.FollowState;
import com.o3dr.android.client.follow.FollowStyle;
import com.o3dr.android.client.follow.FollowStyleImpl;
import com.o3dr.android.client.interfaces.DroneListener;
import com.o3dr.android.client.location.LocationFeeder;
import com.o3dr.android.client.location.LocationReceiver;
import com.o3dr.services.android.lib.drone.attribute.AttributeEvent;
import com.o3dr.services.android.lib.drone.connection.ConnectionResult;

/**
 * Implements Follow (3PV) behavior.
 */
public class Follow implements DroneListener, LocationReceiver {

    private final Drone drone;

    private FollowStyleImpl styleImpl;

    private LocationFeeder locationFeeder;
    private Location lastLocation;

    private int followState = FollowState.VEHICLE_STATE_INVALID;

    Follow(Drone drone){
        this.drone = drone;
    }

    public void setStyle(FollowStyle style) {
        if(this.style != null && this.style != style){
            this.style.disable();
        }

        this.style = style;
        if(isEnabled()){
            this.style.enable();

            if(lastLocation != null)
                this.style.onLocationUpdate(lastLocation);
        }

        this.drone.notifyAttributeUpdated(AttributeEvent.FOLLOW_UPDATE, null);
    }

    public void setLocationFeeder(LocationFeeder locationFeeder) {
        if(this.locationFeeder != null)
            this.locationFeeder.setLocationReceiver(null);

        this.locationFeeder = locationFeeder;
        this.locationFeeder.setLocationReceiver(this);
    }

    @Override
    public void onDroneConnectionFailed(ConnectionResult result) {
        disable();
    }

    @Override
    public void onDroneEvent(String event, Bundle extras) {
        switch(event){
            case AttributeEvent.STATE_VEHICLE_MODE:
                //TODO: complete
                break;

            case AttributeEvent.STATE_DISCONNECTED:
                disable();
                break;
        }
    }

    @Override
    public void onDroneServiceInterrupted(String errorMsg) {
        disable();
    }

    /**
     * Enable follow mode for the vehicle.
     */
    public void enable(){
        this.drone.registerDroneListener(this);
    }

    public boolean isEnabled(){
        return followState == FollowState.RUNNING  || followState == FollowState.STARTED;
    }

    /**
     * Disable follow mode for the vehicle.
     */
    public void disable(){
        this.drone.unregisterDroneListener(this);
    }

    @Override
    public void onLocationUpdate(Location location, boolean isAccurate) {
        if(isAccurate){
            followState = FollowState.RUNNING;
            lastLocation = location;
        }

    }

    @Override
    public void onLocationUnavailable() {

    }

    public int getFollowState() {
        return followState;
    }

    public FollowStyle getFollowStyle(){
        if(styleImpl == null)
            return null;

        return styleImpl.getFollowStyle();
    }
}
