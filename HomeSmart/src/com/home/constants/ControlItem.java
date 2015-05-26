package com.home.constants;

import com.home.utils.Logger;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.widget.ImageView;

/**
 * 遥控器按键
 * 
 * @author hyc
 * 
 */
public class ControlItem implements Parcelable {

	public int id;

	public int x;
	public int y;

	public int width;
	public int height;

	public String name;

	public String time;
	public String repeatDate;

	public Bitmap srcimage;
	public Bitmap bgimage;

	public String code;

	public boolean isDraged;

	public int controlId;
	
	public ImageView itemImageView;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Logger.log("writeToParcel:" + flags);
		dest.writeInt(id);
		dest.writeInt(x);
		dest.writeInt(y);
		dest.writeInt(width);
		dest.writeInt(height);
		dest.writeString(name);
		dest.writeString(time);
		dest.writeString(repeatDate);
		dest.writeParcelable(srcimage, flags);
		dest.writeParcelable(bgimage, flags);
		dest.writeString(code);
		dest.writeInt(controlId);
	}

	public static final Parcelable.Creator<ControlItem> CREATOR = new Creator<ControlItem>() {

		@Override
		public ControlItem createFromParcel(Parcel source) {
			Logger.log("createFromParcel");
			ControlItem control = new ControlItem();
			control.id = source.readInt();
			control.x = source.readInt();
			control.y = source.readInt();
			control.width = source.readInt();
			control.height = source.readInt();
			control.name = source.readString();
			control.time = source.readString();
			control.repeatDate = source.readString();
			control.srcimage = source.readParcelable(Bitmap.class
					.getClassLoader());
			control.bgimage = source.readParcelable(Bitmap.class
					.getClassLoader());
			control.code = source.readString();
			control.controlId = source.readInt();
			return control;
		}

		@Override
		public ControlItem[] newArray(int size) {
			return new ControlItem[size];
		}

	};

}
