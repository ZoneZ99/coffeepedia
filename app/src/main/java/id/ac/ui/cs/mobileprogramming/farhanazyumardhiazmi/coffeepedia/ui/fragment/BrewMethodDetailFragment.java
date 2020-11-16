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
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.FragmentBrewMethodDetailBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.activity.BrewMethodsActivity;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.viewmodel.BrewMethodViewModel;

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
		mBinding.buttonDeleteBrewMethod.setOnClickListener(new DeleteButtonOnClickListener());
		mBinding.buttonEditBrewMethod.setOnClickListener(new EditButtonOnClickListener());
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

	private class DeleteButtonOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			BrewMethod deletedBrewMethod = mViewModel.getBrewMethod().getValue();
			mViewModel.deleteBrewMethod(deletedBrewMethod);
			Toast.makeText(getContext(), R.string.success_delete_brew_method, Toast.LENGTH_SHORT).show();
			getActivity().onBackPressed();
		}
	}

	private class EditButtonOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			BrewMethod editedBrewMethod = mViewModel.getBrewMethod().getValue();
			((BrewMethodsActivity) getActivity()).startBrewMethodEditView(editedBrewMethod);
		}
	}
}
