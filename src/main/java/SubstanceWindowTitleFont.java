import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.fonts.FontPolicy;

/**
 * Defines the main method that will create and display the dialog that
 * demonstrates how the font for the Window title can be changed when using the
 * Substance look and feel. This will NOT work with the standard Java look and
 * feels.
 * 
 * @author Kah Goh
 */
public class SubstanceWindowTitleFont {

	public static void main(String args[]) throws InterruptedException,
			InvocationTargetException {
		EventQueue.invokeAndWait(new Runnable() {

			public void run() {
				CustomFontSet fontPolicy = setLookAndFeel();
				displayUi(fontPolicy);
			}
		});
	}

	/**
	 * Sets up the look and feel for the UI.
	 * 
	 * @return the instance of the {@link CustomFontSet} that will be in use
	 */
	private static CustomFontSet setLookAndFeel() {
		SubstanceLookAndFeel
				.setSkin("org.pushingpixels.substance.api.skin.RavenSkin");
		FontPolicy fontPolicy = SubstanceLookAndFeel.getFontPolicy();

		// Customised font policy to control Window title font.
		CustomFontSet fontSet = new CustomFontSet(fontPolicy.getFontSet("Substance", null));
		CustomFontPolicy customFontPolicy = new CustomFontPolicy(fontPolicy, fontSet);
		SubstanceLookAndFeel.setFontPolicy(customFontPolicy);
		return fontSet;
	}

	/**
	 * Creates and displays the UI.
	 * 
	 * @param fontSet
	 *            the instance of the {@link CustomFontSet} that the UI is being
	 *            configured to use
	 */
	private static void displayUi(CustomFontSet fontSet) {
		FrameBuilder builder = new FrameBuilder(fontSet);
		builder.build().setVisible(true);
	}
}
