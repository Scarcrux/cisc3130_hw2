/* Class: CISC 3130
 * Section: MY9, TY2, TY9
 * EmplId:
 * Name: Jonathan Scarpelli
 */

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import Playlist.Song;

class SongHistoryList
{
	private String arr[] = new String[825];		// array to store queue elements
	private int front;		// front points to front element in the queue (if any)
	private int rear;		// rear points to last element in the queue
	private int capacity;	// maximum capacity of the queue
	private int count;		// current size of the queue

	// Constructor to initialize queue
	SongHistoryList()
	{
		//arr = new String[];
		capacity = 825;
		front = 0;
		rear = -1;
		count = 0;
	}

	// Utility function to remove front element from the queue
	public void dequeue()
	{
		// check for queue underflow
		if (isEmpty())
		{
			System.out.println("UnderFlow\nProgram Terminated");
			System.exit(1);
		}

		System.out.println("Removing " + arr[front]);

		front = (front + 1) % capacity;
		count--;
	}

	// Utility function to add an item to the queue
	public void addSong(String item)
	{
		// check for queue overflow
		if (isFull())
		{
			System.out.println("OverFlow\nProgram Terminated");
			System.exit(1);
		}

		System.out.println("\nInserting " + item);

		rear = (rear + 1) % capacity;
		arr[rear] = item;
		count++;
	}

	// Utility function to return front element in the queue
	public String lastListened()
	{
		if (isEmpty())
		{
			System.out.println("UnderFlow\nProgram Terminated");
			System.exit(1);
		}
		return arr[rear];
	}

	// Utility function to return the size of the queue
	public int size()
	{
		return count;
	}

	// Utility function to check if the queue is empty or not
	public Boolean isEmpty()
	{
		return (size() == 0);
	}

	// Utility function to check if the queue is empty or not
	public Boolean isFull()
	{
		return (size() == capacity);
	}
}

/* The Playlist implementation */
class Playlist {
    public Song head;
    SongHistoryList history = new SongHistoryList();
   Playlist() {

   }
/* A Song represents an artist */
class Song {
    public String data;
    public Song next;
    public Song(String artist) {
        data = artist;
        next = null;
    }
}

    void sortedInsert(Song new_Song)
    {
         Song current;

         /* Special case for head Song */
         if (head == null || head.data.compareToIgnoreCase(new_Song.data) >= 0)
         {
            new_Song.next = head;
            head = new_Song;
         }
         else {

            /* Locate the Song before point of insertion. */
            current = head;

            while (current.next != null &&
                   current.next.data.compareToIgnoreCase(new_Song.data) <= 0)
                  current = current.next;

            new_Song.next = current.next;
            current.next = new_Song;
            System.out.println("insert check: " + new_Song.data);
         }
     }

                  /*Utility functions*/

    /* Function to create a Song */
    Song newSong(String data)
    {
       Song x = new Song(data);
       return x;
    }

    public Song listenToSong(){
        Song current = head;
          if (current == null)
              return null;
          // Move the head pointer to the next Song
          Song temp = head;
          head = head.next;

          //while (temp != null)
          //{
             System.out.print("CHECK: " + temp.data+" ");
             temp = temp.next;
          //}
          //System.out.println("Check song:" + temp.data);
          history.addSong(temp.data);
          return temp;
      }
     /* Function to print linked list */
     void printList()
     {
         Song temp = head;
         while (temp != null)
         {
            System.out.print(temp.data+" ");
            temp = temp.next;
         }
     }
 }

/* The SongHistoryList implementation */


/* Storing one week of data in a queue */
class MyQueue {
    List<String> values = new ArrayList<String>();

    public int getValuesSize() {
        return values.size();
    }
    //convertToQueue
    // constructor creates a linked list that stores songs from one text file
    public MyQueue(String filename){
        String csvFile = filename;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

      try {

          br = new BufferedReader(new FileReader(csvFile));
          while ((line = br.readLine()) != null) {

              // use comma as separator
              String[] country = line.split(cvsSplitBy);
              //System.out.println(country[1]);
              if (country[1].replace("\"", "").trim().equals(" ")) {
                  //values.add("10000 Hours (with Justin Bieber)");
              } else if (country[1].replace("\"", "").trim().equals("next")) {
                  values.add("Ariana Grande");
              }else
              if (!country[1].replace("\"", "").trim().equals("Artist")) {
             values.add(country[1].replace("\"", "").trim());
              }
              }

              //for (int i = 0; i < values.size(); i++) {
               //   System.out.println("Artist: " + values.get(i));
              //  }
               // GFG count = new GFG();
                //count.countFrequencies(values);
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } finally {


          if (br != null) {
              try {
                  br.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
    }
  }



/* A Song represents an artist */
public class Main {

    public static void main(String[] args) {

        String[] myFiles = {"../data/regional-global-weekly-2020-01-03--2020-01-10.csv",
        "../data/regional-global-weekly-2020-01-10--2020-01-17.csv",
        "../data/regional-global-weekly-2020-01-17--2020-01-24.csv",
        "../data/regional-global-weekly-2020-01-24--2020-01-31.csv"};

        ArrayList<String> allTheWeeks = new ArrayList<>();

        for (int i=0; i < myFiles.length; i++){
          MyQueue dataExtract = new MyQueue(myFiles[i]);
          //for (int j = 0; j < dataExtract.getValuesSize(); j++) {
            allTheWeeks.addAll(dataExtract.values);
          //}
          //dataExtract.convertToQueue();
        }
       // System.out.println(allTheWeeks);
        Playlist llist = new Playlist();

        allTheWeeks.forEach(name -> llist.sortedInsert(llist.newSong(name)));
      //  for(String s : allTheWeeks) {
            //System.out.println(s);

       // }
        System.out.println("Created Linked List");
        //llist.printList();

        llist.listenToSong();
        llist.listenToSong();
        llist.listenToSong();
        llist.listenToSong();
        llist.listenToSong();
        //System.out.println("test 2: " + llist.head.data);

        //SongHistoryList test = new SongHistoryList();
        //test.addSong("Test Song");
        //test.addSong("New Song");
        System.out.println("last listened: " + llist.history.lastListened());
    }

}
