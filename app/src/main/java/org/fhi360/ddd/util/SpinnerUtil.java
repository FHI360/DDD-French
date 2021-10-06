package org.fhi360.ddd.util;

import android.widget.Adapter;
import android.widget.Spinner;

public class SpinnerUtil {

    public static int getIndex(Spinner spinner, String string) {
        int index = 0;
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(string)){
                index = i;
            }
        }
        return index;
    }

    public static int getIndex(Adapter adapter, String string) {
        int index = 0;
        for(int i=0; i < adapter.getCount(); i++) {
            if (string.trim().equals(adapter.getItem(i).toString())) {
                index = i;
            }
        }
        return index;
    }

}
