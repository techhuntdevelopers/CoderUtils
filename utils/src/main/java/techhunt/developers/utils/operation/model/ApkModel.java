package techhunt.developers.utils.operation.model;

import android.graphics.drawable.Drawable;

public class ApkModel {
    String appName;
    String version;
    String packageName;
    String installedDate;
    String size;
    Drawable appIcon;

    public ApkModel() {
    }

    public ApkModel(String appName, String version, String packageName, String installedDate, Drawable appIcon, String size) {
        this.appName = appName;
        this.version = version;
        this.packageName = packageName;
        this.installedDate = installedDate;
        this.appIcon = appIcon;
        this.size = size;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getInstalledDate() {
        return installedDate;
    }

    public void setInstalledDate(String installedDate) {
        this.installedDate = installedDate;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ApkModel{" +
                "appName='" + appName + '\'' +
                ", version='" + version + '\'' +
                ", packageName='" + packageName + '\'' +
                ", installedDate=" + installedDate +
                ", size='" + size + '\'' +
                ", appIcon=" + appIcon +
                '}';
    }
}
