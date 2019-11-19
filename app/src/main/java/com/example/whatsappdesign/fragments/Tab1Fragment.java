package com.example.whatsappdesign.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.whatsappdesign.R;
import com.example.whatsappdesign.adapter.DAdapter;
import com.example.whatsappdesign.adapter.ModelD;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {


    private int mToolbarHeight;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    private View view;
    private ArrayList<ModelD> modelDArrayList;
    private RecyclerView recyclerView;
    SearchView searchView;
    TabLayout tabs;
    Toolbar toolbar;
    Toolbar toolbarAnimation;
    boolean isSearchBarShowing = false;
    AppBarLayout appBarLayout;
    ViewPager viewPager;
    CoordinatorLayout coordinatorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        tabs = getActivity().findViewById(R.id.tabs);
        toolbar = getActivity().findViewById(R.id.toolbar);
        viewPager = getActivity().findViewById(R.id.viewPager);
        toolbarAnimation = getActivity().findViewById(R.id.toolbar_animation);
        coordinatorLayout = getActivity().findViewById(R.id.coordinatorLayout);
        appBarLayout = getActivity().findViewById(R.id.appbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        modelDArrayList = new ArrayList<>();
        bindList(modelDArrayList);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position > 0) {
                    if (isSearchBarShowing) {
                        toolbarAnimation.setVisibility(View.GONE);
                        if (toolbar != null) {
                            toolbar.setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.colorPrimary)));
                            showTabs(true);
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    private void bindList(ArrayList<ModelD> arrayList) {
        if (arrayList.isEmpty()) {
            for (int i = 0; i < 20; i++) {
                ModelD modelD = new ModelD();
                modelD.setName("Name " + i);
                arrayList.add(modelD);
            }
        }
        DAdapter adapter = new DAdapter(getContext(), arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            toolbarAnimation.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collapse();
                }
            });

            searchView = (SearchView) searchItem.getActionView();
            searchView.setFitsSystemWindows(true);
            searchView.setOnQueryTextListener(this);
            searchView.setQueryHint("Search....");
            searchView.setFitsSystemWindows(true);


            EditText txtSearch = ((EditText) searchView.findViewById(R.id.search_src_text));
            txtSearch.setHint("Search..");
            txtSearch.setHintTextColor(Color.DKGRAY);
            txtSearch.setBackgroundColor(getResources().getColor(R.color.white));
            txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));

            // traverse the view to the widget containing the hint text
            LinearLayout ll = (LinearLayout) searchView.getChildAt(0);
            LinearLayout ll2 = (LinearLayout) ll.getChildAt(2);
            LinearLayout ll3 = (LinearLayout) ll2.getChildAt(1);
            SearchView.SearchAutoComplete autoComplete = (SearchView.SearchAutoComplete) ll3.getChildAt(0);


            View searchplate = (View) searchView.findViewById(R.id.search_edit_frame);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                searchplate.setBackgroundColor(getResources().getColor(R.color.white));
            }
            searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    // Set styles for expanded state here
                    // if search bar is showing
                    isSearchBarShowing = true;
                    if (toolbar != null) {
                        toolbar.setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.white)));
                        showTabs(false);
                    }

                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {

                    // if search bar is hide
                    isSearchBarShowing = false;
                    if (toolbar != null) {
                        toolbar.setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.colorPrimary)));
                        showTabs(true);
                    }
                    return true;

                }
            });
            // set the hint text color
            autoComplete.setHintTextColor(getResources().getColor(R.color.color333333));
            // set the text color
            autoComplete.setTextColor(getResources().getColor(R.color.color333333));
//            android.support.v7.appcompat.R.id.search_button
//            ImageView searchMagIcon = searchView.findViewById(R.id.search_button);
//            searchMagIcon.setImageResource(R.drawable.ic_search_white_24dp);
            ImageView closeIcon = searchView.findViewById(R.id.search_close_btn);
            closeIcon.setImageResource(R.drawable.ic_close_white_24dp);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            case android.R.id.home:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void showTabs(boolean show) {


        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbarAnimation.getLayoutParams();
        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        if (isSearchBarShowing) {
            params.setScrollFlags(0);
            appBarLayoutParams.setBehavior(null);
            appBarLayout.setLayoutParams(appBarLayoutParams);
        } else {
            params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
            appBarLayout.setLayoutParams(appBarLayoutParams);
        }


        if (show) {
            tabs.setVisibility(View.VISIBLE);
            tabs.animate().scaleY(1).setInterpolator(new DecelerateInterpolator()).start();
        } else {
            expand();
            tabs.animate().scaleY(0).setInterpolator(new AccelerateInterpolator()).start();
            tabs.setVisibility(View.GONE);
        }
    }

    private void collapse() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(false);
        }
    }

    private void expand() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circleReveal(true);
        } else {
            toolbarAnimation.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(final boolean isShow) {

        int width = toolbar.getWidth();
        width -= (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);

        int cx = width;
        int cy = toolbar.getHeight() / 2;

        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(toolbar, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(toolbar, cx, cy, (float) width, 0);

        anim.setDuration((long) 220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    toolbarAnimation.setVisibility(View.GONE);
                    super.onAnimationEnd(animation);
                }
            }
        });

//        // make the view visible and start the animation
        if (isShow) {
            toolbar.setVisibility(View.VISIBLE);
        }

        // start the animation
        anim.start();
    }
}
