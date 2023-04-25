package com.unaerp.trabalhoandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unaerp.trabalhoandroid.fragments_estagio.AnunciosFragment
import com.unaerp.trabalhoandroid.fragments_estagio.PerfilEstagiarioFragment

class MenuEstagiario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_estagiario)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_estagio)

        val homeFragmentEstagiario = AnunciosFragment()
        val perfilEstagiarioFragmentEstagiario = PerfilEstagiarioFragment()

        // Define o fragment inicial como homeFragmentEstagiario
        makeCurrentFragment(homeFragmentEstagiario)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_Vagas_estagio -> makeCurrentFragment(homeFragmentEstagiario)
                R.id.page_Perfil_estagio -> makeCurrentFragment(perfilEstagiarioFragmentEstagiario)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.containerEstagio, fragment).commit()
    }
}

