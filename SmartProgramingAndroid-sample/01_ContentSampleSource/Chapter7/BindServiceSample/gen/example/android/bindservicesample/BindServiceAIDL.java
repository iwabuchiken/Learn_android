/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\techfun\\workspace_samplesrc\\BindServiceSample\\src\\example\\android\\bindservicesample\\BindServiceAIDL.aidl
 */
package example.android.bindservicesample;
public interface BindServiceAIDL extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements example.android.bindservicesample.BindServiceAIDL
{
private static final java.lang.String DESCRIPTOR = "example.android.bindservicesample.BindServiceAIDL";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an example.android.bindservicesample.BindServiceAIDL interface,
 * generating a proxy if needed.
 */
public static example.android.bindservicesample.BindServiceAIDL asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof example.android.bindservicesample.BindServiceAIDL))) {
return ((example.android.bindservicesample.BindServiceAIDL)iin);
}
return new example.android.bindservicesample.BindServiceAIDL.Stub.Proxy(obj);
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
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
example.android.bindservicesample.BindActivityAIDL _arg0;
_arg0 = example.android.bindservicesample.BindActivityAIDL.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
example.android.bindservicesample.BindActivityAIDL _arg0;
_arg0 = example.android.bindservicesample.BindActivityAIDL.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements example.android.bindservicesample.BindServiceAIDL
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
public void registerCallback(example.android.bindservicesample.BindActivityAIDL callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void unregisterCallback(example.android.bindservicesample.BindActivityAIDL callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void registerCallback(example.android.bindservicesample.BindActivityAIDL callback) throws android.os.RemoteException;
public void unregisterCallback(example.android.bindservicesample.BindActivityAIDL callback) throws android.os.RemoteException;
}
