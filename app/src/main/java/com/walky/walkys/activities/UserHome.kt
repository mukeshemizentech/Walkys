package com.walky.walkys.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.walky.utils.Utils
import com.walky.walkys.R
import com.walky.walkys.databinding.ActivityUserHomeBinding
import com.walky.walkys.fragments.*
import com.yarolegovich.slidingrootnav.SlideGravity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder

class UserHome : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    lateinit var binding: ActivityUserHomeBinding
    private var slidingRootNav: SlidingRootNav? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.statusBarOverride(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_home)
        val widthRatio: Int = Utils.ratioOfScreen(this, 0.6f)
        Log.d("widthRatio_of_view", widthRatio.toString() + "")

        binding.header.titleTv.text = resources.getString(R.string.app_name)
        binding.header.backIv.setImageResource(R.drawable.home_menu)
        binding.header.optionMenu.setImageResource(R.drawable.add_post)

        binding.header.optionMenu.setOnClickListener {
            startActivity(Intent(this@UserHome, NewPostActivity::class.java))
        }
//        binding.header.backIv.setBackground(getResources().getDrawable(R.drawable.circle_white));
        //        binding.header.backIv.setBackground(getResources().getDrawable(R.drawable.circle_white));
        Utils.tintColor(this@UserHome, binding.header.optionMenu, R.color.white)


        /*  //===========if type is walky======
          if (Prefs.getBoolean(Constants.WalkerType, false)) {
              if (binding.drawerLayout.isDrawerOpen(binding.homeSideMenu.homeSideMenu)) {
                  binding.drawerLayout.closeDrawer(binding.homeSideMenu.homeSideMenu)
              } else {
                  binding.drawerLayout.openDrawer(binding.homeSideMenu.homeSideMenu)
              }
              binding.homeSideMenu.myBookingCons.setOnClickListener { view ->
                  startActivity(Intent(this@UserHome, MyRequestActivity::class.java))
                  binding.drawerLayout.closeDrawer(binding.homeSideMenu.homeSideMenu)
              }
              binding.homeSideMenu.myCommissionCons.setOnClickListener { view ->
                  startActivity(Intent(this@UserHome, MyCommissionActivity::class.java))
                  binding.drawerLayout.closeDrawer(binding.homeSideMenu.homeSideMenu)
              }
              binding.homeSideMenu.myProfileCons.setOnClickListener { view ->
                  startActivity(Intent(this@UserHome, ProfileActivity::class.java))
                  binding.drawerLayout.closeDrawer(binding.homeSideMenu.homeSideMenu)
              }
              binding.homeSideMenu.waklerSetting.setOnClickListener { view ->
                  startActivity(Intent(this@UserHome, SettingActivity::class.java))
                  binding.drawerLayout.closeDrawer(binding.homeSideMenu.homeSideMenu)
              }
              binding.homeSideMenu.homeCons.setOnClickListener { view ->
                  showFragment(HomeFragment())
                  binding.drawerLayout.closeDrawer(binding.homeSideMenu.homeSideMenu)
              }
              binding.homeSideMenu.userLL.setOnClickListener { view ->
                  Prefs.putBoolean(Constants.UserType, true)
                  Prefs.putBoolean(Constants.WalkerType, false)
                  binding.drawerLayout.closeDrawer(binding.homeSideMenu.homeSideMenu)
                  recreate()
              }
          } else {*/
        slidingRootNav = SlidingRootNavBuilder(this)
            .withDragDistance(widthRatio) //Horizontal translation of a view. Default == 180dp
            .withRootViewScale(0.70f) //Content view's scale will be interpolated between 1f and 0.7f. Default == 0.65f;
            .withRootViewElevation(10) //Content view's elevation will be interpolated between 0 and 10dp. Default == 8.
            .withRootViewYTranslation(4) //
            .withGravity(SlideGravity.LEFT)
            .withMenuLayout(R.layout.menu_user_left_drawer) // Content view's translationY will be interpolated between 0 and 4. Default ==
            .inject()

        findViewById<View>(R.id.userSetting).setOnClickListener {
            startActivity(Intent(this@UserHome, SettingActivity::class.java))
            slidingRootNav!!.closeMenu()
        }

        findViewById<View>(R.id.myProfileCons).setOnClickListener {
            showFragment(MyProfileFragment())
            binding.header.titleTv.text = resources.getString(R.string.my_profile)
            slidingRootNav!!.closeMenu()
        }
        findViewById<View>(R.id.walkysCons).setOnClickListener {
            showFragment(MapsFragment())
            binding.header.titleTv.text = resources.getString(R.string.app_name)
            slidingRootNav!!.closeMenu()

        }
        findViewById<View>(R.id.notificationCons).setOnClickListener{
            startActivity(Intent(this@UserHome,NotificationActivity::class.java))
            slidingRootNav!!.closeMenu()
        }
        findViewById<View>(R.id.petPassCons).setOnClickListener{
            startActivity(Intent(this@UserHome,PetPassActivity::class.java))
            slidingRootNav!!.closeMenu()
        }
        findViewById<View>(R.id.inviteCons).setOnClickListener{
            startActivity(Intent(this@UserHome,InviteActivity::class.java))
            slidingRootNav!!.closeMenu()
        }
        findViewById<View>(R.id.logoutCons).setOnClickListener {

            Utils.retryAlertDialog(this,resources.getString(R.string.app_name),"Are you sure to logout form the walky","Cancel","Ok") {
                startActivity(
                    Intent(
                        this@UserHome,
                        SplashActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
                finish()
            }
            slidingRootNav!!.closeMenu()
        }


//            findViewById<View>(R.id.walkerLL).setOnClickListener { view: View? ->
//                Prefs.putBoolean(Constants.UserType, false)
//                Prefs.putBoolean(Constants.WalkerType, true)
//                recreate()
//                slidingRootNav!!.closeMenu()
//            }
//        }
//        Log.d("user_type", Prefs.getBoolean(Constants.WalkerType, false).toString() + "")
//        Log.d("user_type", Prefs.getBoolean(Constants.UserType, false).toString() + "")

        binding.cons1.setOnClickListener {
            if (slidingRootNav!!.isMenuOpened) {
                slidingRootNav!!.closeMenu()
            }
        }
        binding.header.backIv.setOnClickListener {
            if (slidingRootNav!!.isMenuOpened) {
                slidingRootNav!!.closeMenu()
            } else {
                slidingRootNav!!.openMenu()
            }
        }

        binding.middleIv.setOnClickListener { view ->
            Utils.tintColor(this@UserHome, binding.homeBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.searchBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.chartBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.userBottom, R.color.grey_600)
            showFragment(MapsFragment())
            binding.header.titleTv.setTextColor(resources.getColor(R.color.white))
            Utils.tintColor(this@UserHome, binding.header.optionMenu, R.color.white)
            binding.header.titleTv.text = resources.getString(R.string.app_name)
        }

        Utils.tintColor(this@UserHome, binding.homeBottom, R.color.appThemeColor)
        Utils.tintColor(this@UserHome, binding.searchBottom, R.color.grey_600)
        Utils.tintColor(this@UserHome, binding.chartBottom, R.color.grey_600)
        Utils.tintColor(this@UserHome, binding.userBottom, R.color.grey_600)
        binding.header.titleTv.setTextColor(resources.getColor(R.color.white))

        binding.homeBottom.setOnClickListener { view ->
            Utils.tintColor(this@UserHome, binding.homeBottom, R.color.appThemeColor)
            Utils.tintColor(this@UserHome, binding.searchBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.chartBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.userBottom, R.color.grey_600)
            showFragment(HomeFragment())
            binding.header.titleTv.setTextColor(resources.getColor(R.color.white))
            Utils.tintColor(this@UserHome, binding.header.optionMenu, R.color.white)
            binding.header.titleTv.text = resources.getString(R.string.app_name)
            binding.header.searchCons.visibility = View.GONE
            binding.header.titleTv.visibility = View.VISIBLE
        }
        binding.searchBottom.setOnClickListener { view ->
            Utils.tintColor(this@UserHome, binding.homeBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.searchBottom, R.color.appThemeColor)
            Utils.tintColor(this@UserHome, binding.chartBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.userBottom, R.color.grey_600)
            showFragment(SearchFragment())
            binding.header.titleTv.setTextColor(resources.getColor(R.color.white))
            Utils.tintColor(this@UserHome, binding.header.optionMenu, R.color.white)
            binding.header.titleTv.text = resources.getString(R.string.search)
            binding.header.searchCons.visibility = View.VISIBLE
            binding.header.titleTv.visibility = View.GONE
        }
        binding.chartBottom.setOnClickListener { view ->
            Utils.tintColor(this@UserHome, binding.homeBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.searchBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.chartBottom, R.color.appThemeColor)
            Utils.tintColor(this@UserHome, binding.userBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.header.optionMenu, R.color.white)
            showFragment(ChatFragment())
            binding.header.titleTv.setTextColor(resources.getColor(R.color.white))
            Utils.tintColor(this@UserHome, binding.header.optionMenu, R.color.white)
            binding.header.titleTv.text = resources.getString(R.string.chat)
            binding.header.searchCons.visibility = View.VISIBLE
            binding.header.titleTv.visibility = View.GONE
        }
        binding.userBottom.setOnClickListener { view ->
            Utils.tintColor(this@UserHome, binding.homeBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.searchBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.chartBottom, R.color.grey_600)
            Utils.tintColor(this@UserHome, binding.userBottom, R.color.appThemeColor)
            binding.header.titleTv.text = "Jaccia Montaina"
            binding.header.searchCons.visibility = View.GONE
            binding.header.titleTv.visibility = View.VISIBLE
//            showFragment(UserProfileFragment())
//            if (Prefs.getBoolean(Constants.UserType, false)) {

//            } else
            showFragment(MyProfileFragment())
        }

        showFragment(HomeFragment())
    }

    private fun showFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.mainContainer, fragment).commit()
    }
}