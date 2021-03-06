// Generated by view binder compiler. Do not edit!
package com.example.googlelogin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.googlelogin.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import pl.droidsonroids.gif.GifImageView;

public final class ActivityLandingBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final GifImageView gif;

  @NonNull
  public final TextView landingHeader;

  @NonNull
  public final MaterialButton loginBtn;

  @NonNull
  public final MaterialButton signupBtn;

  private ActivityLandingBinding(@NonNull ConstraintLayout rootView, @NonNull GifImageView gif,
      @NonNull TextView landingHeader, @NonNull MaterialButton loginBtn,
      @NonNull MaterialButton signupBtn) {
    this.rootView = rootView;
    this.gif = gif;
    this.landingHeader = landingHeader;
    this.loginBtn = loginBtn;
    this.signupBtn = signupBtn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLandingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLandingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_landing, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLandingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.gif;
      GifImageView gif = ViewBindings.findChildViewById(rootView, id);
      if (gif == null) {
        break missingId;
      }

      id = R.id.landingHeader;
      TextView landingHeader = ViewBindings.findChildViewById(rootView, id);
      if (landingHeader == null) {
        break missingId;
      }

      id = R.id.loginBtn;
      MaterialButton loginBtn = ViewBindings.findChildViewById(rootView, id);
      if (loginBtn == null) {
        break missingId;
      }

      id = R.id.signupBtn;
      MaterialButton signupBtn = ViewBindings.findChildViewById(rootView, id);
      if (signupBtn == null) {
        break missingId;
      }

      return new ActivityLandingBinding((ConstraintLayout) rootView, gif, landingHeader, loginBtn,
          signupBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
