package application;

public final class Error {
	private static String error = "";
	
	public static String get(){
		return error;
	}
	
	public static void set(String msg) {
		error = msg;
	}
}
