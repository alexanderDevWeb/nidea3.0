package com.ipartek.nidea.ejemplos;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLParserJsoup {

	public static void main(String[] args) throws IOException {

		/*
		 * Document doc = Jsoup.connect("http://example.com/").get();
		 * String title = doc.title();
		 * 
		 * System.out.println("Titulo: " + title);
		 * 
		 * Elements anclas = doc.getElementsByTag("a");
		 * for (Element ancla : anclas) {
		 * String urlPagina = ancla.attr("href");
		 * Document doc2 = Jsoup.connect(urlPagina).get();
		 * System.out.println(doc2.title());
		 * }
		 */

		// String url = "http://192.168.0.15:8080/nidea/login";
		String url = "http://localhost:8080/nidea/login";

		Connection.Response response = Jsoup.connect(url).method(Connection.Method.POST).data("usuario", "admin")
				.data("password", "admin").execute();

		Document doc = response.parse();
		System.out.println(doc.getElementsByTag("h1").get(0).text());

	}

}
