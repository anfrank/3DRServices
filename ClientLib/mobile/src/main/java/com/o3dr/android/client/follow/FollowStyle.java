package com.o3dr.android.client.follow;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Define the style used for follow me.
 */
public enum FollowStyle implements Parcelable {

    LEASH("Leash"),
    LEAD("Lead"),
    RIGHT("Right"),
    LEFT("Left"),
    CIRCLE("Circle"),
    ABOVE("Above"){
        @Override
        public boolean hasParam(String paramKey){
            return false;
        }
    },
    SPLINE_LEASH("Vector Leash"),
    SPLINE_ABOVE("Vector Above"){
        @Override
        public boolean hasParam(String paramKey){
            return false;
        }
    },
    GUIDED_SCAN("Guided Scan"){
        @Override
        public boolean hasParam(String paramKey){
            switch(paramKey){
                case EXTRA_FOLLOW_ROI_TARGET:
                    return true;

                default:
                    return false;
            }
        }
    };

    public static final String EXTRA_FOLLOW_RADIUS = "extra_follow_radius";
    public static final String EXTRA_FOLLOW_ROI_TARGET = "extra_follow_roi_target";

    private final String typeLabel;

    private FollowStyle(String typeLabel){
        this.typeLabel = typeLabel;
    }

    public boolean hasParam(String paramKey){
        switch(paramKey){
            case EXTRA_FOLLOW_RADIUS:
                return true;

            case EXTRA_FOLLOW_ROI_TARGET:
                return false;

            default:
                return false;
        }
    }

    public String getStyleLabel() {
        return typeLabel;
    }

    @Override
    public String toString(){
        return getStyleLabel();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name());
    }

    public static List<FollowStyle> getFollowStyles(boolean includeAdvanced){
        List<FollowStyle> followStyles = new ArrayList<>();
        followStyles.add(LEASH);
        followStyles.add(LEAD);
        followStyles.add(RIGHT);
        followStyles.add(LEFT);
        followStyles.add(CIRCLE);
        followStyles.add(ABOVE);
        followStyles.add(GUIDED_SCAN);

        if(includeAdvanced){
            followStyles.add(SPLINE_LEASH);
            followStyles.add(SPLINE_ABOVE);
        }

        return followStyles;
    }

    public static final Parcelable.Creator<FollowStyle> CREATOR = new Parcelable.Creator<FollowStyle>() {
        public FollowStyle createFromParcel(Parcel source) {
            return FollowStyle.valueOf(source.readString());
        }

        public FollowStyle[] newArray(int size) {
            return new FollowStyle[size];
        }
    };
}
