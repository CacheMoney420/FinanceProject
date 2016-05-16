package cachemoney420.financeproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AddDialog extends DialogFragment {

    private EditText mTicker;
    private ArrayList<String> mOver;
    private ArrayList<String> mUnder;

    public static AddDialog newInstance(ArrayList<String> over, ArrayList<String> under) {
        Bundle args = new Bundle();

        AddDialog fragment = new AddDialog();
        args.putStringArrayList("Over", over);
        args.putStringArrayList("Under", under);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mOver = getArguments().getStringArrayList("Over");
        mUnder = getArguments().getStringArrayList("Under");
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

                        alertDialogBuilder
                                .setTitle("Enter ticker")
                                .setCancelable(false)
                                .setPositiveButton("Add",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                String ticker = mTicker.getText().toString();
                                                if (!mUnder.contains(ticker))
                                                    mUnder.add(ticker);
                                                Intent intent = new Intent();
                                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
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

                        alertDialogBuilder
                                .setTitle("Enter ticker")
                                .setCancelable(false)
                                .setPositiveButton("Add",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                String ticker = userInput.getText().toString();
                                                if (!mOver.contains(ticker))
                                                    mOver.add(userInput.getText().toString());
                                                Intent intent = new Intent();
                                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
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
