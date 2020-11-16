package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentBrewRecipeListBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.BrewRecipesActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.adapter.BrewRecipeAdapter;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.callback.BrewRecipeClickCallback;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.BrewRecipeListViewModel;

import java.util.List;

public class BrewRecipeListFragment extends Fragment {

	public static final String TAG = "BrewRecipeListFragment";

	private BrewRecipeAdapter mBrewRecipeAdapter;

	private BrewRecipeListViewModel mViewModel;

	private FragmentBrewRecipeListBinding mBinding;

	public static BrewRecipeListFragment newInstance() {
		return new BrewRecipeListFragment();
	}

	@Nullable
	@Override
	public View onCreateView(
		@NonNull LayoutInflater inflater,
		@Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState
	) {
		mViewModel = new ViewModelProvider(requireActivity()).get(BrewRecipeListViewModel.class);
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_brew_recipe_list, container, false);
		mBrewRecipeAdapter = new BrewRecipeAdapter(mBrewRecipeClickCallback);
		mBinding.brewRecipeList.setAdapter(mBrewRecipeAdapter);
		return mBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		LiveData<List<BrewRecipe>> brewRecipes = mViewModel.getBrewRecipes();
		brewRecipes.observe(getViewLifecycleOwner(), recipes -> {
			if (recipes != null) {
				mBrewRecipeAdapter.setBrewRecipeList(recipes);
			}
			mBinding.executePendingBindings();
		});
	}

	@Override
	public void onDestroyView() {
		mBinding = null;
		mBrewRecipeAdapter = null;
		super.onDestroyView();
	}

	private final BrewRecipeClickCallback mBrewRecipeClickCallback = brewRecipe -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			((BrewRecipesActivity) requireActivity()).showBrewRecipeDetailFragment(brewRecipe);
		}
	};
}
