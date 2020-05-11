package net.matiello.pdf_reader.utils;

public class IndexCount {
	private int index;

	public IndexCount(int index) {
		this.index = index;
	}

	public int next() {
		return index++;
	}

}
