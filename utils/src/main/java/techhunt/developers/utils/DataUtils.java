package techhunt.developers.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import techhunt.developers.utils.Utils;
import techhunt.developers.utils.operation.model.Album;
import techhunt.developers.utils.operation.model.ApkModel;
import techhunt.developers.utils.operation.model.Media;
import techhunt.developers.utils.operation.provider.AlbumsHelper;
import techhunt.developers.utils.operation.provider.CPHelper;
import techhunt.developers.utils.operation.provider.HandlingAlbums;
import techhunt.developers.utils.operation.sort.SortingMode;
import techhunt.developers.utils.operation.sort.SortingOrder;

public class DataUtils {

    ArrayList<String> excuded = new ArrayList<>();

    private HandlingAlbums db() {
        return HandlingAlbums.getInstance(Utils.getApp().getApplicationContext());
    }

    @SuppressLint("CheckResult")
    public void getImageAlbums(boolean isHidden, AlbumCallback callback) {
        SQLiteDatabase db = HandlingAlbums.getInstance(Utils.getApp().getApplicationContext()).getReadableDatabase();
        CPHelper.getAlbums(Utils.getApp(), isHidden, excuded, AlbumsHelper.getSortingMode(), AlbumsHelper.getSortingOrder())
                .subscribeOn(Schedulers.io())
                .map(album -> album.withSettings(HandlingAlbums.getSettings(db, album.getPath())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        album -> {
                            if (callback != null)
                                callback.onAdd(album);
                        },
                        throwable -> {
                            throwable.printStackTrace();
                        },
                        () -> {
                            db.close();
                            if (callback != null)
                                callback.onDone();
                        });
    }

    public void getAlbumPhotos(Album album, MediaCallback callback) {
        CPHelper.getMedia(Utils.getApp(), album)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(media -> {
                            if (media.getFile() != null) {
                                if (callback != null) callback.onAdd(media);
                            }
                        },
                        throwable -> {
                            Log.wtf("asd", throwable);
                        },
                        () -> {
                            if (callback != null) callback.onDone();
                        });
    }

    public void getAllPhotos(SortingMode sortingMode, SortingOrder sortingOrder, MediaCallback callback) {
        CPHelper.getAllMediaFromMediaStore(Utils.getApp(), sortingMode, sortingOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(media -> {
                            if (media.getFile() != null) {
                                if (callback != null) callback.onAdd(media);
                            }
                        },
                        throwable -> {
                            Log.wtf("asd", throwable);
                        },
                        () -> {
                            if (callback != null) callback.onDone();
                        });
    }

    @SuppressLint("CheckResult")
    public void getVideoAlbums(boolean isHidden, AlbumCallback callback) {
        SQLiteDatabase db = HandlingAlbums.getInstance(Utils.getApp().getApplicationContext()).getReadableDatabase();
        CPHelper.getVideoAlbums(Utils.getApp(), excuded, AlbumsHelper.getSortingMode(), AlbumsHelper.getSortingOrder())
                .subscribeOn(Schedulers.io())
                .map(album -> album.withSettings(HandlingAlbums.getSettings(db, album.getPath())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        album -> {
                            if (callback != null)
                                callback.onAdd(album);
                        },
                        throwable -> {
                            throwable.printStackTrace();
                        },
                        () -> {
                            db.close();
                            if (callback != null)
                                callback.onDone();
                        });
    }

    public void getAlbumVideos(Album album, MediaCallback callback) {
        CPHelper.getVideo(Utils.getApp(), album)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(media -> {
                            if (media.getFile() != null) {
                                if (callback != null) callback.onAdd(media);
                            }
                        },
                        throwable -> {
                            Log.wtf("asd", throwable);
                        },
                        () -> {
                            if (callback != null) callback.onDone();
                        });
    }

    @SuppressLint("CheckResult")
    public void getAllVideos(SortingMode sortingMode, SortingOrder sortingOrder, MediaCallback callback) {
        CPHelper.getAllVideosFromMediaStore(Utils.getApp(), sortingMode, sortingOrder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(media -> {
                            if (media.getFile() != null) {
                                if (callback != null) callback.onAdd(media);
                            }
                        },
                        throwable -> {
                            Log.wtf("asd", throwable);
                        },
                        () -> {
                            if (callback != null) callback.onDone();
                        });
    }

    public static ArrayList<File> getAudioLibrary(Context context) {
        ArrayList<File> list = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] data = new String[]{MediaStore.Audio.Media.DATA};
        String selection = MediaStore.Audio.Media.IS_MUSIC;
        Cursor cursor = context.getContentResolver().query(uri, data, selection, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                File file = new File(cursor.getString(cursor.getColumnIndex(data[0])));
                if (file.exists()) list.add(file);
            }
            cursor.close();
        }
        return list;
    }

    public static List<File> getRecentFiles(Context context) {
        List<File> recentFiles = new ArrayList<>();
        final String[] projection = {MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.DATE_MODIFIED};
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 2);
        Date d = c.getTime();
        Cursor cursor = context.getContentResolver().query(MediaStore.Files
                        .getContentUri("external"), projection,
                null,
                null, null);
        if (cursor == null) return recentFiles;
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                String path = cursor.getString(cursor.getColumnIndex
                        (MediaStore.Files.FileColumns.DATA));
                File f = new File(path);
                if (d.compareTo(new Date(f.lastModified())) != 1 && !f.isDirectory()) {
                    recentFiles.add(f);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        if (recentFiles.size() > 20)
            for (int i = recentFiles.size() - 1; i > 20; i--) {
                recentFiles.remove(i);
            }
        return recentFiles;
    }

    public static ArrayList<File> getAPKLibrary(Context context) {
        ArrayList<File> list = new ArrayList<>();
        Uri uri = MediaStore.Files
                .getContentUri("external");
        String[] data = new String[]{MediaStore.Files.FileColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, data, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                File file = new File(cursor.getString(cursor.getColumnIndex(data[0])));
                if (file.exists())
                    if (getMimeType(file).startsWith("application/vnd.android.package-archive"))
                        list.add(file);
            }
            cursor.close();
        }
        return list;
    }

    public static List<File> getDocuments(Context context) {
        ArrayList<File> list = new ArrayList<>();
        Uri uri = MediaStore.Files
                .getContentUri("external");
        String[] data = new String[]{MediaStore.Files.FileColumns.DATA};
        String[] types = new String[]{".pdf", ".xml", ".html", ".htm", ".asm", ".text/x-asm", ".def", ".in", ".rc",
                ".list", ".log", ".pl", ".prop", ".properties", ".rc",
                ".doc", ".docx", ".xls", ".xlsx", ".msg", ".ppt", ".pptx", ".odt", ".pages", ".rtf", ".txt", ".wpd", ".wps", ".txt"};

        Cursor cursor = context.getContentResolver().query(uri, data, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                File file = new File(cursor.getString(cursor.getColumnIndex(data[0])));
                if (file.exists())
                    if (Arrays.asList(types).contains("." + getExtensionFromFilePath(file.getPath())) && !file.isDirectory()) {
                        list.add(file);
                        Log.e("listDocs: ", file.getAbsolutePath());
                    }
            }
            cursor.close();
        }
        return list;
    }

