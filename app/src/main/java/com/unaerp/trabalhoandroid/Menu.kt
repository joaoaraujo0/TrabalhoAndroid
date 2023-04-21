package com.unaerp.trabalhoandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unaerp.trabalhoandroid.fragments.PainelVagas


class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_nav)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val homeFragment = PainelVagas()

        makeCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_Vagas -> makeCurrentFragment(homeFragment)
                R.id.page_Anunciar -> makeCurrentFragment(homeFragment)
                R.id.page_MinhasVagas -> makeCurrentFragment(homeFragment)
                R.id.page_Perfil -> makeCurrentFragment(homeFragment)

            }
            true
        }


    }
    private fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
           replace(R.id.container,fragment)
            commit()
        }
}