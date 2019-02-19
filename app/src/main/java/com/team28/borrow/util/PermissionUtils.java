package com.team28.borrow.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;

public class PermissionUtils {

    public static ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted, Context context) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wanted) {
            if (hasPermission(perm, context)) {
                result.add(perm);
            }
        }

        return result;
    }

    private static boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static boolean hasPermission(String permission, Context context) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED);
            }
        }
        return false;
    }

}
