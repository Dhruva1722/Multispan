package com.example.multispan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;

public class AboutUs extends AppCompatActivity {

    RelativeLayout relativeLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

     relativeLayout = findViewById(R.id.rlImgBG);
        relativeLayout.setAlpha(0.5f);

        // Create an instance of the PagerAdapter
        AboutUsPagerAdapter pagerAdapter = new AboutUsPagerAdapter(getSupportFragmentManager());

        // Add fragments to the PagerAdapter
        pagerAdapter.addFragment(new Scince1986Fragment(), "Scince 1986");
        pagerAdapter.addFragment(new LogoPhilosophyFragment(), "Logo & Philosophy");
        pagerAdapter.addFragment(new ResearchDevelopmentFragment(), "Research & Development");
        pagerAdapter.addFragment(new ManufacturingQuilityFragment(), "Manufacturing & Quality");
        pagerAdapter.addFragment(new CertificationFragment(), "Certification");

        // Set the adapter for the ViewPager
        viewPager.setAdapter(pagerAdapter);

        // Connect the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);

    }


}
