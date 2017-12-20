package com.chrhc.projects.hotline.common;

import java.util.regex.Pattern;

public class HTMLUtils {

	private static final Pattern HTML_CHECKER = Pattern
			.compile("</[a-z][^>]*>|<[a-z][^/>]*/>");

	public static String toText(String html) {

		if (html == null)
			return null;

		boolean is = isHtml(html);

		if (is) {
			html = html
					.replaceAll("\r?\n", "")
					.replaceAll("<!DOCTYPE[\\d\\D]+?>",	"")
					.replaceAll(
							"<[sS][tT][yY][lL][eE] [^>]+>[\\d\\D]+?</[sS][tT][yY][lL][eE]>",
							"")
					.replaceAll(
							"<[sS][tT][yY][lL][eE]>[\\d\\D]+?</[sS][tT][yY][lL][eE]>",
							"")
					.replaceAll(
							"<[sS][cC][rR][iI][pP][tT] [^>]+>[\\d\\D]+?</[sS][cC][rR][iI][pP][tT]>",
							"")
					.replaceAll(
							"<[sS][cC][rR][iI][pP][tT]>[\\d\\D]+?</[sS][cC][rR][iI][pP][tT]>",
							"")
					.replaceAll(
							"<[tT][aA][bB][lL][eE] [^>]+>[\\d\\D]+?</[tT][aA][bB][lL][eE]>",
							"")
					.replaceAll(
							"<[tT][aA][bB][lL][eE]>[\\d\\D]+?</[tT][aA][bB][lL][eE]>",
							"")
					.replaceAll("\n*(<(?!\\s)[^>\n]+>)\n*", "$1")
					.replaceAll(
							"(</?[bB][rR]\\s*+/?[^>]*+>|</?[pP][^>]*+>|</[dD][iI][vV][^>]*+>|</[lL][iI][^>]*+>)\\s*+",
							"\n");
		}

		return html = html.replaceAll("</?(?!\\s)[a-zA-Z][^>]*>", "")
				.replaceAll("<!--[\\d\\D]*?-->", "").replaceAll("\n{2,}", "\n");

	}

	public static boolean isHtml(String html) {
		return HTML_CHECKER.matcher(html).find() || html.startsWith("<!DOCTYPE");
	}
}
