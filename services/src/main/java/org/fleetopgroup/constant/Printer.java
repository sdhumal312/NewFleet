package org.fleetopgroup.constant;

public class Printer extends CommonsUtil{

	private static volatile Printer INSTANCE; 
	
		
		//private constructor
		private  Printer() {
			  if(INSTANCE!=null) {
				  throw new RuntimeException("object is already created!!!");
			  }
		} 
	
	//static  factory method
		public  static   Printer  getInstance() {
			 if(INSTANCE==null) { //1st NULL check	
				synchronized(Printer.class) {
				 //singleton logic
				  if(INSTANCE==null)  //2nd NULL check
					  INSTANCE=new Printer();
				}
			}
				  return INSTANCE;
			}  //method 
	
			
	//To  Stop Cloning
	@Override
	public  Object clone()throws CloneNotSupportedException  {
		throw new CloneNotSupportedException("Cloning not allowed in Singleton Printer classs");
		//return InnerPrinter.INSTANCE;
	}
	
	//To Stop DeSerialization
	private static  final long serialVersionUID=5354353L;
	public  Object readResolve() {
		//return InnerPrinter.INSTANCE;
		return  INSTANCE;
		//throw  new  IllegalArgumentException("Derailziation is not allowed on singleton class");
	}
	
	
	
	//b.method
	public  void  print(String msg) {
		System.out.println(msg);
	} //print(-)
	

}
