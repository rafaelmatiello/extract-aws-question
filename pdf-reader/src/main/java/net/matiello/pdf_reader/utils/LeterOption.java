package net.matiello.pdf_reader.utils;

public class LeterOption {
	private static final String[] OPTIONS = new String[] { "A", "B", "C", "D", "E", "F" };
	private int position = 0;

	public String next() {

		if (position == OPTIONS.length) {
			throw new IllegalArgumentException("Número máximo de opções");
		}
		String option = OPTIONS[position];
		position++;
		return option;
	}

	public void reset() {
		position = 0;
	}

}
