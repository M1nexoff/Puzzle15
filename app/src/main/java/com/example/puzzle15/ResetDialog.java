package com.example.puzzle15;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.puzzle15.databinding.DialogBinding;


public class ResetDialog extends DialogFragment {
    public DialogBinding binding;
    private Runnable cancel;
    private Runnable restart;
    private int i;

    public ResetDialog(int i) {
        super();
        this.i = i;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(i == 1 ? R.layout.dialog_exit : R.layout.dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DialogBinding.bind(view);
        binding.btnCancel.setOnClickListener(v -> {
            if (cancel != null) {
                cancel.run();
            }
            dismiss();
        });

        binding.btnConfirmReset.setOnClickListener(v -> {
            if (restart != null) {
                restart.run();
            }
            dismiss();
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    public void setCancelAction(Runnable cancel) {
        this.cancel = cancel;
    }

    public void setRestartAction(Runnable restart) {
        this.restart = restart;
    }
}
