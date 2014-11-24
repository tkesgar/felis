package com.felis.lib;

import java.io.Serializable;

public class PairStringInt implements Serializable, Comparable<PairStringInt>, CharSequence {

	private String content;
	private int index;

	@Override
	public int compareTo(PairStringInt o) {
		return content.compareTo(o.content);
	}

	@Override
	public int length() {
		return content.length();
	}

	@Override
	public char charAt(int index) {
		return content.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return content.subSequence(start, end);
	}

	public PairStringInt(String content, int index) {
		this.content = content;
		this.index = index;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return content.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PairStringInt) {
			return content.equals(((PairStringInt) obj).content);
		} else if (obj instanceof String) {
			return content.equals((String) obj);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return content.hashCode();
	}

}
