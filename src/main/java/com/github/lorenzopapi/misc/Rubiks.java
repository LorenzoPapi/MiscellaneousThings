package com.github.lorenzopapi.misc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Rubiks extends JFrame implements MouseWheelListener {
	private static final long[] CUBE = new long[6];
	private static int cubeSize = 20;

	public Rubiks(){
		super("Rubiks Cube");
		this.setSize(640, 480);
		this.getContentPane().setBackground(Color.BLACK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.addMouseWheelListener(this);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.LIGHT_GRAY);
		//TOFIX
		int y = (this.getY() + this.getSize().height) / 2;
		int x = (this.getX() - this.getSize().width) / 2;
		System.out.println(x + ", " + y);


		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					if (i==1) {
						g2d.setColor(new Color(CubeColor.WHITE.rgb));
						g2d.fill3DRect(x+cubeSize*(j+3), y-cubeSize*(k+1), cubeSize, cubeSize, true);
						g2d.setColor(new Color(CubeColor.YELLOW.rgb));
						g2d.fill3DRect(x+cubeSize*(j+3), y+cubeSize*(k+3), cubeSize, cubeSize, true);
					}
					g2d.setColor(new Color(CubeColor.values()[i+1].rgb));
					g2d.fill3DRect(x+cubeSize*(j+3*i), y+cubeSize*k, cubeSize, cubeSize, true);
				}
			}
		}
	}


	public static void main(String[] args) throws InterruptedException {
		Rubiks rubiks = new Rubiks();
		SwingUtilities.invokeLater(() -> rubiks.setVisible(true));
		while (true) {
			rubiks.repaint();
			Thread.sleep(50);
		}
	}

	 private void initCube() {
		//front face:
		// 0xFFFFFF 0xFFFFFF 0xFFFFFF
		// 0xFFFFFF 0xFFFFFF 0xFFFFFF
		// 0xFFFF00 0xFF0000 0x0000FF
	 }

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		cubeSize *= event.getWheelRotation() > 0 ? 0.8 : 1.25;
		event.consume();
	}
}

enum CubeColor {
	WHITE("white", 0xFFFFFF),
	AQUA("aqua", 0x00FFFF),
	GREEN("green", 0x00FF00),
	RED("red", 0xFF0000),
	BLUE("blue", 0x0000FF),
	YELLOW("yellow", 0xFFFF00);

	String name;
	int rgb;

	CubeColor(String name, int rgb) {
		this.name = name;
		this.rgb = rgb;
	}
}

