package com.example.a06_examen02

import android.os.Parcel
import android.os.Parcelable

class ComponenteEntity(
    var id: Int,
    var nameComponente: String,
    var description: String,
    var computador_id: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nameComponente)
        parcel.writeString(description)
        parcel.writeInt(computador_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComponenteEntity> {
        override fun createFromParcel(parcel: Parcel): ComponenteEntity {
            return ComponenteEntity(parcel)
        }

        override fun newArray(size: Int): Array<ComponenteEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$id - $nameComponente"
    }
}
