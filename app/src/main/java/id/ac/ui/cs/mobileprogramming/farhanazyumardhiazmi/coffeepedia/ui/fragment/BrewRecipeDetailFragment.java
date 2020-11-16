package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentBrewRecipeDetailBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.BrewRecipesActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.BrewRecipeViewModel;

public class BrewRecipeDetailFragment extends Fragment {

	private FragmentBrewRecipeDetailBinding mBinding;

	private BrewRecipeViewModel mViewModel;

	private static final String BREW_RECIPE_ID = "brew_recipe_id";

	@Nullable
	@Override
	public View onCreateView(
		@NonNull LayoutInflater inflater,
		@Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState
	) {
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_brew_recipe_detail, container, false);
		return mBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		BrewRecipeViewModel.Factory factory = new BrewRecipeViewModel.Factory(
			requireActivity().getApplication(),
			requireArguments().getLong(BREW_RECIPE_ID)
		);
		mViewModel = new ViewModelProvider(this, factory).get(BrewRecipeViewModel.class);
		mBinding.setLifecycleOwner(getViewLifecycleOwner());
		mBinding.setBrewRecipeViewModel(mViewModel);
		mBinding.buttonEditBrewRecipe.setOnClickListener(new EditButtonOnClickListener());
	}

	@Override
	public void onDestroyView() {
		mBinding = null;
		super.onDestroyView();
	}

	public static BrewRecipeDetailFragment forBrewRecipe(long brewRecipeId) {
		BrewRecipeDetailFragment fragment = new BrewRecipeDetailFragment();
		Bundle args = new Bundle();
		args.putLong(BREW_RECIPE_ID, brewRecipeId);
		fragment.setArguments(args);
		return fragment;
	}

	private class EditButtonOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			BrewRecipe editedBrewRecipe = mViewModel.getBrewRecipe().getValue();
			((BrewRecipesActivity) getActivity()).startBrewRecipeEditView(editedBrewRecipe);
		}
	}
}
