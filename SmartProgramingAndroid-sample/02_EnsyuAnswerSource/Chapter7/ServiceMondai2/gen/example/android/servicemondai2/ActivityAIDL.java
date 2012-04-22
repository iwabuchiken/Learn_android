/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\techfun\\workspace_samplesrc\\ServiceMondai2\\src\\example\\android\\servicemondai2\\ActivityAIDL.aidl
 */
package example.android.servicemondai2;
public interface ActivityAIDL extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements example.android.servicemondai2.ActivityAIDL
{
private static final java.lang.String DESCRIPTOR = "example.android.servicemondai2.ActivityAIDL";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an example.android.servicemondai2.ActivityAIDL interface,
 * generating a proxy if needed.
 */
public static example.android.servicemondai2.ActivityAIDL asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof example.android.servicemondai2.ActivityAIDL))) {
return ((example.android.servicemondai2.ActivityAIDL)iin);
}
return new example.android.servicemondai2.ActivityAIDL.Stub.Proxy(obj);
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
private static class Proxy implements example.android.servicemondai2.ActivityAIDL
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
public void displayTime(java.lang.String time) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(time);
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
public void displayTime(java.lang.String time) throws android.os.RemoteException;
}
