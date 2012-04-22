/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\techfun\\workspace_samplesrc\\ServiceMondai2\\src\\example\\android\\servicemondai2\\ServiceAIDL.aidl
 */
package example.android.servicemondai2;
public interface ServiceAIDL extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements example.android.servicemondai2.ServiceAIDL
{
private static final java.lang.String DESCRIPTOR = "example.android.servicemondai2.ServiceAIDL";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an example.android.servicemondai2.ServiceAIDL interface,
 * generating a proxy if needed.
 */
public static example.android.servicemondai2.ServiceAIDL asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof example.android.servicemondai2.ServiceAIDL))) {
return ((example.android.servicemondai2.ServiceAIDL)iin);
}
return new example.android.servicemondai2.ServiceAIDL.Stub.Proxy(obj);
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
case TRANSACTION_startService:
{
data.enforceInterface(DESCRIPTOR);
example.android.servicemondai2.ActivityAIDL _arg0;
_arg0 = example.android.servicemondai2.ActivityAIDL.Stub.asInterface(data.readStrongBinder());
this.startService(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_stopService:
{
data.enforceInterface(DESCRIPTOR);
this.stopService();
reply.writeNoException();
return true;
}
case TRANSACTION_resetService:
{
data.enforceInterface(DESCRIPTOR);
example.android.servicemondai2.ActivityAIDL _arg0;
_arg0 = example.android.servicemondai2.ActivityAIDL.Stub.asInterface(data.readStrongBinder());
this.resetService(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements example.android.servicemondai2.ServiceAIDL
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
public void startService(example.android.servicemondai2.ActivityAIDL callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_startService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void stopService() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void resetService(example.android.servicemondai2.ActivityAIDL callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_resetService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_startService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_stopService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_resetService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void startService(example.android.servicemondai2.ActivityAIDL callback) throws android.os.RemoteException;
public void stopService() throws android.os.RemoteException;
public void resetService(example.android.servicemondai2.ActivityAIDL callback) throws android.os.RemoteException;
}
