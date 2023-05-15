package caculator;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalculatorInterface extends Remote {
long Add(long a,long b)throws RemoteException;
long Sub(long a,long b)throws RemoteException;
long Mul(long a,long b)throws RemoteException;
float Div(float a,float b)throws RemoteException;
}