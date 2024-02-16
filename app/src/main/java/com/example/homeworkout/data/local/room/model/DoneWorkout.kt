package com.example.homeworkout.data.local.room.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homeworkout.data.remote.model.Workout
import java.time.LocalDate
import java.util.Date

@Entity
data class DoneWorkout (
    var workout: Workout?,
    var total : String?,
    var calorie : String?,
    var time : Long?,
    var date : String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var idUser: String? = null

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Workout::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    ) {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        idUser = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(workout, flags)
        parcel.writeString(total)
        parcel.writeString(calorie)
        parcel.writeValue(time)
        parcel.writeString(date)
        parcel.writeValue(id)
        parcel.writeString(idUser)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DoneWorkout> {
        override fun createFromParcel(parcel: Parcel): DoneWorkout {
            return DoneWorkout(parcel)
        }

        override fun newArray(size: Int): Array<DoneWorkout?> {
            return arrayOfNulls(size)
        }
    }

}