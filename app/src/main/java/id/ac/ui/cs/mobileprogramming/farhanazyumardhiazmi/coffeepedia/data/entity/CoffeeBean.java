package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity(tableName = "coffee_beans")
@Data
@AllArgsConstructor
@Builder
public class CoffeeBean implements Parcelable {

	@PrimaryKey(autoGenerate = true)
	private long coffeeBeanId;

	private String name;

	private String type;

	private String origin;

	private String altitude;

	private String process;

	private String aroma;

	private String tasteNote;

	public CoffeeBean() {

	}

	protected CoffeeBean(Parcel in) {
		coffeeBeanId = in.readLong();
		name = in.readString();
		type = in.readString();
		origin = in.readString();
		altitude = in.readString();
		process = in.readString();
		aroma = in.readString();
		tasteNote = in.readString();
	}

	public static final Creator<CoffeeBean> CREATOR = new Creator<CoffeeBean>() {
		@Override
		public CoffeeBean createFromParcel(Parcel in) {
			return new CoffeeBean(in);
		}

		@Override
		public CoffeeBean[] newArray(int size) {
			return new CoffeeBean[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeLong(coffeeBeanId);
		dest.writeString(name);
		dest.writeString(type);
		dest.writeString(origin);
		dest.writeString(altitude);
		dest.writeString(process);
		dest.writeString(aroma);
		dest.writeString(tasteNote);
	}
}
