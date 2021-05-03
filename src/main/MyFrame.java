package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

class MyFrame extends JFrame implements ActionListener {

    public static Process p;
    public static ProcessBuilder builder;
    public Container c;
    private JLabel name;
    private JLabel attachementNameL;
    private JTextField attachmentET;
    private JTextField userName;
    private JTextField password;
    private JTextField tname;
    private JTextField serverEd;
    public Boolean isScreenshotCaptured = false;
    private JTextArea textArea;
    private JButton takeScreenshot;
    private JButton sub;
    private JButton reset;
    public final JLabel tout = new JLabel();
    public final JScrollPane areaScrollPane = new JScrollPane(tout);
    private JLabel res;

    // constructor, to initialize the components
    // with default values.
    public MyFrame() throws IOException {
        setTitle("Registration Form");
        setBounds(300, 90, 1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        c = getContentPane();
        c.setLayout(null);

        name = new JLabel("Issue Id");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(80, 30);
        name.setLocation(10, 110);
        c.add(name);

        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(150, 40);
        tname.setLocation(100, 110);
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
                                BorderFactory.createTitledBorder("Comment "),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)),
                        areaScrollPane.getBorder()));
        areaScrollPane.setSize(490, 200);
        areaScrollPane.setLocation(10, 180);
        c.add(areaScrollPane);

        attachementNameL = new JLabel("Attachment Name");
        attachementNameL.setFont(new Font("Arial", Font.PLAIN, 20));
        attachementNameL.setSize(250, 20);
        attachementNameL.setLocation(10, 400);
        c.add(attachementNameL);

        attachmentET = new JTextField();
        attachmentET.setFont(new Font("Arial", Font.PLAIN, 15));
        attachmentET.setSize(300, 40);
        attachmentET.setLocation(10, 430);
        c.add(attachmentET);

        takeScreenshot = new JButton("Take Screenshot ");
        takeScreenshot.setFont(new Font("Arial", Font.PLAIN, 15));
        takeScreenshot.setSize(150, 40);
        takeScreenshot.setLocation(550, 720);
        takeScreenshot.addActionListener(this);
        c.add(takeScreenshot);


        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(150, 40);
        sub.setLocation(100, 720);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(150, 40);
        reset.setLocation(270, 720);
        reset.addActionListener(this);
        c.add(reset);

        // to show status in the bottom.
        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 30);
        res.setLocation(10, 670);
        c.add(res);

        serverEd = new HintTextField("   Server Address - sakthivel.atlassian.net ");
        serverEd.setFont(new Font("Arial", Font.PLAIN, 15));
        serverEd.setSize(370, 40);
        serverEd.setLocation(500, 35);
        c.add(serverEd);

        // jira user Name and password
        name = new JLabel("User Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(180, 20);
        name.setLocation(500, 80);
        c.add(name);

        userName = new HintTextField(" Email Id");
        userName.setFont(new Font("Arial", Font.PLAIN, 15));
        userName.setSize(350, 40);
        userName.setLocation(620, 80);
        c.add(userName);

        name = new JLabel("API Token");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(170, 20);
        name.setLocation(500, 120);
        c.add(name);

        password = new HintTextField("  API Token");
        password.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setSize(350, 40);
        password.setLocation(620, 120);
        c.add(password);


        setVisible(true);
    }

    // method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            res.setText(" ");
            String uName = userName.getText();
            String pass = password.getText();
            String issueid = tname.getText();
            String commentTxt = textArea.getText();


            if (!uName.equals("") && !pass.equals("") && !issueid.equals("") && !commentTxt.equals("") && !isScreenshotCaptured && !serverEd.getText().equals("")) {

                System.out.println("in side........if ");

                try {
                    String status = JiraUtil.postCommentOnlyToIssue(uName, pass, issueid, commentTxt, serverEd.getText());
                    res.setText(status);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            } else {
                System.out.println(">>>> out side if comment only..");

            }

            File f = new File("/Users/sakthiveliyappan/Downloads/" + attachmentET.getText() + ".png");
            if (f.exists() && !uName.equals("") && !pass.equals("") && !issueid.equals("") && !commentTxt.equals("") && isScreenshotCaptured && !serverEd.getText().equals("")) {
                System.out.println("in side...attachment.....if ");


                try {
                    String status = JiraUtil.postAttachmentWithComment(uName, pass, issueid, commentTxt, attachmentET.getText(), "/Users/sakthiveliyappan/Downloads/" + attachmentET.getText() + ".png", serverEd.getText());
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
            areaScrollPane.setSize(450, 500);
            areaScrollPane.setLocation(550, 200);
            c.add(areaScrollPane);
            tout.setIcon(null);
            tout.revalidate();
            areaScrollPane.remove(tout);
            attachmentET.setText(def);
            isScreenshotCaptured = false;

        }
        else if (e.getSource() == takeScreenshot) {

            areaScrollPane.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            areaScrollPane.setSize(450, 500);
            areaScrollPane.setLocation(550, 200);
            c.add(areaScrollPane);

            try {


                runCommand("adb shell screencap /sdcard/" + attachmentET.getText() + ".png");
                runCommand("adb  pull /sdcard/" + attachmentET.getText() + ".png  /Users/sakthiveliyappan/Downloads/" + attachmentET.getText() + ".png");


                BufferedImage myPicture = ImageIO.read(new File("/Users/sakthiveliyappan/Downloads/" + attachmentET.getText() + ".png"));

                tout.setIcon(new ImageIcon(myPicture));
                isScreenshotCaptured = true;


            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


            System.out.println("???? done ..");


        }


    }

    public static String runCommand(String command) throws InterruptedException, IOException {
        String os = System.getProperty("os.name");

        // build cmd proccess according to os
        if (os.contains("Windows")) // if windows
        {
            builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Thread.sleep(1000);
            p = builder.start();
        } else // If Mac
            p = Runtime.getRuntime().exec(command);

        // get std output
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

        System.out.println("r" + r.toString());

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
        System.out.println(" Commend finish.." + allLine);
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}

class HintTextField extends JTextField {
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

    private final String _hint;
}





