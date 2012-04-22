package example.android.bindservicesample;

import example.android.bindservicesample.BindActivityAIDL;

interface BindServiceAIDL {
    void registerCallback(BindActivityAIDL callback); 
    void unregisterCallback(BindActivityAIDL callback); 
	
}