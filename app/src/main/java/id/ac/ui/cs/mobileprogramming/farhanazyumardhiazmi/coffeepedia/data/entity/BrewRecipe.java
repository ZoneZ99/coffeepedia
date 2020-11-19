package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
	tableName = "brew_recipes",
	foreignKeys = @ForeignKey(
		entity = BrewMethod.class,
		parentColumns = "brewMethodId",
		childColumns = "relatedBrewMethodId",
		onDelete = CASCADE
	)
)
@Data
@AllArgsConstructor
@Builder
public class BrewRecipe implements Parcelable {

	@PrimaryKey(autoGenerate = true)
	private long brewRecipeId;

	private long relatedBrewMethodId;

	private String name;

	private String roastLevel;

	private String grindLevel;

	private String waterTemperature;

	private String brewTime;

	private String brewSteps;

	public BrewRecipe() {
	}

	protected BrewRecipe(Parcel in) {
		brewRecipeId = in.readLong();
		relatedBrewMethodId = in.readLong();
		name = in.readString();
		roastLevel = in.readString();
		grindLevel = in.readString();
		waterTemperature = in.readString();
		brewTime = in.readString();
		brewSteps = in.readString();
	}

	public static final Creator<BrewRecipe> CREATOR = new Creator<BrewRecipe>() {
		@Override
		public BrewRecipe createFromParcel(Parcel in) {
			return new BrewRecipe(in);
		}

		@Override
		public BrewRecipe[] newArray(int size) {
			return new BrewRecipe[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(brewRecipeId);
		dest.writeLong(relatedBrewMethodId);
		dest.writeString(name);
		dest.writeString(roastLevel);
		dest.writeString(grindLevel);
		dest.writeString(waterTemperature);
		dest.writeString(brewTime);
		dest.writeString(brewSteps);
	}
}
