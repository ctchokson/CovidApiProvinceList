package com.example.mycollectioninkotlin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycollectioninkotlin.ui.theme.MyCollectionInKotlinTheme


data class Province(val iso: String,
    val name: String,
    val province:String,
    val lat:String,
    val long:String)



class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCollectionInKotlinTheme {
                DisplayInList( modifier = Modifier.fillMaxSize(), viewModel)
            }
        }
    }
}

@Composable
fun DisplayInList(modifier: Modifier = Modifier, viewModel: MainViewModel ) {
    val dataList  by viewModel.dataFlow.collectAsState(emptyList())
    val ctx = LocalContext.current
    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .padding(30.dp)
    ) {
        items(dataList) { item ->
                RowItem(province = item) {
                    val intent = Intent(ctx,DetailActivity::class.java).apply {
                        putExtra("iso",item.iso)
                        putExtra("name",item.name)
                        putExtra("province",item.province)
                        putExtra("lat", item.lat)
                        putExtra("long", item.long)
                    }
                    ctx.startActivity(intent)
                }

        }
    }

}


@Composable
fun RowItem(province: Province, onItemClick: (Province) -> Unit) {
    // Detect click and invoke the onItemClick lambda
    Box(
        modifier = Modifier
            .fillMaxWidth().padding(5.dp)
            .clickable { onItemClick(province) },
        contentAlignment = Alignment.Center

    ) {
        Text(text = province.province, fontSize = 25.sp, color = Color.Gray, fontWeight = FontWeight.Bold)    }
}
