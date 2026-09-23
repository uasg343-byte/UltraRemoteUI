package com.example.ultraremoteui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class PlayerColor(val label: String, val tint: Color) {
    RED("RED", Color(0xFFE53935)),
    GREEN("GREEN", Color(0xFF43A047)),
    BLUE("BLUE", Color(0xFF1E88E5)),
    YELLOW("YELLOW", Color(0xFFF9A825))
}

private fun numberWord(n: Int) = when (n) {
    1 -> "One"; 2 -> "Two"; 3 -> "Three"
    4 -> "Four"; 5 -> "Five"; 6 -> "Six"
    else -> "—"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { UltraRemoteApp() }
    }
}

@Composable
fun UltraRemoteApp() {
    var selectedColor by remember { mutableStateOf(PlayerColor.BLUE) }
    var selectedNumber by remember { mutableStateOf<Int?>(null) }
    var ultraPlus by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Color(0xFF0B0D12),
            surface = Color(0xFF151922),
            primary = Color(0xFF7C5CFF)
        )
    ) {
        Surface(Modifier.fillMaxSize(), color = Color(0xFF0B0D12)) {
            Column(
                Modifier.fillMaxSize().padding(horizontal = 18.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Ultra Remote", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                Modifier.size(8.dp)
                                    .background(Color(0xFF38D67A), RoundedCornerShape(50))
                            )
                            Spacer(Modifier.width(7.dp))
                            Text("Ready", color = Color(0xFF9CA3AF), fontSize = 13.sp)
                        }
                    }
                    Text("UI MODE", color = Color(0xFF7C5CFF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(18.dp))

                Card(
                    Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF151922)),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("ULTRA PLUS+", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                if (ultraPlus) "Enabled" else "Disabled",
                                color = Color(0xFF9CA3AF)
                            )
                            Switch(checked = ultraPlus, onCheckedChange = { ultraPlus = it })
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))
                Text("SELECT COLOR", Modifier.fillMaxWidth(), color = Color(0xFF9CA3AF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PlayerColor.entries.forEach { color ->
                        ColorButton(
                            color = color,
                            selected = selectedColor == color,
                            onClick = { selectedColor = color },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                Card(
                    Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF151922)),
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Column(Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("CURRENT SELECTION", color = Color(0xFF9CA3AF), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(7.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(selectedColor.label, color = selectedColor.tint, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                            Text("  •  ", color = Color(0xFF555B68), fontSize = 20.sp)
                            Text(selectedNumber?.toString() ?: "—", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                        }
                        Spacer(Modifier.height(7.dp))
                        Text(
                            if (selectedNumber != null)
                                "Next - ${numberWord(selectedNumber!!)} ${selectedNumber!!}"
                            else "Select a number",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))
                Text("SELECT NUMBER", Modifier.fillMaxWidth(), color = Color(0xFF9CA3AF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 8.dp)
                ) {
                    items((1..6).toList()) { n ->
                        NumberButton(
                            number = n,
                            selected = selectedNumber == n,
                            onClick = { selectedNumber = n }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorButton(
    color: PlayerColor,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val alpha by animateFloatAsState(if (selected) 1f else 0.72f, label = "alpha")
    Box(
        modifier
            .height(48.dp)
            .scale(if (selected) 1f else 0.98f)
            .background(color.tint.copy(alpha = alpha), RoundedCornerShape(15.dp))
            .border(
                width = if (selected) 2.dp else 0.dp,
                color = Color.White.copy(alpha = if (selected) 0.9f else 0f),
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(color.label, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun NumberButton(number: Int, selected: Boolean, onClick: () -> Unit) {
    val bg by animateColorAsState(
        if (selected) Color(0xFF7C5CFF) else Color(0xFF171B24),
        label = "numberBg"
    )
    Box(
        Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(bg, RoundedCornerShape(18.dp))
            .border(
                1.dp,
                if (selected) Color(0xFFB8A7FF) else Color(0xFF252B37),
                RoundedCornerShape(18.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(number.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}
