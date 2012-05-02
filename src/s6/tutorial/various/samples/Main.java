/**
 * File: Main.java
 * Created at: 20120502_195242
 * Author: Iwabuchi Ken
 * Description:
 * 		1. Try use the snippets "Object[] actitvities...", "CharSequence"
 * Related: C:\WORKS\WORKSPACES_ANDROID\S6_Tutorial_Various_Samples\src\tut\various\samples\Dispatch.java
 */
package s6.tutorial.various.samples;

//import tut.various.samples.S6_2_Hello;
//import tut.various.samples.S6_SurfaceView;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		
		Object[] activities = {
				"S6_SurfaceView", 							S6_SurfaceView.class,
				"S6_2_Hello",									S6_2_Hello.class,
		};
		
		System.out.println("Hi.");
		
        // Prepare char sequence
        CharSequence[] list = new CharSequence[activities.length / 2];
        
        // Set chars to the list
        for (int i = 0; i < list.length; i++) {
			list[i] = (String)activities[i * 2];
		}//for (int i = 0; i < list.length; i++)
        
        // Show chars
        for (CharSequence title : list) {
        	System.out.println((String) title);
        	
		}
	}

}
