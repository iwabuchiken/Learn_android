/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\techfun\\workspace_samplesrc\\BindServiceSample\\src\\example\\android\\bindservicesample\\BindActivityAIDL.aidl
 */
package example.android.bindservicesample;
public interface BindActivityAIDL extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements example.android.bindservicesample.BindActivityAIDL
{
private static final java.lang.String DESCRIPTOR = "example.android.bindservicesample.BindActivityAIDL";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an example.android.bindservicesample.BindActivityAIDL interface,
 * generating a proxy if needed.
 */
public static example.android.bindservicesample.BindActivityAIDL asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof example.android.bindservicesample.BindActivityAIDL))) {
return ((example.android.bindservicesample.BindActivityAIDL)iin);
}
return new example.android.bindservicesample.BindActivityAIDL.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_displayTime:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.displayTime(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements example.android.bindservicesample.BindActivityAIDL
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public void displayTime(java.lang.String msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(msg);
mRemote.transact(Stub.TRANSACTION_displayTime, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_displayTime = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void displayTime(java.lang.String msg) throws android.os.RemoteException;
}
