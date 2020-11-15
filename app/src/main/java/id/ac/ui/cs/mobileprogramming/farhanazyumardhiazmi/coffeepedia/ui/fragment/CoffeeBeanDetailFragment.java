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
import androidx.lifecycle.ViewModelProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentCoffeeBeanDetailBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.CoffeeBeanViewModel;

public class CoffeeBeanDetailFragment extends Fragment {

	private FragmentCoffeeBeanDetailBinding mBinding;

	private CoffeeBeanViewModel mViewModel;

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
			requireArguments().getLong("coffee_bean_id")
		);
		mViewModel = new ViewModelProvider(this, factory).get(CoffeeBeanViewModel.class);
		mBinding.setLifecycleOwner(getViewLifecycleOwner());
		mBinding.setCoffeeBeanViewModel(mViewModel);
		mBinding.buttonDeleteCoffeeBean.setOnClickListener(new DeleteButtonOnClickListener());
	}

	@Override
	public void onDestroyView() {
		mBinding = null;
		super.onDestroyView();
	}

	public static CoffeeBeanDetailFragment forCoffeeBean(long coffeeBeanId) {
		CoffeeBeanDetailFragment fragment = new CoffeeBeanDetailFragment();
		Bundle args = new Bundle();
		args.putLong("coffee_bean_id", coffeeBeanId);
		fragment.setArguments(args);
		return fragment;
	}

	private class DeleteButtonOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			CoffeeBean deletedCoffeeBean = mViewModel.getCoffeeBean().getValue();
			mViewModel.deleteCoffeeBean(deletedCoffeeBean);
			Toast.makeText(getContext(), "Coffee Bean deleted.", Toast.LENGTH_LONG).show();
			getActivity().onBackPressed();
		}
	}
}
