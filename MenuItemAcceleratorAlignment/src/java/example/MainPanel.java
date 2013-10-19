package example;
//-*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
//@homepage@
import java.awt.*;
import java.awt.event.*;
//import java.util.Locale;
import javax.swing.*;
// import javax.swing.plaf.basic.*;
import sun.swing.*;

public class MainPanel {
    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu0 = new JMenu("Default");
        JMenu menu1 = new JMenu("RightAcc");
        //XXX: JMenuItem.setDefaultLocale(Locale.ENGLISH);

        menu0.setMnemonic(KeyEvent.VK_D);
        menu1.setMnemonic(KeyEvent.VK_R);
        menuBar.add(menu0);
        menuBar.add(menu1);

        JMenuItem menuItem = new JMenuItem("mi");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menu0.add(menuItem);
        menuItem = makeMenuItem("mi");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menu1.add(menuItem);

        menu0.addSeparator();
        menu1.addSeparator();

        menuItem = new JMenuItem("aaa");
        menuItem.setMnemonic(KeyEvent.VK_1);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.ALT_MASK));
        menu0.add(menuItem);
        menuItem = makeMenuItem("aaa");
        menuItem.setMnemonic(KeyEvent.VK_1);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.ALT_MASK));
        menu1.add(menuItem);

        menuItem = new JMenuItem("bbbbb");
        menuItem.setMnemonic(KeyEvent.VK_2);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, ActionEvent.ALT_MASK|ActionEvent.CTRL_MASK));
        menu0.add(menuItem);
        menuItem = makeMenuItem("bbbbb");
        menuItem.setMnemonic(KeyEvent.VK_2);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, ActionEvent.ALT_MASK|ActionEvent.CTRL_MASK));
        menu1.add(menuItem);

        menuItem = new JMenuItem("c");
        menuItem.setMnemonic(KeyEvent.VK_3);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, ActionEvent.ALT_MASK|ActionEvent.CTRL_MASK|ActionEvent.SHIFT_MASK));
        menu0.add(menuItem);
        menuItem = makeMenuItem("c");
        menuItem.setMnemonic(KeyEvent.VK_3);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, ActionEvent.ALT_MASK|ActionEvent.CTRL_MASK|ActionEvent.SHIFT_MASK));
        menu1.add(menuItem);

        return menuBar;
    }
    private static JMenuItem makeMenuItem(String str) {
        return new JMenuItem(str) {
            @Override public void updateUI() {
                super.updateUI();
                //System.out.println(getLocale());
                if(getUI() instanceof com.sun.java.swing.plaf.windows.WindowsMenuItemUI) {
                    setUI(new RAAWindowsMenuItemUI());
                }else{
                    setUI(new RAABasicMenuItemUI());
                }
                //XXX: setLocale(Locale.JAPAN);
            }
        };
    }

    public static void main(String[] args) {
        //Locale.setDefault(Locale.ENGLISH);
        //java.util.ResourceBundle awtBundle = java.util.ResourceBundle.getBundle(
        //    "sun.awt.resources.awt", sun.util.CoreResourceBundleControl.getRBControlInstance());
        //Locale.setDefault(new Locale("xx"));
        //JMenuItem.setDefaultLocale(Locale.ENGLISH);
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                createAndShowGUI();
            }
        });
    }
    public static void createAndShowGUI() {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("@title@");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.getContentPane().add(new MainPanel());
        frame.setJMenuBar(createMenuBar());
        frame.setSize(320, 240);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

//@see javax/swing/plaf/basic/BasicMenuItemUI.java
class MenuItemUIHelper {
    public static void paintIcon(Graphics g, MenuItemLayoutHelper lh, MenuItemLayoutHelper.LayoutResult lr, Color holdc) {
        if(lh.getIcon() != null) {
            Icon icon;
            ButtonModel model = lh.getMenuItem().getModel();
            if(!model.isEnabled()) {
                icon = lh.getMenuItem().getDisabledIcon();
            }else if(model.isPressed() && model.isArmed()) {
                icon = lh.getMenuItem().getPressedIcon();
                if(icon == null) {
                    // Use default icon
                    icon = lh.getMenuItem().getIcon();
                }
            }else{
                icon = lh.getMenuItem().getIcon();
            }
            if(icon != null) {
                icon.paintIcon(lh.getMenuItem(), g, lr.getIconRect().x, lr.getIconRect().y);
                g.setColor(holdc);
            }
        }
    }

    public static void paintCheckIcon(Graphics g, MenuItemLayoutHelper lh, MenuItemLayoutHelper.LayoutResult lr, Color holdc, Color foreground) {
        if(lh.getCheckIcon() != null) {
            ButtonModel model = lh.getMenuItem().getModel();
            if(model.isArmed() || (lh.getMenuItem() instanceof JMenu && model.isSelected())) {
                g.setColor(foreground);
            }else{
                g.setColor(holdc);
            }
            if(lh.useCheckAndArrow()) {
                lh.getCheckIcon().paintIcon(lh.getMenuItem(), g, lr.getCheckRect().x, lr.getCheckRect().y);
            }
            g.setColor(holdc);
        }
    }

    public static void paintAccText(Graphics g, MenuItemLayoutHelper lh, MenuItemLayoutHelper.LayoutResult lr, Color disabledForeground, Color acceleratorForeground, Color acceleratorSelectionForeground) {
        if(!lh.getAccText().equals("")) {
            ButtonModel model = lh.getMenuItem().getModel();
            g.setFont(lh.getAccFontMetrics().getFont());
            if(!model.isEnabled()) {
                // *** paint the accText disabled
                if(disabledForeground != null) {
                    g.setColor(disabledForeground);
                    SwingUtilities2.drawString(lh.getMenuItem(), g, lh.getAccText(), lr.getAccRect().x, lr.getAccRect().y + lh.getAccFontMetrics().getAscent());
                }else{
                    g.setColor(lh.getMenuItem().getBackground().brighter());
                    SwingUtilities2.drawString(lh.getMenuItem(), g, lh.getAccText(), lr.getAccRect().x, lr.getAccRect().y + lh.getAccFontMetrics().getAscent());
                    g.setColor(lh.getMenuItem().getBackground().darker());
                    SwingUtilities2.drawString(lh.getMenuItem(), g, lh.getAccText(), lr.getAccRect().x - 1, lr.getAccRect().y + lh.getFontMetrics().getAscent() - 1);
                }
            }else{
                // *** paint the accText normally
                if(model.isArmed() || (lh.getMenuItem() instanceof JMenu && model.isSelected())) {
                    g.setColor(acceleratorSelectionForeground);
                }else{
                    g.setColor(acceleratorForeground);
                }
                SwingUtilities2.drawString(
                    lh.getMenuItem(), g, lh.getAccText(),
                    lh.getViewRect().x + lh.getViewRect().width - lh.getMenuItem().getIconTextGap() - lr.getAccRect().width, lr.getAccRect().y +
                    lh.getAccFontMetrics().getAscent());
            }
        }
    }

    public static void paintArrowIcon(Graphics g, MenuItemLayoutHelper lh, MenuItemLayoutHelper.LayoutResult lr, Color foreground) {
        if(lh.getArrowIcon() != null) {
            ButtonModel model = lh.getMenuItem().getModel();
            if(model.isArmed() || (lh.getMenuItem() instanceof JMenu && model.isSelected())) {
                g.setColor(foreground);
            }
            if(lh.useCheckAndArrow()) {
                lh.getArrowIcon().paintIcon(lh.getMenuItem(), g, lr.getArrowRect().x, lr.getArrowRect().y);
            }
        }
    }

    public static void applyInsets(Rectangle rect, Insets insets) {
        if(insets != null) {
            rect.x += insets.left;
            rect.y += insets.top;
            rect.width -= (insets.right + rect.x);
            rect.height -= (insets.bottom + rect.y);
        }
    }
}

class RAAWindowsMenuItemUI extends com.sun.java.swing.plaf.windows.WindowsMenuItemUI {
    @Override protected void paintMenuItem(Graphics g, JComponent c, Icon checkIcon, Icon arrowIcon, Color background, Color foreground, int defaultTextIconGap) {
        // Save original graphics font and color
        Font holdf = g.getFont();
        Color holdc = g.getColor();

        //System.out.println(defaultTextIconGap);

        JMenuItem mi = (JMenuItem) c;
        g.setFont(mi.getFont());

        Rectangle viewRect = new Rectangle(0, 0, mi.getWidth(), mi.getHeight());
        MenuItemUIHelper.applyInsets(viewRect, mi.getInsets());

        MenuItemLayoutHelper lh = new MenuItemLayoutHelper(
            mi, checkIcon, arrowIcon, viewRect, defaultTextIconGap, "+", //acceleratorDelimiter,
            true, mi.getFont(), acceleratorFont, MenuItemLayoutHelper.useCheckAndArrow(menuItem), getPropertyPrefix());
        MenuItemLayoutHelper.LayoutResult lr = lh.layoutMenuItem();

        paintBackground(g, mi, background);
        MenuItemUIHelper.paintCheckIcon(g, lh, lr, holdc, foreground);
        MenuItemUIHelper.paintIcon(g, lh, lr, holdc);
        paintText(g, lh, lr);
        MenuItemUIHelper.paintAccText(g, lh, lr, disabledForeground, acceleratorForeground, acceleratorSelectionForeground);
        MenuItemUIHelper.paintArrowIcon(g, lh, lr, foreground);

        // Restore original graphics font and color
        g.setColor(holdc);
        g.setFont(holdf);
    }
    private void paintText(Graphics g, MenuItemLayoutHelper lh, MenuItemLayoutHelper.LayoutResult lr) {
        if(!lh.getText().equals("")) {
            if(lh.getHtmlView() != null) {
                // Text is HTML
                lh.getHtmlView().paint(g, lr.getTextRect());
            }else{
                // Text isn't HTML
                paintText(g, lh.getMenuItem(), lr.getTextRect(), lh.getText());
            }
        }
    }
}

class RAABasicMenuItemUI extends javax.swing.plaf.basic.BasicMenuItemUI {
    @Override protected void paintMenuItem(Graphics g, JComponent c, Icon checkIcon, Icon arrowIcon, Color background, Color foreground, int defaultTextIconGap) {
        // Save original graphics font and color
        Font holdf = g.getFont();
        Color holdc = g.getColor();

        //System.out.println(defaultTextIconGap);

        JMenuItem mi = (JMenuItem) c;
        g.setFont(mi.getFont());

        Rectangle viewRect = new Rectangle(0, 0, mi.getWidth(), mi.getHeight());
        MenuItemUIHelper.applyInsets(viewRect, mi.getInsets());

        MenuItemLayoutHelper lh = new MenuItemLayoutHelper(
            mi, checkIcon, arrowIcon, viewRect, defaultTextIconGap, "+", //acceleratorDelimiter,
            true, mi.getFont(), acceleratorFont, MenuItemLayoutHelper.useCheckAndArrow(menuItem), getPropertyPrefix());
        MenuItemLayoutHelper.LayoutResult lr = lh.layoutMenuItem();

        paintBackground(g, mi, background);
        MenuItemUIHelper.paintCheckIcon(g, lh, lr, holdc, foreground);
        MenuItemUIHelper.paintIcon(g, lh, lr, holdc);
        paintText(g, lh, lr);
        MenuItemUIHelper.paintAccText(g, lh, lr, disabledForeground, acceleratorForeground, acceleratorSelectionForeground);
        MenuItemUIHelper.paintArrowIcon(g, lh, lr, foreground);

        // Restore original graphics font and color
        g.setColor(holdc);
        g.setFont(holdf);
    }
    private void paintText(Graphics g, MenuItemLayoutHelper lh, MenuItemLayoutHelper.LayoutResult lr) {
        if(!lh.getText().equals("")) {
            if(lh.getHtmlView() != null) {
                // Text is HTML
                lh.getHtmlView().paint(g, lr.getTextRect());
            }else{
                // Text isn't HTML
                paintText(g, lh.getMenuItem(), lr.getTextRect(), lh.getText());
            }
        }
    }
}