package com.sounge.soungeapp.actitivy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.sounge.soungeapp.R
import com.sounge.soungeapp.databinding.ActionBarEditBinding.inflate
import com.sounge.soungeapp.databinding.ActivityPremiumChoiceBinding
import com.sounge.soungeapp.databinding.FormularioPremiumConfirmLayoutBinding
import com.sounge.soungeapp.databinding.FormularioPremiumLayoutBinding
import com.sounge.soungeapp.models.Offer

class PremiumChoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPremiumChoiceBinding
    private lateinit var dialog: AlertDialog

    val offers: Array<Offer> = arrayOf(
        Offer("Plano Bronze", "plano mensal", "25", R.drawable.headphone),
        Offer("Plano Prata", "plano semestral", "100", R.drawable.cassetteplayer),
        Offer("Plano Ouro", "plano anual", "150", R.drawable.jazz)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        addDots(offers.size)
        binding.viewPager.adapter = OnboardingAdapter(offers)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                addDots(offers.size, position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        botaoConfirm()

    }

    private fun testeDialog() {
        val build = AlertDialog.Builder(this)
            .setView(R.layout.formulario_premium_confirm_layout)

        val dialogBinding: FormularioPremiumConfirmLayoutBinding =
            FormularioPremiumConfirmLayoutBinding
                .inflate(LayoutInflater.from(this))

        dialogBinding.closeButtonFormPremiumConfirm.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.buttonFormPremiumConfirm.setOnClickListener {
            dialog.dismiss()
        }

        build.setView(dialogBinding.root)
        dialog = build.create()
        dialog.show()
    }

    private fun dialogConfirmacaoPremium() {

        val build = AlertDialog.Builder(this)
            .setView(R.layout.formulario_premium_layout)

        val dialogBinding: FormularioPremiumLayoutBinding = FormularioPremiumLayoutBinding
            .inflate(LayoutInflater.from(this))

        dialogBinding.closeButtonFormPremium.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.confirmarButtonFormPremium.setOnClickListener {
            dialog.dismiss()
            testeDialog()
        }

        build.setView(dialogBinding.root)
        dialog = build.create()
        dialog.show()

    }

    private fun botaoConfirm() {

        binding.buttonPremiumPlanos.setOnClickListener {
            dialogConfirmacaoPremium()
        }
    }

    private fun addDots(size: Int, position: Int = 0) {
        val dots = findViewById<LinearLayout>(R.id.dots)
        dots.removeAllViews()
        Array(size) {
            val textView = TextView(baseContext).apply {
                text = getText(R.string.dotted)
                textSize = 35f
                setTextColor(
                    if (position == it) {
                        ContextCompat.getColor(baseContext, R.color.main_purple)
                    } else {
                        ContextCompat.getColor(baseContext, android.R.color.darker_gray)
                    }
                )
            }
            dots.addView(textView)
        }
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