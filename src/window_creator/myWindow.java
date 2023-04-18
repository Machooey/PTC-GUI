package window_creator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

public class myWindow {
	private JFrame myFrame;
	Color primaryTextColor = new Color(250, 250, 250);
	Color primaryBackgroundColor = new Color(48, 48, 48);

	public myWindow(int width, int height) {
		
		initializeWindow(width, height);
	}

	private void initializeWindow(int width, int height) {
		myFrame = new JFrame();
		myFrame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e){
				Dimension d= myFrame.getSize();
				Dimension minD= myFrame.getMinimumSize();
				if(d.width<minD.width)
					d.width=minD.width;
				if(d.height<minD.height)
					d.height=minD.height;
				myFrame.setSize(d);
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				Dimension d= myFrame.getSize();
				Dimension minD= myFrame.getMinimumSize();
				if(d.width<minD.width)
					d.width=minD.width;
				if(d.height<minD.height)
					d.height=minD.height;
				myFrame.setSize(d);
			}
		});
	
		//sets the close operation to exitting 
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//myFrame.setResizable(false);
		Dimension initialPoint = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3, 
												(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3);
		myFrame.setLocation((int)initialPoint.getWidth(), (int)initialPoint.getHeight());
		myFrame.setMinimumSize(new Dimension(width, height));
		myFrame.setTitle("Photo to Color Palette");
		myFrame.setSize(new Dimension(width, height));
		myFrame.setMinimumSize(new Dimension(width, height));
		myFrame.setLayout(new BorderLayout());
		Image icon = Toolkit.getDefaultToolkit().getImage("PTCIcon.png");
		myFrame.setIconImage(icon);

		JPanel eastPanel = new JPanel(new BorderLayout());
		eastPanel.setBackground(new Color(48, 48, 48));
		eastPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 0, 5, new Color(28, 28, 28)));

		JPanel textPanel = createBoxPanel(BoxLayout.Y_AXIS);
		eastPanel.add(textPanel, BorderLayout.CENTER);

		JPanel fieldPanel = createBoxPanel(BoxLayout.Y_AXIS);
		eastPanel.add(fieldPanel, BorderLayout.EAST);

		textPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10 , 0));
		// textPanel.setBackground(Color.RED);
		// fieldPanel.setBackground(Color.GREEN);
		fieldPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 10 , 3));


		Font jetbrains = createFont("JetBrainsMono.ttf", 10f); //creates the jetbrains font

		JLabel percentColorText = createLabel("<html>Percent of image<br/>color takes up<html>", jetbrains); //creates a text label and adds it to the box-layout panel
		NumberFormat decimalFormat = NumberFormat.getNumberInstance();
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		JFormattedTextField percentField = createTextField(jetbrains, percentFormat); //creates a text field and adds it to the box-layout panel
		percentField.setValue(.1);
		percentField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		percentField.setMinimumSize(new Dimension(8, 2));
		percentField.setMaximumSize(new Dimension(50, 20));;
		percentField.setPreferredSize(new Dimension(50, 20));
		textPanel.add(percentColorText); //adds the box-layout panel to the textPanel
		fieldPanel.add(percentField);

		textPanel.add(Box.createRigidArea(new Dimension(10, 15)));
		fieldPanel.add(Box.createRigidArea(new Dimension(10, 25)));

		JLabel colorSimilarityThresholdText = createLabel("<html>Color alikeness <br/>threshold </html>", jetbrains);
		JFormattedTextField similarityField = createTextField(jetbrains);
		similarityField.setValue(15);
		similarityField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		similarityField.setMinimumSize(new Dimension(8, 2));
		similarityField.setMaximumSize(new Dimension(50, 20));;
		similarityField.setPreferredSize(new Dimension(30, 20));
		textPanel.add(colorSimilarityThresholdText);
		fieldPanel.add(similarityField);
		
		textPanel.add(Box.createRigidArea(new Dimension(10, 15)));
		fieldPanel.add(Box.createRigidArea(new Dimension(10, 23)));

		JLabel amountMultText = createLabel("<html>Multiplier for <br/>amount<html>", jetbrains);
		JFormattedTextField amountMultField = createTextField(jetbrains, decimalFormat);
		amountMultField.setValue(1);
		amountMultField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		amountMultField.setMinimumSize(new Dimension(8, 2));
		amountMultField.setMaximumSize(new Dimension(50, 20));;
		amountMultField.setPreferredSize(new Dimension(30, 20));
		textPanel.add(amountMultText);
		fieldPanel.add(amountMultField);

		textPanel.add(Box.createRigidArea(new Dimension(10, 15)));
		fieldPanel.add(Box.createRigidArea(new Dimension(10, 23)));

		JLabel uniqueMultText = createLabel("<html>Multiplier for<br/>uniqueness", jetbrains);
		JFormattedTextField uniqueMultField = createTextField(jetbrains, decimalFormat);
		uniqueMultField.setValue(1);
		uniqueMultField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		uniqueMultField.setMinimumSize(new Dimension(8, 2));
		uniqueMultField.setMaximumSize(new Dimension(50, 20));;
		uniqueMultField.setPreferredSize(new Dimension(30, 20));
		textPanel.add(uniqueMultText);
		fieldPanel.add(uniqueMultField);

		textPanel.add(Box.createRigidArea(new Dimension(10, 15)));
		fieldPanel.add(Box.createRigidArea(new Dimension(10, 25)));

		JLabel varietyMultLabel = createLabel("<html>Multiplier for <br/>color variety", jetbrains);
		JFormattedTextField varietyMultField = createTextField(jetbrains, decimalFormat);
		varietyMultField.setValue(100);
		varietyMultField.setAlignmentX(Component.RIGHT_ALIGNMENT);
		varietyMultField.setMinimumSize(new Dimension(8, 2));
		varietyMultField.setMaximumSize(new Dimension(50, 20));;
		varietyMultField.setPreferredSize(new Dimension(30, 20));
		textPanel.add(varietyMultLabel);
		fieldPanel.add(varietyMultField);

		//sets preferred dimensions for eastPanel
		eastPanel.setMaximumSize(new Dimension(216, 500));
		eastPanel.setMinimumSize(new Dimension(216, 500));
		eastPanel.setPreferredSize(new Dimension(216, 500));
		
		myFrame.add(eastPanel, BorderLayout.EAST);

		//-------------------------------------
		// THE IMAGE FILE NAME
		//-------------------------------------
		String filename = "dalle.png";
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		ColorPanel colorpanel = new ColorPanel(filename, 16, 66);
		Image rescaledImage = null;
		colorpanel.setBorder(BorderFactory.createMatteBorder(5, 5, 0, 0, new Color(28, 28, 28)));
		if(image != null) {
			double divisionFactor = (100 / (double)image.getHeight());
			rescaledImage = image.getScaledInstance((int)(image.getWidth() * divisionFactor) ,(int) (image.getHeight() * divisionFactor), Image.SCALE_DEFAULT);
		}

		 
		JPanel buttonPanel = new JPanel();
		JButton calculateButton = new JButton("Calculate!");
		calculateButton.setFocusPainted(false);
		calculateButton.setBackground(new Color(28, 28, 28));
		calculateButton.setForeground(primaryTextColor);
		calculateButton.setFont(jetbrains);
		calculateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double[] nums = new double[5];
				JFormattedTextField[] fields = new JFormattedTextField[5];
				fields[0] = percentField;
				fields[1] = similarityField;
				fields[2] = amountMultField;
				fields[3] = uniqueMultField;
				fields[4] = varietyMultField;
				for(int i = 0; i < fields.length; i++) {
					try{
						fields[i].commitEdit();
					} catch(Exception ex){}
					if(fields[i].getValue() instanceof Long) {
						Long l = (Long) fields[i].getValue();
						nums[i] = l.doubleValue();
					}
					else if(fields[i].getValue() instanceof Integer) {
						Integer inte = (int) fields[i].getValue();
						nums[i] = inte.doubleValue();
					}
					else nums[i] = (double) fields[i].getValue();
				}
				colorpanel.getColorPalette(nums);
				colorpanel.repaint();
			}
		});
		myFrame.add(colorpanel, BorderLayout.CENTER);
		buttonPanel.add(calculateButton);
		buttonPanel.setBackground(primaryBackgroundColor);
		buttonPanel.add(Box.createRigidArea(new Dimension(1, 40)));
		eastPanel.add(buttonPanel, BorderLayout.SOUTH);

		JPanel southPanel = new JPanel();
		JLabel imageLabel = new JLabel(new ImageIcon(rescaledImage));
		southPanel.setBackground(primaryBackgroundColor);
		southPanel.setBorder(BorderFactory.createLineBorder(new Color(28, 28, 28), 5));
		southPanel.add(imageLabel, BorderLayout.WEST);

		JPanel sliderPanel = createBoxPanel(BoxLayout.Y_AXIS);
		JPanel sliderTextPanel = createBoxPanel(BoxLayout.Y_AXIS);
		sliderPanel.setBackground(primaryBackgroundColor);
		sliderTextPanel.setBackground(primaryBackgroundColor);
		sliderTextPanel.setPreferredSize(new Dimension(100, 100));
		sliderTextPanel.setBorder(BorderFactory.createEmptyBorder(5, 1, 5, 1));
		JLabel squareSizeText = createLabel("Square Size", jetbrains);
		JSlider sizeSlider = createSlider(0, 200, 66);
		ChangeListener sizeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				colorpanel.setSquareSize(sizeSlider.getValue());
				recalculateSquares(colorpanel);
			}
		};
		sizeSlider.addChangeListener(sizeListener);
		sizeSlider.setFont(jetbrains.deriveFont(8f));
		sliderTextPanel.add(squareSizeText);
		sliderPanel.add(sizeSlider);
		southPanel.add(sliderTextPanel, BorderLayout.CENTER);
		southPanel.add(sliderPanel, BorderLayout.EAST);

		sliderTextPanel.add(Box.createRigidArea(new Dimension(25, 30)));

		JLabel squareAmountText = createLabel("<html>Amount of <br/>squares<html>", jetbrains);
		JSlider squareAmountSlider = new JSlider(0, 250, 16);
		squareAmountSlider.setUI(new SizeSliderUI(squareAmountSlider));
		squareAmountSlider.setOpaque(false);
		squareAmountSlider.setForeground(primaryTextColor);
		squareAmountSlider.setMajorTickSpacing(100);
		squareAmountSlider.setMinorTickSpacing(25);
		squareAmountSlider.setPaintLabels(true);
		squareAmountSlider.setPaintTicks(true);
		squareAmountSlider.setFont(jetbrains.deriveFont(8f));
		squareAmountSlider.setPreferredSize(new Dimension(250, 50));
		ChangeListener amountListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				colorpanel.setSquareAmount(squareAmountSlider.getValue());
				colorpanel.repaint();
			}
		};
		squareAmountSlider.addChangeListener(amountListener);
		sliderTextPanel.add(squareAmountText);
		sliderPanel.add(squareAmountSlider);
		myFrame.add(southPanel, BorderLayout.SOUTH);
		myFrame.setVisible(true);
	}

	/**
	 * creates a slider with the correct formatting
	 * @param min
	 * @param max
	 * @param value
	 * @return
	 */
	private JSlider createSlider(int min, int max, int value) {
		JSlider slider = new JSlider(min, max, value);
		slider.setBackground(primaryTextColor);
		slider.setForeground(primaryTextColor);
		slider.setPreferredSize(new Dimension(150, 50));
		slider.setMajorTickSpacing(25);
		slider.setMinorTickSpacing(5);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setUI(new SizeSliderUI(slider));
		slider.setOpaque(false);
		return slider;
	}

	private void recalculateSquares(ColorPanel colorpanel) {
		colorpanel.repaint();
	}

	/**
	 * Creates a panel with a box layout
	 * @param layoutStyle - the desired layout style, for example, BoxLayout.X_AXIS
	 * @return the created box panel
	 */
	private JPanel createBoxPanel(int layoutStyle) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, layoutStyle));
		panel.setBackground(primaryBackgroundColor);
		return panel;
	}

	/**
	 * Creates a JLabel stylized for the east border
	 * @param text - the text 
	 * @param font - the font used
	 * @return the created label
	 */
	private JLabel createLabel(String text, Font font) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		label.setForeground(primaryTextColor);
		return label;
	}

	/**
	 * Creates a text field stylized for the east panel
	 * @param font - the font used in the field
	 * @return the created text field
	 */
	private JFormattedTextField createTextField(Font font, NumberFormat format) {
		JFormattedTextField field = new JFormattedTextField(format);
		field.setFont(font);
		field.setForeground(primaryTextColor);
		field.setPreferredSize(new Dimension(10, 1));
		field.setForeground(primaryTextColor);
		field.setBackground(primaryBackgroundColor);
		return field;
	}

	private JFormattedTextField createTextField(Font font) {
		JFormattedTextField field = new JFormattedTextField();
		field.setFont(font);
		field.setForeground(primaryTextColor);
		field.setPreferredSize(new Dimension(10, 1));
		field.setForeground(primaryTextColor);
		field.setBackground(primaryBackgroundColor);
		return field;
	}

	/**
	 * Creates a font using a file name and a given font size.
	 * @param fileName - the name of the font file
	 * @param size - the desired size of the font
	 * @return the font if successfully created and null if not
	 */
	private Font createFont(String fileName, float size) {
		Font font = null;
		try{
			font = Font.createFont(Font.TRUETYPE_FONT, new File(fileName));
			font = font.deriveFont(size);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		} catch(Exception e) {}
		return font;
	}
}