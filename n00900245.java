import java.io.*;
import java.util.*;

class DataItem
{
   private String key;

   public DataItem(String s)
   {
      key = s;
   }//constructor

   public String getKey()
   {
      return key;
   }//get key

}//DataItem

class HashTableA
{
   private DataItem[] hashArray;
   private int arraySize;
   private DataItem nonItem;

   public HashTableA(int size)
   {
      arraySize = size;
      hashArray = new DataItem[arraySize];
      nonItem = new DataItem("-1");
   }//constructor
   
   public void displayTable()
   {
      String header = "Index  String    Probe-Length\n";
      String row = "%-7d%-20s%-3d\n";
      float probeSum = 0;
      int numItems = 0;
      
      System.out.println("\nTable A");
      System.out.print(header);
      for(int j=0; j < arraySize; j++)
      {
         if(hashArray[j] != null)
         {
            System.out.print(String.format(row, j, hashArray[j].getKey(), findProbeLength(hashArray[j].getKey())));
            probeSum += findProbeLength(hashArray[j].getKey());
            numItems++;
         }
      }
      System.out.println("Ave Probe Length: " + probeSum/numItems);
   }//displayTable
   
   public void compareString(String[] otherSet)
   {
      int failCount = 0;
      int successCount = 0;
      float failSum = 0;
      float successSum = 0;
   
      String row = "%-20s %-7s  %-7s %-3d\n";
      String header = "String              Success Failure Probe Length\n";
   
      System.out.println("\nTable A with file 2 -- search");
      System.out.print(header);
   
      for(int j=0; j < otherSet.length; j++) 
      {
         if(find(otherSet[j]) != null) //success
         {
            System.out.print(String.format(row, otherSet[j], "yes", " " , findProbeLength(otherSet[j])));
            successCount++;
            successSum += findProbeLength(otherSet[j]);
         }
         else //fail
         {
            System.out.print(String.format(row, otherSet[j], " ", "yes", findProbeLength(otherSet[j])));
            failCount++;
            failSum += findProbeLength(otherSet[j]);
         }
      
      }//for
      
      System.out.println("Ave Probe Length:    " + String.format("%.2f     ",successSum/successCount) + String.format("%.2f", failSum/failCount));
   }//compareString
   
   public void insert(DataItem item)
   {
      String key = item.getKey();
      int hashVal = hashFunc(key);
   
   //until empty cell or nonItem
      while(hashArray[hashVal] != null && hashArray[hashVal].getKey() != "-1")
      {
         ++hashVal;//go to next cell i.e linear probing
         hashVal %= arraySize;//wraparound if needed
      }//while
      hashArray[hashVal] = item;
   }//insert
   
   public void insertList(String list[])
   {
   
      for(int i = 0; i < list.length; i++)
      {
         DataItem item = new DataItem(list[i]);
         insert(item);
      }//for
   
   }//insertList
   
   public int hashFunc(String key)
   {
      int hashVal = 0;
      for(int j=0; j<key.length(); j++)
      {
         int letter = key.charAt(j) - 96; //get char code
         hashVal = (hashVal * 26 + letter) % arraySize; //mod
      }
      return hashVal;
   }//hashFunc
   
   
   public DataItem find(String key)
   {
      int hashVal = hashFunc(key);
   
      while(hashArray[hashVal] != null)
      {
         if(hashArray[hashVal].getKey().equals(key))
            return hashArray[hashVal];
            
         ++hashVal; //linear probe
         hashVal %= arraySize; //wraparound
      
      }//while
      return null; //couldn't find it
   }//find
   
   
   public int findProbeLength(String key)
   {
      int hashVal = hashFunc(key);
      int probeLength = 1;//initialize to 1
   
      while(hashArray[hashVal] != null)
      {
         if(hashArray[hashVal].getKey().equals(key))
            return probeLength;
         ++hashVal; //linear probe
         ++probeLength;//increase probeLength
         hashVal %= arraySize; //wraparound
      
      }//while
      return probeLength; //couldn't find it
   }//find
   
   public DataItem delete(String key)
   {
      int hashVal = hashFunc(key);
      int k = 0;
   
      while(hashArray[hashVal] != null)
      {
         if(hashArray[hashVal].getKey().equals(key))
         {
            DataItem temp = hashArray[hashVal];
            hashArray[hashVal] = nonItem;
            return temp;
         }
         //linear probe
         ++hashVal;
         //wraparound
         hashVal %= arraySize;
      }
      return null;
   }//delete
   
