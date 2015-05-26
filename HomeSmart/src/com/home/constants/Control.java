package com.home.constants;

import com.home.utils.Logger;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/**
 * 遥控器
 * 
 * @author hyc
 * 
 */
public class Control implements Parcelable {

	public int id;
	public String name;
	public String type;
	public Bitmap image;
	// public String time;

	public int isDefault;// 是否默认遥控器

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Logger.log("writeToParcel:" + flags);
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeString(type);
		dest.writeParcelable(image, flags);
		dest.writeInt(isDefault);
	}

	public static final Parcelable.Creator<Control> CREATOR = new Creator<Control>() {

		@Override
		public Control createFromParcel(Parcel source) {
			Logger.log("createFromParcel");
			Control control = new Control();
			control.id = source.readInt();
			control.name = source.readString();
			control.type = source.readString();
			control.image = source
					.readParcelable(Bitmap.class.getClassLoader());
			control.isDefault = source.readInt();
			return control;
		}

		@Override
		public Control[] newArray(int size) {
			return new Control[size];
		}

	};

}
