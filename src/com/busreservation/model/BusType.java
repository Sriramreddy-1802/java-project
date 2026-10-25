package com.busreservation.model;

import java.io.Serializable;

public enum BusType implements Serializable {
    AC_SLEEPER("AC Sleeper (2+1)"),
    AC_SEATER("AC Seater (2+2)"),
    NON_AC_SEATER("Non-AC Seater (2+2)"),
    VOLVO_MULTI_AXLE("Volvo Multi-Axle Luxury");

    private final String displayName;

    BusType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
