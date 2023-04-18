package window_creator;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

import ptcpackage.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ColorPanel extends JPanel {
    static final long serialVersionUID = 294293214L;
    private String fileName;
    private int numOfSquares;
	private int squareSize = 66;
	private Color[] colorPalette;

    public ColorPanel(String fileName, int numOfSquares, int size) {
        this.fileName = fileName;
        this.numOfSquares = numOfSquares;
		this.squareSize = size;
		getColorPalette();
    }

	public void setSquareSize(int newSize) {
		this.squareSize = newSize;
	}

    public void setFileName(String newName) {
        fileName = newName;
    }

	public void setSquareAmount(int newAmount) {
		numOfSquares = newAmount;
	}
	private void getColorPalette() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//generates a color palette of the amount of squares needed
		colorPalette = PhotoToColorPalette.generatePerfectPalette(numOfSquares, image);
	}

	public void getColorPalette(double[] multipliers) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//generates a color palette of the amount of squares needed
		colorPalette = PhotoToColorPalette.generatePerfectPalette(numOfSquares, image, multipliers);
	}

	@Override
    public void paintComponent(Graphics g) {
		
		//turns the g to a g2d
		Graphics2D g2D = (Graphics2D) g.create();
		g2D.setColor(new Color(68, 68, 68));
		g2D.fillRect(0, 0, getSize().width, getSize().height);
		
		//size of the window
		Dimension windowSize = this.getSize();
		
		//indexes for square placement
		int xIndex = getBorder().getBorderInsets(this).left;
		int yIndex = getBorder().getBorderInsets(this).top;

		//for loop going through each color in the palette for a square
		for(int i = 0; i < numOfSquares; i++) {
			Color c = colorPalette[i % colorPalette.length];
			g2D.setColor(c);
			g2D.fillRect(xIndex, yIndex, (int)squareSize, (int)squareSize);		
			xIndex += squareSize;
			if(xIndex > windowSize.getWidth() - squareSize) {
				xIndex = getBorder().getBorderInsets(this).left; //-(int)size.getHeight()/3;
				yIndex += squareSize;
			}
		}
		System.out.println(Arrays.toString(colorPalette));
		System.out.println("");
    } 
}
