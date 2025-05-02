import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stopwatchapp.TimerViewModel
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopwatchApp()
        }
    }
}

@Composable
fun StopwatchApp() {
    val timerViewModel: TimerViewModel = viewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D47A1))
    ) {
        // 👇 Background drawn first
        DottedBackground(modifier = Modifier.matchParentSize())

        // 👇 Foreground content drawn above
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Optional padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TimerDisplay(
                minutes = timerViewModel.minutes.intValue,
                seconds = timerViewModel.seconds.intValue,
                milliseconds = timerViewModel.milliseconds.intValue
            )

            TimerControls(
                onStart = { timerViewModel.startTimer() },
                onPause = { timerViewModel.pauseTimer() },
                onReset = { timerViewModel.resetTimer() }
            )
        }
    }
}


@Composable
fun DottedBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val dotRadius = 4f
        val spacing = 40f

        for (x in 0..size.width.toInt() step spacing.toInt()) {
            for (y in 0..size.height.toInt() step spacing.toInt()) {
                drawCircle(
                    Color(0xFFB3E5FC).copy(alpha = 0.25f),
                    radius = dotRadius,
                    center = Offset(x.toFloat(), y.toFloat())
                )
            }
        }
    }
}


@Composable
fun TimerDisplay(minutes: Int, seconds: Int, milliseconds: Int) {
    Card(
        modifier = Modifier
            .padding(24.dp)
            .width(260.dp)
            .shadow(20.dp, shape = RoundedCornerShape(16.dp), ambientColor = Color(0xFF00B0FF)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        border = BorderStroke(3.dp, Color(0xFF00B0FF)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF263238)
        )
    ) {
        Text(
            text = "%02d:%02d:%02d".format(minutes, seconds, milliseconds),
            style = MaterialTheme.typography.displaySmall.copy(
                color = Color.White,
                fontSize = 36.sp,
                letterSpacing = 2.sp
            ),
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 32.dp)
                .align(Alignment.CenterHorizontally)
        )
    }

}


@Composable
fun TimerControls(onStart: () -> Unit, onPause: () -> Unit, onReset: () -> Unit) {
    var selectedButton by remember { mutableStateOf("") }

    val activeColor = Color(0xFFD32F2F) // Red
    val defaultColor = Color(0xFF0288D1) // Blue

    Row(modifier = Modifier.padding(top = 50.dp)) {

        // Start Button
        Button(
            onClick = {
                onStart()
                selectedButton = "start"
            },
            shape = CircleShape,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedButton == "start") activeColor else defaultColor,
                contentColor = Color(0xFFE0F7FA)
            )
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Start", modifier = Modifier.size(32.dp))
        }

        // Pause Button
        Button(
            onClick = {
                onPause()
                selectedButton = "pause"
            },
            shape = CircleShape,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedButton == "pause") activeColor else defaultColor,
                contentColor = Color(0xFFE0F7FA)
            )
        ) {
            Icon(Icons.Default.Pause, contentDescription = "Pause", modifier = Modifier.size(32.dp))
        }

        // Reset Button
        Button(
            onClick = {
                onReset()
                selectedButton = "reset"
            },
            shape = CircleShape,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedButton == "reset") activeColor else defaultColor,
                contentColor = Color(0xFFE0F7FA)
            )
        ) {
            Icon(Icons.Default.Replay, contentDescription = "Reset", modifier = Modifier.size(32.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StopwatchApp()
}
