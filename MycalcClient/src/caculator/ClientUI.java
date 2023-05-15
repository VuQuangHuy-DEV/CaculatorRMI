package caculator;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.rmi.*;

public class ClientUI extends JFrame implements ActionListener {
	private JTextField txtA, txtB, txtKQ;
	private JButton btnClear, btnExit;
	private JButton btnCalcAdd, btnCalcSub, btnCalcMul, btnCalcDev;

	public ClientUI() {
		JPanel pCen = new JPanel(new GridLayout(4, 1));
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		pCen.add(p1);
		pCen.add(p2);
		pCen.add(p3);
		pCen.add(p4);
		getContentPane().add(pCen);
//==========================================
		p1.add(new JLabel("Nhap so a"));
		p1.add(txtA = new JTextField(15));
		p2.add(new JLabel("Nhap so b"));
		p2.add(txtB = new JTextField(15));
		p3.add(new JLabel("Ket qua :"));
		p3.add(txtKQ = new JTextField(15));
		txtKQ.setEditable(false);
		p4.add(btnCalcAdd = new JButton(" + "));
		btnCalcAdd.addActionListener(this);
		p4.add(btnCalcSub = new JButton(" – "));
		btnCalcSub.addActionListener(this);
		p4.add(btnCalcMul = new JButton(" * "));
		btnCalcMul.addActionListener(this);
		p4.add(btnCalcDev = new JButton(" / "));
		btnCalcDev.addActionListener(this);

		p4.add(btnClear = new JButton("Clear"));
		btnClear.addActionListener(this);
		p4.add(btnExit = new JButton("Exit"));
		btnExit.addActionListener(this);
		setResizable(false);
		setSize(350, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(300, 200);
	}

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnExit)) {
			System.exit(1);
		} else {
			if (txtA.getText().trim().equals("") || txtB.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "Text Fields cannot be null", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (o.equals(btnCalcAdd)) {
				try {
					CalculatorInterface obj = null;
					obj = (CalculatorInterface) Naming.lookup("//localhost/calc");
					int a = Integer.parseInt(txtA.getText());
					int b = Integer.parseInt(txtB.getText());
					long x = obj.Add(a, b);
					txtKQ.setText("" + x);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (o.equals(btnCalcSub)) {
				try {
					CalculatorInterface obj = null;
					obj = (CalculatorInterface) Naming.lookup("//localhost/calc");
					int a = Integer.parseInt(txtA.getText());
					int b = Integer.parseInt(txtB.getText());
					long x = obj.Sub(a, b);
					txtKQ.setText("" + x);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (o.equals(btnCalcMul)) {
				try {
					CalculatorInterface obj = null;
					obj = (CalculatorInterface) Naming.lookup("//localhost/calc");
					int a = Integer.parseInt(txtA.getText());
					int b = Integer.parseInt(txtB.getText());
					long x = obj.Mul(a, b);
					txtKQ.setText("" + x);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (o.equals(btnCalcDev)) {
				try {
					CalculatorInterface obj = null;
					obj = (CalculatorInterface) Naming.lookup("//localhost/calc");
					int a = Integer.parseInt(txtA.getText());
					int b = Integer.parseInt(txtB.getText());
					double x = obj.Div(a, b);
					txtKQ.setText("" + x);
				} catch (ArithmeticException aex) {
					txtKQ.setText("Divide by zero");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (o.equals(btnClear)) {
				txtA.setText("");
				txtB.setText("");
				txtKQ.setText("");
			}
		}
	}

	public static void main(String[] args) {
		System.setProperty("java.security.policy", "client.policy");
		ClientUI cc = new ClientUI();
		cc.setVisible(true);
	}
}
