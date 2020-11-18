package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
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
		mBinding.buttonEditBrewRecipe.setOnClickListener(mEditButtonOnClickCallback);
		mBinding.buttonDeleteBrewRecipe.setOnClickListener(mDeleteButtonOnClickCallback);
		mBinding.buttonExportBrewRecipeDetail.setOnClickListener(mExportButtonOnClickCallback);
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

	private final View.OnClickListener mDeleteButtonOnClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			BrewRecipe deletedBrewRecipe = mViewModel.getBrewRecipe().getValue();
			mViewModel.deleteBrewRecipe(deletedBrewRecipe);
			Toast.makeText(getContext(), R.string.success_delete_brew_recipe, Toast.LENGTH_SHORT).show();
			getActivity().onBackPressed();
		}
	};

	private final View.OnClickListener mEditButtonOnClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			BrewRecipe editedBrewRecipe = mViewModel.getBrewRecipe().getValue();
			((BrewRecipesActivity) getActivity()).startBrewRecipeEditView(editedBrewRecipe);
		}
	};

	private final View.OnClickListener mExportButtonOnClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			mViewModel.getBrewRecipe().observe(this, recipe -> ((BrewRecipesActivity) getActivity())
					.exportDataToPdf(recipe.getBrewRecipeId(), "brew_recipe_detail.pdf"));
		}
	};
}
