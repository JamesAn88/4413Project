package com.foodrus.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.foodrus.bean.CrumbDetail;
import com.foodrus.util.IOHelper;

public class BreadCrumbs extends SimpleTagSupport{
	// *** constants
	public static final String FORMAT = "<a href='%s'>%s</a> &nbsp;&gt;&gt;&nbsp;";
	// *** attributes
	private CrumbDetail crumb;
	
	@Override
	public void doTag() throws IOException{
		this.getJspContext().getOut().write(buildPath());
	}

	private String buildPath() {
		List<String> path = new ArrayList<String>();
		buildPath(crumb, path);
		StringBuffer sb = new StringBuffer(IOHelper.listToString(path));
		return sb.substring(0, sb.lastIndexOf("&gt;&gt;")).toString();
	}

	private String buildPath(CrumbDetail crumbDetail, List<String> path) {
		if(crumbDetail != null){
			if(crumbDetail.getCrumbUrl() != null && crumbDetail.getCrumbName() != null){
				path.add(String.format(FORMAT, crumbDetail.getCrumbUrl(), crumbDetail.getCrumbName()));
			}
			buildPath(crumbDetail.getNext(), path);
		}
		return "";
	}

	public CrumbDetail getCrumbs() {
		return crumb;
	}

	public void setCrumbs(CrumbDetail crumb) {
		this.crumb = crumb;
	}
}
