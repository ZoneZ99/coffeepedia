package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.BrewRecipeItemBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.callback.BrewRecipeClickCallback;

import java.util.List;

public class BrewRecipeAdapter extends RecyclerView.Adapter<BrewRecipeAdapter.BrewRecipeViewHolder> {

	List<BrewRecipe> mBrewRecipes;

	private final BrewRecipeClickCallback mBrewRecipeClickCallback;

	public BrewRecipeAdapter(BrewRecipeClickCallback brewRecipeClickCallback) {
		mBrewRecipeClickCallback = brewRecipeClickCallback;
		setHasStableIds(true);
	}

	@NonNull
	@Override
	public BrewRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		BrewRecipeItemBinding binding = DataBindingUtil.inflate(
			LayoutInflater.from(parent.getContext()),
			R.layout.brew_recipe_item,
			parent,
			false
		);
		binding.setCallback(mBrewRecipeClickCallback);
		return new BrewRecipeViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(@NonNull BrewRecipeAdapter.BrewRecipeViewHolder holder, int position) {
		holder.binding.setBrewRecipe(mBrewRecipes.get(position));
		holder.binding.executePendingBindings();
	}

	@Override
	public int getItemCount() {
		return mBrewRecipes == null ? 0 : mBrewRecipes.size();
	}

	@Override
	public long getItemId(int position) {
		return mBrewRecipes.get(position).getBrewRecipeId();
	}

	public void setBrewRecipeList(final List<BrewRecipe> brewRecipeList) {
		if (mBrewRecipes == null) {
			mBrewRecipes = brewRecipeList;
			notifyItemRangeInserted(0, brewRecipeList.size());
		} else {
			DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
				@Override
				public int getOldListSize() {
					return mBrewRecipes.size();
				}

				@Override
				public int getNewListSize() {
					return brewRecipeList.size();
				}

				@Override
				public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
					return mBrewRecipes.get(oldItemPosition).getBrewRecipeId() ==
						brewRecipeList.get(newItemPosition).getBrewRecipeId();
				}

				@Override
				public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
					BrewRecipe oldBrewRecipe = mBrewRecipes.get(oldItemPosition);
					BrewRecipe newBrewRecipe = brewRecipeList.get(newItemPosition);
					return oldBrewRecipe.equals(newBrewRecipe);
				}
			});
			mBrewRecipes = brewRecipeList;
			result.dispatchUpdatesTo(this);
		}
	}

	static class BrewRecipeViewHolder extends RecyclerView.ViewHolder {

		final BrewRecipeItemBinding binding;

		public BrewRecipeViewHolder(BrewRecipeItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}
	}
}
