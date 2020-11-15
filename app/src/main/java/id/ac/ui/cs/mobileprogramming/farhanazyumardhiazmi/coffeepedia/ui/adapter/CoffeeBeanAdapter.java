package id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.R;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.data.entity.CoffeeBean;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.databinding.CoffeeBeanItemBinding;
import id.ac.ui.cs.mobileprogramming.farhanazyumardhiazmi.coffeepedia.ui.callback.CoffeeBeanClickCallback;

import java.util.List;

public class CoffeeBeanAdapter extends RecyclerView.Adapter<CoffeeBeanAdapter.CoffeeBeanViewHolder> {

	List<CoffeeBean> mCoffeeBeans;

	private final CoffeeBeanClickCallback mCoffeeBeanClickCallback;

	public CoffeeBeanAdapter(CoffeeBeanClickCallback coffeeBeanClickCallback) {
		mCoffeeBeanClickCallback = coffeeBeanClickCallback;
		setHasStableIds(true);
	}

	@NonNull
	@Override
	public CoffeeBeanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		CoffeeBeanItemBinding binding = DataBindingUtil.inflate(
			LayoutInflater.from(parent.getContext()),
			R.layout.coffee_bean_item,
			parent,
			false
		);
		binding.setCallback(mCoffeeBeanClickCallback);
		return new CoffeeBeanViewHolder(binding);
	}

	@Override
	public void onBindViewHolder(@NonNull CoffeeBeanAdapter.CoffeeBeanViewHolder holder, int position) {
		holder.binding.setCoffeeBean(mCoffeeBeans.get(position));
		holder.binding.executePendingBindings();
	}

	@Override
	public int getItemCount() {
		return mCoffeeBeans == null ? 0 : mCoffeeBeans.size();
	}

	@Override
	public long getItemId(int position) {
		return mCoffeeBeans.get(position).getCoffeeBeanId();
	}

	public void setCoffeeBeanList(final List<CoffeeBean> coffeeBeanList) {
		if (mCoffeeBeans == null) {
			mCoffeeBeans = coffeeBeanList;
			notifyItemRangeInserted(0, coffeeBeanList.size());
		} else {
			DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
				@Override
				public int getOldListSize() {
					return mCoffeeBeans.size();
				}

				@Override
				public int getNewListSize() {
					return coffeeBeanList.size();
				}

				@Override
				public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
					return mCoffeeBeans.get(oldItemPosition).getCoffeeBeanId() ==
						coffeeBeanList.get(newItemPosition).getCoffeeBeanId();
				}

				@Override
				public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
					CoffeeBean oldCoffeeBean = mCoffeeBeans.get(oldItemPosition);
					CoffeeBean newCoffeeBean = coffeeBeanList.get(newItemPosition);
					return oldCoffeeBean.equals(newCoffeeBean);
				}
			});
			mCoffeeBeans = coffeeBeanList;
			result.dispatchUpdatesTo(this);
		}
	}

	static class CoffeeBeanViewHolder extends RecyclerView.ViewHolder {

		final CoffeeBeanItemBinding binding;

		public CoffeeBeanViewHolder(CoffeeBeanItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}
	}
}
