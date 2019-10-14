package techhunt.developers.utils.operation.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import techhunt.developers.utils.operation.filter.FilterMode;
import techhunt.developers.utils.operation.sort.SortingMode;
import techhunt.developers.utils.operation.sort.SortingOrder;

public class AlbumSettings implements Serializable, Parcelable {

    public String coverPath;
    public int sortingMode;
    public int sortingOrder;
    public boolean pinned;
    public FilterMode filterMode = FilterMode.ALL;

    public static AlbumSettings getDefaults() {
        return new AlbumSettings(null, SortingMode.DATE.getValue(), 1, 0);
    }

    public AlbumSettings(String cover, int sortingMode, int sortingOrder, int pinned) {
        this.coverPath = cover;
        this.sortingMode = sortingMode;
        this.sortingOrder = sortingOrder;
        this.pinned = pinned == 1;
    }

    public SortingMode getSortingMode() {
        return SortingMode.fromValue(sortingMode);
    }

    public SortingOrder getSortingOrder() {
        return SortingOrder.fromValue(sortingOrder);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.coverPath);
        dest.writeInt(this.sortingMode);
        dest.writeInt(this.sortingOrder);
        dest.writeByte(this.pinned ? (byte) 1 : (byte) 0);
        dest.writeInt(this.filterMode == null ? -1 : this.filterMode.ordinal());
    }

    /** This is the constructor used by CREATOR. */
    protected AlbumSettings(Parcel in) {
        this.coverPath = in.readString();
        this.sortingMode = in.readInt();
        this.sortingOrder = in.readInt();
        this.pinned = in.readByte() != 0;
        int tmpFilterMode = in.readInt();
        this.filterMode = tmpFilterMode == -1 ? null : FilterMode.values()[tmpFilterMode];
    }

    /** It is a non-null static field that must be in parcelable. */
    public static final Creator<AlbumSettings> CREATOR = new Creator<AlbumSettings>() {

        @Override
        public AlbumSettings createFromParcel(Parcel source) {
            return new AlbumSettings(source);
        }

        @Override
        public AlbumSettings[] newArray(int size) {
            return new AlbumSettings[size];
        }
    };
}