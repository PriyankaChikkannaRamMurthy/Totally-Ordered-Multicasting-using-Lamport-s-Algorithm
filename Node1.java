import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Node1 extends Thread
{
	   private Thread thread_pr1 = null;
	   private String thread_pr1_name = null;
	   int lamportClock=10;
	   DatagramPacket send_packet=null;
	   public static String[] eventId = new String[]{"a", "b", "c", "d", "e", "f", "g", "i","j"};
	   public static int no_events = eventId.length;
	   public static int[] port_list = new int[]{ 8080, 8081, 8082 };
	   public String val = "Node 1: ";
	   
	   public static void main(String args[])throws IOException
	   {
		   System.out.println("Type 'Y' to proceed : ");
		   Scanner in_buffer = new Scanner (System.in);
		   String value = in_buffer.nextLine();
		   if(value.equals("Y") || value.equals("y")){
		   System.out.println("Creating Sender thread for Node1... " );
		   Node1 p1_thread1 = new Node1("Node1_Sender_Thread");
		   p1_thread1.start();
		    
		   System.out.println("Creating Receiver thread for Node1... " );
		   Node1 p1_thread2 = new Node1("Node1_Receiver_Thread");
		   p1_thread2.start();
		   }
	   }
	   
	   Node1(String name) 
	   {
		  thread_pr1_name = name;
	   }
	   
	   public void start() 
	   {
	      System.out.println("Starting " +  thread_pr1_name );
	      if (thread_pr1 == null) 
	      {
	    	  thread_pr1 = new Thread (this, thread_pr1_name);
	    	  thread_pr1.start ();
	      }
	   }
	   
	   public void run()
	   {
	      System.out.println("Running the " +  thread_pr1_name );
	      try 
	      {
	         if(thread_pr1_name.equalsIgnoreCase("Node1_Sender_Thread"))
	         {
	        	fncall_send();
	            Thread.sleep(80);
	         }
	         else
	         {
	        	 fncall_recv();
	        	 Thread.sleep(80);
	         }
	      }catch (SocketException exp) {
				exp.printStackTrace();
		}  catch (InterruptedException exp) {
	         System.out.println(thread_pr1_name + " is been interrupted.");
	      } catch (IOException exp) {
			exp.printStackTrace();
		}finally {
	      System.out.println(thread_pr1_name + " exiting.");}
	   }
	   
	   void fncall_send() throws IOException, InterruptedException, SocketException {
		   System.out.println("starting to send the message in 8 secs...");
		   Thread.sleep(8000);
		   int i;
		   String s;
		   DatagramSocket client_socket = new DatagramSocket();
		   byte send_buff[] = new byte[1000];
		   		  
		   for(i=0;i<no_events;i++)
		   {
			   lamportClock= lamportClock + 1;
			   s=val.concat(eventId[i] + "," + lamportClock);
			   send_buff=s.getBytes();
			   int len=send_buff.length;
			   InetAddress host_name = InetAddress.getByName("localhost");
			   send_packet = new DatagramPacket(send_buff,len, host_name, port_list[0]);
			   client_socket.send(send_packet);
			   send_packet=null;
			   send_packet = new DatagramPacket(send_buff,len, host_name, port_list[1]);
			   client_socket.send(send_packet);
			   send_packet=null;
			   send_packet = new DatagramPacket(send_buff,len, host_name, port_list[2]);
			   client_socket.send(send_packet);
			   send_packet=null;
			   Thread.sleep(500);
			}
		   client_socket.close();
	   }
	   
	   void fncall_recv() throws IOException, SocketException {
		   int i=0;
		   int temp_time[] = new int[50];
		   int lamportTime[] = new int[50];
		   String data="";
		   String new_ar[] = new String[100];
		   DatagramSocket server_socket = new DatagramSocket(8080);
		   
		   for(i=0;i<27;i++) {
		   byte recv_buff[] = new byte[1000];
		   int recv_len = recv_buff.length;
		   DatagramPacket recv_packet = new DatagramPacket(recv_buff,recv_len);
		   server_socket.receive(recv_packet);
		   lamportClock= lamportClock + 1;
		   String recvl = new String(recv_packet.getData(),"UTF-8");
		   data = recvl.substring(0, recvl.indexOf(','));
		   //String timeStamp = recvl.substring(recvl.indexOf(',')+1);
		   char ch = recvl.charAt(recvl.indexOf(',')+1);
		   int temp = (int)(ch);
		   temp=temp-48;
		   new_ar[i]=data;
		   temp_time[i]=temp;
		   if(temp_time[i]>lamportClock)
		   {
			   lamportClock=temp_time[i]+1;
		   }
		   lamportTime[i]=lamportClock;
		   }
		   fncall_display(new_ar,lamportTime);
		   server_socket.close();
	   }
	   
	   void fncall_display(String arr[], int lamportTime[])
	   {
		   int i,j,temp,n=27;
		   String temp2="";
		   for(i=0;i<n;i++)
		   {
			   for(j=0;j<(n-i-1);j++)
			   {
				   if(lamportTime[j]>lamportTime[j+1])
				   {
					   temp=lamportTime[j];
					   lamportTime[j]=lamportTime[j+1];
					   lamportTime[j+1]=temp;
					  					   			   
					   temp2=arr[j];
					   arr[j]=arr[j+1];
					   arr[j+1]=temp2;
				   }
			   }
		   }
		   for(i=0;i<27;i++)
		   {
			   String tempo = arr[i]+"::"+lamportTime[i];
			   System.out.println(tempo);
		   }
	   }
	   
	   
	   
	    

}
