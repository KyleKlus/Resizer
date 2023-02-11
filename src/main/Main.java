package main;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1554119310077614846L;
	private JTextArea txt;
	private JScrollPane scp;
	private JPanel hbx;
	private JTextField width;
	private JTextField height;
	private JButton search;
	private JButton go;
	private File data;
	private String[] files;
	
	public Main() {
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		setTitle("PNG-Resizer v0.1");
		setMinimumSize(new Dimension(400, 300));
		setPreferredSize(new Dimension(400, 300));
		setMaximumSize(new Dimension(400, 300));
		
		hbx=new JPanel();
		hbx.setLayout(new BoxLayout(hbx, BoxLayout.X_AXIS));
		
		txt=new JTextArea();
		
		scp=new JScrollPane();
		scp.setMinimumSize(new Dimension(380, 200));
		scp.setPreferredSize(new Dimension(380, 200));
		scp.setMaximumSize(new Dimension(380, 200));
		scp.setViewportView(txt);
		
		width=new JTextField();
		width.setMinimumSize(new Dimension(40, 25));
		width.setPreferredSize(new Dimension(40,25));
		width.setMaximumSize(new Dimension(40, 25));
		
		height=new JTextField();
		height.setMinimumSize(new Dimension(40, 25));
		height.setPreferredSize(new Dimension(40,25));
		height.setMaximumSize(new Dimension(40, 25));
		
		search=new JButton("Search");
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				data = new File("images");
				files=data.list();
				for(int i=0;i<files.length;i++) {
					txt.append(files[i]+"\n");
				}
			}
		});
		
		go=new JButton("Go");
		go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(files.length>0) {
					for(int i=0;i<files.length;i++) {
						try {
							if(getExtensionByStringHandling(files[i]).equals("png")) {
								File imgFile=new File("images/"+files[i]);
								Image img =ImageIO.read(imgFile);
								img=img.getScaledInstance(Integer.parseInt(width.getText()), Integer.parseInt(height.getText()), Image.SCALE_SMOOTH);
								ImageIO.write(toBufferedImage(img), "png", new File("images/"+files[i]));
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		hbx.add(width);
		hbx.add(Box.createRigidArea(new Dimension(10, 0)));
		hbx.add(new JLabel("x"));
		hbx.add(Box.createRigidArea(new Dimension(10, 0)));
		hbx.add(height);
		hbx.add(Box.createRigidArea(new Dimension(10, 0)));
		hbx.add(search);
		hbx.add(Box.createRigidArea(new Dimension(10, 0)));
		hbx.add(go);
		
		add(Box.createHorizontalStrut(0));
		add(scp);
		add(Box.createHorizontalStrut(0));
		add(hbx);
		add(Box.createHorizontalStrut(0));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		new Main();
	}
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	public String getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	      .filter(f -> f.contains("."))
	      .map(f -> f.substring(filename.lastIndexOf(".") + 1)).get();
	}
}
