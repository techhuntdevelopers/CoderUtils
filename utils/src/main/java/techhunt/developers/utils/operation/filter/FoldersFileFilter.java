package techhunt.developers.utils.operation.filter;

import java.io.File;
import java.io.FileFilter;

public class FoldersFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.isDirectory();
    }
}