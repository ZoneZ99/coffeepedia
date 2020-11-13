package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static androidx.room.ForeignKey.SET_NULL;

@Entity(tableName = "brew_recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrewRecipe {

	@PrimaryKey(autoGenerate = true)
	public long brewRecipeId;

	@ForeignKey(
		entity = BrewMethod.class,
		parentColumns = "brewMethodId",
		childColumns = "relatedBrewMethodId",
		onDelete = SET_NULL
	)
	public long relatedBrewMethodId;

	public String name;

	public String roastLevel;

	public String grindLevel;

	public String waterTemperature;

	public String brewTime;

	public String brewSteps;
}
