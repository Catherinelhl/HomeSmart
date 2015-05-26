package com.home.constants;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Scene implements Parcelable {

	public int id;
	public String name;
	public Bitmap image;
	public String time;
	public String repeatDate;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(time);
		dest.writeString(repeatDate);
		dest.writeParcelable(image, flags);
	}

	public static final Parcelable.Creator<Scene> CREATOR = new Creator<Scene>() {

		@Override
		public Scene createFromParcel(Parcel source) {
			Scene scene = new Scene();
			scene.id = source.readInt();
			scene.name = source.readString();
			scene.time = source.readString();
			scene.repeatDate = source.readString();
			scene.image = source.readParcelable(Bitmap.class.getClassLoader());
			return scene;
		}

		@Override
		public Scene[] newArray(int size) {
			return new Scene[size];
		}
	};

}
