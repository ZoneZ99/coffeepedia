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
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentCoffeeBeanListBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.CoffeeBeansActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.PermissionRationaleActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.adapter.CoffeeBeanAdapter;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.callback.CoffeeBeanClickCallback;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.CoffeeBeanListViewModel;

import java.util.List;

public class CoffeeBeanListFragment extends Fragment {

	public static final String TAG = "CoffeeBeanListFragment";

	private CoffeeBeanAdapter mCoffeeBeanAdapter;

	private CoffeeBeanListViewModel mViewModel;

	private FragmentCoffeeBeanListBinding mBinding;

	public static CoffeeBeanListFragment newInstance() {
		return new CoffeeBeanListFragment();
	}

	@Override
	public View onCreateView(
		@NonNull LayoutInflater inflater,
		@Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState
	) {
		mViewModel = new ViewModelProvider(requireActivity()).get(CoffeeBeanListViewModel.class);
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coffee_bean_list, container, false);
		mCoffeeBeanAdapter = new CoffeeBeanAdapter(mCoffeeBeanClickCallback);
		mBinding.coffeeBeanList.setAdapter(mCoffeeBeanAdapter);
		mBinding.buttonExportCoffeeBeanList.setOnClickListener(mExportButtonClickCallback);
		return mBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		LiveData<List<CoffeeBean>> coffeeBeans = mViewModel.getCoffeeBeans();
		coffeeBeans.observe(getViewLifecycleOwner(), beans -> {
			if (beans != null) {
				mCoffeeBeanAdapter.setCoffeeBeanList(beans);
				if (beans.isEmpty()) {
					mBinding.buttonExportCoffeeBeanList.setVisibility(View.GONE);
				} else {
					mBinding.buttonExportCoffeeBeanList.setVisibility(View.VISIBLE);
				}
			}
			mBinding.executePendingBindings();
		});
	}

	@Override
	public void onDestroyView() {
		mBinding = null;
		mCoffeeBeanAdapter = null;
		super.onDestroyView();
	}

	private final CoffeeBeanClickCallback mCoffeeBeanClickCallback = coffeeBean -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			((CoffeeBeansActivity) requireActivity()).showCoffeeBeanDetailFragment(coffeeBean);
		}
	};

	private final View.OnClickListener mExportButtonClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
				== PackageManager.PERMISSION_GRANTED)) {
				((CoffeeBeansActivity) getActivity()).exportDataToPdf(0, "coffee_bean_list.pdf");
			} else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				Intent intent = new Intent(this.getContext(), PermissionRationaleActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
			}
		}
	};

}
