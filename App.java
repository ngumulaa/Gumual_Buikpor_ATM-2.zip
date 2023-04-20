package edu.guilford;

import java.util.ArrayList;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        // attributes
        String name = "John";
        int age = 21;
        double gpa = 3.5;
        boolean isMale = true;

        // constructors 
        Person p1 = new Person();
        Person p2 = new Person("John", 21, 3.5, true);
        Person p3 = new Person("Jane", 20, 3.8, false);

        Random rand = new Random();
        int randNum = rand.nextInt(100);

        // getters and setters
        // create method to set name to "John"
        p1.setName("John");
        p1.setAge(21);
        p1.setGpa(3.5);
        p1.setIsMale(true);

        //toString
        System.out.println(p1.toString());

        // create method to set name to "Jane"
        p2.setName("Jane");
        p2.setAge(20);
        p2.setGpa(3.8);
        p2.setIsMale(false);

        //toString
        System.out.println(p2.toString());

        // create method to set name to "Joe"
        p3.setName("Joe");
        p3.setAge(22);
        p3.setGpa(3.2);
        p3.setIsMale(true);

        //toString  
        System.out.println(p3.toString());

        //helper methods 
        // create method to return name
        System.out.println(p1.getName());
        System.out.println(p2.getName());
        System.out.println(p3.getName());

        //a single statement that instantiates one object with values for all attributes 
        Person p4 = new Person("John", 21, 3.5, true);

        //arraylist of store objects
        ArrayList<Person> people = new ArrayList<Person>();
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);

        //loop that instantiates at least five objects and adds them to the arraylist
        for (int i = 0; i < 5; i++) {
            people.add(new Person("John", 21, 3.5, true));
        }
        
        //loop that prints out the name of each object in the arraylist
        for (Person person : people) {
            System.out.println(person.getName());
        }
        
        // analysis of the objects in the arraylist
        int maleCount = 0;
        int femaleCount = 0;
        double totalGpa = 0;
        for (Person person : people) {
            if (person.getIsMale()) {
                maleCount++;
            } else {
                femaleCount++;
            }
            totalGpa += person.getGpa();
        }

    




       


        

       






        
    }

    
    }

