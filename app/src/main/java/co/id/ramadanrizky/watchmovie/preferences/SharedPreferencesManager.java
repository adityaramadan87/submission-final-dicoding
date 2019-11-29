package co.id.ramadanrizky.watchmovie.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    SharedPreferences sharedPreferences;

    private static final String KEY_NOTIF_STATE = "state_notif";
    private static final String KEY_NOTIF_STATE_RELEASE = "state_notif_release";
    private static final String NAME_SHRDPREF = "notif";

    public SharedPreferencesManager(Context context){
        sharedPreferences = context.getSharedPreferences(NAME_SHRDPREF, Context.MODE_PRIVATE);
    }

    public void saveStateNotificatio(Boolean state){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_NOTIF_STATE, state);
        editor.apply();
    }

    public Boolean loadStateNotification(){
        Boolean state = sharedPreferences.getBoolean(KEY_NOTIF_STATE, false);
        return state;
    }
    public void saveStateNotificationRelease(Boolean state){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_NOTIF_STATE_RELEASE, state);
        editor.apply();
    }

    public Boolean loadStateNotificationRelease(){
        Boolean stateRelease = sharedPreferences.getBoolean(KEY_NOTIF_STATE_RELEASE, false);
        return stateRelease;
    }


}
