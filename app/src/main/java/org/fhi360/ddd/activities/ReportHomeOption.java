package org.fhi360.ddd.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import org.fhi360.ddd.R;

public class ReportHomeOption extends AppCompatActivity {
    private View theMenu;
    private View menu2;
    private View menu4;
    private View menu5;
    private View menu6;
    private View overlay;
    private boolean menuOpen = false;
    private TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        theMenu = findViewById(R.id.the_menu);
        next = findViewById(R.id.next);
        menu2 = findViewById(R.id.menu2);
        menu4 = findViewById(R.id.menu4);
        menu5 = findViewById(R.id.menu5);
        menu6 = findViewById(R.id.menu6);
        overlay = findViewById(R.id.overlay);
        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OutletHome.class);
                startActivity(intent);
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!menuOpen) {
                    revealMenu();
                } else {
                    hideMenu();
                }
            }
        });
        ImageButton dasboard = findViewById(R.id.dashboard);
        //
        ImageButton rountineNational = findViewById(R.id.rountineNational);
        //
        ImageButton listClient = findViewById(R.id.listClient);
        ImageButton defualterList = findViewById(R.id.defualterList);
       // ImageView imageViewInventory = findViewById(R.id.inventoryManagementReport);

        dasboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OutletDashboard.class);
                view.getContext().startActivity(intent);
            }
        });

        rountineNational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OutletRoutineNationalReport.class);
                view.getContext().startActivity(intent);
            }
        });

        listClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PatientList.class);
                view.getContext().startActivity(intent);
            }
        });

        defualterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DefaulterListActivity1.class);
                view.getContext().startActivity(intent);
            }
        });

//        imageViewInventory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ReportHomeOption.this, InventoryReport.class);
//                startActivity(intent);
//            }
//        });

    }


    public void revealMenu() {
        menuOpen = true;
        theMenu.setVisibility(View.INVISIBLE);
        int cx = theMenu.getRight() - 200;
        int cy = theMenu.getTop();
        int finalRadius = Math.max(theMenu.getWidth(), theMenu.getHeight());
        Animator anim =
                ViewAnimationUtils.createCircularReveal(theMenu, cx, cy, 0, finalRadius);
        anim.setDuration(600);
        theMenu.setVisibility(View.VISIBLE);
        overlay.setVisibility(View.VISIBLE);
        anim.start();
        Animation popup1 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popup2 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popup3 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popup4 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popeup5 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popeup6 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        popup1.setStartOffset(50);
        popup2.setStartOffset(100);
        popup3.setStartOffset(150);
        popup4.setStartOffset(200);
        popeup5.setStartOffset(250);
        popeup6.setStartOffset(300);
        menu2.startAnimation(popup2);
        menu4.startAnimation(popup4);
        menu5.startAnimation(popeup5);
        menu6.startAnimation(popeup6);

    }

    public void hideMenu() {
        menuOpen = false;
        int cx = theMenu.getRight() - 200;
        int cy = theMenu.getTop();
        int initialRadius = theMenu.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(theMenu, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                theMenu.setVisibility(View.INVISIBLE);
                theMenu.setVisibility(View.GONE);
                overlay.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.GONE);
            }
        });
        anim.start();
    }

    @Override
    public void onBackPressed() {
        if (menuOpen) {
            hideMenu();
        } else {
            finishAfterTransition();
        }
    }

    public void overlayClick(View v) {
        hideMenu();
    }

}
