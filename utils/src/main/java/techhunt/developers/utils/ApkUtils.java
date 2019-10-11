package techhunt.developers.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

public class ApkUtils {

    /**
     * Install APK
     */
    public static void installAPK(Context context, File apkFile) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * Uninstall APK
     */
    public static void uninstallAPK(Context context, String apkPackageName) {
        Intent intent = new Intent("android.intent.action.DELETE");
        intent.setData(Uri.parse("package:" + apkPackageName));
        context.startActivity(intent);
    }

    /**
     * App Info
     */
    public static void infoScreen(Context context, String apkPackageName) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + apkPackageName));
        context.startActivity(intent);
    }

    /**
     * Playstore Intent
     */
    public static void playStoreIntent(Context context, String apkPackageName) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + apkPackageName)));
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apkPackageName)));
        }
    }

    /**Share App*/
    public static String getAppName(Activity activity, String packageName) {
        try {
            PackageManager packageManager = activity.getPackageManager();
            ApplicationInfo info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            String appName = (String) packageManager.getApplicationLabel(info);
            return appName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static File saveAppToStorage(Activity context, String packageName, File dest) {
        try {
            File file = ApkUtils.getApkFile(context, packageName);
            if (!dest.exists()) dest.mkdirs();
            dest = new File(dest.getPath() + "/" + getAppName(context, packageName) + ".apk");
            if (!dest.exists()) dest.createNewFile();
            InputStream in = new FileInputStream(file);
            OutputStream out = new FileOutputStream(dest);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
            return dest;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " in the specified directory.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static boolean isValid(List<PackageInfo> packageInfos) {
        return packageInfos != null && !packageInfos.isEmpty();
    }

    private static HashMap<String, String> getAllInstalledApkFiles(Context context) {
        HashMap<String, String> installedApkFilePaths = new HashMap<>();

        PackageManager packageManager = context.getPackageManager();
        @SuppressLint("WrongConstant") List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(PackageManager.SIGNATURE_MATCH);

        if (isValid(packageInfoList)) {
            for (PackageInfo packageInfo : packageInfoList) {
                ApplicationInfo applicationInfo;

                applicationInfo = getApplicationInfoFrom(packageManager, packageInfo);

                String packageName = applicationInfo.packageName;
                String versionName = packageInfo.versionName;
                int versionCode = packageInfo.versionCode;

                File apkFile = new File(applicationInfo.publicSourceDir);
                if (apkFile.exists()) {
                    installedApkFilePaths.put(packageName, apkFile.getAbsolutePath());
                }
            }
        }

        return installedApkFilePaths;
    }

    private static ApplicationInfo getApplicationInfoFrom(PackageManager
                                                                  packageManager, PackageInfo packageInfo) {
        return packageInfo.applicationInfo;
    }

    public static File getApkFile(Context context, String packageName) {
        HashMap<String, String> installedApkFilePaths = getAllInstalledApkFiles(context);
        File apkFile = new File(installedApkFilePaths.get(packageName));
        if (apkFile.exists()) {
            return apkFile;
        }

        return null;
    }

    public static void openApp(Activity activity, String packageName) {
        if (isAppInstalled(activity, packageName)) {
            if (activity.getPackageManager().getLaunchIntentForPackage(packageName) != null) {
                activity.startActivity(activity.getPackageManager().getLaunchIntentForPackage(packageName));
            } else {
                Toast.makeText(activity, "Couldn't open", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "App not installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isAppInstalled(Activity activity, String packageName) {
        PackageManager pm = activity.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }
}
