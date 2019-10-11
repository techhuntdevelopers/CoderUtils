package techhunt.developers.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.text.DecimalFormat;

import static android.content.Context.ACTIVITY_SERVICE;

public class StorageUtils {


    public static boolean isSDPresent() {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        Boolean isSDSupportedDevice = Environment.isExternalStorageRemovable();

        return isSDSupportedDevice && isSDPresent;
    }

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static String getTotalInternalMemorySize(String path) {
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return formatSize(totalBlocks * blockSize,2);
    }

    public static long getTotalInternalMemorySizeLong(String path) {
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    @SuppressLint("ObsoleteSdkInt")
    public static String getAvailableExternalMemorySize(String path) {
        if (externalMemoryAvailable()) {
            StatFs stat = new StatFs(path);
            long bytesAvailable;
            if (android.os.Build.VERSION.SDK_INT >=
                    android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
            } else {
                bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
            }
            return StorageUtils.formatSize(bytesAvailable, 2);
        } else {
            return "";
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public static long getAvailableExternalMemorySizeLong(String path) {
        if (externalMemoryAvailable()) {
            StatFs stat = new StatFs(path);
            long bytesAvailable;
            if (android.os.Build.VERSION.SDK_INT >=
                    android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
            } else {
                bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
            }
            return bytesAvailable;
        } else {
            return 0;
        }
    }

    public static String getTotalExternalMemorySize(String path) {
        if (externalMemoryAvailable()) {
            StatFs stat = new StatFs(path);
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return formatSize(totalBlocks * blockSize,2);
        } else {
            return "";
        }
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = " KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = " MB";
                size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    public static String formatSize(double size, int digits) {
        String[] dictionary = {"bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
        int index = 0;
        for (index = 0; index < dictionary.length; index++) {
            if (size < 1024) {
                break;
            }
            size = size / 1024;
        }
        return String.format("%." + digits + "f", size) + " " + dictionary[index];
    }

    private long freeRamMemorySize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) Utils.getApp().getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;

        return availableMegs;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private long totalRamMemorySize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) Utils.getApp().getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.totalMem / 1048576L;
        return availableMegs;
    }
}
