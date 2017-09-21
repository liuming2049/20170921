
public class Main2 {
	
	private static  Thread t;

	public static void main(String[] args) throws InterruptedException{
		print();
		print();

		
	}
	public static synchronized void print() throws InterruptedException{
		
		System.out.print(Thread.currentThread().getName()+"\n");
		
		String a = "a";
		a.intern();
		t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				print2();
				
			}
		});
		t.start();
		Thread.sleep(9000);
		
		System.out.print(Thread.currentThread().getName()+"000\n");
	}
	
	public static synchronized void print2(){
		System.out.print(Thread.currentThread().getName()+"\n");
	}
}
