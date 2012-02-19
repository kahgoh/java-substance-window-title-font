import javax.swing.UIDefaults;

import org.pushingpixels.substance.api.fonts.FontPolicy;
import org.pushingpixels.substance.api.fonts.FontSet;

/**
 * An implementation of {@link FontPolicy} that will provide a custom
 * {@link FontSet} for the Substance look and feel. For all other look and
 * feels, it will delegate to the provided {@link FontPolicy}.
 * 
 * @author Kah Goh
 */
public class CustomFontPolicy implements FontPolicy {

	/**
	 * The {@link FontPolicy} to delegate to when not using the Substance look
	 * and feel.
	 */
	private final FontPolicy delegatePolicy;

	/**
	 * The {@link FontSet} to provide when using the Substance look and feel.
	 */
	private final FontSet fontSet;

	/**
	 * @param delegatePolicy
	 *            the {@link FontPolicy} to delegate to when not using the
	 *            substance look and feel
	 * @param fontSet
	 *            the {@link FontSet} to provide when using the Substance look
	 *            and feel
	 */
	public CustomFontPolicy(FontPolicy delegatePolicy, FontSet fontSet) {
		this.delegatePolicy = delegatePolicy;
		this.fontSet = fontSet;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FontSet getFontSet(String lafName, UIDefaults table) {
		if (lafName.equals("Substance") && table == null) {
			return fontSet;
		}
		return delegatePolicy.getFontSet(lafName, table);
	}
}
