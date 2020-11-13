package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static androidx.room.ForeignKey.SET_NULL;

@Entity(tableName = "brew_methods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrewMethod {

	@PrimaryKey(autoGenerate = true)
	public long brewMethodId;

	@ForeignKey(
		entity = CoffeeBean.class,
		parentColumns = "coffeeBeanId",
		childColumns = "relatedCoffeeBeanId",
		onDelete = SET_NULL
	)
	public long relatedCoffeeBeanId;

	public String name;

	public String description;
}
