package techhunt.developers.utils;

import android.os.Build;
import android.os.Environment;

import java.io.File;

public class PathUtils {

    private PathUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getRootPath() {
        return getAbsolutePath(Environment.getRootDirectory());
    }

    public static String getDataPath() {
        return getAbsolutePath(Environment.getDataDirectory());
    }

    public static String getDownloadCachePath() {
        return getAbsolutePath(Environment.getDownloadCacheDirectory());
    }

    public static String getInternalAppDataPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return Utils.getApp().getApplicationInfo().dataDir;
        }
        return getAbsolutePath(Utils.getApp().getDataDir());
    }

    public static String getInternalAppCodeCacheDir() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return Utils.getApp().getApplicationInfo().dataDir + "/code_cache";
        }
        return getAbsolutePath(Utils.getApp().getCodeCacheDir());
    }

    public static String getInternalAppCachePath() {
        return getAbsolutePath(Utils.getApp().getCacheDir());
    }

    public static String getInternalAppDbsPath() {
        return Utils.getApp().getApplicationInfo().dataDir + "/databases";
    }

    public static String getInternalAppDbPath(String name) {
        return getAbsolutePath(Utils.getApp().getDatabasePath(name));
    }

    public static String getInternalAppFilesPath() {
        return getAbsolutePath(Utils.getApp().getFilesDir());
    }

    public static String getInternalAppSpPath() {
        return Utils.getApp().getApplicationInfo().dataDir + "/shared_prefs";
    }

    public static String getInternalAppNoBackupFilesPath() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return Utils.getApp().getApplicationInfo().dataDir + "/no_backup";
        }
        return getAbsolutePath(Utils.getApp().getNoBackupFilesDir());
    }

    public static String getExternalStoragePath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStorageDirectory());
    }

    public static String getExternalMusicPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
    }

    public static String getExternalPodcastsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS));
    }

    public static String getExternalRingtonesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES));
    }

    public static String getExternalAlarmsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS));
    }

    public static String getExternalNotificationsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS));
    }

    public static String getExternalPicturesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
    }

    public static String getExternalMoviesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
    }

    public static String getExternalDownloadsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
    }

    public static String getExternalDcimPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
    }

    public static String getExternalDocumentsPath() {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getAbsolutePath(Environment.getExternalStorageDirectory()) + "/Documents";
        }
        return getAbsolutePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
    }

    public static String getExternalAppDataPath() {
        if (isExternalStorageDisable()) return "";
        File externalCacheDir = Utils.getApp().getExternalCacheDir();
        if (externalCacheDir == null) return "";
        return getAbsolutePath(externalCacheDir.getParentFile());
    }

    public static String getExternalAppCachePath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalCacheDir());
    }

    public static String getExternalAppFilesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(null));
    }

    public static String getExternalAppMusicPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MUSIC));
    }

    public static String getExternalAppPodcastsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PODCASTS));
    }

    public static String getExternalAppRingtonesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_RINGTONES));
    }

    public static String getExternalAppAlarmsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_ALARMS));
    }

    public static String getExternalAppNotificationsPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS));
    }

    public static String getExternalAppPicturesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    public static String getExternalAppMoviesPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_MOVIES));
    }

    public static String getExternalAppDownloadPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
    }

    public static String getExternalAppDcimPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DCIM));
    }

    public static String getExternalAppDocumentsPath() {
        if (isExternalStorageDisable()) return "";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getAbsolutePath(Utils.getApp().getExternalFilesDir(null)) + "/Documents";
        }
        return getAbsolutePath(Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
    }

    public static String getExternalAppObbPath() {
        if (isExternalStorageDisable()) return "";
        return getAbsolutePath(Utils.getApp().getObbDir());
    }

    private static boolean isExternalStorageDisable() {
        return !Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    private static String getAbsolutePath(final File file) {
        if (file == null) return "";
        return file.getAbsolutePath();
    }
}