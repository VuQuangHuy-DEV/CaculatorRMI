package caculator;

import java.rmi.*;
import java.rmi.server.*;

public class CalculatorImpl extends UnicastRemoteObject implements CalculatorInterface {
	private ServerUI ui;

	public CalculatorImpl() throws RemoteException {
	}

	public CalculatorImpl(ServerUI ui) throws RemoteException {
		this.ui = ui;
	}

	public long Add(long a, long b) throws RemoteException {
		ui.addItem("Call from client: " + a + "+" + b + "=" + (a + b)*2);
		return (a + b)*2;
	}

	public long Sub(long a, long b) throws RemoteException {
		ui.addItem("Call from client: " + a + "-" + b + "=" + (a - b));
		return a - b;
	}

	public long Mul(long a, long b) throws RemoteException {
		ui.addItem("Call from client: " + a + "*" + b + "=" + (a * b));
		return a * b;
	}

	public float Div(float a, float b) throws RemoteException {
		if (Math.abs(b - 0) < 0.000001) {
			ui.addItem("Call from client: Divide by zero");
			throw new ArithmeticException();
		}
		ui.addItem("Call from client: " + a + "/" + b + "=" + (a / b));
		return a / b;
	}
}