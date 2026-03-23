package com.mivanba.catsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.VerticalAlign
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import coil3.compose.AsyncImage
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mivanba.catsapp.navigation.AppNavigation
import com.mivanba.catsapp.navigation.DrawerItem
import com.mivanba.catsapp.navigation.NavItem
import com.mivanba.catsapp.ui.theme.CatsAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())

        setContent {
            CatsAppTheme {
                val snackBarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

                MyModalDrawer(drawerState) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding(),
                        topBar = { MyTopAppBar { scope.launch { drawerState.open()  } } },
                        snackbarHost = {
                            SnackbarHost(hostState = snackBarHostState)
                        },
                        floatingActionButton = { MyFab() },
                        floatingActionButtonPosition = FabPosition.Center,
                        bottomBar = { MyNavigationBar() }
                    ) { innerPadding ->
                        // AppNavigation(modifier = Modifier.padding(innerPadding))
                        // Test(modifier = Modifier.padding(innerPadding))
                        Column(modifier = Modifier
                            .padding(innerPadding)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(Color.Red)
                                    .clickable {
                                        scope.launch {
                                            val result = snackBarHostState.showSnackbar(
                                                "Ejemplo",
                                                actionLabel = "Aceptar",
                                                withDismissAction = true,
                                                duration = SnackbarDuration.Short
                                            )

                                            if (result == SnackbarResult.ActionPerformed) {
                                                Log.i("Snackbar", "ActionPerformed")
                                            } else {
                                                Log.i("Snackbar", "Dismissed")
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Vista de prueba", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun MyModalDrawer(drawerState: DrawerState, content: @Composable () -> Unit) {
        var selectedOption by remember { mutableIntStateOf(0) }
        val items = listOf<DrawerItem>(
            DrawerItem("Home", Icons.Default.Home, 3),
            DrawerItem("Fav", Icons.Default.Favorite, 0),
            DrawerItem("Build", Icons.Default.Build, 9),
            DrawerItem("Call", Icons.Default.Call, 0),
            DrawerItem("Location", Icons.Default.LocationOn, 3)
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerShape = RoundedCornerShape(
                        topEnd = 20.dp,
                        bottomEnd = 20.dp
                    )
                ) {
                    Text("Drawer title", modifier = Modifier.padding(16.dp))
                    Spacer(Modifier.height(12.dp))
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            label = { Text(item.title) },
                            selected = index == selectedOption,
                            onClick = { selectedOption = index },
                            icon = {Icon(Icons.Default.Star, contentDescription = "",)},
                            badge = {
                                if (item.notifications > 0) {
                                    Badge(
                                        containerColor = if (selectedOption == index) Color.White else Color.Red,
                                        contentColor = if (selectedOption == index) Color.Red else Color.White
                                    ) { Text("${item.notifications}") }
                                }},
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color.Gray,
                                selectedTextColor = Color.White,
                                selectedIconColor = Color.White,
                                selectedBadgeColor = Color.White,
                                unselectedContainerColor = Color.White,
                                unselectedTextColor = Color.Black,
                                unselectedIconColor = Color.Black,
                                unselectedBadgeColor = Color.Black
                            )
                        )
                    }
                }
            },
            scrimColor = Color.DarkGray
        ) {
            content()
        }
    }

    @Composable
    fun MyTopAppBar(modifier: Modifier = Modifier, onNavSelected: () -> Unit) {
        TopAppBar(
            title = { Text("Cats App")},
            navigationIcon = {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "",
                    modifier = Modifier.clickable { onNavSelected() }
                )
            },
            actions = {
                Icon(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                )
                Icon(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.DarkGray,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )
    }

    @Composable
    fun MyFab(modifier: Modifier = Modifier) {
        FloatingActionButton(
            onClick = {},
            shape = CircleShape,
            containerColor = Color.Gray,
            contentColor = Color.White,
            modifier = Modifier.size(80.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = ""
            )
        }
    }

    @Composable
    fun MyNavigationBar(modifier: Modifier = Modifier) {
        val itemList = listOf(
            NavItem("Home", Icons.Default.Home),
            NavItem("Settings", Icons.Default.Settings),
        )

        var selectedIndex by remember { mutableIntStateOf(0) }

        NavigationBar(
            containerColor = Color.DarkGray,
            contentColor = Color.White,
            modifier = Modifier.height(80.dp)
        ) {
            itemList.forEachIndexed { index, item ->
                MyNavigationItem(item, isSelected = index == selectedIndex) {
                    selectedIndex = index
                }
            }
        }
    }

    @Composable
    fun RowScope.MyNavigationItem(navItem: NavItem, isSelected: Boolean, onClick: () -> Unit) {
        NavigationBarItem(
            selected = isSelected,
            onClick = { onClick() },
            icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.name) },
            label = { Text(navItem.name) },
            alwaysShowLabel = true,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                indicatorColor = Color.Gray,

                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,

                disabledIconColor = Color.Gray,
                disabledTextColor = Color.Black
            )
        )
    }

    @Composable
    fun TestLoading(modifier: Modifier = Modifier) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                R.raw.ai_loading_model
            )
        )
        Column(modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
        }
    }
}