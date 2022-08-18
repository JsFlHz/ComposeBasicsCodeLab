package com.example.composebasicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebasicscodelab.ui.theme.ComposeBasicsCodeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicsCodeLabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(){
    var shouldShowOnboarding by rememberSaveable {
        mutableStateOf(true)
    }
    if(shouldShowOnboarding){
        OnboardingScreen(
            onContinueClicked = {shouldShowOnboarding = false}
        )
    }else{
        Gretings()
    }
}
@Composable
private fun Gretings( names:List<String> = List(1000){"$it"}){
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)){
       items(names){ name ->
           Greeting(name = name)
       }
    }
}
@Composable
private fun Greeting(name:String){
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ){
        CardContent(name)
    }
}

@Composable
fun CardContent(name: String) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Row (
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif
                )
            )
            if(expanded){
                Text(text = ("Compose ipsum color sit " +
                        "lazy padding theme elit, sed so bouncy.").repeat(4)
                )
            }
        }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if(expanded){
                        stringResource(id = R.string.app_name)
                    }else{
                        stringResource(id = R.string.app_name)
                    }
                )
            }
        }
}

@Composable
fun CardContentOld(name: String) {
    val expanded = remember {
        mutableStateOf(false)
    }
    val  extraPadding by animateDpAsState(
        if (expanded.value) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness =Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp
            )
        ){
        Row(modifier = Modifier.padding(24.dp)) {
            Column (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(extraPadding.coerceAtLeast(0.dp))
            ){
                Text(
                    text = "Hello",
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text( text = name, style = MaterialTheme.typography.h4)
            }
            OutlinedButton(
                modifier = Modifier.align( Alignment.CenterVertically),
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if(expanded.value)"Show less" else "Show more")
            }
        }
    }
}

@Composable
fun OnboardingScreen( onContinueClicked :() -> Unit){
    //TodoL this  statet be hostied
    Surface{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Wellcome to Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ){
                Text("Continue...")
            }
        }
    }

}
 @Preview(
     showBackground =  true,
     widthDp = 320,
     heightDp = 320,
     uiMode = UI_MODE_NIGHT_YES,
 )
 @Composable
 fun OnboardingPreview(){
     ComposeBasicsCodeLabTheme {
         OnboardingScreen( onContinueClicked = {})
     }
 }

@Preview(
    showBackground =  true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Preview(
    showBackground =  true,
)
@Composable
fun GretingsPreview(){
    ComposeBasicsCodeLabTheme {
        Gretings()
    }
}
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeBasicsCodeLabTheme {
       MyApp()
    }
}
