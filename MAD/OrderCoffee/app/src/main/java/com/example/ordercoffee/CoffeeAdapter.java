package com.example.ordercoffee;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CoffeeAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] names;
    private final TypedArray images;

    public CoffeeAdapter(Context context, String[] names, TypedArray images) {
        super(context, 0, names);
        this.context = context;
        this.names = names;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    public Uri getImageUri(int position) {
        // Для пункта "Нет" возвращаем null.
        if (position < 0 || position >= names.length) return null;
        if ("Нет".equals(names[position])) return null;

        int imageIndex = position - 1;
        int resourceId = images.getResourceId(imageIndex, 0);
        if (resourceId == 0) return null;
        return Uri.parse("android.resource://" + context.getPackageName() + "/" + resourceId);
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_coffee, parent, false);
        }

        TextView textView = view.findViewById(R.id.coffeeText);
        ImageView imageView = view.findViewById(R.id.coffeeImage);

        textView.setText(names[position]);

        int normalMarginDp = 48;
        int negativeShiftDp = 0;

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textView.getLayoutParams();

        if ("Нет".equals(names[position])) {
            imageView.setVisibility(View.GONE);
            lp.setMarginStart(dpToPx(negativeShiftDp));
            textView.setLayoutParams(lp);
        } else {
            imageView.setVisibility(View.VISIBLE);
            lp.setMarginStart(dpToPx(normalMarginDp));
            textView.setLayoutParams(lp);

            int imgIndex = position - 1;
            if (imgIndex >= 0 && imgIndex < images.length()) {
                int resId = images.getResourceId(imgIndex, 0);
                if (resId != 0) {
                    imageView.setImageResource(resId);
                } else {
                    imageView.setImageDrawable(null);
                }
            } else {
                imageView.setImageDrawable(null);
            }
        }

        return view;
    }

    private int dpToPx(int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
