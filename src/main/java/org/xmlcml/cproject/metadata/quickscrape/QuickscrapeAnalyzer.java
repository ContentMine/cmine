package org.xmlcml.cproject.metadata.quickscrape;

import java.io.File;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xmlcml.cproject.files.CProject;
import org.xmlcml.cproject.files.CTree;
import org.xmlcml.cproject.files.CTreeList;
import org.xmlcml.cproject.metadata.AbstractMDAnalyzer;
import org.xmlcml.graphics.html.HtmlElement;
import org.xmlcml.xml.XMLUtil;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import nu.xom.Node;

public class QuickscrapeAnalyzer extends AbstractMDAnalyzer {
	
	private static final Logger LOG = Logger.getLogger(QuickscrapeAnalyzer.class);
	static {
		LOG.setLevel(Level.DEBUG);
	}

	public QuickscrapeAnalyzer() {
	}

	private QuickscrapeAnalyzer(File directory) {
		this.setCProject(directory);
	}
	
	public QuickscrapeAnalyzer(CProject cProject) {
		this.setCProject(cProject);
	}


	public Multiset<String> getHTMLMetaTagNameSet() {
		Multiset<String> tagSet = HashMultiset.create();
		CTreeList cTreeList = getCTreeList();
		for (CTree cTree : cTreeList) {
			HtmlElement fulltextXHtml = cTree.getOrCreateFulltextXHtml();
			List<Node> metaTags = XMLUtil.getQueryNodes(fulltextXHtml, "//*[local-name()='meta']/@name");
			for (Node tag : metaTags) {
				tagSet.add(tag.getValue());
			}
		}
		return tagSet;
	}


}
