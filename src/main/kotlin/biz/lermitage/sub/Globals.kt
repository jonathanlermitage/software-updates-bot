package biz.lermitage.sub

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

object Globals {

    const val SCRAPPER_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0"
    const val SCRAPPER_FOLLOW_REDIRECTS = true
    const val SCRAPPER_IGNORE_CONTENT_TYPE = true // prevent some 4xx errors

    /** JSON parser used to load and save reports. */
    fun gsonForReport(): Gson = GsonBuilder()
        .setPrettyPrinting()
        //.setDateFormat("YYYY/MM/dd:HH:mm:ss Z")
        .disableHtmlEscaping()
        .create()

    /** Charset used by reports. */
    fun reportCharset(): Charset = StandardCharsets.UTF_8
}
