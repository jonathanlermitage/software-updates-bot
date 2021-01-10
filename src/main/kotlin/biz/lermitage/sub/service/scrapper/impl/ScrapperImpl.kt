package biz.lermitage.sub.service.scrapper.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.service.scrapper.Scrapper
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

@Service
class ScrapperImpl : Scrapper {

    val fetchFinalCache = ConcurrentHashMap<String, Element>()
    val fetchSimpleTextCache = ConcurrentHashMap<String, String>()

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Throws(IOException::class)
    @Retryable(maxAttempts = 3, backoff = Backoff(value = 10_000), include = [IOException::class])
    override fun fetchHtml(url: String): Element {
        Thread.sleep(1000)
        if (fetchFinalCache.containsKey(url)) {
            logger.info("reading from cache $url")
            return fetchFinalCache[url]!!
        }
        logger.info("fetching $url")
        val res = Jsoup.connect(url)
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .execute().parse().body()
        fetchFinalCache[url] = res
        return res
    }

    @Throws(IOException::class)
    @Retryable(maxAttempts = 3, backoff = Backoff(delay = 10_000), include = [IOException::class])
    override fun fetchText(url: String): String {
        Thread.sleep(1000)
        if (fetchSimpleTextCache.containsKey(url)) {
            logger.info("reading from cache $url")
            return fetchSimpleTextCache[url]!!
        }
        logger.info("fetching $url")
        val res = Jsoup.connect(url)
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .get().text()
        fetchSimpleTextCache[url] = res
        return res
    }
}
