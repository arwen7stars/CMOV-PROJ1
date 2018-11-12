package org.feup.cmov.customerapp.shows;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.feup.cmov.customerapp.R;

public class FooterViewHolder extends RecyclerView.ViewHolder {

    public FrameLayout loadingView;
    public ProgressBar loadingPB;
    public RelativeLayout errorView;
    public Button reload;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    FooterViewHolder(View itemView) {

        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);

        loadingView = itemView.findViewById(R.id.loading_fl);
        loadingPB = itemView.findViewById(R.id.loading_pb);
    }
}
