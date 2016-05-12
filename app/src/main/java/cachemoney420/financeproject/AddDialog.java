package cachemoney420.financeproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddDialog extends DialogFragment {

    public static final String EXTRA_TICKER = "ticker";
    public static final String EXTRA_OU = "0";

    private EditText mTicker;
    private String newTag;

    public static AddDialog newInstance() {
        Bundle args = new Bundle();

        AddDialog fragment = new AddDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Add ticker...")
                .setPositiveButton("Underweight", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View v = LayoutInflater.from(getActivity()).inflate(R.layout.prompts, null);

                        mTicker = (EditText) v.findViewById(R.id.editTextDialogUserInput);
                        mTicker.setText(newTag);
                        mTicker.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                newTag = s.toString();
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });

                        new AlertDialog.Builder(getActivity())
                                .setView(v)
                                .setTitle("Enter ticker:")
                                .setCancelable(false)
                                .setPositiveButton("Add",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                sendTicker(Activity.RESULT_OK, mTicker.toString() + "0");
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        })
                                .show();

                    }
                })
                .setNegativeButton("Overweight", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View v = LayoutInflater.from(getActivity()).inflate(R.layout.prompts, null);

                        mTicker = (EditText) v.findViewById(R.id.editTextDialogUserInput);
                        mTicker.setText(newTag);
                        mTicker.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                newTag = s.toString();
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });

                        new AlertDialog.Builder(getActivity())
                                .setView(v)
                                .setTitle("Enter ticker:")
                                .setCancelable(false)
                                .setPositiveButton("Add",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                sendTicker(Activity.RESULT_OK, mTicker.toString() + "1");
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();
                                            }
                                        })
                                .show();

                    }
                })
                .create();
    }

    private void sendTicker(int resultCode, String ticker) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TICKER, ticker);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