    public static List<ApkModel> getInstalledApp(Context context) {
        List<ApkModel> user = new ArrayList<>();
        // Flags: See below
        int flags = PackageManager.GET_META_DATA |
                PackageManager.GET_SHARED_LIBRARY_FILES |
                PackageManager.GET_UNINSTALLED_PACKAGES;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> applications = pm.getInstalledApplications(flags);
        for (ApplicationInfo appInfo : applications) {
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                // System application
            } else {
                // Installed by user
                PackageInfo pinfo = null;
                try {
                    pinfo = context.getPackageManager().getPackageInfo(appInfo.packageName, 0);
                    long installed = context.getPackageManager()
                            .getPackageInfo(pinfo.packageName, 0)
                            .firstInstallTime;
                    File file = new File(appInfo.publicSourceDir);
                    String size = StorageUtils.formatSize(file.length(), 2);
                    Drawable icon = context.getPackageManager().getApplicationIcon(appInfo.packageName);
                    user.add(new ApkModel(pinfo.applicationInfo.loadLabel(context.getPackageManager()).toString(), pinfo.versionName, pinfo.packageName, CalenderUtils.getDateFrommMilli(installed, "dd-MM-yyyy"), icon, size));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return user;
    }

    public static List<ApkModel> getSystemApp(Context context) {
        List<ApkModel> user = new ArrayList<>();
        // Flags: See below
        int flags = PackageManager.GET_META_DATA |
                PackageManager.GET_SHARED_LIBRARY_FILES |
                PackageManager.GET_UNINSTALLED_PACKAGES;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> applications = pm.getInstalledApplications(flags);
        for (ApplicationInfo appInfo : applications) {
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                // System application
                PackageInfo pinfo = null;
                try {
                    pinfo = context.getPackageManager().getPackageInfo(appInfo.packageName, 0);
                    long installed = context.getPackageManager()
                            .getPackageInfo(pinfo.packageName, 0)
                            .firstInstallTime;
                    File file = new File(appInfo.publicSourceDir);
                    String size = StorageUtils.formatSize(file.length(), 2);
                    Drawable icon = context.getPackageManager().getApplicationIcon(appInfo.packageName);
                    user.add(new ApkModel(pinfo.applicationInfo.loadLabel(context.getPackageManager()).toString(), pinfo.versionName, pinfo.packageName, CalenderUtils.getDateFrommMilli(installed, "dd-MM-yyyy"), icon, size));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                // Installed by user
            }
        }

        return user;
    }

    private static String getExtension(String filename) {
        return filename.contains(".") ? filename.substring(filename.lastIndexOf(".") + 1) : "";
    }

    public static String getMimeType(File file) {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(getExtension(file.getName()));
    }

    public static List<File> getZrchives(Context context) {
        ArrayList<File> list = new ArrayList<>();
        Uri uri = MediaStore.Files
                .getContentUri("external");
        String[] data = new String[]{MediaStore.Files.FileColumns.DATA};
        String[] types = new String[]{".zip", ".rar", ".tar", ".tar.gz", ".7z"};

        Cursor cursor = context.getContentResolver().query(uri, data, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                File file = new File(cursor.getString(cursor.getColumnIndex(data[0])));
                if (file.exists())
                    if (Arrays.asList(types).contains("." + getExtensionFromFilePath(file.getPath())) && !file.isDirectory()) {
                        list.add(file);
                        Log.e("listDocs: ", file.getAbsolutePath());
                    }
            }
            cursor.close();
        }
        return list;
    }

    public static List<File> getWhatsAppImages() {
        List<File> files = new ArrayList<>();
        String uri = MediaStore.Images.Media.DATA;
        String[] projection = {uri, MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.SIZE};

        String selection = MediaStore.Images.Media.DATA + " like?";
        String[] selectionArgs = new String[]{"%WhatsApp%"};

        try {
            Cursor cursor = Utils.getApp().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, selection, selectionArgs, MediaStore.Images.Media.DATE_TAKEN + " DESC");
            if (cursor != null) {
                boolean isDataPresent = cursor.moveToFirst();
                if (isDataPresent) {
                    do {
                        files.add(new File(cursor.getString(cursor.getColumnIndex(uri))));
                    } while (cursor.moveToNext());
                }
                if (cursor != null) {
                    cursor.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    public static List<File> getWhatsAppVideos() {
        List<File> files = new ArrayList<>();
        String uri = MediaStore.Video.Media.DATA;
        String[] projection = {uri, MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.SIZE};

        String selection = MediaStore.Video.Media.DATA + " like?";
        String[] selectionArgs = new String[]{"%WhatsApp%"};

        try {
            Cursor cursor = Utils.getApp().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    projection, selection, selectionArgs, MediaStore.Video.Media.DATE_TAKEN + " DESC");
            if (cursor != null) {
                boolean isDataPresent = cursor.moveToFirst();
                if (isDataPresent) {
                    do {
                        files.add(new File(cursor.getString(cursor.getColumnIndex(uri))));
                    } while (cursor.moveToNext());
                }
                if (cursor != null) {
                    cursor.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    public static String getExtensionFromFilePath(String fullPath) {
        String[] filenameArray = fullPath.split("\\.");
        return filenameArray[filenameArray.length - 1];
    }

    public interface AlbumCallback {
        void onAdd(Album album);

        void onDone();
    }

    public interface MediaCallback {
        void onAdd(Media media);

        void onDone();
    }
}
