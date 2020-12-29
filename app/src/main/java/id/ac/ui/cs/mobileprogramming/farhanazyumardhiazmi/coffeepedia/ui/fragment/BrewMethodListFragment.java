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
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentBrewMethodListBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.BrewMethodsActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.PermissionRationaleActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.adapter.BrewMethodAdapter;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.callback.BrewMethodClickCallback;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.BrewMethodListViewModel;

import java.util.List;

public class BrewMethodListFragment extends Fragment {

	public static final String TAG = "BrewMethodListFragment";

	private BrewMethodAdapter mBrewMethodAdapter;

	private BrewMethodListViewModel mViewModel;

	private FragmentBrewMethodListBinding mBinding;

	public static BrewMethodListFragment newInstance() {
		return new BrewMethodListFragment();
	}

	@Nullable
	@Override
	public View onCreateView(
		@NonNull LayoutInflater inflater,
		@Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState
	) {
		mViewModel = new ViewModelProvider(requireActivity()).get(BrewMethodListViewModel.class);
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_brew_method_list, container, false);
		mBrewMethodAdapter = new BrewMethodAdapter(mBrewMethodClickCallback);
		mBinding.brewMethodList.setAdapter(mBrewMethodAdapter);
		mBinding.buttonExportBrewMethodList.setOnClickListener(mExportButtonClickCallback);
		return mBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		LiveData<List<BrewMethod>> brewMethods = mViewModel.getBrewMethods();
		brewMethods.observe(getViewLifecycleOwner(), methods -> {
			if (methods != null) {
				mBrewMethodAdapter.setBrewMethodList(methods);
				if (methods.isEmpty()) {
					mBinding.buttonExportBrewMethodList.setVisibility(View.GONE);
				} else {
					mBinding.buttonExportBrewMethodList.setVisibility(View.VISIBLE);
				}
			}
			mBinding.executePendingBindings();
		});
	}

	@Override
	public void onDestroyView() {
		mBinding = null;
		mBrewMethodAdapter = null;
		super.onDestroyView();
	}

	private final BrewMethodClickCallback mBrewMethodClickCallback = brewMethod -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			((BrewMethodsActivity) requireActivity()).showBrewMethodDetailFragment(brewMethod);
		}
	};

	private final View.OnClickListener mExportButtonClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
				== PackageManager.PERMISSION_GRANTED)) {
				((BrewMethodsActivity) getActivity()).exportDataToPdf(0, "brew_method_list.pdf");
			} else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				Intent intent = new Intent(this.getContext(), PermissionRationaleActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
			}
		}
	};
}
