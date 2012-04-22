package example.android.servicemondai2;

import example.android.servicemondai2.ActivityAIDL;

interface ServiceAIDL {
    void startService(ActivityAIDL callback);
    void stopService();
    void resetService(ActivityAIDL callback); 
	
}