   public void seekAndDestroy(String[] list)
   {
      int failCount = 0;
      int successCount = 0;
      float failSum = 0;
      float successSum = 0;
   
      String row = "%-20s %-7s  %-7s %-3d\n";
      String header = "String              Success Failure Probe Length\n";
   
      System.out.println("\nTable A with file 3 -- deletions");
      System.out.print(header);
   
      for(int j=0; j < list.length; j++) 
      {
         int tempProbe = findProbeLength(list[j]);
         if(delete(list[j]) != null) //success deleted
         {
            System.out.print(String.format(row, list[j], "yes", " " , tempProbe));
            successCount++;
            successSum += tempProbe;
         }
         else //fail
         {
            System.out.print(String.format(row, list[j], " ", "yes", tempProbe));
            failCount++;
            failSum += tempProbe;
         }
      
      }//for
      
      System.out.println("Ave Probe Length:    " + String.format("%.2f     ",successSum/successCount) + String.format("%.2f", failSum/failCount));
   }//seekAndDestroy
   
   

}//typeA


class HashTableB
{
   private DataItem[] hashArray;
   private int arraySize;
   private DataItem nonItem;

   public HashTableB(int size)
   {
      arraySize = size;
      hashArray = new DataItem[arraySize];
      nonItem = new DataItem("-1");
   }//constructor
   
   public void displayTable()
   {
      String header = "Index  String    Probe-Length\n";
      String row = "%-7d%-20s%-3d\n";
      float probeSum = 0;
      int numItems = 0;
      
      System.out.println("\nTable B");
      System.out.print(header);
      for(int j=0; j < arraySize; j++)
      {
         if(hashArray[j] != null && hashArray[j].getKey() != "-1")
         {
            System.out.print(String.format(row, j, hashArray[j].getKey(), findProbeLength(hashArray[j].getKey())));
            probeSum += findProbeLength(hashArray[j].getKey());
            numItems++;
         }
      }
      System.out.println("Ave Probe Length: " + probeSum/numItems);
   }//displayTable
   
   public void compareString(String[] otherSet)
   {
      int failCount = 0;
      int successCount = 0;
      float failSum = 0;
      float successSum = 0;
   
      String row = "%-20s %-7s  %-7s %-3d\n";
      String header = "String              Success Failure Probe Length\n";
   
      System.out.println("\nTable B with file 2 -- search");
      System.out.print(header);
   
      for(int j=0; j < otherSet.length; j++) 
      {
         if(find(otherSet[j]) != null) //success
         {
            System.out.print(String.format(row, otherSet[j], "yes", " " , findProbeLength(otherSet[j])));
            successCount++;
            successSum += findProbeLength(otherSet[j]);
         }
         else //fail
         {
            System.out.print(String.format(row, otherSet[j], " ", "yes", findProbeLength(otherSet[j])));
            failCount++;
            failSum += findProbeLength(otherSet[j]);
         }
      
      }//for
      
      System.out.println("Ave Probe Length:    " + String.format("%.2f     ",successSum/successCount) + String.format("%.2f", failSum/failCount));
   }//compareString
   
   public void insert(DataItem item)
   {
      String key = item.getKey();
      int hashVal = hashFunc(key);
      int k = 0;
   
   //until empty cell or nonItem
      while(hashArray[hashVal] != null && hashArray[hashVal].getKey() != "-1")
      {
         //quadratic probe
         hashVal += (2*k+1);
         ++k;
         hashVal %= arraySize;//wraparound if needed
      }//while
      hashArray[hashVal] = item;
   }//insert
   
   public void insertList(String list[])
   {
   
      for(int i = 0; i < list.length; i++)
      {
         DataItem item = new DataItem(list[i]);
         insert(item);
      }//for
   
   }//insertList
   
