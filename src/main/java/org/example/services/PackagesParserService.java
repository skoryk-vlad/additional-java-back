package org.example.services;

import org.example.exceptions.NotFoundException;
import org.example.models.NpmPackage;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PackagesParserService {
    private final String url = "https://www.npmjs.com/package/";

    public NpmPackage parseByName(String name) {
        NpmPackage npmPackage = new NpmPackage();

        try {
            Document doc = Jsoup.connect(url + name).get();

            Elements nameElement = doc.getElementsByAttributeValue("class", "_50685029 truncate");
            npmPackage.setName(nameElement.text());

            Elements elements = doc.getElementsByAttributeValueContaining("class", "_702d723c dib w-50 fl bb b--black-10 pr2");
            for (Element element: elements) {
                String blockName = element.getElementsByAttributeValueContaining("class", "c84e15be f5 mt2 pt2 mb0").text();

                if (blockName.trim().equalsIgnoreCase("Version")) {
                    String version = element.getElementsByAttributeValue("class", "f2874b88 fw6 mb3 mt2 truncate black-80 f4").text();
                    npmPackage.setVersion(version);
                }
                if (blockName.trim().equalsIgnoreCase("Unpacked Size")) {
                    String size = element.getElementsByAttributeValue("class", "f2874b88 fw6 mb3 mt2 truncate black-80 f4").text();
                    npmPackage.setSize(size);
                }
                if (blockName.trim().equalsIgnoreCase("Last publish")) {
                    String lastPublish = element.getElementsByAttributeValue("class", "f2874b88 fw6 mb3 mt2 truncate black-80 f4").text();
                    npmPackage.setLastPublish(lastPublish);
                }
                if (blockName.trim().equalsIgnoreCase("DownloadsWeekly Downloads")) {
                    String weeklyDownloads = element.getElementsByAttributeValue("class", "_9ba9a726 f4 tl flex-auto fw6 black-80 ma0 pr2 pb1").text();
                    String onlyNumbers = weeklyDownloads.replaceAll("[^0-9]", "");
                    npmPackage.setWeeklyDownloads(Integer.parseInt(onlyNumbers));
                }
            }
        } catch (HttpStatusException e) {
            throw new NotFoundException();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return npmPackage;
    }
}
