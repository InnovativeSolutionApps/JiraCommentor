package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

class MyFrame extends JFrame implements ActionListener {

    public static Process p;
    public static ProcessBuilder builder;
    public Container c;
    private JLabel name;
    private JTextArea header;
    private JLabel attachementNameL;
    private JLabel serverLabel;
    private JTextField attachmentET;
    private JTextField userName;
    private JTextField password;
    private JTextField tname;
    private JTextField serverEd;
    private JTextArea textArea;
    private JButton takeScreenshot;
    private JButton sub;
    private JButton reset;
    public final JLabel tout = new JLabel();
    public final JScrollPane areaScrollPane = new JScrollPane(tout);
    private JLabel res;
    public String home;


    public MyFrame() throws IOException, InterruptedException {


        String headerText =
                 " Innovative Solutions -  Contact : innovativesolutionsapps@gmail.com" +
                "\n" +
                "Transform your mundane and Repetitive tasks from manual to automation and increase you productivity. \n" +
                         "We will help you to that for you.let me know if you have something in your mind" +"\n" +
                         " to achieve like this."
                ;

        home = System.getProperty("user.home");
        setTitle("Jira Screenshot Attachment Tool - V1.0   By SAKTHIVEL IYAPPAN  ");
        setBounds(300, 90, 1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        c = getContentPane();
        c.setLayout(null);

        name = new JLabel("Issue Id");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(80, 30);
        name.setLocation(10, 270);
        c.add(name);

        tname = new RoundedJTextField(50,"");
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(150, 40);
        tname.setLocation(150, 270);
        c.add(tname);


        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(500, 300));
        areaScrollPane.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Bug Description "),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        areaScrollPane.getBorder()));
        areaScrollPane.setSize(490, 200);
        areaScrollPane.setLocation(10, 400);
        c.add(areaScrollPane);

        attachementNameL = new JLabel("Screenshot Name:");
        attachementNameL.setFont(new Font("Arial", Font.PLAIN, 20));
        attachementNameL.setSize(250, 20);
        attachementNameL.setLocation(10, 340);
        c.add(attachementNameL);

        attachmentET = new RoundedJTextField(50, " file name ");
        attachmentET.setFont(new Font("Arial", Font.PLAIN, 15));
        attachmentET.setSize(300, 40);
        attachmentET.setLocation(200, 330);
        c.add(attachmentET);

        takeScreenshot = new JButton("Take Screenshot ");
        takeScreenshot.setFont(new Font("Arial", Font.PLAIN, 15));
        takeScreenshot.setSize(150, 40);
        takeScreenshot.setLocation(380, 720);
        takeScreenshot.addActionListener(this);
        c.add(takeScreenshot);

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(150, 40);
        sub.setLocation(20, 720);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(150, 40);
        reset.setLocation(200, 720);
        reset.addActionListener(this);
        c.add(reset);

        // to show status in the bottom.
        res = new JLabel("");
        res.setForeground (Color.red);
        res.setFont(new Font("Arial", Font.PLAIN, 18));
        res.setSize(500, 50);
        res.setLocation(20, 640);
        c.add(res);

        serverLabel = new JLabel("Server Name");
        serverLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        serverLabel.setSize(80, 30);
        serverLabel.setLocation(10, 210);
        c.add(serverLabel);

        serverEd = new RoundedJTextField(50," Server Address - xxxx.atlassian.net ");
        serverEd.setFont(new Font("Arial", Font.PLAIN, 15));
        serverEd.setSize(350, 40);
        serverEd.setLocation(150, 210);
        c.add(serverEd);

        // jira user Name and password
        header = new JTextArea();
        header.setFont(new Font("Arial", Font.ITALIC, 14));
        header.setSize(500, 70);
        header.setForeground(Color.BLUE);
        header.setLocation(10, 10);
        c.add(header);
        header.setText(headerText);
        header.setEnabled(false);

        name = new JLabel("User Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(180, 30);
        name.setLocation(10, 94);
        c.add(name);

        userName = new RoundedJTextField(50," Email Id");
        userName.setFont(new Font("Arial", Font.PLAIN, 15));
        userName.setSize(350, 40);
        userName.setLocation(150, 90);
        c.add(userName);

        name = new JLabel("API Token");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(170, 20);
        name.setLocation(10, 163);
        c.add(name);

        password = new RoundedJTextField(50," API Token");
        password.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setSize(350, 40);
        password.setLocation(150, 150);
        c.add(password);

        setVisible(true);
        checkDeviceConnectedORNOT();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            res.setText(" ");
            String uName = userName.getText();
            String pass = password.getText();
            String issueid = tname.getText();
            String commentTxt = textArea.getText();

            File f = new File(home + "/Downloads/" + attachmentET.getText() + ".png");
            if (!f.exists() && !uName.equals("") && !pass.equals("") && !issueid.equals("") && !commentTxt.equals("") && !serverEd.getText().equals("")) {
                try {
                    String status = JiraUtil.postCommentOnlyToIssue(uName, pass, issueid, commentTxt, serverEd.getText());
                    res.setText(status);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                System.out.println(">>>> out side if comment only..");
            }
            /*System.out.println(f.exists());
            System.out.println(!uName.equals(""));
            System.out.println(!pass.equals(""));
            System.out.println(!issueid.equals(""));
            System.out.println(!commentTxt.equals(""));
            System.out.println(!serverEd.getText().equals(""));*/

            if (f.exists() && !uName.equals("") && !pass.equals("") && !issueid.equals("") && !commentTxt.equals("") && !serverEd.getText().equals("")) {
                try {
                    String status = JiraUtil.postAttachmentWithComment(uName, pass, issueid, commentTxt, attachmentET.getText(), home + "/Downloads/" + attachmentET.getText() + ".png", serverEd.getText());
                    res.setText(status);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                System.out.println(">>>> out side if Attachment.. ");
            }
        } else if (e.getSource() == reset) {
            String def = "";
            res.setText(def);
            tname.setText(def);
            textArea.setText(def);
            areaScrollPane.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            areaScrollPane.setSize(450, 790);
            areaScrollPane.setLocation(550, 10);
            c.add(areaScrollPane);
            tout.setIcon(null);
            tout.revalidate();
            areaScrollPane.remove(tout);
            attachmentET.setText(def);
        } else if (e.getSource() == takeScreenshot) {
            areaScrollPane.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            areaScrollPane.setSize(450, 790);
            areaScrollPane.setLocation(550, 10);
            c.add(areaScrollPane);
            try {
                if (checkADB_StartedOrNot() && checkDeviceConnectedORNOT()) {
                    runCommand("adb shell screencap /sdcard/" + attachmentET.getText() + ".png");
                    String pullToSystem = home + "/Downloads/" + attachmentET.getText() + ".png";
                    String adbPullCmd = "adb  pull /sdcard/" + attachmentET.getText() + ".png" + " " + pullToSystem + "";
                    runCommand(adbPullCmd);

                    ImageIcon imageIcon = new ImageIcon(home + "/Downloads/" + attachmentET.getText() + ".png"); // load the image to a imageIcon
                    Image image = imageIcon.getImage(); // transform it
                    Image newimg = image.getScaledInstance(430, 780, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                    imageIcon = new ImageIcon(newimg);  // transform it back
                    tout.setIcon(imageIcon);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    public static String runCommand(String command) throws InterruptedException, IOException {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) // if windows
        {
            builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Thread.sleep(1000);
            p = builder.start();
        } else // If Mac
            p = Runtime.getRuntime().exec(command);
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "";
        String allLine = "";
        int i = 1;
        while ((line = r.readLine()) != null) {
            System.out.println(i + ". " + line);
            allLine = allLine + "" + line + "\n";
            if (line.contains("Console LogLevel: debug"))
                break;
            i++;
        }
        return allLine;
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.TRUE);
                try {
                    MyFrame f = new MyFrame();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public Boolean checkADB_StartedOrNot() throws IOException, InterruptedException {

        boolean isConnected = false;
        runCommand("adb kill-server");
        String output = runCommand("adb start-server");
        String[] lines = output.split("\n");
        if (lines.length == 1) {
            res.setText("adb service already started");
            isConnected = true;
            checkDeviceConnectedORNOT();
        } else if
        (lines[1].equalsIgnoreCase("* daemon started successfully *")) {
            res.setText("adb service started");
            isConnected = true;
            checkDeviceConnectedORNOT();
        } else if (lines[0].contains("internal or external command")) {
            res.setText("adb path not set in system varibale");
        }
        return isConnected;
    }

    public Boolean checkDeviceConnectedORNOT() throws IOException, InterruptedException {
        String output = runCommand("adb devices");
        String[] lines = output.split("\n");
        boolean isConnected = false;
        if (lines.length <= 1) {
            res.setText("No Device Connected");
            runCommand("adb kill-server");
        } else if (lines.length >= 2 && lines[1].contains("device")) {
            isConnected = true;
            res.setText(" Device Connected");
        }
        return isConnected;
    }

}

class HintTextField extends JTextField {
    private final String _hint;

    public HintTextField(String hint) {
        _hint = hint;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getText().length() == 0) {
            int h = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            int c0 = getBackground().getRGB();
            int c1 = getForeground().getRGB();
            int m = 0xfefefefe;
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
            g.setColor(new Color(c2, true));
            g.drawString(_hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }

}

// implement a round-shaped JTextField
class RoundedJTextField extends JTextField {
    private Shape shape;
    private  String _hint;

    public RoundedJTextField(int size,String hint) {
        super(size);
        _hint = hint;
        setOpaque(false);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getText().length() == 0) {
            int h = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            int c0 = getBackground().getRGB();
            int c1 = getForeground().getRGB();
            int m = 0xfefefefe;
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
            g.setColor(new Color(c2, true));
            g.drawString(_hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        return shape.contains(x, y);
    }
}






