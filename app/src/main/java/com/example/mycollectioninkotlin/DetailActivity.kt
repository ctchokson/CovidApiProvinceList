package com.example.mycollectioninkotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycollectioninkotlin.ui.theme.MyCollectionInKotlinTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCollectionInKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CenterAlignedTopAppBarExample()
                    DisplayDetails(extraDetail(intent))
                }
            }
        }
    }
}

private fun extraDetail(intent: Intent): Province{
    val iso = intent.getStringExtra("iso") ?: "iso"
    val name = intent.getStringExtra("name") ?: "name"
    val province = intent.getStringExtra("province") ?: ""
    val lat = intent.getStringExtra("lat") ?: ""
    val long = intent.getStringExtra("long") ?: ""

    return Province(iso, name, province,lat, long)

}

@Composable
fun DisplayDetails(province: Province, modifier: Modifier = Modifier) {
    Column {
        Spacer(modifier = Modifier.height(80.dp))
        Text(text = province.iso, modifier.align(Alignment.CenterHorizontally), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        // Add a vertical space between the author and message texts
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = province.name,modifier.align(Alignment.CenterHorizontally),fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = province.province,modifier.align(Alignment.CenterHorizontally), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = province.lat,modifier.align(Alignment.CenterHorizontally), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = province.long,modifier.align(Alignment.CenterHorizontally), fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopAppBarExample() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Detail about province",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        },
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyCollectionInKotlinTheme {
    }
}