package org.youdian.android_demos.contentprovider;

import org.youdian.android_demos.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class NewItemFragment extends DialogFragment implements OnClickListener {
	private EditText name;
	private EditText number;
	private Button cancel, confirm;
	private long list_id = 1;

	public NewItemFragment() {
		super();

		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_contentprovider_newitem,
				container, false);
		name = (EditText) v.findViewById(R.id.name);
		number = (EditText) v.findViewById(R.id.number);
		cancel = (Button) v.findViewById(R.id.cancel);
		confirm = (Button) v.findViewById(R.id.confirm);
		cancel.setOnClickListener(this);
		confirm.setOnClickListener(this);
		Bundle args = this.getArguments();
		list_id = args.getLong("list_id");
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == cancel) {
			this.dismiss();
		} else if (v == confirm) {
			String sName = name.getText().toString();
			int sCount = Integer.parseInt(number.getText().toString());
			((MainActivity) getActivity()).asyncAddItem(sName, sCount, list_id);
			this.dismiss();
		}
	}

}
