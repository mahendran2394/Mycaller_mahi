package com.mahi.mycaller

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.ActionBarDrawerToggle

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var phoneNumberTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        phoneNumberTextView = findViewById(R.id.tvPhoneNumber)

        setSupportActionBar(toolbar)

        // Drawer Toggle
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Navigation Drawer Item Clicks
        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_contacts -> {
                    Toast.makeText(this, "Opening Contacts", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ContactsActivity::class.java))
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_call_history -> {
                    Toast.makeText(this, "Opening Call History", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, CallHistoryActivity::class.java))
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }

        // Set up number buttons
        val numberButtons = listOf(
            R.id.dial_button_1, R.id.dial_button_2, R.id.dial_button_3,
            R.id.dial_button_4, R.id.dial_button_5, R.id.dial_button_6,
            R.id.dial_button_7, R.id.dial_button_8, R.id.dial_button_9,
            R.id.dial_button_0, R.id.dial_button_star, R.id.dial_button_hash
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener { button ->
                phoneNumberTextView.text = phoneNumberTextView.text.toString() + (button as Button).text
            }
        }

        // Delete Button
        findViewById<Button>(R.id.dial_button_delete).setOnClickListener {
            val currentText = phoneNumberTextView.text.toString()
            if (currentText.isNotEmpty()) {
                phoneNumberTextView.text = currentText.substring(0, currentText.length - 1)
            }
        }

        // Call Button
        findViewById<Button>(R.id.dial_button_call).setOnClickListener {
            val phoneNumber = phoneNumberTextView.text.toString()
            if (phoneNumber.isNotEmpty()) {
                val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
                startActivity(callIntent)
            } else {
                Toast.makeText(this, "Enter a phone number first", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up click listener for Call History button
        val option1Button: Button = findViewById(R.id.option1)
        option1Button.setOnClickListener {
            val intent = Intent(this, CallHistoryActivity::class.java)
            startActivity(intent)
        }

        // Set up click listener for Contacts button
        val option2Button: Button = findViewById(R.id.option2)
        option2Button.setOnClickListener {
            val intent = Intent(this, ContactsActivity::class.java)
            startActivity(intent)
        }
    }
}
