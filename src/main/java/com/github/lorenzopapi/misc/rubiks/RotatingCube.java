package com.github.lorenzopapi.misc.rubiks;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class RotatingCube extends JPanel {

	private final double[][][][] vertex =
	{
			{
					{
							{-1, -1, -1},
							{1, -1, -1}
					},
					{
							{-1, 1, -1},
							{1, 1, -1}
					}
			},
			{
					{
							{-1, -1, 1},
							{1, -1, 1}
					},
					{
							{-1, 1, 1},
							{1, 1, 1}
					}
			}
	};

	// rotations in radians
	private final double xyR = 0;
	private final double xzR = Math.toRadians(5);
	private final double yzR = 0;

	// view attributes
	private final static int IMAGE_SIZE = 500,
			SCALE = 100,
			OFFSET = 200,
			DIAMETER = 8;

	@Override
	public void paintComponent(Graphics graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);

		// rotate cube
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				for (int z = 0; z < 2; z++) {
					xyRotate(vertex[x][y][z], Math.sin(xyR), Math.cos(xyR));
					xzRotate(vertex[x][y][z], Math.sin(xzR), Math.cos(xzR));
					yzRotate(vertex[x][y][z], Math.sin(yzR), Math.cos(yzR));
				}
			}
		}

		// draw cube edges
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				graphics.setColor(Color.red);
				drawEdge(vertex[x][y][0][0], vertex[x][y][0][1], vertex[x][y][1][0], vertex[x][y][1][1], graphics);
				graphics.setColor(Color.blue);
				drawEdge(vertex[x][0][y][0], vertex[x][0][y][1], vertex[x][1][y][0], vertex[x][1][y][1], graphics);
				graphics.setColor(Color.black);
				drawEdge(vertex[0][x][y][0], vertex[0][x][y][1], vertex[1][x][y][0], vertex[1][x][y][1], graphics);
			}
		}

		drawFace(vertex[1][0][0][0], vertex[1][0][0][1], vertex[1][1][1][0], vertex[1][1][1][1], graphics);

		// draw cube vertices
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				for (int z = 0; z < 2; z++) {
					drawVertex(vertex[x][y][z][0], vertex[x][y][z][1], graphics);
				}
			}
		}
	}

	final void xyRotate(double[] p, double sin, double cos) {
		double temp;
		temp = cos * p[0] + sin * p[1];
		p[1] = -sin * p[0] + cos * p[1];
		p[0] = temp;
	}

	final void xzRotate(double[] p, double sin, double cos) {
		double temp;
		temp = cos * p[0] + sin * p[2];
		p[2] = -sin * p[0] + cos * p[2];
		p[0] = temp;
	}

	final void yzRotate(double[] p, double sin, double cos) {
		double temp;
		temp = cos * p[1] + sin * p[2];
		p[2] = -sin * p[1] + cos * p[2];
		p[1] = temp;
	}

	final void drawFace(double x1, double y1, double x2, double y2, Graphics g) {
		System.out.println(x1 + ", " + y1 + ", " + x2 + ", " + y2);
		g.drawRect((int) (x1 * SCALE) + OFFSET, (int) (y1 * SCALE) + OFFSET, (int) (x2 * SCALE) + OFFSET, (int) (y2 * SCALE) + OFFSET);
	}

	final void drawEdge(double x1, double y1, double x2, double y2, Graphics g) {
		g.drawLine((int) (x1 * SCALE) + OFFSET, (int) (-y1 * SCALE) + OFFSET, (int) (x2 * SCALE) + OFFSET, (int) (-y2 * SCALE) + OFFSET);
	}

	final void drawVertex(double x, double y, Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval((int) (SCALE * x) + OFFSET - DIAMETER / 2, (int) (SCALE * (-y)) + OFFSET - DIAMETER / 2, DIAMETER, DIAMETER);

		g.setColor(Color.black);
		g.drawOval((int) (SCALE * x) + OFFSET - DIAMETER / 2, (int) (SCALE * (-y)) + OFFSET - DIAMETER / 2, DIAMETER, DIAMETER);
	}

	public static void main(String[] args) throws InterruptedException {
		RotatingCube jPanel = new RotatingCube();
		JFrame application = new JFrame("Rotating Cube");
		System.out.println(Arrays.toString(new double[]{jPanel.vertex[0][0][0][0], jPanel.vertex[0][0][0][1], jPanel.vertex[0][1][1][0], jPanel.vertex[0][1][1][1]}));
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.add(jPanel);
		application.setSize(400, 400);
		application.setVisible(true);
		while (true) {
			Thread.sleep(50);
			jPanel.repaint();
		}
	}
}