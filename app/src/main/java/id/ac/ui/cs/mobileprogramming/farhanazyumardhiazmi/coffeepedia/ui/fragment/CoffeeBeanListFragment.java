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
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentCoffeeBeanListBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.CoffeeBeansActivity;
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
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		mViewModel = new ViewModelProvider(requireActivity()).get(CoffeeBeanListViewModel.class);
		mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coffee_bean_list, container, false);
		mCoffeeBeanAdapter = new CoffeeBeanAdapter(mCoffeeBeanClickCallback);
		mBinding.coffeeBeanList.setAdapter(mCoffeeBeanAdapter);
		return mBinding.getRoot();
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		LiveData<List<CoffeeBean>> coffeeBeans = mViewModel.getCoffeeBeans();
		coffeeBeans.observe(getViewLifecycleOwner(), beans -> {
			if (beans != null) {
				mCoffeeBeanAdapter.setCoffeeBeanList(beans);
			} else {

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
			((CoffeeBeansActivity) requireActivity()).show(coffeeBean);
		}
	};
}