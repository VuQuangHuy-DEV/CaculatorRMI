package caculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.rmi.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public class ServerUI extends JFrame implements ActionListener {
	private DefaultListModel dlm;
	private JList lst;
	private JLabel lblMessage;
	private JButton btnStart, btnStop, btnExit;
	private Context ctx = null;

	public ServerUI() {
		super("Server RMI");
		lst = new JList(dlm = new DefaultListModel());
		this.add(new JScrollPane(lst), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 300);

		JPanel pB = new JPanel(new GridLayout(2, 1));
		JPanel pF = new JPanel();
		pB.add(pF);
		pF.add(btnStart = new JButton("Start"));
		pF.add(btnStop = new JButton("Stop"));
		btnStop.setEnabled(false);
		pF.add(btnExit = new JButton("Exit"));
		pB.add(lblMessage = new JLabel());

		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
		btnExit.addActionListener(this);

		this.add(pB, BorderLayout.SOUTH);
	}

	public void DislplayInfos(String msg) {
		lblMessage.setText(msg);
	}

	public void addItem(String item) {
		dlm.addElement(item);
	}

	public static void main(String[] args) {
		try {
			ServerUI ui = new ServerUI();
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			ui.DislplayInfos("server is stop…");
			ui.setVisible(true);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnStart)) {
			new Thread(new Runnable() {
				public void run() {
					try {

						CalculatorInterface calc = null;
						calc = new CalculatorImpl(ServerUI.this);
						ctx = new InitialContext();
						ctx.rebind("rmi:calc", calc);
						lblMessage.setText("Calculator server is running…");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}).start();

			btnStop.setEnabled(true);
			btnStart.setEnabled(false);
		} else if (o.equals(btnStop)) {
			try {
				ctx.unbind("rmi:calc");
				ctx = null;
				btnStop.setEnabled(false);
				btnStart.setEnabled(true);
				lblMessage.setText("Calculator server is stopped…");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (o.equals(btnExit)) {
			System.exit(1);
		}
	}
}