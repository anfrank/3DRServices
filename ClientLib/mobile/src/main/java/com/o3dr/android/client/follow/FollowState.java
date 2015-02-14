package com.o3dr.android.client.follow;

/**
 * Possible follow states.
 */
public class FollowState {

    /**
     * Vehicle is in an invalid state.
     */
    public static final int VEHICLE_STATE_INVALID = 0;

    /**
     * Vehicle is not armed.
     */
    public static final int STATE_VEHICLE_NOT_ARMED = 1;

    /**
     * Connection to the vehicle was lost.
     */
    public static final int STATE_VEHICLE_DISCONNECTED = 2;

    /**
     * Follow mode was started.
     */
    public static final int STATE_START = 3;

    /**
     * Follow mode is running.
     */
    public static final int STATE_RUNNING = 4;

    /**
     * Follow mode was ended.
     */
    public static final int STATE_END = 5;

    //Prevent class instantiation
    private FollowState(){}
}
