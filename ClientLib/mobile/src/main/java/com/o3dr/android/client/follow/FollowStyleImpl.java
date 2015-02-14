package com.o3dr.android.client.follow;

import android.location.Location;
import android.os.Bundle;

import com.o3dr.android.client.Drone;
import com.o3dr.android.client.apis.drone.GuidedApi;
import com.o3dr.services.android.lib.drone.attribute.AttributeType;
import com.o3dr.services.android.lib.drone.property.GuidedState;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Base class for the follow styles implementation.
 */
public abstract class FollowStyleImpl {

    protected final Drone drone;
    private final AtomicBoolean isEnabled = new AtomicBoolean(false);

    public FollowStyleImpl(Drone drone){
        this.drone = drone;
    }

    protected boolean isEnabled(){
        return isEnabled.get();
    }

    public void enable(){
        isEnabled.set(true);
    }

    public void disable(){
        isEnabled.set(false);

        GuidedState guidedState = drone.getAttribute(AttributeType.GUIDED_STATE);
        if(guidedState != null && guidedState.isInitialized()){
            GuidedApi.pauseAtCurrentLocation(drone);
        }
    }

    public final void onLocationUpdate(Location location){
        if(isEnabled()){
            onLocationUpdateImpl(location);
        }
    }

    /**
     * Used to update the parameters for the style implementation
     * @param parameters Bundle of parameters value.
     */
    public void updateParameters(Bundle parameters){}

    protected abstract void onLocationUpdateImpl(Location location);

    public abstract FollowStyle getFollowStyle();

    public static FollowStyleImpl getImpl(Drone drone, FollowStyle style){
        switch(style){
            case LEASH:
                return new LeashStyleImpl(drone);

            case LEAD:
                return new LeadStyleImpl(drone);

            case RIGHT:
                return new RightStyleImpl(drone);

            case LEFT:
                return new LeftStyleImpl(drone);

            case CIRCLE:
                return new CircleStyleImpl(drone);

            case ABOVE:
                return new AboveStyleImpl(drone);

            case SPLINE_LEASH:
                return new SplineLeashStyleImpl(drone);

            case SPLINE_ABOVE:
                return new SplineAboveStyleImpl(drone);

            case GUIDED_SCAN:
                return new GuidedScanStyleImpl(drone);

            default:
                return null;
        }
    }
}
