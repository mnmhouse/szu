package cn.szu.edu.app.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.szu.edu.app.R;
import cn.szu.edu.app.bean.Report;
import cn.szu.edu.app.ui.dialog.CommonDialog;
import cn.szu.edu.app.ui.dialog.DialogHelper;
import cn.szu.edu.app.util.TDevice;

public class ReportDialog extends CommonDialog implements
		android.view.View.OnClickListener {

	private static final int MAX_CONTENT_LENGTH = 250;
	private TextView mTvReason;
	private TextView mTvLink;
	private EditText mEtContent;
	private String[] reasons;
	private String mLink;
	private int mReportId;

	public ReportDialog(Context context, String link, int reportId) {
		this(context, R.style.dialog_common, link, reportId);
	}

	private ReportDialog(Context context, int defStyle, String link,
			int reportId) {
		super(context, defStyle);
		mLink = link;
		mReportId = reportId;
		initViews(context);
	}

	private ReportDialog(Context context, boolean flag,
			OnCancelListener listener) {
		super(context, flag, listener);
	}

	@SuppressLint("InflateParams")
	private void initViews(Context context) {
		reasons = getContext().getResources().getStringArray(
				R.array.report_reason);

		View view = getLayoutInflater()
				.inflate(R.layout.dialog_report, null);

		mTvReason = (TextView) view.findViewById(R.id.tv_reason);
		mTvReason.setOnClickListener(this);

		mTvReason.setText(reasons[0]);

		mTvLink = (TextView) view.findViewById(R.id.tv_link);
		mTvLink.setText(mLink);

		mEtContent = (EditText) view.findViewById(R.id.et_content);
		
		super.setContent(view, 0);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tv_reason) {
			selectReason();
		}
	}

	private void selectReason() {
		String reason = mTvReason.getText().toString();
		int idx = 0;
		for (int i = 0; i < reasons.length; i++) {
			if (reasons[i].equals(reason)) {
				idx = i;
				break;
			}
		}
		final CommonDialog dialog = DialogHelper
				.getPinterestDialogCancelable(getContext());
		dialog.setTitle(R.string.report_reson);
		dialog.setItems(reasons, idx, new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dialog.dismiss();
				mTvReason.setText(reasons[position]);
			}
		});
		dialog.setNegativeButton(R.string.cancle, null);
		dialog.show();
	}

	public Report getReport() {
		String text = mEtContent.getText().toString();
		TDevice.hideSoftKeyboard(mEtContent);
		Report report = new Report();
		report.setReportId(mReportId);
		report.setLinkAddress(mLink);
		report.setReason(mTvReason.getText().toString());
		report.setOtherReason(text);
		return report;
	}
}
