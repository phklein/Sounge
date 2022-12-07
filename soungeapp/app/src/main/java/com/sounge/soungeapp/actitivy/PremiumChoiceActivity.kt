package com.sounge.soungeapp.actitivy

import android.graphics.Color.blue
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.ActivityPremiumBinding
import com.sounge.soungeapp.databinding.ActivityPremiumChoiceBinding
import com.sounge.soungeapp.models.Offer

class PremiumChoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPremiumChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val offers: Array<Offer> = arrayOf(
            Offer("Plano Bronze", "plano mensal", "25", R.drawable.headphone),
            Offer("Plano Prata", "plano semestral", "100", R.drawable.violao_cantor),
            Offer("Plano Ouro", "plano anual", "150", R.drawable.discoteca)
        )

        binding.viewPager.adapter = OnboardingAdapter(offers)


    }

    private inner class OnboardingAdapter(val offers: Array<Offer>) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            //instancia um layout customizado que vai servir pra pegar propriedades dinamicas, assim como o
            //recycler view
            val view: View = layoutInflater.inflate(R.layout.offers_premium, container, false)

            with(offers[position]) {
                view.findViewById<TextView>(R.id.planosOffersText).text = plano
                view.findViewById<TextView>(R.id.tipoPlanosOffersText).text = tipoPlano
                view.findViewById<TextView>(R.id.valorPlanoPremiumText).text = valorPlano
                view.findViewById<ImageView>(R.id.imagemPlanosOffers).setImageResource(logo)

            }

            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            //destroy a view depois que movimenta o carrossel
            container.removeView(`object` as View)
        }


        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            //garante que a view que instanciei, é a mesma que está chegando

            return view == `object`
        }

        override fun getCount(): Int = offers.size
        //vai mostrar a quantidade de paginas dinamicas

    }
}