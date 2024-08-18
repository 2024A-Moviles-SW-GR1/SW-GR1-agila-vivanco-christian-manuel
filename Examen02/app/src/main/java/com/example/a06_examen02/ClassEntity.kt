package com.example.a06_examen02

import android.os.Parcel
import android.os.Parcelable

class ComputadorEntity(
    var id: Int,
    var name: String,
    var brand: String, // Cambiado de semester a brand
    var model: String, // Cambiado de groupClass a model
    var latitude: String,
    var longitude: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        requireNotNull(parcel.readString()) { "Name is null" },
        requireNotNull(parcel.readString()) { "Brand is null" },
        requireNotNull(parcel.readString()) { "Model is null" },
        requireNotNull(parcel.readString()) { "Latitude is null" },
        requireNotNull(parcel.readString()) { "Longitude is null" }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(brand)
        parcel.writeString(model)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComputadorEntity> {
        override fun createFromParcel(parcel: Parcel): ComputadorEntity {
            return ComputadorEntity(parcel)
        }

        override fun newArray(size: Int): Array<ComputadorEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$id - $name"
    }
}
