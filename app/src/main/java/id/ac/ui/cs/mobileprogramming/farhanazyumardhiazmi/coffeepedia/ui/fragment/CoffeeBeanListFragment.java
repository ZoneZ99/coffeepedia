package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentCoffeeBeanListBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.CoffeeBeanListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoffeeBeanListFragment extends Fragment {

	public static final String TAG = "CoffeeBeanListFragment";

	private CoffeeBeanListViewModel mViewModel;

	private FragmentCoffeeBeanListBinding mBinding;

	public static CoffeeBeanListFragment newInstance() {
		return new CoffeeBeanListFragment();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		this.mBinding = FragmentCoffeeBeanListBinding.inflate(inflater, container, false);
		return this.mBinding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = new ViewModelProvider(requireActivity()).get(CoffeeBeanListViewModel.class);

		LiveData<List<CoffeeBean>> coffeeBeans = mViewModel.getCoffeeBeans();
		coffeeBeans.observe(getViewLifecycleOwner(), beans -> {
			if (beans != null) {
				List<String> coffeeBeanNames = beans.stream()
					.map(CoffeeBean::getName)
					.collect(Collectors.toList());
				mBinding.coffeeBeanList.setAdapter(
					new ArrayAdapter<>(
						getActivity(),
						android.R.layout.simple_list_item_1,
						coffeeBeanNames
					)
				);
			} else {

			}
		});

		ArrayList<String> loadingContent = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			loadingContent.add("Loading...");
		}
		this.mBinding.coffeeBeanList.setAdapter(
			new ArrayAdapter<>(
				getActivity(),
				android.R.layout.simple_list_item_1,
				loadingContent
			)
		);
	}

	@Override
	public void onDestroyView() {
		mBinding = null;
		super.onDestroyView();
	}
}