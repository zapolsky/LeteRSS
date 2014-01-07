package com.zapolsky.leterss;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.util.Log;

public class RssParse {

	private String titleName;
	private String descText;
	private Date publicationDate;
	private String link;
	private String imgLink;


	public RssParse(String titleName, String descText, Date publicationDate,
			String link, String imgLink) {
		this.descText = descText;
		this.imgLink = imgLink;
		this.link = link;
		this.publicationDate = publicationDate;
		this.titleName = titleName;
	}

	public String getTitleName() {
		return titleName;
	}

	public String getDescText() {
		return descText;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public String getLink() {
		return link;
	}

	public String getImgLink() {
		return imgLink;
	}

	public static ArrayList<RssParse> getParse(String url) {

		ArrayList<RssParse> rssParse = new ArrayList<RssParse>();

		try {
			URL uri = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) uri
					.openConnection();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();

				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder docbild = dbf.newDocumentBuilder();

				org.w3c.dom.Document doc = docbild.parse(inputStream);
				org.w3c.dom.Element docelement = doc.getDocumentElement();

				NodeList nodeList = docelement.getElementsByTagName("item");

				if (nodeList.getLength() > 0 && nodeList != null) {
					for (int i = 0; i < nodeList.getLength(); i++) {
						Element entry = (Element) nodeList.item(i);

						Element titleElement = (Element) entry
								.getElementsByTagName("title").item(0);
						Element descriptionElement = (Element) entry
								.getElementsByTagName("description").item(0);
						Element publicationDateElement = (Element) entry
								.getElementsByTagName("pubDate").item(0);
						Element linkElement = (Element) entry
								.getElementsByTagName("link").item(0);
						Element imgLinkElement = (Element) entry
								.getElementsByTagName("enclosure").item(0);

						String _titleName = titleElement.getFirstChild()
								.getNodeValue();
						String _descText = descriptionElement.getFirstChild()
								.getNodeValue();
						@SuppressWarnings("deprecation")
						Date _publicationDate = new Date(publicationDateElement
								.getFirstChild().getNodeValue());
						String _link = linkElement.getFirstChild()
								.getNodeValue();
						String _imgLink = imgLinkElement.getAttribute("url");

						RssParse rss = new RssParse(_titleName, _descText,
								_publicationDate, _link, _imgLink);

						rssParse.add(rss);

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.w("Exeption",e);
		}

		return rssParse;

	}
	@SuppressLint("SimpleDateFormat")
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/DD hh:mm");
		String date = sdf.format(publicationDate);
		return date + " " + titleName;
	}
}
