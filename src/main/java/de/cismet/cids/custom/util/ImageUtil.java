/*
 *  Copyright (C) 2010 srichter
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import org.jdesktop.swingx.graphics.ShadowRenderer;

/**
 *
 * @author srichter
 */
public final class ImageUtil {

    public static Image adjustScale(BufferedImage bi, JComponent component, int insetX, int insetY) {
        double scalex = (double) component.getWidth() / bi.getWidth();
        double scaley = (double) component.getHeight() / bi.getHeight();
        double scale = Math.min(scalex, scaley);
        if (scale <= 1d) {
            return bi.getScaledInstance((int) (bi.getWidth() * scale) - insetX, (int) (bi.getHeight() * scale) - insetY, Image.SCALE_SMOOTH);
        } else {
            return bi;
        }
    }
    public static Image adjustScale(BufferedImage bi, int targetW, int targetH, int insetX, int insetY) {
        double scalex = (double) targetW / bi.getWidth();
        double scaley = (double) targetH / bi.getHeight();
        double scale = Math.min(scalex, scaley);
        if (scale <= 1d) {
            return bi.getScaledInstance((int) (bi.getWidth() * scale) - insetX, (int) (bi.getHeight() * scale) - insetY, Image.SCALE_SMOOTH);
        } else {
            return bi;
        }
    }

        public static BufferedImage generateShadow(final Image in, final int shadowPixel) {
        if (in == null) {
            return null;
        }
        final BufferedImage input;
        if (in instanceof BufferedImage) {
            input = (BufferedImage) in;
        } else {
            final BufferedImage temp = new BufferedImage(in.getWidth(null), in.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
            final Graphics tg = temp.createGraphics();
            tg.drawImage(in, 0, 0, null);
            tg.dispose();
            input = temp;
        }
        if (shadowPixel < 1) {
            return input;
        }
        final ShadowRenderer renderer = new ShadowRenderer(shadowPixel, 0.5f, Color.BLACK);
        final BufferedImage shadow = renderer.createShadow(input);
        final BufferedImage result = new BufferedImage(input.getWidth() + 2 * shadowPixel,
                input.getHeight() + 2 * shadowPixel, BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics2D rg = result.createGraphics();
        rg.drawImage(shadow, 0, 0, null);
        rg.drawImage(input, 0, 0, null);
        rg.dispose();
        return result;
    }
}
