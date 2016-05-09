package cachemoney420.financeproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AddDialog extends DialogFragment {

    public static final String EXTRA_TICKER = "ticker";
    public static final int OU = 0;

    private EditText mTicker;
    private ArrayList<String> mOver;
    private ArrayList<String> mUnder;

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
                        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                        View promptsView = layoutInflater.inflate(R.layout.prompts, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                        alertDialogBuilder.setView(promptsView);

                        mTicker = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                        mTicker.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                mTicker.setText(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });

                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("Add",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                mUnder.add(mTicker.toString());
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
                        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                        View promptsView = layoutInflater.inflate(R.layout.prompts, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                        alertDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                        userInput.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                userInput.setText(s);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                            }
                        });

                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("Add",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                mTicker.setText(userInput.getText().toString());
                                                mOver.add(mTicker.toString());
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
}
