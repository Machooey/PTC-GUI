package window_creator;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class SizeSliderUI extends BasicSliderUI{
    public SizeSliderUI(JSlider slider) {
        super(slider);
    }

    @Override
    public void paintThumb(Graphics g) { 
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(slider.getForeground());
        g2.fillOval(thumbRect.x, thumbRect.y + 5, thumbRect.width + 1, thumbRect.width + 1);
        slider.repaint();
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(28, 28, 28));
        g2.fillRect(5, slider.getHeight() / 2 - 11, slider.getWidth() - 8, 4);
        //g2.fillRoundRect(MAX_SCROLL, trackBuffer, POSITIVE_SCROLL, NEGATIVE_SCROLL, MIN_SCROLL, MAX_SCROLL);

    }

    @Override
    public void paintFocus(Graphics g) { }
}
