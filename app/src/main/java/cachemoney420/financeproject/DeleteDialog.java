package cachemoney420.financeproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mspear on 5/13/16.
 */
public class DeleteDialog extends DialogFragment {
    private Spinner mDeleteTicker;
    private Button mDeleteSingle;

    public static final String EXTRA_TICKER = "extraTicker";

    public static DeleteDialog newInstance(ArrayList<String> over, ArrayList<String> under) {
        Bundle args = new Bundle();

        DeleteDialog fragment = new DeleteDialog();
        args.putStringArrayList("Over", over);
        args.putStringArrayList("Under", under);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.delete_dialog, null);
        ArrayList<String> mOver = getArguments().getStringArrayList("Over");
        ArrayList<String> mUnder = getArguments().getStringArrayList("Under");
        System.out.println(mOver);
        System.out.println(mUnder);
        mOver.addAll(mUnder);
        mDeleteTicker = (Spinner) v.findViewById(R.id.tickerSpinner);
        ArrayAdapter<String> tickerAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, mOver);
        mDeleteTicker.setAdapter(tickerAdapter);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Delete ticker...")
                .setView(v)
                .setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK, mDeleteTicker.getSelectedItem().toString());
                            }
                        })
                .setNegativeButton("Delete All!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK, null);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, String ticker) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TICKER, ticker);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
