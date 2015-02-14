package com.o3dr.android.client.location;

import android.location.Location;

/**
 * Interface for consumer of location provider data.
 */
public interface LocationReceiver {

    void onLocationUpdate(Location location, boolean isAccurate);

    void onLocationUnavailable();
}
