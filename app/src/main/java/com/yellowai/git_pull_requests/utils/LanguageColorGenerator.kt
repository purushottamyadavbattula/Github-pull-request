package com.yellowai.git_pull_requests.utils

import android.graphics.Color
import java.util.*

class LanguageColorGenerator {
    companion object {
        private var languageColorMap: HashMap<String, Int> = HashMap<String, Int>()
        fun generateColorForLanguage(language: String): Int {
            return if (languageColorMap[language] != null) {
                languageColorMap[language]!!
            } else {
                val rnd: Random = Random()
                val color: Int =
                    Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                languageColorMap[language] = color
                color
            }
        }
    }
}