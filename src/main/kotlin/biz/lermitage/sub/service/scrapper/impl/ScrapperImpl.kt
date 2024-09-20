package biz.lermitage.sub.service.scrapper.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.service.scrapper.Scrapper
import org.apache.commons.io.IOUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import java.io.IOException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.ConcurrentHashMap

@Service
class ScrapperImpl : Scrapper {

    val fetchFinalCache = ConcurrentHashMap<String, Element>()
    val fetchSimpleTextCache = ConcurrentHashMap<String, String>()

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(IOException::class)
    @Retryable(maxAttempts = 3, backoff = Backoff(value = 10_000), include = [IOException::class])
    override fun fetchHtml(url: String, executeJS: Boolean): Element {
        if (fetchFinalCache.containsKey(url)) {
            logger.info("[ * ] reading from Element cache $url")
            return fetchFinalCache[url]!!
        }
        Thread.sleep(1000)
        logger.info("[*  ] fetching $url")
        if (executeJS) {
            val execute = Jsoup.connect(url)
                .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
                .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
                .userAgent(Globals.SCRAPPER_USER_AGENT)
                .execute()
            //logger.info("final url is =${execute.url()}")
            val res = execute.parse().body()
            fetchFinalCache[url] = res
            return res
        } else {
            val text = downloadAsText(url)
            val res = Jsoup.parse(text, url)
            fetchFinalCache[url] = res
            return res
        }
    }

    @Throws(IOException::class)
    @Retryable(maxAttempts = 3, backoff = Backoff(delay = 10_000), include = [IOException::class])
    override fun fetchText(url: String): String {
        if (fetchSimpleTextCache.containsKey(url)) {
            logger.info("[ * ] reading from Text cache $url")
            return fetchSimpleTextCache[url]!!
        }
        Thread.sleep(1000)
        logger.info("[*  ] fetching $url")
        val res = downloadAsText(url)
        fetchSimpleTextCache[url] = res
        return res
    }

    private fun downloadAsText(url: String): String {
        return IOUtils.toString(URL(url), StandardCharsets.UTF_8)
    }
}
