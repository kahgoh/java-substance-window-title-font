import javax.swing.plaf.FontUIResource;

import org.pushingpixels.substance.api.fonts.FontSet;

/**
 * An implementation of {@link FontSet} that allows only window title font to be
 * set. For all other font types, it will provide the font given by the delegate
 * {@link FontSet}.
 * 
 * @author Kah Goh
 */
public class CustomFontSet implements FontSet {

	/**
	 * The delegate that will provide the fonts, other than the window title
	 * font.
	 */
	private final FontSet delegateFontSet;

	/**
	 * The font that will be provided for the title of the window.
	 */
	private FontUIResource windowTitleFont;

	/**
	 * @param delegateFontSet
	 *            provides the fonts other than the one for the window title
	 */
	public CustomFontSet(FontSet delegateFontSet) {
		this.delegateFontSet = delegateFontSet;
		this.windowTitleFont = delegateFontSet.getWindowTitleFont();
	}

	/**
	 * Sets the font for the window title.
	 * 
	 * @param titleFont
	 *            the font to use
	 */
	public void setWindowTitleFont(FontUIResource titleFont) {
		this.windowTitleFont = titleFont;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FontUIResource getControlFont() {
		return delegateFontSet.getControlFont();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FontUIResource getMenuFont() {
		return delegateFontSet.getMenuFont();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FontUIResource getTitleFont() {
		return delegateFontSet.getTitleFont();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FontUIResource getWindowTitleFont() {
		return windowTitleFont;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FontUIResource getSmallFont() {
		return delegateFontSet.getSmallFont();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FontUIResource getMessageFont() {
		return delegateFontSet.getMessageFont();
	}

	/**
	 * @return the window title font from the delegate {@link FontSet}
	 */
	public FontUIResource getDelegateWindowTitleFont() {
		return delegateFontSet.getWindowTitleFont();
	}
}
