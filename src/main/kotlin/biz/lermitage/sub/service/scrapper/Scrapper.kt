package biz.lermitage.sub.service.scrapper

import org.jsoup.nodes.Element

interface Scrapper {

    /**
     * Fetch remote content as HTML element. Javascript code is executed and element updated.
     * Suits for HTML scrapping.
     */
    fun fetchHtml(url: String): Element

    /**
     * Get remote content as text. Javascript code is not executed.
     * Suits for API fetching.
     */
    fun fetchText(url: String): String
}
