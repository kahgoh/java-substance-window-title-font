/**
 *  Copyright 2011 Kah Goh
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.plaf.FontUIResource;

import net.miginfocom.swing.MigLayout;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceSkin;
import org.pushingpixels.substance.api.skin.SkinInfo;

/**
 * Provides a builder for creating the dialog that will demonstrate the ability
 * to control the Window title font in the Substance look and feel.
 * 
 * @author Kah Goh
 */
public class FrameBuilder {

	private final CustomFontSet fontSet;

	/**
	 * @param fontSet
	 *            the {@link CustomFontSet} that Substance is configured to use
	 */
	public FrameBuilder(CustomFontSet fontSet) {
		this.fontSet = fontSet;
	}

	/**
	 * Builds the window (or {@link JFrame}) based on the configuration.
	 * 
	 * @return the created {@link JFrame}
	 */
	public JFrame build() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame();
		frame.setTitle("Window Title Font");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contents = frame.getContentPane();
		contents.setLayout(new MigLayout());
		contents.add(new JLabel("Look and feel: "), "sgx label");
		contents.add(lnfComboBox(), "wrap, sgx field, pushx");

		contents.add(new JLabel("Window Title font: "), "sgx label");
		contents.add(fontSelector(fontSet), "wrap, sgx field, pushx");

		frame.pack();
		return frame;
	}

	/**
	 * Creates the combo box for choosing the font of the window title. The font
	 * will automatically be set when the user changes selection.
	 * 
	 * @param fontSet
	 *            the instance of the {@link CustomFontSet} that is being used
	 *            by Substance
	 * @return the created combo box
	 */
	private JComboBox<?> fontSelector(final CustomFontSet fontSet) {
		final JComboBox<FontUIResource> fontSelector = new JComboBox<FontUIResource>();
		fontSelector.addItem(fontSet.getDelegateWindowTitleFont());

		String[] fontNames = new String[] { Font.MONOSPACED, Font.SANS_SERIF,
				Font.SERIF, Font.DIALOG };
		for (String fontName : fontNames) {
			fontSelector.addItem(new FontUIResource(fontName, Font.PLAIN, 12));
		}

		// Display the name of the fonts in the combo box.
		final ListCellRenderer<? super FontUIResource> originalRenderer = fontSelector
				.getRenderer();
		fontSelector.setRenderer(new ListCellRenderer<FontUIResource>() {

			public Component getListCellRendererComponent(
					JList<? extends FontUIResource> list, FontUIResource value,
					int index, boolean isSelected, boolean cellHasFocus) {

				Component component = originalRenderer
						.getListCellRendererComponent(list, value, index,
								isSelected, cellHasFocus);

				if (component instanceof JLabel) {
					((JLabel) component).setText(value.getName());
				}

				return component;
			}
		});

		// Update the window when the user changes the selection.
		fontSelector.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				fontSet.setWindowTitleFont((FontUIResource) fontSelector
						.getSelectedItem());

				Window window = SwingUtilities.getWindowAncestor(fontSelector);
				if (window != null) {
					SwingUtilities.updateComponentTreeUI(window);
					window.pack();
				}
			}
		});

		return fontSelector;
	}
	
	/**
	 * Creates a {@link JComboBox} for changing the look and feel.
	 * 
	 * @return the created {@link JComboBox}
	 */
	private static JComboBox<?> lnfComboBox() {
		final JComboBox<LnfLoader> skinSelector = new JComboBox<LnfLoader>();

		// Add the options for the Substance look and feels.
		Map<String, SkinInfo> skins = SubstanceLookAndFeel.getAllSkins();
		SubstanceSkin currentSkin = SubstanceLookAndFeel.getCurrentSkin();

		for (SkinInfo skin : skins.values()) {
			LnfLoader item = substance(skin);
			skinSelector.addItem(item);
			if (currentSkin != null) {
				if (currentSkin.getClass().getName()
						.equals(skin.getClassName())) {
					skinSelector.setSelectedItem(item);
				}
			}
		}

		// Action listener to swap change the look and feels.
		skinSelector.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int index = skinSelector.getSelectedIndex();
				final LnfLoader loader = skinSelector.getItemAt(index);
				EventQueue.invokeLater(new Runnable() {

					public void run() {
						loader.loadLnf();
						Window window = SwingUtilities
								.getWindowAncestor(skinSelector);
						if (window != null) {
							SwingUtilities.updateComponentTreeUI(window);
							window.pack();
						}
					}
				});
			}
		});

		return skinSelector;
	}

	/**
	 * Provides a {@link LnfLoader} for loading a Substance skin.
	 * 
	 * @param skin
	 *            the Substance skin that will be loaded by the
	 *            {@link LnfLoader}
	 * @return the {@link LnfLoader}
	 */
	private static LnfLoader substance(final SkinInfo skin) {
		return new LnfLoader() {

			public void loadLnf() {
				SubstanceLookAndFeel.setSkin(skin.getClassName());
			}

			public String toString() {
				return skin.getDisplayName();
			}
		};
	}

	/**
	 * Interface for an object that loads a look and feel
	 */
	private static interface LnfLoader {
		/**
		 * Changes the look and feel.
		 */
		public void loadLnf();
	}
}
