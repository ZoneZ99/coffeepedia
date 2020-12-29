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
import androidx.lifecycle.ViewModelProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentCoffeeBeanDetailBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.CoffeeBeansActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.PermissionRationaleActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.CoffeeBeanViewModel;

public class CoffeeBeanDetailFragment extends Fragment {

	private FragmentCoffeeBeanDetailBinding mBinding;

	private CoffeeBeanViewModel mViewModel;

	private static final String COFFEE_BEAN_ID = "coffee_bean_id";

	@Nullable
	@Override
	public View onCreateView(
		@NonNull LayoutInflater inflater,
		@Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState
	) {
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coffee_bean_detail, container, false);
		return mBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		CoffeeBeanViewModel.Factory factory = new CoffeeBeanViewModel.Factory(
			requireActivity().getApplication(),
			requireArguments().getLong(COFFEE_BEAN_ID)
		);
		mViewModel = new ViewModelProvider(this, factory).get(CoffeeBeanViewModel.class);
		mBinding.setLifecycleOwner(getViewLifecycleOwner());
		mBinding.setCoffeeBeanViewModel(mViewModel);
		mBinding.buttonDeleteCoffeeBean.setOnClickListener(mDeleteButtonOnClickCallback);
		mBinding.buttonEditCoffeeBean.setOnClickListener(mEditButtonOnClickCallback);
		mBinding.buttonExportCoffeeBeanDetail.setOnClickListener(mExportButtonClickCallback);
	}

	@Override
	public void onDestroyView() {
		mBinding = null;
		super.onDestroyView();
	}

	public static CoffeeBeanDetailFragment forCoffeeBean(long coffeeBeanId) {
		CoffeeBeanDetailFragment fragment = new CoffeeBeanDetailFragment();
		Bundle args = new Bundle();
		args.putLong(COFFEE_BEAN_ID, coffeeBeanId);
		fragment.setArguments(args);
		return fragment;
	}

	private final View.OnClickListener mDeleteButtonOnClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			CoffeeBean deletedCoffeeBean = mViewModel.getCoffeeBean().getValue();
			mViewModel.deleteCoffeeBean(deletedCoffeeBean);
			Toast.makeText(getContext(), R.string.success_delete_coffee_bean, Toast.LENGTH_SHORT).show();
			getActivity().onBackPressed();
		}
	};

	private final View.OnClickListener mEditButtonOnClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			CoffeeBean editedCoffeeBean = mViewModel.getCoffeeBean().getValue();
			((CoffeeBeansActivity) getActivity()).startCoffeeBeanEditView(editedCoffeeBean);
		}
	};

	private final View.OnClickListener mExportButtonClickCallback = view -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
				== PackageManager.PERMISSION_GRANTED)) {
				mViewModel.getCoffeeBean().observe(this, bean -> ((CoffeeBeansActivity) getActivity())
					.exportDataToPdf(bean.getCoffeeBeanId(), "coffee_bean_detail.pdf"));
			} else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				Intent intent = new Intent(this.getContext(), PermissionRationaleActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
			}
		}
	};

}
