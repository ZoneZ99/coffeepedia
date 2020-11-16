package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity(tableName = "brew_methods")
@Data
@AllArgsConstructor
@Builder
public class BrewMethod implements Parcelable {

	@PrimaryKey(autoGenerate = true)
	public long brewMethodId;

	public String name;

	public String description;

	public BrewMethod() {

	}

	protected BrewMethod(Parcel in) {
		brewMethodId = in.readLong();
		name = in.readString();
		description = in.readString();
	}

	public static final Creator<BrewMethod> CREATOR = new Creator<BrewMethod>() {
		@Override
		public BrewMethod createFromParcel(Parcel in) {
			return new BrewMethod(in);
		}

		@Override
		public BrewMethod[] newArray(int size) {
			return new BrewMethod[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(brewMethodId);
		dest.writeString(name);
		dest.writeString(description);
	}
}
