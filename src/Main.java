/* Class: CISC 3130
 * Section: TY2
 * EmplId:
 * Name: Jonathan Scarpelli
 */

import java.io.*;
import java.util.*;

/* Queue implementation of playlist history */
class SongHistoryList {
  private String arr[] = new String[825];
  private int front;
  private int rear;
  private int capacity;
  private int count;

  /* Constructor to initialize queue */
  SongHistoryList() {
    capacity = 825;
    front = 0;
    rear = -1;
    count = 0;
  }

  /* Removes front element from the queue */
  public void dequeue() {
    /* Checks for queue underflow */
    if (isEmpty()) {
      System.out.println("Error: Underflow");
      System.exit(1);
    }

    front = (front + 1) % capacity;
    count--;
  }

  /* Adds an item to the queue */
public void addSong(String item) {
  /* Checks for queue overflow */
  if (isFull()) {
    System.out.println("Error: Overflow");
    System.exit(1);
  }

  rear = (rear + 1) % capacity;
  arr[rear] = item;
  count++;
}

  /* Returns front element in the queue */
  public String lastListened() {
    if (isEmpty()) {
      System.out.println("Error: Underflow");
      System.exit(1);
    }
    return arr[rear];
}

  /* Returns the size of the queue */
  public int size() {
    return count;
  }

  /* Checks if the queue is empty or not */
  public Boolean isEmpty() {
    return (size() == 0);
  }

  /* Checks if the queue is full */
  public Boolean isFull() {
    return (size() == capacity);
  }
}

/* The Playlist implementation */
class Playlist {
  public Song head;
  SongHistoryList history = new SongHistoryList();
  /* A node represents a song */
  class Song {
    public String data;
    public Song next;
    public Song(String artist) {
      data = artist;
      next = null;
    }
  }

  /* Create a Song */
  Song newSong(String data) {
    Song s = new Song(data);
    return s;
}

  /* Inserts song name by ascending order */
  void sortedInsert(Song newSong) {
    Song current;

    /* Special case for head song */
    if (head == null || head.data.compareToIgnoreCase(newSong.data) >= 0) {
      newSong.next = head;
      head = newSong;
    } else {
      /* Locates the song before point of insertion */
      current = head;
      while (current.next != null &&
             current.next.data.compareToIgnoreCase(newSong.data) <= 0)
        current = current.next;

        newSong.next = current.next;
        current.next = newSong;
      }
  }

  /* Listens to a song */
  public Song listenToSong() {
    Song current = head;
    if (current == null) {
      return null;
    }

    /* Moves the head pointer to the next song */
    Song temp = head;
    head = head.next;
    temp = temp.next;

    /* Adds song to the playlist history */
    history.addSong(temp.data);

    return temp;
  }

  /* Prints the linked list or playlist */
  void printList() {
    Song temp = head;
    while (temp != null) {
      System.out.print(temp.data + ", ");
      temp = temp.next;
    }
  }
}

/* Storing one week of data in a queue */
class MyQueue {
  List<String> values = new ArrayList<String>();

  public MyQueue(String filename) {
    String csvFile = filename;
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";

    try {
      br = new BufferedReader(new FileReader(csvFile));
      while ((line = br.readLine()) != null) {
        // Uses comma as a delimiter
        String[] country = line.split(cvsSplitBy);

        /* Edge case handling */
        if (country[1].replace("\"", "").trim().equals("")) {
        } else if (country[1].replace("\"", "").trim().equals("10")) {
          values.add("10,000 Hours (with Justin Bieber)");
        } else if (country[1].replace("\"", "").trim().equals("next")) {
          values.add("Ariana Grande");
        } else if (!country[1].replace("\"", "").trim().equals("Artist")) {
          values.add(country[1].replace("\"", "").trim());
        }
      }
    } catch (FileNotFoundException error) {
      error.printStackTrace();
    } catch (IOException error) {
      error.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException error) {
          error.printStackTrace();
        }
      }
    }
  }
}

/* Test driver class */
public class Main {

  public static void main(String[] args) {
    String[] myFiles = {"../data/regional-global-weekly-2020-01-03--2020-01-10.csv",
      "../data/regional-global-weekly-2020-01-10--2020-01-17.csv",
      "../data/regional-global-weekly-2020-01-17--2020-01-24.csv",
      "../data/regional-global-weekly-2020-01-24--2020-01-31.csv"};

    ArrayList<String> allTheWeeks = new ArrayList<>();

    for (int i=0; i < myFiles.length; i++) {
      MyQueue dataExtract = new MyQueue(myFiles[i]);
      allTheWeeks.addAll(dataExtract.values);
    }

    /* Test playlist */
    Playlist list = new Playlist();
    allTheWeeks.forEach(name -> list.sortedInsert(list.newSong(name)));
    list.printList();
    list.listenToSong();
    System.out.println("Last Played: " + list.history.lastListened());
    list.listenToSong();
    list.listenToSong();
    list.listenToSong();
    list.listenToSong();
    System.out.println("Last Played: " + list.history.lastListened());
    list.listenToSong();
    list.listenToSong();
    list.listenToSong();
    list.listenToSong();
    System.out.println("Last Played: " + list.history.lastListened());
  }
}
