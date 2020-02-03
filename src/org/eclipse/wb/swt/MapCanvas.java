package org.eclipse.wb.swt;

import java.util.HashSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class MapCanvas extends Canvas{

	private int dropIndicatorSize = 20;
	private int dropStartX, dropStartY, dropEndX, dropEndY = 0;
	private boolean drawDropStart, drawDropEnd, drawLZ = false;
	
	private HashSet<MouseMoveListener> mouseMoveListeners = new HashSet<MouseMoveListener>();

	public MapCanvas(Composite parent, int style) {
		super(parent, style);
	}

	public void drawMap() {
	    this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Image mapImage;
				mapImage = new Image(getDisplay(), "img/map.png");
			    e.gc.drawImage(mapImage, 0, 0);
			    mapImage.dispose();
//			    // Debug: draw the colored map
//				Image mapImage2;
//				mapImage2 = new Image(getDisplay(), "img/map_colored2.png");
//				ImageData data2 = mapImage2.getImageData();
//				e.gc.drawImage(mapImage2, 0, 0);
//				mapImage2.dispose();
			}
		});
	    redraw();
	}

	public void drawDropStartIndicator() {
	    this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (drawDropStart) {
					e.gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_GREEN));
					e.gc.fillOval(dropStartX-dropIndicatorSize/2,dropStartY-dropIndicatorSize/2,dropIndicatorSize,dropIndicatorSize);
				}
			}
		});
	    redraw();
	}

	public void drawDropEndIndicator() {
	    this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (drawDropEnd) {
					e.gc.setLineWidth(3);
					e.gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_YELLOW));
					e.gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_RED));
					e.gc.fillOval(dropEndX-dropIndicatorSize/2,dropEndY-dropIndicatorSize/2,dropIndicatorSize,dropIndicatorSize);
					e.gc.drawLine(dropStartX, dropStartY, dropEndX, dropEndY);
				}
			}
		});
	    redraw();
	}

	public void drawLZIndicator(int x, int y) {
	    this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (drawLZ) {
					e.gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLUE));
					e.gc.fillRectangle(x-dropIndicatorSize/2, y-dropIndicatorSize/2, dropIndicatorSize, dropIndicatorSize);
				}
			}
		});
	    redraw();
	}
	
	public void setCaptureDropStart(boolean captureDropStart) {
		if (captureDropStart) {
			this.removeMouseListeners();
			this.drawDropStart = true;
			MouseMoveListener l = new MouseMoveListener() {
				public void mouseMove(MouseEvent e) {
					if (e.x <= 235) {
						if (e.y < e.x) {
							dropStartX = e.x;
							dropStartY = 0;
						} else if (470 - e.y < e.x) {
							dropStartX = e.x;
							dropStartY = 470;
						} else {
							dropStartX = 0;
							dropStartY = e.y;
						}
					} else {
						if (e.y < 470 - e.x) {
							dropStartX = e.x;
							dropStartY = 0;
						} else if (470 - e.y < 470 - e.x) {
							dropStartX = e.x;
							dropStartY = 470;
						} else {
							dropStartX = 470;
							dropStartY = e.y;
						}
					}
					drawDropStartIndicator();
				}
			};
			this.addMouseMoveListener(l);
			this.mouseMoveListeners.add(l);
		} else {
			this.removeMouseListeners();
		}
	}

	public void setCaptureDropEnd(boolean captureDropEnd) {
		if (captureDropEnd) {
			this.removeMouseListeners();
			this.drawDropEnd = true;
			MouseMoveListener l = new MouseMoveListener() {
				public void mouseMove(MouseEvent e) {
					if (e.x <= 235) {
						if (e.y < e.x) {
							dropEndX = e.x;
							dropEndY = 0;
						} else if (470 - e.y < e.x) {
							dropEndX = e.x;
							dropEndY = 470;
						} else {
							dropEndX = 0;
							dropEndY = e.y;
						}
					} else {
						if (e.y < 470 - e.x) {
							dropEndX = e.x;
							dropEndY = 0;
						} else if (470 - e.y < 470 - e.x) {
							dropEndX = e.x;
							dropEndY = 470;
						} else {
							dropEndX = 470;
							dropEndY = e.y;
						}
					}
					drawDropEndIndicator();
				}
			};
			this.addMouseMoveListener(l);
			this.mouseMoveListeners.add(l);
		} else {
			this.removeMouseListeners();
		}
	}

	public void setCaptureLZ(boolean captureLZ) {
		if (captureLZ) {
			this.removeMouseListeners();
			this.drawLZ = true;
			MouseMoveListener l = new MouseMoveListener() {
				public void mouseMove(MouseEvent e) {
					drawLZIndicator(e.x, e.y);
				}
			};
			this.addMouseMoveListener(l);
			this.mouseMoveListeners.add(l);
		} else {
			this.removeMouseListeners();
		}
	}
	
	private void removeMouseListeners() {
		for (MouseMoveListener e : this.mouseMoveListeners) {
			this.removeMouseMoveListener(e);
		}
	}
}
