package com.home.constants;

import java.io.Serializable;

import com.home.utils.Logger;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 开关
 * 
 * @author hyc
 * 
 */
public class Switch implements Parcelable {

	public int id;
	public String name;
	public Bitmap image;
	public int isOpen;

	@Override
	public int describeContents() {
		Logger.log("describeContents");
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Logger.log("writeToParcel:" + flags);
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeParcelable(image, flags);
		dest.writeInt(isOpen);
	}

	public static final Parcelable.Creator<Switch> CREATOR = new Creator<Switch>() {

		@Override
		public Switch createFromParcel(Parcel source) {
			Logger.log("createFromParcel");
			Switch switch1 = new Switch();
			switch1.id = source.readInt();
			switch1.name = source.readString();
			switch1.image = source
					.readParcelable(Bitmap.class.getClassLoader());
			switch1.isOpen = source.readInt();
			return switch1;
		}

		@Override
		public Switch[] newArray(int size) {
			return new Switch[size];
		}

	};

}
