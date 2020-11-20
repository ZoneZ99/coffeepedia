package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewRecipe;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentBrewMethodDetailBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.BrewMethodsActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.BrewRecipeFormActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.callback.BrewMethodClickCallback;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.BrewMethodViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class BrewMethodDetailFragment extends Fragment {

	private FragmentBrewMethodDetailBinding mBinding;

	private BrewMethodViewModel mViewModel;

	public static final String BREW_METHOD_ID = "brew_method_id";

	@Nullable
	@Override
	public View onCreateView(
		@NonNull LayoutInflater inflater,
		@Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState
	) {
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_brew_method_detail, container, false);
		mBinding.setCallback(mBrewMethodClickCallback);
		return mBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		BrewMethodViewModel.Factory factory = new BrewMethodViewModel.Factory(
			requireActivity().getApplication(),
			requireArguments().getLong(BREW_METHOD_ID)
		);
		mViewModel = new ViewModelProvider(this, factory).get(BrewMethodViewModel.class);
		mBinding.setLifecycleOwner(getViewLifecycleOwner());
		mBinding.setBrewMethodViewModel(mViewModel);
		mBinding.buttonDeleteBrewMethod.setOnClickListener(mDeleteButtonOnClickCallback);
		mBinding.buttonEditBrewMethod.setOnClickListener(mEditButtonOnClickCallback);
		mBinding.buttonExportBrewMethodDetail.setOnClickListener(mExportButtonClickCallback);

		mViewModel.getBrewMethodWithBrewRecipes().observe(
			getViewLifecycleOwner(),
			brewMethodWithBrewRecipes -> {
				if (brewMethodWithBrewRecipes.getBrewRecipes().isEmpty()) {
					mBinding.titleRelatedBrewRecipes.setText(R.string.empty_related_brew_recipes);
				} else {
					mBinding.titleRelatedBrewRecipes.setText(R.string.recipes);
					List<String> brewRecipeNames = brewMethodWithBrewRecipes.getBrewRecipes().stream().map(BrewRecipe::getName).collect(Collectors.toList());
					mBinding.listRelatedBrewRecipes.setAdapter(
						new ArrayAdapter<>(
							getContext(),
							android.R.layout.simple_list_item_1,
							brewRecipeNames
						)
					);
				}
			}
		);
	}

	@Override
	public void onDestroyView() {
		mBinding = null;
		super.onDestroyView();
	}

	public static BrewMethodDetailFragment forBrewMethod(long brewMethodId) {
		BrewMethodDetailFragment fragment = new BrewMethodDetailFragment();
		Bundle args = new Bundle();
		args.putLong(BREW_METHOD_ID, brewMethodId);
		fragment.setArguments(args);
		return fragment;
	}

	private final BrewMethodClickCallback mBrewMethodClickCallback = brewMethod -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			Intent intent = new Intent(getActivity(), BrewRecipeFormActivity.class);
			intent.putExtra(BrewRecipeFormActivity.IS_EDIT, false);
			intent.putExtra(BREW_METHOD_ID, brewMethod.getBrewMethodId());
			startActivity(intent);
		}
	};

	private final View.OnClickListener mDeleteButtonOnClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			BrewMethod deletedBrewMethod = mViewModel.getBrewMethodWithBrewRecipes().getValue().getBrewMethod();
			mViewModel.deleteBrewMethod(deletedBrewMethod);
			Toast.makeText(getContext(), R.string.success_delete_brew_method, Toast.LENGTH_SHORT).show();
			getActivity().onBackPressed();
		}
	};

	private final View.OnClickListener mEditButtonOnClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			BrewMethod editedBrewMethod = mViewModel.getBrewMethodWithBrewRecipes().getValue().getBrewMethod();
			((BrewMethodsActivity) getActivity()).startBrewMethodEditView(editedBrewMethod);
		}
	};

	private final View.OnClickListener mExportButtonClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			mViewModel.getBrewMethodWithBrewRecipes().observe(this, brewMethodWithBrewRecipes -> ((BrewMethodsActivity) getActivity())
				.exportDataToPdf(
					brewMethodWithBrewRecipes.getBrewMethod().getBrewMethodId(),
					"brew_method_detail.pdf"
				));
		}
	};
}
