package com.rappi.adminsion.data.local;

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity

data class FavoriteMovie(
    @PrimaryKey(autoGenerate = false) var id: Int? = null,
    @ColumnInfo(name = "vote_count")
    var voteCount: String? = null,
    @ColumnInfo(name = "poster_path")
    var poster: String? = null,
    @ColumnInfo(name = "backdrop_path")
    var backdrop: String? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "vote_average")
    var rating: Double? = null,
    @ColumnInfo(name = " overview")
    var overview: String? = null,
    @ColumnInfo(name = "release_date")
    var releaseDate: String?,
    @ColumnInfo(name = "type")
    var type: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(voteCount)
        parcel.writeString(poster)
        parcel.writeString(backdrop)
        parcel.writeString(title)
        parcel.writeValue(rating)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FavoriteMovie> {
        override fun createFromParcel(parcel: Parcel): FavoriteMovie {
            return FavoriteMovie(parcel)
        }

        override fun newArray(size: Int): Array<FavoriteMovie?> {
            return arrayOfNulls(size)
        }
    }
}