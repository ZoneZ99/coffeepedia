package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
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
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.provider.PdfProvider;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.CoffeeBeansActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.adapter.CoffeeBeanAdapter;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.callback.CoffeeBeanClickCallback;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.CoffeeBeanListViewModel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CoffeeBeanListFragment extends Fragment {

	public static final String TAG = "CoffeeBeanListFragment";

	public static final int COFFEE_BEAN_LIST_EXPORT_PDF_REQUEST_CODE = 3;

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

	private final View.OnClickListener mExportButtonClickCallback = view -> {
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("application/pdf");
		intent.putExtra(Intent.EXTRA_TITLE, "coffee_bean_list.pdf");
		intent.putExtra("coffee_bean_id", 0);
		startActivityForResult(intent, COFFEE_BEAN_LIST_EXPORT_PDF_REQUEST_CODE);
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			ContentValues contentValues = new ContentValues();
			long coffeeBeanId = data.getLongExtra("coffee_bean_id", 0);
			contentValues.put(PdfProvider.CONTENT_TYPE, PdfProvider.COFFEE_BEAN);
			contentValues.put(PdfProvider.CONTENT_ID, coffeeBeanId);
			contentValues.put("uri", data.getDataString());
			getActivity().getContentResolver().insert(PdfProvider.CONTENT_URI, contentValues);
		}
	}

	private final CoffeeBeanClickCallback mCoffeeBeanClickCallback = coffeeBean -> {
		if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
			((CoffeeBeansActivity) requireActivity()).showCoffeeBeanDetailFragment(coffeeBean);
		}
	};
}