package com.somethingsimple.simplelist.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.somethingsimple.simplelist.R;

public class BottomDrawerFragment extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drawer_bottom_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NavigationView view = getView().findViewById(R.id.navigation_view);
        view.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.sign_out_menu_drawer:
                    ((MainActivity) getActivity()).processLogout();
                    this.dismiss();
                    return true;
                case R.id.settings_menu_drawer:
                    Toast.makeText(getContext(), "Not yet implemented",
                            Toast.LENGTH_SHORT).show();
                default:
                    return true;
            }
        });
        ImageView close_image = getView().findViewById(R.id.close_imageview);
        close_image.setOnClickListener(v -> BottomDrawerFragment.this.dismiss());
    }
}
