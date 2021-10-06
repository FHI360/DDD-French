

package org.fhi360.ddd.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import org.fhi360.ddd.R;

public class ReportHomeOptionAdmin extends AppCompatActivity {
    private View theMenu;
    private View menu1;
    private View menu2;
    private View menu4;
    private View menu5;
    private View menu6;
    private View menu8,menu9;
    private View overlay;
    private TextView next;
    private boolean menuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        theMenu = findViewById(R.id.the_menu);
        next = findViewById(R.id.next);
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu4 = findViewById(R.id.menu4);
        menu5 = findViewById(R.id.menu5);
        menu6 = findViewById(R.id.menu6);
        menu8 = findViewById(R.id.menu8);
        menu9 = findViewById(R.id.menu9);
        overlay = findViewById(R.id.overlay);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminHomePage.class);
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
    }


    public void revealMenu() {
        menuOpen = true;
        theMenu.setVisibility(View.INVISIBLE);
        int cx = theMenu.getRight() - 200;
        int cy = theMenu.getTop();
        int finalRadius = Math.max(theMenu.getWidth(), theMenu.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(theMenu, cx, cy, 0, finalRadius);
        anim.setDuration(600);
        theMenu.setVisibility(View.VISIBLE);
        overlay.setVisibility(View.VISIBLE);
        anim.start();
        Animation popup1 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popup2 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popup3 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popup4 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popup5 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popup6 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        popup1.setStartOffset(50);
        popup2.setStartOffset(100);
        popup3.setStartOffset(150);
        popup4.setStartOffset(200);
        popup5.setStartOffset(250);
        popup6.setStartOffset(300);
        menu1.startAnimation(popup1);
        menu2.startAnimation(popup2);
        menu4.startAnimation(popup4);
        menu5.startAnimation(popup5);
        menu6.startAnimation(popup6);
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

    public void menuClick(View view) {
        hideMenu();
        if (view.getTag().equals("Tableau de bord")) {
            Intent intent = new Intent(view.getContext(), AdminDashboard.class);
            view.getContext().startActivity(intent);
        }
        if (view.getTag().equals("Routine/Nationale")) {
            Intent intent = new Intent(view.getContext(),HomeRoutineNationalReport.class);
            view.getContext().startActivity(intent);
        }
        if (view.getTag().equals("Liste des points de distribution DDD enregistrés")) {
            Intent intent = new Intent(view.getContext(), AdminListDDDClient.class);
            view.getContext().startActivity(intent);
        }
        if (view.getTag().equals("Liste des clients pour DDD")) {
            Intent intent = new Intent(view.getContext(), AdminPatientList3.class);
            view.getContext().startActivity(intent);
        }
        if (view.getTag().equals("Liste des défaillants")) {
            Intent intent = new Intent(view.getContext(), AdminDefaulterListActivity.class);
            view.getContext().startActivity(intent);
        }
        if (view.getTag().equals("Client assigné")) {
            Intent intent = new Intent(view.getContext(), AdminPatientList3.class);
            view.getContext().startActivity(intent);
        }
        if (view.getTag().equals("Rechercher le client")) {
            Intent intent = new Intent(view.getContext(), PatientByDateRange2.class);
            view.getContext().startActivity(intent);
        }

    }
}
