package com.amin.digipeyui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvAppName;
    private TextView tvItemTitle;
    private ImageButton btnHome;
    private ImageButton btnTransaction;
    private ImageButton btnVideo;
    private ImageButton btnScan;
    private RecyclerView recyclerViewMain;
    private Animation slideUp;
    private Animation slideDown;
    private Button btnCardManager;
    private Button btn1;
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolBar);
        // setSupportActionBar(toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.appBar);

        btnHome = findViewById(R.id.btnHome);
        btnScan = findViewById(R.id.btnScan);
        btnVideo = findViewById(R.id.btnVideo);
        btnTransaction = findViewById(R.id.btnTransaction);
        btnCardManager = findViewById(R.id.btnCardManager);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        btnCardManager.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);

        btnHome.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
        btnScan.setOnClickListener(this);
        btnTransaction.setOnClickListener(this);

        tvAppName = findViewById(R.id.tvAppName);
        tvItemTitle = findViewById(R.id.tvItemTitle);

        hideOption();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption();
                } else if (isShow) {
                    isShow = false;
                    hideOption();
                }
            }
        });

        int padding20Dp = getResources().getDimensionPixelSize(R.dimen.item_padding_10dp);
        int padding0Dp = getResources().getDimensionPixelSize(R.dimen.item_padding_0dp);
        ScrollItem cardItem = new ScrollItem("کارت بانکی");
        cardItem.setVisibility(View.VISIBLE);
        // cardItem.setPadding(padding0Dp);
        // cardItem.setWidth(getResources().getDimensionPixelSize(R.dimen.item_with));
        // cardItem.setHeight(getResources().getDimensionPixelSize(R.dimen.item_height));
        cardItem.setAlpha(1.0F);

        ScrollItem item2 = new ScrollItem("کیف ");
        cardItem.setPadding(padding20Dp);

        ScrollItem item3 = new ScrollItem("کیف پول");
        cardItem.setPadding(padding20Dp);

        ScrollItem item4 = new ScrollItem(" اعتبارات");
        cardItem.setPadding(padding20Dp);


        List<ScrollItem> list = new ArrayList<>();
        list.add(cardItem);
        list.add(item3);
        list.add(item4);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, list);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.scrollToPosition(Integer.MAX_VALUE / 2);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //  int prevPosition = -1;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //  if (prevPosition!=-1)
                    //  tvItemTitle.setText(list.get(prevPosition).getName());
                    slideDown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_down_textview);
                    tvItemTitle.setVisibility(View.INVISIBLE);
                    tvItemTitle.setAnimation(slideDown);

                   /* slideDown = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_down_textview);
                    btnCardManager.setVisibility(View.INVISIBLE);
                    btnCardManager.setAnimation(slideDown);*/
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition() % list.size();
                    Log.i("Postion: ", position + "");
                    for (int i = 0; i < list.size(); i++) {

                        if (i != position) {
                            list.get(i).setVisibility(View.INVISIBLE);
                            list.get(i).setWidth(getResources().getDimensionPixelSize(R.dimen.item_with_s));
                            list.get(i).setHeight(getResources().getDimensionPixelSize(R.dimen.item_height_s));
                            list.get(i).setAlpha(0.8F);

                        } else {
                            slideUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up_textview);
                            list.get(i).setVisibility(View.VISIBLE);
                            list.get(i).setWidth(getResources().getDimensionPixelSize(R.dimen.item_with));
                            list.get(i).setHeight(getResources().getDimensionPixelSize(R.dimen.item_height));
                            // list.get(i).setMarginLeft(getResources().getDimensionPixelSize(R.dimen.item_margin_left));
                            // list.get(i).setMarginRight(getResources().getDimensionPixelSize(R.dimen.item_margin_right));
                            list.get(i).setAlpha(1.0F);
                            tvItemTitle.setText(list.get(i).getName());
                            tvItemTitle.setVisibility(View.VISIBLE);
                            tvItemTitle.setAnimation(slideUp);
                            //prevPosition = position;

                            showButtons(position);

                        }

                    }
                    adapter.notifyDataSetChanged();

                }

            }
        });
        recyclerView.smoothScrollBy(1, 0);


        List<String> names = Arrays.asList("کارت به کارت", "خرید اقساطی",
                "بسته اینترنت", "خریدشارژ", "بیشتر", "خیریه", "عوارض جاده ای", "پرداخت قیض");

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.ic_baseline_settings_24);
        images.add(R.drawable.ic_baseline_notifications_none_24);
        images.add(R.drawable.ic_baseline_settings_24);
        images.add(R.drawable.ic_baseline_notifications_none_24);
        images.add(R.drawable.ic_baseline_settings_24);
        images.add(R.drawable.ic_baseline_notifications_none_24);
        images.add(R.drawable.ic_baseline_settings_24);
        images.add(R.drawable.ic_baseline_notifications_none_24);

        recyclerViewMain = findViewById(R.id.recyclerViewMain);
        RecyclerViewAdapterMain adapterMain = new RecyclerViewAdapterMain(this, images, names);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerViewMain.setAdapter(adapterMain);
        recyclerViewMain.setLayoutManager(gridLayoutManager);

        bottomAppBarRadius();

    }

    private void showButtons(int position) {

        btnCardManager.setVisibility(View.INVISIBLE);
        switch (position) {
            case 2:
                btn2.setVisibility(View.GONE);
                btn1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_left_24, 0);
                btn1.setText("ثیت نام دریافت اعتبارات");
                slideUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up_textview);
                btn1.setAnimation(slideUp);
                break;
            case 1:
                btn1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_circle_outline_24, 0);
                btn2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_north_west_24, 0);
                btn1.setText("افزودن موجودی");
                btn2.setText("انتقال از کیف");

                btn2.setVisibility(View.VISIBLE);
                slideUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up_textview);
                btn2.setAnimation(slideUp);
                slideUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up_textview);
                btn1.setAnimation(slideUp);
                break;
            case 0:
                btnCardManager.setVisibility(View.VISIBLE);
                slideUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up_textview);
              //  btn1.setVisibility(View.VISIBLE);
                slideUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up_textview);
                btn1.setAnimation(slideUp);

                btn2.setVisibility(View.GONE);
                btn1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_credit_card_24, 0);
                btn1.setText("انتقال از کارت");
                break;

        }
    }


    private void bottomAppBarRadius() {
        float radius = getResources().getDimensionPixelSize(R.dimen.default_corner_radius);
        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);

        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                .setTopRightCorner(CornerFamily.ROUNDED, radius)

                .build();

        MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
        shapeDrawable.setFillColor(ColorStateList.valueOf(getColor(R.color.milky)));
        //shapeDrawable.setStrokeColor(ColorStateList.valueOf(getColor(R.color.light_gray)));
        shapeDrawable.setStroke(2.0F, getColor(R.color.light_gray));
        bottomAppBar.setBackground(shapeDrawable);
        //   ViewCompat.setBackground(bottomAppBar, shapeDrawable);
        //   ViewCompat.setBackgroundTintMode(bottomAppBar, PorterDuff.Mode.CLEAR);

    }

    private void showOption() {
        tvAppName.setVisibility(View.VISIBLE);
    }

    private void hideOption() {
        tvAppName.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnHome:
                btnHome.setImageResource(R.drawable.ic_baseline_home_active_24);
                btnScan.setImageResource(R.drawable.ic_baseline_qr_code_scanner_24);
                btnVideo.setImageResource(R.drawable.ic_baseline_video_label_24);
                btnTransaction.setImageResource(R.drawable.ic_baseline_transform_24);

                findViewById(R.id.tvHome).setVisibility(View.VISIBLE);
                findViewById(R.id.tvScan).setVisibility(View.GONE);
                findViewById(R.id.tvTransaction).setVisibility(View.GONE);
                findViewById(R.id.tvVideo).setVisibility(View.GONE);
                break;
            case R.id.btnScan:
                btnHome.setImageResource(R.drawable.ic_baseline_home_24);
                btnScan.setImageResource(R.drawable.ic_baseline_qr_code_active_scanner_24);
                btnVideo.setImageResource(R.drawable.ic_baseline_video_label_24);
                btnTransaction.setImageResource(R.drawable.ic_baseline_transform_24);

                findViewById(R.id.tvHome).setVisibility(View.GONE);
                findViewById(R.id.tvScan).setVisibility(View.VISIBLE);
                findViewById(R.id.tvTransaction).setVisibility(View.GONE);
                findViewById(R.id.tvVideo).setVisibility(View.GONE);
                break;
            case R.id.btnTransaction:

                btnHome.setImageResource(R.drawable.ic_baseline_home_24);
                btnScan.setImageResource(R.drawable.ic_baseline_qr_code_scanner_24);
                btnVideo.setImageResource(R.drawable.ic_baseline_video_label_24);
                btnTransaction.setImageResource(R.drawable.ic_baseline_transform_active_24);

                findViewById(R.id.tvHome).setVisibility(View.GONE);
                findViewById(R.id.tvScan).setVisibility(View.GONE);
                findViewById(R.id.tvTransaction).setVisibility(View.VISIBLE);
                findViewById(R.id.tvVideo).setVisibility(View.GONE);
                break;
            case R.id.btnVideo:

                btnHome.setImageResource(R.drawable.ic_baseline_home_24);
                btnScan.setImageResource(R.drawable.ic_baseline_qr_code_scanner_24);
                btnVideo.setImageResource(R.drawable.ic_baseline_video_label_active_24);
                btnTransaction.setImageResource(R.drawable.ic_baseline_transform_24);

                findViewById(R.id.tvHome).setVisibility(View.GONE);
                findViewById(R.id.tvScan).setVisibility(View.GONE);
                findViewById(R.id.tvTransaction).setVisibility(View.GONE);
                findViewById(R.id.tvVideo).setVisibility(View.VISIBLE);
                break;

        }

    }
}