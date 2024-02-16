package com.example.homeworkout.data.remote.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Workout(
    var id : String? = null,
    var title : String? = null,
    var image_path : String? = null,
    var description : String? = null,
    var calorie : String? = null,
    var time : String? = null,
    var level : String? = null,
    var type : String? = null,
    var workout_type : String? = null,
    var exercise_list : List<Exercise>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Exercise)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(image_path)
        parcel.writeString(description)
        parcel.writeString(calorie)
        parcel.writeString(time)
        parcel.writeString(level)
        parcel.writeString(type)
        parcel.writeString(workout_type)
        parcel.writeTypedList(exercise_list)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Workout> {
        override fun createFromParcel(parcel: Parcel): Workout {
            return Workout(parcel)
        }

        override fun newArray(size: Int): Array<Workout?> {
            return arrayOfNulls(size)
        }
    }
}