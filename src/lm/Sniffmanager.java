/*
 * Main window for the application.
 */

package lm;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import lm.data.Location;
import lm.data.LootItem;
import lm.data.Match;
import lm.systems.KeyListener;
import lm.systems.LocationManager;
import lm.systems.LootManager;
import lm.systems.MatchManager;
import lm.systems.SoundManager;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.LineUnavailableException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.wb.swt.MapCanvas;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class Sniffmanager extends Thread {

	private KeyListener keyListener = null;
	private	VoiceManager voiceManager = VoiceManager.getInstance();
	private LocationManager locationManager = new LocationManager();
	private LootManager lootManager = new LootManager();
	private LootItem lastLootedItem = null;

	private Shell shell;
	private Display display;
	private MapCanvas cvsMap;
	private Button btnDiscardAndRestart;
	private Button btnTimerStart;
	private Button btnTimerStop;
	private Button btnSaveSameSquad;
	private Button btnSaveNewSquad;
	private Button btnSaveAndQuit;
	private Button btnClearAllLoot;
	private Button btnDiscardAndQuit;
	private Label lblDebug;
	private Text txtEventLog;
	private Text txtPlayer1;
	private Text txtPlayer2;
	private Text txtPlayer3;
	private Text txtOwnKillsLastGame;
	private Text txtSquadKillsLastGame;
	private Text txtMatchTime;
	private Text txtLengthLastGame;
	private Text txtTimer;
	private Text txtOwnKillsThisGame;
	private Text txtSquadKillsThisGame;
	private Text txtLengthThisGame;
	private Table tblLootLog;
	private TableColumn tblclmnTime;
	private TableColumn tblclmnItem;
	private TableColumn tblclmnQuality;
	private TableColumn tblclmnLocation;
	private TableColumn tblclmnDistanceFromStart;
	private TableColumn tblclmnDistanceFromEnd;

	// General purpose timer
	private Timer timer;
	// Tracking player movements
	private Location currentLocation = null;
	private Location dropLocation = null;
	// Mouse listener for the canvas
	private MouseMoveListener cvsMouseListener = null;
	// Standard save-load type tracking
	private boolean isModified = false;
	// Drop data
	private int currentStep = -1;
	private int dropStartX, dropStartY, dropEndX, dropEndY = 0;
	// TTS control
	private long lastVoiceTime = 0;
	private long limitSpeechSeconds = 2000;
	private long timerElapsedTime = 0;
	private String startTime = "";

	public void open() {
		display = Display.getDefault();
		createContents();
		
	    Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    
	    shell.setLocation(x, y);
		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN );
		shell.setBackground(SWTResourceManager.getColor(0, 0, 0));
		shell.setMinimumSize(new Point(900, 650));
		shell.setSize(900, 657);
		shell.setText("Sniffmanager");
		
		lblDebug = new Label(shell, SWT.SHADOW_NONE | SWT.CENTER);
		lblDebug.setAlignment(SWT.RIGHT);
		lblDebug.setLocation(95, 486);
		lblDebug.setSize(390, 23);
		lblDebug.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION_TEXT));
		lblDebug.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblDebug.setFont(SWTResourceManager.getFont("Tahoma", 13, SWT.NORMAL));
		
		cvsMap = new MapCanvas(shell, SWT.BORDER | SWT.NO_REDRAW_RESIZE | SWT.DOUBLE_BUFFERED);
		cvsMap.setBounds(10, 10, 475, 475);
		cvsMap.drawMap();
		
		btnDiscardAndRestart = new Button(shell, SWT.NONE);
		btnDiscardAndRestart.setEnabled(false);
		btnDiscardAndRestart.setBounds(673, 466, 211, 23);
		btnDiscardAndRestart.setText("Discard and Restart");
		
		Label lblEventLog = new Label(shell, SWT.NONE);
		lblEventLog.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblEventLog.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblEventLog.setBounds(16, 512, 49, 13);
		lblEventLog.setText("Event Log");
		
		Button btnCopyLog = new Button(shell, SWT.NONE);
		btnCopyLog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StringSelection selection = new StringSelection(txtEventLog.getText());
			    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
			}
		});
		btnCopyLog.setBounds(108, 509, 98, 19);
		btnCopyLog.setText("Copy to Clipboard");
		
		Button btnClearLog = new Button(shell, SWT.NONE);
		btnClearLog.setText("Clear");
		btnClearLog.setBounds(71, 509, 37, 19);
		btnClearLog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtEventLog.setText("");
			}
		});
		
		txtEventLog = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtEventLog.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		txtEventLog.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtEventLog.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtEventLog.setBounds(10, 530, 874, 92);
		
		Group grpMatchInformation = new Group(shell, SWT.NONE);
		grpMatchInformation.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		grpMatchInformation.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		grpMatchInformation.setFont(SWTResourceManager.getFont("Tahoma", 12, SWT.NORMAL));
		grpMatchInformation.setText("Match Information");
		grpMatchInformation.setBounds(494, 5, 390, 119);
		
		Label lblSquadLevels = new Label(grpMatchInformation, SWT.NONE);
		lblSquadLevels.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblSquadLevels.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblSquadLevels.setBounds(10, 32, 64, 13);
		lblSquadLevels.setText("Squad levels:");
		
		txtPlayer1 = new Text(grpMatchInformation, SWT.BORDER | SWT.RIGHT);
		txtPlayer1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtPlayer1.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtPlayer1.setBounds(80, 30, 31, 19);
		txtPlayer1.setText("100");
		
		txtPlayer2 = new Text(grpMatchInformation, SWT.BORDER | SWT.RIGHT);
		txtPlayer2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtPlayer2.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtPlayer2.setBounds(117, 30, 31, 19);
		txtPlayer2.setText("75");
		
		txtPlayer3 = new Text(grpMatchInformation, SWT.BORDER | SWT.RIGHT);
		txtPlayer3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtPlayer3.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtPlayer3.setBounds(154, 30, 31, 19);
		txtPlayer3.setText("75");
		
		Label lblSquadKillsLast = new Label(grpMatchInformation, SWT.NONE);
		lblSquadKillsLast.setText("Kills last game:");
		lblSquadKillsLast.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblSquadKillsLast.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblSquadKillsLast.setBounds(10, 63, 70, 13);
		
		txtOwnKillsLastGame = new Text(grpMatchInformation, SWT.BORDER | SWT.RIGHT);
		txtOwnKillsLastGame.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtOwnKillsLastGame.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtOwnKillsLastGame.setBounds(86, 60, 45, 19);
		txtOwnKillsLastGame.setText("3");
		
		txtSquadKillsLastGame = new Text(grpMatchInformation, SWT.BORDER | SWT.RIGHT);
		txtSquadKillsLastGame.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtSquadKillsLastGame.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtSquadKillsLastGame.setBounds(140, 60, 45, 19);
		txtSquadKillsLastGame.setText("5");
		
		Label lblMatchDate = new Label(grpMatchInformation, SWT.NONE);
		lblMatchDate.setText("Match day / time:");
		lblMatchDate.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblMatchDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblMatchDate.setBounds(196, 32, 88, 13);
		
		txtMatchTime = new Text(grpMatchInformation, SWT.BORDER | SWT.RIGHT);
		txtMatchTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtMatchTime.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtMatchTime.setBounds(287, 30, 93, 19);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE kk:mm");
		LocalDateTime now = LocalDateTime.now();
		txtMatchTime.setText(dtf.format(now));
		
		Label lblLength = new Label(grpMatchInformation, SWT.NONE);
		lblLength.setText("Length of Last Game:");
		lblLength.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblLength.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblLength.setBounds(196, 63, 103, 13);
		
		txtLengthLastGame = new Text(grpMatchInformation, SWT.BORDER | SWT.RIGHT);
		txtLengthLastGame.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtLengthLastGame.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtLengthLastGame.setBounds(305, 60, 75, 19);
		txtLengthLastGame.setText("12:00");
		
		Label lblKillsThisGame = new Label(grpMatchInformation, SWT.NONE);
		lblKillsThisGame.setText("Kills this game:");
		lblKillsThisGame.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblKillsThisGame.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblKillsThisGame.setBounds(10, 93, 70, 13);
		
		txtOwnKillsThisGame = new Text(grpMatchInformation, SWT.BORDER | SWT.RIGHT);
		txtOwnKillsThisGame.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtOwnKillsThisGame.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtOwnKillsThisGame.setBounds(86, 90, 45, 19);
		txtOwnKillsThisGame.setText("TBD");
		
		txtSquadKillsThisGame = new Text(grpMatchInformation, SWT.BORDER | SWT.RIGHT);
		txtSquadKillsThisGame.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtSquadKillsThisGame.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtSquadKillsThisGame.setBounds(140, 90, 45, 19);
		txtSquadKillsThisGame.setText("TBD");
		
		Label lblLengthOfThis = new Label(grpMatchInformation, SWT.NONE);
		lblLengthOfThis.setText("Length of This Game:");
		lblLengthOfThis.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblLengthOfThis.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblLengthOfThis.setBounds(196, 93, 103, 13);
		
		txtLengthThisGame = new Text(grpMatchInformation, SWT.BORDER | SWT.RIGHT);
		txtLengthThisGame.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtLengthThisGame.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtLengthThisGame.setBounds(305, 90, 75, 19);
		txtLengthThisGame.setText("TBD");
		
		btnTimerStart = new Button(shell, SWT.NONE);
		btnTimerStart.setEnabled(false);
		btnTimerStart.setText("Start");
		btnTimerStart.setBounds(519, 451, 61, 19);
		
		btnTimerStop = new Button(shell, SWT.NONE);
		btnTimerStop.setEnabled(false);
		btnTimerStop.setText("Stop");
		btnTimerStop.setBounds(579, 451, 61, 19);
		
		txtTimer = new Text(shell, SWT.BORDER | SWT.CENTER);
		txtTimer.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtTimer.setForeground(SWTResourceManager.getColor(204, 255, 0));
		txtTimer.setText("0:00");
		txtTimer.setFont(SWTResourceManager.getFont("Tahoma", 18, SWT.NORMAL));
		txtTimer.setBounds(519, 418, 120, 35);
		
		btnSaveSameSquad = new Button(shell, SWT.NONE);
		btnSaveSameSquad.setEnabled(false);
		btnSaveSameSquad.setBounds(673, 375, 211, 23);
		btnSaveSameSquad.setText("Save and Play (Same Squad)");
		
		btnSaveNewSquad = new Button(shell, SWT.NONE);
		btnSaveNewSquad.setEnabled(false);
		btnSaveNewSquad.setText("Save and Play (New Squad)");
		btnSaveNewSquad.setBounds(673, 399, 211, 23);
		
		btnSaveAndQuit = new Button(shell, SWT.NONE);
		btnSaveAndQuit.setEnabled(false);
		btnSaveAndQuit.setText("Save and Quit");
		btnSaveAndQuit.setBounds(673, 423, 211, 23);
		
		Label lblLootLog = new Label(shell, SWT.NONE);
		lblLootLog.setText("Loot Log");
		lblLootLog.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblLootLog.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblLootLog.setBounds(504, 150, 49, 13);
		
		tblLootLog = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tblLootLog.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		tblLootLog.setForeground(SWTResourceManager.getColor(204, 255, 0));
		tblLootLog.setBounds(494, 169, 390, 188);
		tblLootLog.setHeaderVisible(true);
		tblLootLog.setLinesVisible(true);
		
		tblclmnTime = new TableColumn(tblLootLog, SWT.NONE);
		tblclmnTime.setWidth(43);
		tblclmnTime.setText("Time");
		
		tblclmnLocation = new TableColumn(tblLootLog, SWT.NONE);
		tblclmnLocation.setWidth(107);
		tblclmnLocation.setText("Location");
		
		tblclmnItem = new TableColumn(tblLootLog, SWT.NONE);
		tblclmnItem.setWidth(63);
		tblclmnItem.setText("Item");
		
		tblclmnQuality = new TableColumn(tblLootLog, SWT.NONE);
		tblclmnQuality.setWidth(50);
		tblclmnQuality.setText("Quality");
		
		tblclmnDistanceFromStart = new TableColumn(tblLootLog, SWT.NONE);
		tblclmnDistanceFromStart.setWidth(55);
		tblclmnDistanceFromStart.setText("d Start");
		
		tblclmnDistanceFromEnd = new TableColumn(tblLootLog, SWT.NONE);
		tblclmnDistanceFromEnd.setWidth(51);
		tblclmnDistanceFromEnd.setText("d End");
		
		btnClearAllLoot = new Button(shell, SWT.NONE);
		btnClearAllLoot.setEnabled(false);
		btnClearAllLoot.setText("Clear All Loot");
		btnClearAllLoot.setBounds(809, 145, 75, 23);
		
		btnDiscardAndQuit = new Button(shell, SWT.NONE);
		btnDiscardAndQuit.setEnabled(false);
		btnDiscardAndQuit.setText("Discard and Quit");
		btnDiscardAndQuit.setBounds(673, 490, 211, 23);
		
		txtEventLog.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				e.doit = false;
			}
		});

		initWorkspace();
		startNewSession();
	}
	
	/*
	 * Initialization of a new game workspace and button anonymous functions
	 */
	private void initWorkspace() {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		keyListener = new KeyListener();
		keyListener.setParentWindow(this);
		GlobalScreen.addNativeKeyListener(keyListener);
		
		// Save the data using old squad data (same team as last run)
		btnSaveSameSquad.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String p1 = txtPlayer1.getText();
				String p2 = txtPlayer2.getText();
				String p3 = txtPlayer3.getText();
				String ownk = txtOwnKillsThisGame.getText();
				String sqdk = txtSquadKillsThisGame.getText();
				String time = txtLengthThisGame.getText();
				finalizeSession();
				startNewSession();
				txtPlayer1.setText(p1);
				txtPlayer2.setText(p2);
				txtPlayer3.setText(p3);
				txtOwnKillsLastGame.setText(ownk);
				txtSquadKillsLastGame.setText(sqdk);
				txtLengthLastGame.setText(time);
				isModified = false;
			}
		});
		
		// Save the data using new squad data
		btnSaveNewSquad.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String ownk = txtOwnKillsThisGame.getText();
				String time = txtLengthThisGame.getText();
				finalizeSession();
				startNewSession();
				txtOwnKillsLastGame.setText(ownk);
				txtLengthLastGame.setText(time);
				isModified = false;
			}
		});
		
		btnSaveAndQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				finalizeSession();
				System.exit(0);
			}
		});
		
		btnDiscardAndQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean proceed = true;
				if (isModified) {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
					messageBox.setText("Warning");
					messageBox.setMessage("Quit without saving?");
					int buttonID = messageBox.open();
					switch (buttonID) {
						case SWT.YES:
							break;
						case SWT.NO:
							proceed = false;
							break;
					}
				}
				if (proceed) {
					System.exit(0);
				}
			}
		});
		
		btnClearAllLoot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lootManager = new LootManager();
				updateLootLog();
			}
		});
		
		// Basically a full reset with nothing saved at all
		btnDiscardAndRestart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean proceed = true;
				if (isModified) {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
					messageBox.setText("Warning");
					messageBox.setMessage("Discard the current session without saving?");
					int buttonID = messageBox.open();
					switch (buttonID) {
						case SWT.YES:
							break;
						case SWT.NO:
							proceed = false;
							break;
					}
				}
				if (proceed) {
					startNewSession();
				}
			}
		});

		btnTimerStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				startTimer();
			}
		});

		btnTimerStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stopTimer();
			}
		});

		cvsMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				attemptStep(e.x, e.y);
			}
		});
	}
	
	// Data initialization
	private void startNewSession() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE HH:mm");
		LocalDateTime now = LocalDateTime.now();
		txtMatchTime.setText(dtf.format(now));
		dtf = DateTimeFormatter.ofPattern("HH-mm");
		startTime = dtf.format(now);
		txtPlayer1.setText("100");
		txtPlayer2.setText("75");
		txtPlayer3.setText("75");
		txtSquadKillsThisGame.setText("TBD");
		txtSquadKillsLastGame.setText("5");
		txtOwnKillsThisGame.setText("TBD");
		txtOwnKillsLastGame.setText("3");
		txtLengthLastGame.setText("12:00");
		txtLengthThisGame.setText("TBD");
		txtTimer.setText("0:00");
		if (timer != null) {
			stopTimer();
		}
		timerElapsedTime = 0;
		txtEventLog.setText("");
		dropStartX = 0;
		dropStartY = 0;
		dropEndX = 0;
		dropEndY = 0;
		currentStep = 0;
		dropLocation = null;
		currentLocation = null;
		lootManager = new LootManager();
		updateLootLog();
		addToEventLog("[meta]\tStarted new map session");
		attemptStep(0,0);
	}
	
	// Probably not the best way to control the flow of the application, but since
	// there are only 4 possible states, it is concise
	private void attemptStep(int mouseX, int mouseY) {
		switch (currentStep) {
			case 0:
			    cvsMap.setCaptureDropStart(true);
			    this.currentStep = 0;
			    lblDebug.setText("Select dropship start location.");
				currentStep = 1;
				break;
			case 1:
				isModified = true;
				dropStartX = mouseX;
				dropStartY = mouseY;
				cvsMap.setCaptureDropStart(false);
				cvsMap.setCaptureDropEnd(true);
			    lblDebug.setText("Select dropship end location.");
				currentStep = 2;
				break;
			case 2:
				dropEndX = mouseX;
				dropEndY = mouseY;
				cvsMap.setCaptureDropEnd(false);
				addToEventLog("[drop]\tShip path: ("+dropStartX+","+dropStartY+") to ("+dropEndX+","+dropEndY+")");
			    lblDebug.setText("Select landing zone.");
			    cvsMouseListener = new MouseMoveListener() {
					public void mouseMove(MouseEvent e) {
						Location lz = getLocationFromCoord(e.x, e.y);
						if (lz == null) {
							lblDebug.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
						    lblDebug.setText("Select landing zone.");
						} else {
							lblDebug.setForeground(display.getSystemColor(SWT.COLOR_GREEN));
						    lblDebug.setText(lz.getName());
						}
					}
				};
			    cvsMap.addMouseMoveListener(cvsMouseListener);
				currentStep = 3;
				break;
			case 3:
				Location lz = getLocationFromCoord(mouseX, mouseY);
				if (lz != null) {
					cvsMap.removeMouseMoveListener(cvsMouseListener);
					currentLocation = lz;
					dropLocation = lz;
					addToEventLog("[area]\tEntered area: "+lz.getName()+" ("+lz.getX()+","+lz.getY()+")");
				    lblDebug.setText(lz.getName());
				    ttsSpeak(lz.getName());
					keyListener.setActive(true);
					if (timerElapsedTime == 0) {
						startTimer();
					}
				}
				break;
		}
	}
	
	// Handle hotkey presses
	public void keyPressed(int kc) {
		display.asyncExec(new Runnable() {
		    public void run() {
		    	try {
		    		// 97-105 = numpad1-numpad9
		    		if (kc >= 97 && kc <= 105 && kc != 101) {
		    			Location l = travelDirection(kc-96);
		    			if (l != null) {
		    				currentLocation = l;
		    				lblDebug.setText(l.getName());
							SoundManager.tone(1200, 25, 0.4);
							addToEventLog("[area]\tTravelled to "+l.getName()+" ("+l.getX()+","+l.getY()+")");
						    ttsSpeak(l.getName());
		    			} else {
							SoundManager.tone(50, 25, 0.9);
							SoundManager.tone(50, 25, 0.9);
		    			}
		    		}
		    		// 112-119 = F1-F8
		    		// Add a new loot item
		    		if (kc >= 112 && kc <= 115) {
		    			LootItem newItem = new LootItem();
		    			switch (kc) {
			    			case 112:
			    				newItem.setName("Armor");		// Helmets and vests
			    				break;
			    			case 113:
			    				newItem.setName("Med");			// Battery, kit, phoenix
			    				break;
			    			case 114:
			    				newItem.setName("Bag");	// Backpacks
			    				break;
			    			case 115:
			    				newItem.setName("Mod");	// All including sights and hops
			    				break;
		    			}
		    			Location l = currentLocation;
		    			newItem.setTimeFound(timerElapsedTime);
		    			newItem.setLocationName(l.getName());
		    			newItem.setLocationX(l.getX());
		    			newItem.setLocationY(l.getY());
		    			DecimalFormat df = new DecimalFormat("#######0.00");
		    			newItem.setDistanceFromStart(Double.valueOf(df.format(Math.hypot(Math.abs(l.getY() - dropStartY), Math.abs(l.getX() - dropStartX)))));
		    			newItem.setDistanceFromEnd(Double.valueOf(df.format(Math.hypot(Math.abs(l.getY() - dropEndY), Math.abs(l.getX() - dropEndX)))));
		    			lastLootedItem = newItem;
		    			lootManager.addItem(newItem);
		    			updateLootLog();
		    			addToEventLog("[loot]\tFound "+newItem.getName());
		    			SoundManager.tone(400, 25, 0.5);
		    			
		    		// Set quality of last added item
		    		} else if (kc >= 116 && kc <= 118) {
		    			if (lastLootedItem != null) {
			    			switch (kc) {
				    			case 116:
				    				lastLootedItem.setQuality("Blue");
				    				break;
				    			case 117:
				    				lastLootedItem.setQuality("Purple");
				    				break;
				    			case 118:
				    				lastLootedItem.setQuality("Gold");
				    				break;
			    			}
		    			}
		    			updateLootLog();
		    			addToEventLog("[loot]\tItem quality set to "+lastLootedItem.getQuality());
		    			SoundManager.tone(550, 25, 0.5);
		    			
		    		// Remove last added item
		    		} else if (kc == 119) {
		    			if (lastLootedItem != null) {
			    			addToEventLog("[loot]\tRemoved last item ("+lastLootedItem.getQuality()+" "+lastLootedItem.getName()+")");
		    				lootManager.getItems().remove(lastLootedItem);
		    				if (!lootManager.getItems().isEmpty()) {
		    					lastLootedItem = lootManager.getItems().get(lootManager.getItems().size()-1);
		    				} else {
		    					lastLootedItem = null;
		    				}
		    				updateLootLog();
		    				SoundManager.tone(100, 25, 0.8);
		    				SoundManager.tone(100, 25, 0.8);
		    			}
		    		
		    		// Start timer
		    		} else if (kc == 122) {
		    			startTimer();
		    			SoundManager.tone(200, 400, 0.7);
		    		// Stop timer
		    		} else if (kc == 123) {
		    			stopTimer();
		    			SoundManager.tone(450, 50, 0.5);
		    			SoundManager.tone(450, 50, 0.5);
		    		}
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
		    }
		});
	}
	
	// Retrieve the location name of a coordinate from the locationManager
	private Location getLocationFromCoord(int x, int y) {
		Image clrMap = new Image(display, "img/map_colored2.png");
        ImageData data = clrMap.getImageData();
        int pixelRaw = data.getPixel(x, y);
        RGB pixelColor = data.palette.getRGB(pixelRaw);
        HashSet<Location> locations = locationManager.getLocations();
        Location foundResult = null;
        for (Location l : locations) {
        	if (l.getColor().red == pixelColor.red && l.getColor().blue == pixelColor.green && l.getColor().green == pixelColor.blue) {
        		foundResult = l;
        		break;
        	}
        }
        clrMap.dispose();
        return foundResult;
	}

	// Handle travel (numpad directions)
	private Location travelDirection(int dir) {
        Location closestLocation = null;
        int startX = currentLocation.getX();
        int startY = currentLocation.getY();
		int curX = currentLocation.getX();
		int curY = currentLocation.getY();
		int dirX, dirY = 0;
		double radMult = 0.5;
		double distMult = 2;
        int closestLocationDistance = 999;
        HashSet<Location> testedLocations = new HashSet<Location>();
        HashSet<Location> workingSetLocations = new HashSet<Location>();
        for (Location l : locationManager.getLocations()) {
        	workingSetLocations.add(l);
        }
		for (int scanDist = 15; scanDist <= 30; scanDist+=5) {
			if (dir == 1 || dir == 4 || dir == 7) {
				dirX = -1;
			} else if (dir == 3 || dir == 6 || dir == 9) {
				dirX = 1;
			} else {
				dirX = 0;
			}
			if (dir == 7 || dir == 8 || dir == 9) {
				dirY = -1;
			} else if (dir == 1 || dir == 2 || dir == 3) {
				dirY = 1;
			} else {
				dirY = 0;
			}
			curX += scanDist * dirX;
			curY += scanDist * dirY;

			for (int scanRad = 5; scanRad <= 15; scanRad+=5) {
		        for (Location l : workingSetLocations) {
		        	if (l != currentLocation && !testedLocations.contains(l)) {
			        	if (l.getX() >= curX - scanRad && l.getX() <= curX + scanRad) {
			            	if (l.getY() >= curY - scanRad && l.getY() <= curY + scanRad) {
			            		double distanceToTestLocation =
			            				Math.abs(curY-l.getY()) + Math.abs(curX-l.getX()) +
			            				Math.abs(startY-l.getY()) + Math.abs(startX-l.getX()) +
			            				scanRad*radMult + scanDist*distMult;
			            		if (distanceToTestLocation < closestLocationDistance) {
			            			closestLocationDistance = (int) Math.floor(distanceToTestLocation);
			            			closestLocation = l;
			            		}
		            			testedLocations.add(l);
			            	}
			        	}
		        	}
		        }
		        for (Location l : testedLocations) {
		        	workingSetLocations.remove(l);
		        }
			}
		}
		return closestLocation;
	}
	
	private void startTimer() {
		if (txtTimer.getText().equals("0:00")) {
			timerElapsedTime = 0;
		}
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
        timer.scheduleAtFixedRate(new TickTimer(), 1000, 1000);
		addToEventLog("[time]\tStarted game timer");
		btnSaveSameSquad.setEnabled(false);
		btnSaveNewSquad.setEnabled(false);
		btnSaveAndQuit.setEnabled(false);
		btnDiscardAndQuit.setEnabled(false);
		btnDiscardAndRestart.setEnabled(false);
		btnTimerStart.setEnabled(false);
		btnTimerStop.setEnabled(true);
	}
	
	private void stopTimer() {
		timer.cancel();
		addToEventLog("[time]\tStopped game timer");
		btnSaveSameSquad.setEnabled(true);
		btnSaveNewSquad.setEnabled(true);
		btnSaveAndQuit.setEnabled(true);
		btnDiscardAndQuit.setEnabled(true);
		btnDiscardAndRestart.setEnabled(true);
		btnTimerStart.setEnabled(true);
		btnTimerStop.setEnabled(false);
	}
	
	private void updateLootLog() {
		tblLootLog.removeAll();
		for (LootItem li : lootManager.getItems()) {
			TableItem ti = new TableItem(tblLootLog, SWT.NONE);
			ti.setText(0, String.valueOf(longTimeToString(li.getTimeFound())));
			ti.setText(1, li.getLocationName());
			ti.setText(2, li.getName());
			ti.setText(3, li.getQuality());
			ti.setText(4, String.valueOf(li.getDistanceFromStart()));
			ti.setText(5, String.valueOf(li.getDistanceFromEnd()));
		}
		tblLootLog.setTopIndex(tblLootLog.getItemCount());
	}

	private void addToEventLog(String s) {
		if (txtEventLog.getText().isEmpty()) {
			txtEventLog.setText("--:--\t"+s);
		} else {
			txtEventLog.setText(txtEventLog.getText() + "\r\n" + txtTimer.getText() + "\t" + s);
		}
		txtEventLog.setTopIndex(txtEventLog.getText().length());
	}
	
	private void finalizeSession() {
		// Save session loot
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
		LocalDateTime now = LocalDateTime.now();
		String filename = dtf.format(now) + "_" + startTime;
		lootManager.saveLootSet("out/loot/"+filename+".loot");
		
		// Append to master loot
		File master = new File("out/loot/MASTER.loot");
		if (master.exists()) {
			LootManager mlm = new LootManager();
			mlm.loadLootSet("out/loot/MASTER.loot");
			ArrayList<LootItem> mlist = mlm.getItems();
			for (LootItem li : lootManager.getItems()) {
				mlist.add(li);
			}
			mlm.saveLootSet("out/loot/MASTER.loot");
		} else {
			lootManager.saveLootSet("out/loot/MASTER.loot");
		}
		
		// Save Match Data
		Match m = new Match();
		dtf = DateTimeFormatter.ofPattern("EEE");
		m.setDay(dtf.format(now));
		dtf = DateTimeFormatter.ofPattern("yyMMddHHmm");
		m.setDate(Integer.valueOf(dtf.format(now)));
		m.setShipStartX(dropStartX);
		m.setShipStartY(dropStartY);
		m.setShipEndX(dropEndX);
		m.setShipEndY(dropEndY);
		m.setDropLocation(dropLocation.getName());
		int numItems = 0;
		int numArmor = 0;
		int numMed = 0;
		int numBag = 0;
		int numMod = 0;
		int numBlue = 0;
		int numPurple = 0;
		int numGold = 0;
		double avgDistFromStartAll = 0;
		double avgDistFromEndAll = 0;
		double avgDistFromStartBlue = 0;
		double avgDistFromEndBlue = 0;
		double avgDistFromStartPurple = 0;
		double avgDistFromEndPurple = 0;
		double avgDistFromStartGold = 0;
		double avgDistFromEndGold = 0;
		for (LootItem li : lootManager.getItems()) {
			numItems += 1;
			if (li.getName().equals("Armor")) {
				numArmor += 1;
			} else if (li.getName().equals("Med")) {
				numMed += 1;
			} else if (li.getName().equals("Bag")) {
				numBag += 1;
			} else if (li.getName().equals("Mod")) {
				numMod += 1;
			}
			avgDistFromStartAll += li.getDistanceFromStart();
			avgDistFromEndAll += li.getDistanceFromEnd();
			if (li.getQuality().equals("Blue")) {
				avgDistFromStartBlue += li.getDistanceFromStart();
				avgDistFromEndBlue += li.getDistanceFromEnd();
				numBlue += 1;
			} else if (li.getQuality().equals("Purple")) {
				avgDistFromStartPurple += li.getDistanceFromStart();
				avgDistFromEndPurple += li.getDistanceFromEnd();
				numPurple += 1;
			} else if (li.getQuality().equals("Gold")) {
				avgDistFromStartGold += li.getDistanceFromStart();
				avgDistFromEndGold += li.getDistanceFromEnd();
				numGold += 1;
			}
		}
		avgDistFromStartAll = avgDistFromStartAll / numItems;
		avgDistFromEndAll = avgDistFromEndAll / numItems;
		avgDistFromStartBlue = avgDistFromStartBlue / numBlue;
		avgDistFromEndBlue = avgDistFromEndBlue / numBlue;
		avgDistFromStartPurple = avgDistFromStartPurple / numPurple;
		avgDistFromEndPurple = avgDistFromEndPurple / numPurple;
		avgDistFromStartGold = avgDistFromStartGold / numGold;
		avgDistFromEndGold = avgDistFromEndGold / numGold;
		m.setNumItems(numItems);
		m.setNumArmor(numArmor);
		m.setNumMed(numMed);
		m.setNumBag(numBag);
		m.setNumMod(numMod);
		m.setNumBlue(numBlue);
		m.setNumPurple(numPurple);
		m.setNumGold(numGold);
		m.setAvgDistFromStartAll(avgDistFromStartAll);
		m.setAvgDistFromEndAll(avgDistFromEndAll);
		m.setAvgDistFromStartBlue(avgDistFromStartBlue);
		m.setAvgDistFromEndBlue(avgDistFromEndBlue);
		m.setAvgDistFromStartPurple(avgDistFromStartPurple);
		m.setAvgDistFromEndPurple(avgDistFromEndPurple);
		m.setAvgDistFromStartGold(avgDistFromStartGold);
		m.setAvgDistFromEndGold(avgDistFromEndGold);
		m.setLvlPlayer1(Integer.valueOf(txtPlayer1.getText()));
		m.setLvlPlayer2(Integer.valueOf(txtPlayer2.getText()));
		m.setLvlPlayer3(Integer.valueOf(txtPlayer3.getText()));
		m.setOwnKillsLast(Integer.valueOf(txtOwnKillsLastGame.getText()));
		m.setSqdKillsLast(Integer.valueOf(txtSquadKillsLastGame.getText()));
		m.setMatchLength(timerElapsedTime);
		m.setLastMatchLength(txtLengthLastGame.getText());
		m.setLootItems(lootManager.getItems());
		
		MatchManager mm = new MatchManager();
		mm.addMatch(m);
		dtf = DateTimeFormatter.ofPattern("yyMMdd");
		filename = dtf.format(now) + "_" + startTime;
		mm.saveMatchSet("out/match/"+filename+".match");
		
		// Append to master match list
		master = new File("out/match/MASTER.match");
		if (master.exists()) {
			MatchManager matchMasterManager = new MatchManager();
			matchMasterManager.loadMatchSet("out/match/MASTER.match");
			ArrayList<Match> mlist = matchMasterManager.getMatches();
			for (Match ma : mm.getMatches()) {
				mlist.add(ma);
			}
			matchMasterManager.saveMatchSet("out/match/MASTER.match");
		} else {
			mm.saveMatchSet("out/match/MASTER.match");
		}
		
		// Reset the local lootManager
		lootManager = new LootManager();
	}
	
	private String longTimeToString(long t) {
    	String mins = String.valueOf((t / (1000 * 60)) % 60);
    	String secs = String.format("%02d", (int) (t / 1000) % 60);
    	return mins + ":" + secs;
	}
	
	private void ttsSpeak(String s) {
    	try {
    		keyListener.setActive(false);
			SoundManager.tone(100, 80, 0.7);
		    long curTime = System.currentTimeMillis();
		    if(curTime >= (lastVoiceTime + limitSpeechSeconds)) {
		    	lastVoiceTime = curTime;
				Voice voice = voiceManager.getVoice("kevin16");
		    	voice.setRate(190);
				voice.allocate();
				voice.speak(s);
		    }
			SoundManager.tone(200, 80, 0.7);
    		keyListener.setActive(true);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	class TickTimer extends TimerTask {
		@Override
    	public void run() {
    		Display.getDefault().asyncExec(new Runnable() {
    			public void run() {
    	        	timerElapsedTime += 1000;
    	        	String t = longTimeToString(timerElapsedTime);
    	        	txtTimer.setText(t);
    	    		txtLengthThisGame.setText(t);
    			}
    		});
		}
    }
}