package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentBrewRecipeListBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.BrewRecipesActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.PermissionRationaleActivity;
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
		mBinding.buttonExportBrewRecipeList.setOnClickListener(mExportButtonClickCallback);
		return mBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		LiveData<List<BrewRecipe>> brewRecipes = mViewModel.getBrewRecipes();
		brewRecipes.observe(getViewLifecycleOwner(), recipes -> {
			if (recipes != null) {
				mBrewRecipeAdapter.setBrewRecipeList(recipes);
				if (recipes.isEmpty()) {
					mBinding.buttonExportBrewRecipeList.setVisibility(View.GONE);
				} else {
					mBinding.buttonExportBrewRecipeList.setVisibility(View.VISIBLE);
				}
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

	private final View.OnClickListener mExportButtonClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
				== PackageManager.PERMISSION_GRANTED)) {
				((BrewRecipesActivity) getActivity()).exportDataToPdf(0, "brew_recipe_list.pdf");
			} else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				Intent intent = new Intent(this.getContext(), PermissionRationaleActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
			}
		}
	};
}
