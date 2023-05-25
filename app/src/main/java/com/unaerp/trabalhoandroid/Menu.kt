package com.unaerp.trabalhoandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.unaerp.trabalhoandroid.databinding.MenuNavBinding
import com.unaerp.trabalhoandroid.fragments.AnunciarVagaFragment
import com.unaerp.trabalhoandroid.fragments.MinhasVagasFragment
import com.unaerp.trabalhoandroid.fragments.PainelVagasFragment
import com.unaerp.trabalhoandroid.fragments.PerfilEmpresaFragment

class Menu : AppCompatActivity() {
    private lateinit var binding: MenuNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mensagem = intent.getStringExtra("mensagem")

        val homeFragment = PainelVagasFragment()
        val perfilFragment = PerfilEmpresaFragment()
        val vagaFragment = AnunciarVagaFragment()
        val minhasVagasFragment = MinhasVagasFragment()

        // Configura o listener para o bottomNavigationView
        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_Vagas -> makeCurrentFragment(homeFragment)
                R.id.page_Perfil -> makeCurrentFragment(perfilFragment)
                R.id.page_Anunciar -> makeCurrentFragment(vagaFragment)
                R.id.page_MinhasVagas -> makeCurrentFragment(minhasVagasFragment)
            }
            true
        }

        // Define o fragment inicial como homeFragment
        makeCurrentFragment(homeFragment)

        if(mensagem != null){
            Toast.makeText(
                baseContext,
                mensagem,
                Toast.LENGTH_SHORT,
            ).show()


        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}

