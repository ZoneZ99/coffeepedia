package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.service.util;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeReview implements Parcelable {

	private String name;

	private String roaster;

	private String score;

	private String review;

	protected CoffeeReview(Parcel in) {
		name = in.readString();
		roaster = in.readString();
		score = in.readString();
		review = in.readString();
	}

	public static final Creator<CoffeeReview> CREATOR = new Creator<CoffeeReview>() {
		@Override
		public CoffeeReview createFromParcel(Parcel in) {
			return new CoffeeReview(in);
		}

		@Override
		public CoffeeReview[] newArray(int size) {
			return new CoffeeReview[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(roaster);
		dest.writeString(score);
		dest.writeString(review);
	}

	@Override
	public String toString() {
		return "Name: " + name + "\n\n" +
			"Roaster: " + roaster + "\n\n" +
			"Cupping Score: " + score + "\n\n" +
			"Review: " + review;
	}
}
