// Generated by view binder compiler. Do not edit!
package com.grewal.chatapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.grewal.chatapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class NavHeaderBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView ProfileEmail;

  @NonNull
  public final ImageView ProfileImage;

  @NonNull
  public final TextView ProfileName;

  private NavHeaderBinding(@NonNull LinearLayout rootView, @NonNull TextView ProfileEmail,
      @NonNull ImageView ProfileImage, @NonNull TextView ProfileName) {
    this.rootView = rootView;
    this.ProfileEmail = ProfileEmail;
    this.ProfileImage = ProfileImage;
    this.ProfileName = ProfileName;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NavHeaderBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NavHeaderBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.nav_header, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NavHeaderBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Profile_email;
      TextView ProfileEmail = ViewBindings.findChildViewById(rootView, id);
      if (ProfileEmail == null) {
        break missingId;
      }

      id = R.id.Profile_image;
      ImageView ProfileImage = ViewBindings.findChildViewById(rootView, id);
      if (ProfileImage == null) {
        break missingId;
      }

      id = R.id.Profile_name;
      TextView ProfileName = ViewBindings.findChildViewById(rootView, id);
      if (ProfileName == null) {
        break missingId;
      }

      return new NavHeaderBinding((LinearLayout) rootView, ProfileEmail, ProfileImage, ProfileName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
