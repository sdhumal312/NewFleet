package org.fleetopgroup.constant;

public class SinggoltonTest {


	public static void main(String[] args) {
		  //create threads having  Servlet class obj as  data
		   TicketBookingServlet servlet=new TicketBookingServlet();
		   Thread t1=new Thread(servlet);
		   Thread t2=new Thread(servlet);
		   Thread t3=new Thread(servlet);
		   t1.start();
		   t2.start();
		   t3.start();
		
		}//main

}
