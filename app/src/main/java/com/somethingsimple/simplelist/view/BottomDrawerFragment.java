package com.somethingsimple.simplelist.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
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

        ImageView close_image = getView().findViewById(R.id.close_imageview);
        close_image.setOnClickListener(v -> BottomDrawerFragment.this.dismiss());

        NavigationView view = getView().findViewById(R.id.navigation_view);

        Bundle bundle = getArguments();
        FirebaseUser fbuser = bundle.getParcelable(getString(R.string.user_bundle_key));
        if (fbuser != null) {
            ImageView imageUser = getView().findViewById(R.id.imageUser);
            Glide.with(this)
                    .load(fbuser.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageUser);
            TextView userName = getView().findViewById(R.id.textUsername);
            userName.setText(fbuser.getDisplayName());
            TextView userEmail = getView().findViewById(R.id.textEmail);
            userEmail.setText(fbuser.getEmail());
        } else {
            view.getMenu().findItem(R.id.sign_out_menu_drawer)
                    .setTitle(getString(R.string.sign_in));
        }

        view.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.sign_out_menu_drawer:
                    ((MainActivity) getActivity()).processLogout();
                    this.dismiss();
                    return true;
                case R.id.settings_menu_drawer:
                    Toast.makeText(getContext(), "Not yet implemented",
                            Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return true;
            }
        });
    }
}
