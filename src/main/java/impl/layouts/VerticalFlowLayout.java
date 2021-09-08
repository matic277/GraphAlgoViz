package impl.layouts;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class VerticalFlowLayout implements LayoutManager, Serializable
{
    private static final long serialVersionUID = 1L;
    public static final float TOP = 0.0f;
    public static final float BOTTOM = 1.0f;
    public static final float CENTER = 0.5f;
    public static final float LEFT = 0.0f;
    public static final float RIGHT = 1.0f;
    public static final int NO_FILL = 20010215;
    public static final int MATCH_COMPONENTS = 20020215;
    public static final int FILL_SPACE = 20020727;
    private int hgap;
    private int vgap;
    private int horizontalFill;
    private float verticalAlign;
    private float horizontalAlign;
    private int preferredWidth;
    
    public VerticalFlowLayout() {
        this(0.5f, 0.0f, 5, 5);
    }
    
    public VerticalFlowLayout(final float horizontalAlign, final float verticalAlign) {
        this(horizontalAlign, verticalAlign, 5, 5);
    }
    
    public VerticalFlowLayout(final float horizontalAlign, final float verticalAlign, int hgap, int vgap) {
        this.hgap = 0;
        this.vgap = 0;
        this.horizontalFill = 20010215;
        this.verticalAlign = 0.0f;
        this.horizontalAlign = 0.5f;
        this.preferredWidth = 0;
        if (horizontalAlign >= 0.0f && horizontalAlign <= 1.0f) {
            this.horizontalAlign = horizontalAlign;
        }
        if (verticalAlign >= 0.0f && verticalAlign <= 1.0f) {
            this.verticalAlign = verticalAlign;
        }
        if (hgap < 0) {
            hgap = 0;
        }
        if (vgap < 0) {
            vgap = 0;
        }
        this.hgap = hgap;
        this.vgap = vgap;
    }
    
    public int getHgap() {
        return this.hgap;
    }
    
    public void setHgap(final int hgap) {
        this.hgap = hgap;
    }
    
    public int getVgap() {
        return this.vgap;
    }
    
    public void setVgap(final int vgap) {
        this.vgap = vgap;
    }
    
    public void setSpace(final int hgap, final int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }
    
    public int getHorizontalFill() {
        return this.horizontalFill;
    }
    
    public void setHorizontalFill(final int fillPolicy) {
        switch (fillPolicy) {
            case 20010215: {
                this.horizontalFill = 20010215;
                break;
            }
            case 20020215: {
                this.horizontalFill = 20020215;
                break;
            }
            case 20020727: {
                this.horizontalFill = 20020727;
                break;
            }
        }
    }
    
    public float getHorizontalAlign() {
        return this.horizontalAlign;
    }
    
    public void setHorizontalAlign(final float horizontalAlign) {
        if (horizontalAlign >= 0.0f && horizontalAlign <= 1.0f) {
            this.horizontalAlign = horizontalAlign;
        }
    }
    
    public float getVerticalAlign() {
        return this.verticalAlign;
    }
    
    public void setVerticalAlign(final float verticalAlign) {
        if (verticalAlign >= 0.0f && verticalAlign <= 1.0f) {
            this.verticalAlign = verticalAlign;
        }
    }
    
    public void setAlignment(final float horizontal, final float vertical) {
        this.setHorizontalAlign(horizontal);
        this.setVerticalAlign(vertical);
    }
    
    @Override
    public void addLayoutComponent(final String name, final Component comp) {
    }
    
    @Override
    public void removeLayoutComponent(final Component comp) {
    }
    
    @Override
    public Dimension preferredLayoutSize(final Container parent) {
        synchronized (parent.getTreeLock()) {
            final Insets insets = parent.getInsets();
            int height = insets.top + insets.bottom;
            int width = insets.left + insets.right;
            final int ncomponents = parent.getComponentCount();
            height += this.vgap;
            int widest = 0;
            for (int i = 0; i < ncomponents; ++i) {
                final Component comp = parent.getComponent(i);
                Dimension d = comp.getPreferredSize();
                if (comp instanceof Scrollable) {
                    final Scrollable scroll = (Scrollable)comp;
                    if (scroll.getScrollableTracksViewportWidth()) {
                        d = scroll.getPreferredScrollableViewportSize();
                    }
                }
                height += d.height + this.vgap;
                final int wide = d.width + this.hgap;
                if (wide > widest) {
                    widest = wide;
                }
            }
            width += widest + 2 * this.hgap;
            if (this.horizontalFill == 20010215) {
                this.preferredWidth = 0;
            }
            else if (this.horizontalFill == 20020215) {
                this.preferredWidth = widest;
            }
            else if (this.horizontalFill == 20020727) {
                width = parent.getWidth() + insets.left + insets.right;
                this.preferredWidth = parent.getWidth() - this.hgap * 2;
                final Component c = parent.getParent();
                if (c instanceof JViewport) {
                    width = (this.preferredWidth = ((JViewport)c).getExtentSize().width);
                }
            }
            // monitorexit(parent.getTreeLock())
            return new Dimension(width, height);
        }
    }
    
    @Override
    public Dimension minimumLayoutSize(final Container parent) {
        synchronized (parent.getTreeLock()) {
            this.preferredWidth = -1;
            final Insets insets = parent.getInsets();
            int height = insets.top + insets.bottom;
            int width = insets.left + insets.right;
            final int ncomponents = parent.getComponentCount();
            height += this.vgap;
            int widest = 0;
            for (int i = 0; i < ncomponents; ++i) {
                final Component comp = parent.getComponent(i);
                final Dimension d = comp.getMinimumSize();
                height += d.height + this.vgap;
                final int wide = d.width + this.hgap;
                if (wide > widest) {
                    widest = wide;
                }
            }
            width += widest + 2 * this.hgap;
            if (this.horizontalFill == 20010215) {
                this.preferredWidth = -1;
            }
            else if (this.horizontalFill == 20020215) {
                this.preferredWidth = widest;
            }
            else if (this.horizontalFill == 20020727) {
                width = parent.getWidth() + insets.left + insets.right;
                this.preferredWidth = parent.getWidth() - this.hgap * 2;
            }
            // monitorexit(parent.getTreeLock())
            return new Dimension(width, height);
        }
    }
    
    @Override
    public void layoutContainer(final Container parent) {
        synchronized (parent.getTreeLock()) {
            final Insets insets = parent.getInsets();
            final Dimension preferredLayoutSize = this.preferredLayoutSize(parent);
            final Dimension availableSpace = parent.getSize();
            int vSpace = availableSpace.height - preferredLayoutSize.height - insets.top - insets.bottom;
            if (vSpace < 0) {
                vSpace = 0;
            }
            int y = insets.top + this.vgap + (int)(vSpace * this.verticalAlign);
            for (int ncomponents = parent.getComponentCount(), i = 0; i < ncomponents; ++i) {
                final Component comp = parent.getComponent(i);
                final Dimension preferredSize = comp.getPreferredSize();
                if (this.horizontalFill == 20010215) {
                    this.preferredWidth = preferredSize.width;
                }
                int hSpace = availableSpace.width - this.preferredWidth - insets.left - insets.right - this.hgap * 2;
                if (hSpace < 0) {
                    hSpace = 0;
                }
                final int x = insets.left + this.hgap + (int)(hSpace * this.horizontalAlign);
                comp.setBounds(x, y, this.preferredWidth, preferredSize.height);
                y += preferredSize.height + this.vgap;
            }
        }
        // monitorexit(parent.getTreeLock())
    }
}
