package main.utils;

import main.domain.JD;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬虫测试类
 */
public class JtmlUtil {
    public static void main(String[] args) throws IOException {
        List<JD> java = new JtmlUtil().JD("crocs");
        System.out.println(java);
    }

    public List<JD> JD(String keyword) throws IOException {
        String url ="https://search.jd.com/Search?keyword="+keyword;
        Document parse = Jsoup.parse(new URL(url),30000);
        Element elements = parse.getElementById("J_goodsList");
        //System.out.println(elements.html());
        Elements elementsByTag = elements.getElementsByTag("li");
        List<JD> list = new ArrayList<>();

        for (Element element:elementsByTag) {
            String img = element.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = element.getElementsByClass("p-price").eq(0).text();
            String name = element.getElementsByClass("p-name p-name-type-2").eq(0).text();

            JD jd = new JD();
            jd.setName(name);
            jd.setPrice(price);
            jd.setImagUrl(img);
            list.add(jd);
        }
        return list;
    }
}
