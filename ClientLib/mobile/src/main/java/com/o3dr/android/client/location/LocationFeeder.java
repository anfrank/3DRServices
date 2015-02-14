package com.o3dr.android.client.location;

/**
 * Interface for location provider.
 */
public interface LocationFeeder {

    void enableLocationUpdate();

    void disableLocationUpdate();

    void setLocationReceiver(LocationReceiver receiver);
}
