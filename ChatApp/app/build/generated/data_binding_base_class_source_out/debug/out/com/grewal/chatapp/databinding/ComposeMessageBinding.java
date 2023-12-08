// Generated by view binder compiler. Do not edit!
package com.grewal.chatapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.grewal.chatapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ComposeMessageBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final RadioButton PromotionButton;

  @NonNull
  public final RadioButton SimpleMessageButton;

  @NonNull
  public final EditText message;

  @NonNull
  public final RadioGroup radioGroup;

  @NonNull
  public final Spinner recipients;

  @NonNull
  public final Button send;

  private ComposeMessageBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull RadioButton PromotionButton, @NonNull RadioButton SimpleMessageButton,
      @NonNull EditText message, @NonNull RadioGroup radioGroup, @NonNull Spinner recipients,
      @NonNull Button send) {
    this.rootView = rootView;
    this.PromotionButton = PromotionButton;
    this.SimpleMessageButton = SimpleMessageButton;
    this.message = message;
    this.radioGroup = radioGroup;
    this.recipients = recipients;
    this.send = send;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static ComposeMessageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ComposeMessageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.compose_message, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ComposeMessageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Promotion_button;
      RadioButton PromotionButton = ViewBindings.findChildViewById(rootView, id);
      if (PromotionButton == null) {
        break missingId;
      }

      id = R.id.Simple_message_button;
      RadioButton SimpleMessageButton = ViewBindings.findChildViewById(rootView, id);
      if (SimpleMessageButton == null) {
        break missingId;
      }

      id = R.id.message;
      EditText message = ViewBindings.findChildViewById(rootView, id);
      if (message == null) {
        break missingId;
      }

      id = R.id.radioGroup;
      RadioGroup radioGroup = ViewBindings.findChildViewById(rootView, id);
      if (radioGroup == null) {
        break missingId;
      }

      id = R.id.recipients;
      Spinner recipients = ViewBindings.findChildViewById(rootView, id);
      if (recipients == null) {
        break missingId;
      }

      id = R.id.send;
      Button send = ViewBindings.findChildViewById(rootView, id);
      if (send == null) {
        break missingId;
      }

      return new ComposeMessageBinding((LinearLayoutCompat) rootView, PromotionButton,
          SimpleMessageButton, message, radioGroup, recipients, send);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}