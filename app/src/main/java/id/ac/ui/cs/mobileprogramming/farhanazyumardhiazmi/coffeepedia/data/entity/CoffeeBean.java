package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "coffee_beans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeBean {

	@PrimaryKey(autoGenerate = true)
	public long coffeeBeanId;

	public String name;

	public String type;

	public String origin;

	public String altitude;

	public String process;

	public String aroma;

	public String tasteNote;
}
