package com.bootme.post.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PostUtils {

    private PostUtils() {
    }

    private static final int EXCERPT_LENGTH = 200;

    public static String getContentExcerpt(String content) {
        if ("<p><br></p>".equals(content)) {
            return "";
        }

        Document doc = Jsoup.parse(content);
        Elements iframeTags = doc.getElementsByTag("iframe");
        Elements videoTags = doc.getElementsByTag("video");
        Elements imgTags = doc.getElementsByTag("img");

        String firstIframe;
        if (!iframeTags.isEmpty()) {
            firstIframe = iframeTags.first().toString();
            return firstIframe;
        }

        String firstVideo;
        if (!videoTags.isEmpty()) {
            firstVideo = videoTags.first().toString();
            return firstVideo;
        }

        String firstImg;
        if (!imgTags.isEmpty()) {
            firstImg = imgTags.first().toString();
            return firstImg;
        }

        if (content.length() <= EXCERPT_LENGTH) {
            return content;
        } else {
            return content.substring(0, EXCERPT_LENGTH) + "...";
        }
    }

}
