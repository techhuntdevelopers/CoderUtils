package techhunt.developers.utils.operation.provider;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import techhunt.developers.utils.operation.model.Album;
import techhunt.developers.utils.operation.sort.SortingMode;
import techhunt.developers.utils.operation.sort.SortingOrder;
import techhunt.developers.utils.operation.utils.StorageHelper;

import static techhunt.developers.utils.operation.utils.MediaHelper.scanFile;

public class AlbumsHelper {

    @NonNull
    public static SortingMode getSortingMode() {
        return SortingMode.NAME;
    }

    @NonNull
    public static SortingOrder getSortingOrder() {
        return SortingOrder.DESCENDING;
    }

    public static void hideAlbum(String path, Context context) {

        File dirName = new File(path);
        File file = new File(dirName, ".nomedia");
        if (!file.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                out.flush();
                out.close();
                scanFile(context, new String[]{file.getAbsolutePath()});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void unHideAlbum(String path, Context context) {

        File dirName = new File(path);
        File file = new File(dirName, ".nomedia");
        if (file.exists()) {
            if (file.delete())
                scanFile(context, new String[]{file.getAbsolutePath()});
        }
    }

    public static boolean deleteAlbum(Album album, Context context) {
        return StorageHelper.deleteFilesInFolder(context, new File(album.getPath()));
    }
}