package com.unaerp.trabalhoandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unaerp.trabalhoandroid.fragments.AnunciarVaga
import com.unaerp.trabalhoandroid.fragments.MinhasVagas
import com.unaerp.trabalhoandroid.fragments.PainelVagas
import com.unaerp.trabalhoandroid.fragments.PerfilUser

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_nav)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val homeFragment = PainelVagas()
        val perfilFragment = PerfilUser()
        val vagaFragment = AnunciarVaga()
        val minhasVagas = MinhasVagas()


        makeCurrentFragment(homeFragment)
        makeCurrentFragment(perfilFragment)
        makeCurrentFragment(vagaFragment)
        makeCurrentFragment(minhasVagas)



        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_Vagas -> makeCurrentFragment(homeFragment)
                R.id.page_Perfil -> makeCurrentFragment(perfilFragment)
                R.id.page_Anunciar -> makeCurrentFragment(vagaFragment)
                R.id.page_MinhasVagas -> makeCurrentFragment(minhasVagas)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}
