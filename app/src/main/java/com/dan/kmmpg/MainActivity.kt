package com.dan.kmmpg


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ConversionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this as FragmentActivity).get(ConversionViewModel::class.java)

        // Action bar
        // Setup Action Bar
        val ab = setSupportActionBar(findViewById(R.id.main_toolbar))


        val kilometersView = findViewById<EditText>(R.id.kilometers)
        val milesView = findViewById<EditText>(R.id.miles)
        val litersView = findViewById<EditText>(R.id.liters)
        val gallonsView = findViewById<EditText>(R.id.gallons)
        val kplView = findViewById<TextView>(R.id.kpl)
        val mpgView = findViewById<TextView>(R.id.mpg)

        // Edit Text Events
        val listeners  = mapOf<EditText, EditTextWatcher>(
            kilometersView to EditTextWatcher(InputType.Kilometers, viewModel),
            milesView to EditTextWatcher(InputType.Miles, viewModel),
            litersView to EditTextWatcher(InputType.Liters, viewModel),
            gallonsView to EditTextWatcher(InputType.Gallons, viewModel)
        )
        listeners.forEach {
            it.key.addTextChangedListener(it.value)
            it.key.setSelectAllOnFocus(true)
        }

        // Bindings
        viewModel.kilometers.observe(this, Observer<Double> {
            if (!kilometersView.isFocused) {
                kilometersView.setText(viewModel.formatNumber(it))
            }
        })
        viewModel.miles.observe(this, Observer<Double> {
            if (!milesView.isFocused) {
                milesView.setText(viewModel.formatNumber(it))
            }
        })
        viewModel.liters.observe(this, Observer<Double> {
            if (!litersView.isFocused) {
                litersView.setText(viewModel.formatNumber(it))
            }
        })
        viewModel.gallons.observe(this, Observer<Double> {
            if (!gallonsView.isFocused) {
                gallonsView.setText(viewModel.formatNumber(it))
            }
        })
        viewModel.kilometersPerLiter.observe(this, Observer<String> {
            kplView.text = "Kilometers Per Liter: $it"
        })
        viewModel.milesPerGallon.observe(this, Observer<String> {
            mpgView.text = "Miles Per Gallon: $it"
        })
        viewModel.clearValues.observe(this, Observer<Boolean> {
            if (it) {
                kilometersView.setText("")
                milesView.setText("")
                litersView.setText("")
                gallonsView.setText("")
            }
        })

    }

    // Options Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_clear_field -> viewModel.clearVals()
        }

        return super.onOptionsItemSelected(item)
    }
}

class EditTextWatcher(private val it: InputType, private val vm: ConversionViewModel): TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        when(it) {
            InputType.Kilometers -> vm.setKilometers(s.toString().toDoubleOrNull())
            InputType.Gallons -> vm.setGallons(s.toString().toDoubleOrNull())
            InputType.Liters -> vm.setLiters(s.toString().toDoubleOrNull())
            InputType.Miles -> vm.setMiles(s.toString().toDoubleOrNull())
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}
