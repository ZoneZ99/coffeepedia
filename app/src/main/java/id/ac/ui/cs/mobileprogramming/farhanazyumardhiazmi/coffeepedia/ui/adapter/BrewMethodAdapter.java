package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.BrewMethod;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.BrewMethodItemBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.callback.BrewMethodClickCallback;

import java.util.List;

public class BrewMethodAdapter extends RecyclerView.Adapter<BrewMethodAdapter.BrewMethodViewHolder> {

	List<BrewMethod> mBrewMethods;

	private final BrewMethodClickCallback mBrewMethodClickCallback;

	public BrewMethodAdapter(BrewMethodClickCallback brewMethodClickCallback) {
		mBrewMethodClickCallback = brewMethodClickCallback;
		setHasStableIds(true);
	}

	@NonNull
	@Override
	public BrewMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		BrewMethodItemBinding binding = DataBindingUtil.inflate(
			LayoutInflater.from(parent.getContext()),
			R.layout.brew_method_item,
			parent,
			false
		);
		binding.setCallback(mBrewMethodClickCallback);
		return new BrewMethodViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(@NonNull BrewMethodAdapter.BrewMethodViewHolder holder, int position) {
		holder.binding.setBrewMethod(mBrewMethods.get(position));
		holder.binding.executePendingBindings();
	}

	@Override
	public int getItemCount() {
		return mBrewMethods == null ? 0 : mBrewMethods.size();
	}

	@Override
	public long getItemId(int position) {
		return mBrewMethods.get(position).getBrewMethodId();
	}

	public void setBrewMethodList(final List<BrewMethod> brewMethodList) {
		if (mBrewMethods == null) {
			mBrewMethods = brewMethodList;
			notifyItemRangeInserted(0, brewMethodList.size());
		} else {
			DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
				@Override
				public int getOldListSize() {
					return mBrewMethods.size();
				}

				@Override
				public int getNewListSize() {
					return brewMethodList.size();
				}

				@Override
				public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
					return mBrewMethods.get(oldItemPosition).getBrewMethodId() ==
						brewMethodList.get(newItemPosition).getBrewMethodId();
				}

				@Override
				public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
					BrewMethod oldBrewMethod = mBrewMethods.get(oldItemPosition);
					BrewMethod newBrewMethod = brewMethodList.get(newItemPosition);
					return oldBrewMethod.equals(newBrewMethod);
				}
			});
			mBrewMethods = brewMethodList;
			result.dispatchUpdatesTo(this);
		}
	}

	static class BrewMethodViewHolder extends RecyclerView.ViewHolder {

		final BrewMethodItemBinding binding;

		public BrewMethodViewHolder(BrewMethodItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}
	}
}
