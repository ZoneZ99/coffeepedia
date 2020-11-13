package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "brew_methods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrewMethod {

	@PrimaryKey(autoGenerate = true)
	public long brewMethodId;

	public long relatedCoffeeBeanId;

	public String name;

	public String description;
}
