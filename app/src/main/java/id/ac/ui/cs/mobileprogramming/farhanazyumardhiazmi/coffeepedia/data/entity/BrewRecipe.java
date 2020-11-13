package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "brew_recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrewRecipe {

	@PrimaryKey(autoGenerate = true)
	public long brewRecipeId;

	public long relatedBrewMethodId;

	public String name;

	public String roastLevel;

	public String grindLevel;

	public String waterTemperature;

	public String brewTime;

	public String brewSteps;
}
