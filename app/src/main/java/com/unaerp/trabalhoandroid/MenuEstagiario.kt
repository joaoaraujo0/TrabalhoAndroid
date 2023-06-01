package com.unaerp.trabalhoandroid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.unaerp.trabalhoandroid.databinding.MenuEstagiarioBinding
import com.unaerp.trabalhoandroid.fragments_estagio.AnunciosFragment
import com.unaerp.trabalhoandroid.fragments_estagio.PerfilEstagiarioFragment

class MenuEstagiario : AppCompatActivity() {
    private lateinit var binding: MenuEstagiarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuEstagiarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mensagem = intent.getStringExtra("mensagem")

        val homeFragmentEstagiario = AnunciosFragment()
        val perfilEstagiarioFragmentEstagiario = PerfilEstagiarioFragment()



        binding.bottomNavigationEstagio.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_Vagas_estagio -> makeCurrentFragment(homeFragmentEstagiario)
                R.id.page_Perfil_estagio -> makeCurrentFragment(perfilEstagiarioFragmentEstagiario)
            }
            true
        }
        // Define o fragment inicial como homeFragmentEstagiario
        makeCurrentFragment(homeFragmentEstagiario)

        if(mensagem != null){
            Toast.makeText(
                baseContext,
                mensagem,
                Toast.LENGTH_SHORT,
            ).show()
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.containerEstagio, fragment).commit()
    }
}

