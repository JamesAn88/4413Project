package com.foodrus.bean;


public class CrumbDetail implements DomainBean{
	// *** serial number
	private static final long serialVersionUID = -4815229688167266460L;

	private String crumbName;
	private String crumbUrl;
	private CrumbDetail next;

	public CrumbDetail() {
		super();
	}

	public CrumbDetail(String crumbName, String crumbUrl) {
		super();
		this.crumbName = crumbName;
		this.crumbUrl = crumbUrl;
	}

	public String getCrumbName() {
		return crumbName;
	}

	public void setCrumbName(String crumbName) {
		this.crumbName = crumbName;
	}

	public String getCrumbUrl() {
		return crumbUrl;
	}

	public void setCrumbUrl(String crumbUrl) {
		this.crumbUrl = crumbUrl;
	}

	public CrumbDetail getNext() {
		return next;
	}

	public CrumbDetail setNext(CrumbDetail next) {
		this.next = next;
		return this.next;
	}

	public CrumbDetail setTail(CrumbDetail tail) {
		CrumbDetail newTail = null;
		// validate if the added 'tail' exist in the breadCrumb.
		// equality is done by crumbName
		if(this.equals(tail)){
			this.crumbUrl = tail.crumbUrl;
			this.crumbName = tail.crumbName;
			this.next = tail.next;
			newTail = this;
		} else {
			CrumbDetail it = this;
			while(it.next != null && newTail == null){
				if(it.next.equals(tail)){
					it.next = tail;
					newTail = it.next;
				} 
				it = it.next;
			}
			// if tail does not exist
			// then just append it
			if(newTail == null){
				it.next = tail;
				newTail = it.next;
			}
		}
		return newTail;
	}	

	public CrumbDetail getTail() {
		CrumbDetail it = this;
		while(it.next != null){
			it = it.next;
		}
		return it;
	}	
	
	public CrumbDetail getBeforeTail() {
		CrumbDetail it = this;
		while(it.next != null && it.next.next != null){
			it = it.next;
		}
		it = (it.next == null) ? null : it; 
		return it;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("crumbName=" + crumbName+'\n');
		sb.append("crumbUrl=" + crumbUrl+'\n');
		return sb.toString();
	}

	@Override
	public int hashCode() {
		return (crumbName != null) ? crumbName.hashCode() : 0;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = true;
		if (obj == null || getClass() != obj.getClass()){
			equals = false;
		} else {
			CrumbDetail other = (CrumbDetail) obj;
			if (crumbName != null) {
				equals = crumbName.equals(other.crumbName);
			} else {
				equals = (other.crumbName == null);
			}
		}
		return equals;
	}
}