   public int hashFunc(String key)
   {
      int hashVal = 0;
      for(int j=0; j<key.length(); j++)
      {
         int letter = key.charAt(j) - 96; //get char code
         hashVal = (hashVal * 26 + letter) % arraySize; //mod
      }
      return hashVal;
   }//hashFunc
   
   
   public DataItem find(String key)
   {
      int hashVal = hashFunc(key);
      int k = 0;
   
      while(hashArray[hashVal] != null)
      {
         if(hashArray[hashVal].getKey().equals(key))
            return hashArray[hashVal];
         //quadratic probe
         hashVal += (2*k+1);
         ++k;
         hashVal %= arraySize; //wraparound
      
      }//while
      return null; //couldn't find it
   }//find
   
   
   public int findProbeLength(String key)
   {
      int hashVal = hashFunc(key);
      int probeLength = 1;//initialize to 1
      int k = 0;
   
      while(hashArray[hashVal] != null)
      {
         if(hashArray[hashVal].getKey().equals(key))
            return probeLength;
         //quadratic probe
         hashVal += (2*k+1);
         ++k;
         ++probeLength;//increase probeLength
         hashVal %= arraySize; //wraparound
      
      }//while
      return probeLength; //couldn't find it
   }//find
   
   
   public DataItem delete(String key)
   {
      int hashVal = hashFunc(key);
      int k = 0;
   
      while(hashArray[hashVal] != null)
      {
         if(hashArray[hashVal].getKey().equals(key))
         {
            DataItem temp = hashArray[hashVal];
            hashArray[hashVal] = nonItem;
            return temp;
         }
         //quadratic probe
         hashVal += (2*k+1);
         ++k;
         //wraparound
         hashVal %= arraySize;
      }
      return null;
   }//delete
   
   public void seekAndDestroy(String[] list)
   {
      int failCount = 0;
      int successCount = 0;
      float failSum = 0;
      float successSum = 0;
   
      String row = "%-20s %-7s  %-7s %-3d\n";
      String header = "String              Success Failure Probe Length\n";
   
      System.out.println("\nTable B with file 3 -- deletions");
      System.out.print(header);
   
      for(int j=0; j < list.length; j++) 
      {
         int tempProbe = findProbeLength(list[j]);
         if(delete(list[j]) != null) //success deleted
         {
            System.out.print(String.format(row, list[j], "yes", " " , tempProbe));
            successCount++;
            successSum += tempProbe;
         }
         else //fail
         {
            System.out.print(String.format(row, list[j], " ", "yes", tempProbe));
            failCount++;
            failSum += tempProbe;
         }
      
      }//for
      
      System.out.println("Ave Probe Length:    " + String.format("%.2f     ",successSum/successCount) + String.format("%.2f", failSum/failCount));
   }//seekAndDestroy

}//typeB



class n00900245
{
   public static void main(String args[]) throws FileNotFoundException
   {
   
      String[] myLines = getLines(args[0]);//collect all the lines of the file
      
      int x = myLines.length;
      int arraySize = getPrime(x*2); //calculate prime arraysize of 2n where n is the number of lines in the file
      
      System.out.println("Array size: " + arraySize);
      //Create Hash Table A
      HashTableA A = new HashTableA(arraySize);
      A.insertList(myLines);
      A.displayTable();
      
      System.out.println();
      
      //Create Hash Table B
      HashTableB B = new HashTableB(arraySize);
      B.insertList(myLines);
      B.displayTable();
      
      //Get file two
      String[] fileTwo = getLines(args[1]);
      
      //Compare file two to A and B
      A.compareString(fileTwo);
      B.compareString(fileTwo);
      
      //Get file three
      String[] fileThree = getLines(args[2]);
      
      //Find delete strings that exist in both file 3 and in A/B
      A.seekAndDestroy(fileThree);
      B.seekAndDestroy(fileThree);
      
      //
      System.out.println("Value: " + hashFunc("aaaab", 11));
      
   }//main
   
   
   public static int getPrime(int min)
   {
      for(int j = min+1; true; j++)
      {
         if( isPrime(j) )
            return j;
      }//for
   }//getPrime
   
   public static boolean isPrime(int n)
   {
      for(int j = 2; (j*j <= n); j++)
      {
         if( n % j == 0)
            return false;
      }//for
      return true;
   }
   
   public static int hashFunc(String key, int arraySize)
   {
      int hashVal = 0;
      for(int j=0; j<key.length(); j++)
      {
         int letter = key.charAt(j); //get char code
         hashVal = (hashVal * 26 + letter) % arraySize; //mod
      }
      return hashVal;
   }//hashFunc
   
   public static String[] getLines(String fileName) throws FileNotFoundException
   {
      File fileOne = new File(fileName);
      Scanner s1 = new Scanner(fileOne);
      String[] testers;
   
   
      //count how many lines in file
      int lineCounter = 0;
      while(s1.hasNext())
      {
         String temp = s1.nextLine();
         lineCounter++;
      }//while
      
      testers = new String[lineCounter];//initialize the string array
      
      
      //get the strings
      Scanner s2 = new Scanner(fileOne);
      int i = 0;
      while(s2.hasNext())
      {
         testers[i] = s2.nextLine();
         i++;
      }//while
      
      return testers;
   
   }//getLines
   
}//n00900245